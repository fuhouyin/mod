package utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class CryptoUtils {

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        } else {
            Security.removeProvider("BC");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    /**
     * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同，也可以不同!
     */
    private static final String KEY = "UNo49jvVsvz5idwx";
    private static final String IV = "UNo49jvVsvz5idwx";
    private static Cipher cipher = null;
    private static final byte[] raw = KEY.getBytes(StandardCharsets.UTF_8);
    private static final SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    private static final IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度

    static {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     */
    public static String encryptAESPkcs7(String sourceStr) throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sourceStr.getBytes(StandardCharsets.UTF_8));
        return  Hex.encodeHexString(encrypted);

    }

    public static String decryptAESPkcs7(String sourceStr) throws Exception{
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(Hex.decodeHex(sourceStr));
        return new String(original, StandardCharsets.UTF_8);
    }

    // 文章参考 https://blog.csdn.net/haoyafei_/article/details/106826423
    public static void main(String[] args) throws Exception{
        String str = "3.141592653579";
        String enc = encryptAESPkcs7(str);
        String dec = decryptAESPkcs7(enc);
        System.out.println(enc);
        System.out.println(dec);
    }
}
