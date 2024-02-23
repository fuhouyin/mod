import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import utils.*;
import utils.page.PageHelper;
import utils.page.PageResp;
import utils.response.VoResult;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception{
        setObjectValueByField();
    }

    /**
     * 计算两个日期之间相差的天数
     * DateTimeUtils.daysBetween 使用演示
     */
    public static void daysBetweenTest() throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=sdf.parse("2022-09-26 23:59:59");
        Date d2=sdf.parse("2022-09-28 10:00:00");
        System.out.println(DateTimeUtils.daysBetween(d1,d2));
    }

    /**
     * 计算临期/超期天数
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
     * AES/CBC/PKCS7Padding 加解密
     * CryptoUtils 使用演示 文件加密/解密
     */
    public static void fileEncDecTest() throws Exception{

        String filePath = "D:\\file.txt";
        String encFilepath = "D:\\newFile.txt";
        //文件加密
        String base64 = FileUtils.fileToBase64(filePath);
        String enc = CryptoUtils.encryptAESPkcs7(base64);
        String suffixEnc = FileUtils.suffix(filePath.substring(filePath.lastIndexOf(".") + 1))+enc;
        MultipartFile encMf = FileUtils.Base64ToMultipartFile.base64ToMultipart(suffixEnc);
        assert encMf != null;
        FileUtils.saveFile(encFilepath,encMf);
        //文件解密
        String encBase64 = FileUtils.fileToBase64(encFilepath);
        String decBase64 = CryptoUtils.decryptAESPkcs7(encBase64.split(",")[1]);
        MultipartFile decMf = FileUtils.Base64ToMultipartFile.base64ToMultipart(decBase64);
        assert decMf != null;
        FileUtils.saveFile(encFilepath, decMf);
        //另一种保存方式
        //Files.write(Paths.get(paths), Base64.getDecoder().decode(dec), StandardOpenOption.CREATE);
    }

    /**
     * 获取网络文件并下载
     * FileUtils.getFile 使用演示
     */
    public static void fileDownTest() throws Exception{
        File file = FileUtils.getFile("https://www.baidu.com/a.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new FileUtils.MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(inputStream));
        multipartFile.transferTo(Paths.get("D:\\a.jpg"));
    }

    /**
     * 分页方法测试
     * @param getPageNum 页码
     * @param getPageSize 数量
     * @param list 集合
     * @return pageResp<T>
     */
    public PageResp<String> PageTest(int getPageNum, int getPageSize, List<String> list){
        PageResp<String> pageResp = new PageResp<String>();
        PageHelper pageHelper = new PageHelper(getPageNum, getPageSize);
        pageHelper.setTotalRowCount(list.size());
        //query.addLimit(pageHelper.offset(), pageHelper.getPageSize());
        //List<String> list = query.fetchInto(String.class);
        pageResp.setTotalCount(pageHelper.getTotalRowCount());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * VoResult 使用演示 常用回显方法整合
     * @return VoResult
     */
    public static VoResult resultTest(){
        return VoResult.success();
        //return VoResult.success().add("key","value");
        //return VoResult.errorParam("报错啦！！！");
    }

    /**
     *根据属性，拿到set方法，并把值set到对象中
     */
    public static void setObjectValueByField() throws NoSuchFieldException {

        String field = "name";
        String value = "李雷";
        User user = new User();
        MethodGetAndSet.setValue(user,user.getClass(),field,User.class.getDeclaredField(field).getType(),value);
        System.out.println(user);
    }
    @Data
    public static class User{
        private String name;
        private String age;
    }
}
