package utils;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class SM2Util {
    private static Logger logger = LoggerFactory.getLogger(SM2Util.class.getSimpleName());
    //ECC 曲线 SM2命名曲线
    private final static String NAME_CURVE = "sm2p256v1";
    private final static ECGenParameterSpec SM2_SPEC = new ECGenParameterSpec(NAME_CURVE);

    //椭圆曲线ECParameters ASN.1 结构
    private static X9ECParameters x9ECParameters = GMNamedCurves.getByName(NAME_CURVE);
    //椭圆曲线公钥或私钥的基本域参数。
    private static ECParameterSpec ecDomainParameters = new ECParameterSpec(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN());

    /**
     * 生成密钥
     */
    public static Map<String, String> generateKey() {
        Map<String, String> resultMap = new HashMap<>();
        KeyPair keyPair = initECKeyPair();
        if (null != keyPair) {
            //生成公钥
            PublicKey publicKey = keyPair.getPublic();
            if (publicKey instanceof BCECPublicKey) {
                byte[] publicKeyByte = ((BCECPublicKey) publicKey).getQ().getEncoded(false);
                logger.info("publicKey is : " + Hex.toHexString(publicKeyByte));
                logger.info("publicKey byte size : " + publicKeyByte.length);
                if (null != publicKeyByte && publicKeyByte.length > 0) {
                    resultMap.put("PublicKey", Hex.toHexString(publicKeyByte));
                }
            }
            //生成私钥
            PrivateKey privateKey = keyPair.getPrivate();
            if (privateKey instanceof BCECPrivateKey) {
                byte[] privateKeyByte = ((BCECPrivateKey) privateKey).getD().toByteArray();
                logger.info("privateKey is : " + Hex.toHexString(privateKeyByte));
                logger.info("privateKey byte size : " + privateKeyByte.length);
                if (null != privateKeyByte && privateKeyByte.length > 0)
                    resultMap.put("PrivateKey", Hex.toHexString(privateKeyByte));
            }
        }
        return resultMap;
    }

    /**
     * 初始化密钥
     *
     * @return
     */
    private static KeyPair initECKeyPair() {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            kpg.initialize(SM2_SPEC, new SecureRandom());
            return kpg.generateKeyPair();
        } catch (Exception e) {
            logger.error("生成密钥错误：" + e.getMessage());
            return null;
        }
    }

    /**
     * 内容加密
     *
     * @param publicKeyByte
     * @param data
     * @return
     */
    public static byte[] encrypt(byte[] publicKeyByte, byte[] data) {
        //通过公钥对象获取公钥的基本与参数
        BCECPublicKey publicKey = getECPublicKeyByPublicKeyHex(Hex.toHexString(publicKeyByte));
        ECParameterSpec ecParameterSpec = publicKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(), ecParameterSpec.getG(), ecParameterSpec.getN());
        ECPublicKeyParameters ecPublicKeyParameters = new ECPublicKeyParameters(publicKey.getQ(), ecDomainParameters);

        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(true, new ParametersWithRandom(ecPublicKeyParameters, new SecureRandom()));
        byte[] arrayOfBytes = null;
        try {
            arrayOfBytes = sm2Engine.processBlock(data, 0, data.length);
        } catch (Exception e) {
            logger.error("加密错误:" + e.getMessage());
        }
        return arrayOfBytes;
    }

    /**
     * 内容公钥加密 返回字符串
     * @param publicKeyHex
     * @param data
     * @return
     */
    public static String encrypt(String publicKeyHex, String data) {
        byte[] publicKeyByte = Hex.decode(publicKeyHex);
        byte[] dataByte = data.getBytes(StandardCharsets.UTF_8);
        byte[] encryptByte = encrypt(publicKeyByte, dataByte);
        return Hex.toHexString(encryptByte);
    }

    /**
     * 私钥解密
     *
     * @return
     */
    public static byte[] decrypt(byte[] privateKeyByte, byte[] data) {
        //通过私钥对象获取私钥的基本域参数。
        BCECPrivateKey privateKey = getBCECPrivateKeyByPrivateKeyHex(Hex.toHexString(privateKeyByte));
        ECParameterSpec ecParameterSpec = privateKey.getParameters();
        ECDomainParameters ecDomainParameters = new ECDomainParameters(ecParameterSpec.getCurve(),
                ecParameterSpec.getG(), ecParameterSpec.getN());

        //通过私钥值和私钥钥基本参数创建私钥参数对象
        ECPrivateKeyParameters ecPrivateKeyParameters = new ECPrivateKeyParameters(privateKey.getD(),
                ecDomainParameters);

        //通过解密模式创建解密引擎并初始化
        SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
        sm2Engine.init(false, ecPrivateKeyParameters);

        try {
            //通过解密引擎对密文字节串进行解密
            byte[] arrayOfBytes = sm2Engine.processBlock(data, 0, data.length);
            //将解密后的字节串转换为utf8字符编码的字符串（需要与明文加密时字符串转换成字节串所指定的字符编码保持一致）
            return arrayOfBytes;
        } catch (Exception e) {
            logger.error("解密错误：" + e.getMessage());
            return null;
        }

    }

    /**
     * 私钥解密 返回字符串
     * @param privateKeyHex
     * @param data
     * @return
     */
    public static String decrypt(String privateKeyHex, String data) {
        byte[] privateKeyByte = Hex.decode(privateKeyHex);
        byte[] dataByte = Hex.decode(data);
        byte[] decryptByte = decrypt(privateKeyByte, dataByte);
        return new String(decryptByte, StandardCharsets.UTF_8);
    }

    /**
     * 签名
     * @param privateKeyHex
     * @param message
     * @return
     */
    public static byte[] sign(String privateKeyHex, byte[] message) {
        try {
            Signature signature = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), new BouncyCastleProvider());
            signature.initSign(getBCECPrivateKeyByPrivateKeyHex(privateKeyHex));
            signature.update(message);
            byte[] bytes = signature.sign();
            return bytes;
        } catch (Exception e) {
            logger.error("签名错误：" + e.getMessage());
            return null;
        }

    }

    /**
     * 私钥生成签名 返回字符串
     */
    public static String sign(String privateKeyHex, String message) {
        byte[] messageByte = message.getBytes(StandardCharsets.UTF_8);
        byte[] signByte = sign(privateKeyHex, messageByte);
        return Hex.toHexString(signByte);
    }

    /**
     * 验签
     * @param publicKeyHex
     * @param signedMsg
     * @param originMsg
     * @return
     * @throws NoSuchAlgorithmException
     * @throws SignatureException
     */
    public static boolean verifySign(String publicKeyHex, byte[] signedMsg, byte[] originMsg) throws NoSuchAlgorithmException, SignatureException {
        try {
            Signature signature = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), new BouncyCastleProvider());
            signature.initVerify(getECPublicKeyByPublicKeyHex(publicKeyHex));
            signature.update(originMsg);
            return signature.verify(signedMsg);
        } catch (Exception e) {
            logger.error("验签错误：" + e.getMessage());
            return false;
        }

    }

    /**
     * 公钥验签 返回布尔值
     */
    public static boolean verifySign(String publicKeyHex, String signedMsg, String originMsg) throws NoSuchAlgorithmException, SignatureException {
        byte[] signedMsgByte = Hex.decode(signedMsg);
        byte[] originMsgByte = originMsg.getBytes(StandardCharsets.UTF_8);
        return verifySign(publicKeyHex, signedMsgByte, originMsgByte);
    }

    /**
     * 根据16进制内容生成公钥
     *
     * @param pubKeyHex 16进制公钥
     * @return
     */
    public static BCECPublicKey getECPublicKeyByPublicKeyHex(String pubKeyHex) {
        //截取64字节有效的SM2公钥（如果公钥首个字节为0x04）
        if (pubKeyHex.length() > 128) {
            pubKeyHex = pubKeyHex.substring(pubKeyHex.length() - 128);
        }
        //将公钥拆分为x,y分量（各32字节）
        String stringX = pubKeyHex.substring(0, 64);
        String stringY = pubKeyHex.substring(stringX.length());
        //将公钥x、y分量转换为BigInteger类型
        BigInteger x = new BigInteger(stringX, 16);
        BigInteger y = new BigInteger(stringY, 16);
        //通过公钥x、y分量创建椭圆曲线公钥规范
        ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(x9ECParameters.getCurve().createPoint(x, y), ecDomainParameters);
        //通过椭圆曲线公钥规范，创建出椭圆曲线公钥对象（可用于SM2加密及验签）
        return new BCECPublicKey("EC", ecPublicKeySpec, BouncyCastleProvider.CONFIGURATION);
    }


    /**
     * 私钥字符串转换为 BCECPrivateKey 私钥对象
     *
     * @param privateKeyHex: 32字节十六进制私钥字符串
     * @return BCECPrivateKey:SM2私钥对象
     */
    public static BCECPrivateKey getBCECPrivateKeyByPrivateKeyHex(String privateKeyHex) {
        //将十六进制私钥字符串转换为BigInteger对象
        BigInteger d = new BigInteger(privateKeyHex, 16);
        //通过私钥和私钥域参数集创建椭圆曲线私钥规范
        ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(d, ecDomainParameters);
        //通过椭圆曲线私钥规范，创建出椭圆曲线私钥对象（可用于SM2解密和签名）
        return new BCECPrivateKey("EC", ecPrivateKeySpec, BouncyCastleProvider.CONFIGURATION);
    }


    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException {
        // 生成密钥对
        Map<String, String> map = generateKey();

        //密钥对
        String signPublicKey = map.get("PublicKey");
        String signPrivateKey = map.get("PrivateKey");

        //加密内容
        String info = "Test123Test123Test1234567";

//        //加密
//        byte[] encodeInfoBytes =  encrypt(Hex.decode(signPublicKey),info.getBytes(StandardCharsets.UTF_8));
//        String encodeInfo = Hex.toHexString(encodeInfoBytes);
//        logger.info("加密内容"+encodeInfo);
//
//        // 解密
//        byte[] decodeInfoBytes = decrypt(Hex.decode(signPrivateKey),Hex.decode(encodeInfo));
//        String decodeInfo = null;
//        if (decodeInfoBytes != null) {
//            decodeInfo = new String(decodeInfoBytes);
//        }
//        logger.info("解密内容"+decodeInfo);
//
//        String signedInfo = Hex.toHexString(Objects.requireNonNull(sign(signPrivateKey, encodeInfo.getBytes(StandardCharsets.UTF_8))));
//        logger.info("签名：" + signedInfo);
//
//        boolean isSigned = verifySign(signPublicKey,Hex.decode(signedInfo),encodeInfo.getBytes(StandardCharsets.UTF_8));
//        logger.info("验签：" + isSigned);

        //加密
        String encodeInfo = encrypt(signPublicKey,info);
        logger.info("加密内容"+encodeInfo);

        //解密
        String decodeInfo = decrypt(signPrivateKey,encodeInfo);
        logger.info("解密内容"+decodeInfo);

        //签名
        String signedInfo = sign(signPrivateKey,encodeInfo);
        logger.info("签名：" + signedInfo);

        //验签
        boolean isSigned = verifySign(signPublicKey,signedInfo,encodeInfo);
        logger.info("验签：" + isSigned);

    }
}
