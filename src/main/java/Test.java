import mail.Mail;
import org.springframework.web.multipart.MultipartFile;
import utils.Base64ToMultipartFile;
import utils.CryptoUtils;
import utils.DateTimeUtils;
import utils.FileUtils;
import wxMod.wxSendMsgController;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws Exception{
        //daysBetweenTest();
        //overdueAdventTest();
        //wxTest();
        //mailTest();
        //fileEncDecTest();
    }

    /**
     * DateTimeUtils.daysBetween 使用演示
     */
    public static void daysBetweenTest() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=sdf.parse("2022-09-26 23:59:59");
        Date d2=sdf.parse("2022-09-28 10:00:00");
        System.out.println(DateTimeUtils.daysBetween(d1,d2));
    }

    /**
     * DateTimeUtils.overdueAdvent 使用演示
     */
    public static void overdueAdventTest() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endTime = sdf.parse("2022-10-12 10:07:49"); //截至日期
        Date finishTime = null; //未完成

        DateTimeUtils.TimeOverdueAdventPojo timeOverdueAdventPojo = DateTimeUtils.overdueAdvent(endTime,finishTime);
        System.out.println(timeOverdueAdventPojo);
    }

    /**
     * wxMod 使用演示
     */
    public static void wxTest(){
        wxSendMsgController.sendMsgMod("标题", "内容");
    }

    /**
     * mail 使用演示
     */
    public static void mailTest() throws Exception {
        Mail.sendMail("发件地址","密钥","收件地址","标题", "内容");
    }

    /**
     * 文件加密/解密
     */
    public static void fileEncDecTest() throws Exception{

        String filePath = "D:\\file.txt";
        String encFilepath = "D:\\newFile.txt";
        //文件加密
        String base64 = FileUtils.fileToBase64(filePath);
        String enc = CryptoUtils.encryptAESPkcs7(base64);
        String suffixEnc = FileUtils.suffix(filePath.substring(filePath.lastIndexOf(".") + 1))+enc;
        MultipartFile encMf = Base64ToMultipartFile.base64ToMultipart(suffixEnc);
        assert encMf != null;
        FileUtils.saveFile(encFilepath,encMf);
        //文件解密
        String encBase64 = FileUtils.fileToBase64(encFilepath);
        String decBase64 = CryptoUtils.decryptAESPkcs7(encBase64.split(",")[1]);
        MultipartFile decMf = Base64ToMultipartFile.base64ToMultipart(decBase64);
        assert decMf != null;
        FileUtils.saveFile(encFilepath, decMf);
    }
}
