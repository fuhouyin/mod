package utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * @author fuhouyin
 * @time 2023/2/1 11:55
 */
public class FileUtils {

    /**
     * 根据文件后缀名，获取base64的文件头
     * @param str 文件名后缀
     */
    public static String suffix(String str){
        String strSuffix = null;
        switch (str) {
            case "txt":  strSuffix = "data:text/plain;base64,"; break;
            case "doc":  strSuffix = "data:application/msword;base64,";break;
            case "docx":  strSuffix = "data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,";break;
            case "xls":  strSuffix = "data:application/vnd.ms-excel;base64,";break;
            case "xlsx":  strSuffix = "data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,";break;
            case "pdf":  strSuffix = "data:application/pdf;base64,";break;
            case "pptx":  strSuffix = "data:application/vnd.openxmlformats-officedocument.presentationml.presentation;base64,";break;
            case "ppt":  strSuffix = "data:application/vnd.ms-powerpoint;base64,";break;
            case "png":  strSuffix = "data:image/png;base64,";break;
            case "jpg":  strSuffix = "data:image/jpeg;base64,";break;
            case "gif":  strSuffix = "data:image/gif;base64,";break;
            case "svg":  strSuffix = "data:image/svg+xml;base64,";break;
            case "ico":  strSuffix = "data:image/x-icon;base64,";break;
            case "bmp":  strSuffix = "data:image/bmp;base64,";break;
            default: strSuffix = "data:image/bmp;base64,";break;
        }
        return strSuffix;
    }

    /**
     * 文件转base64
     * @param path 文件地址
     */
    public static String fileToBase64(String path) throws Exception {
        byte[] b = Files.readAllBytes(Paths.get(path));
        String strSuffix = suffix(path.substring(path.lastIndexOf(".") + 1));
        String base64 = Base64.getEncoder().encodeToString(b);
        return strSuffix + base64;
    }

    /**
     * 保存文件
     * @param path 文件地址
     * @param multipartFile 文件
     * @throws Exception
     */
    public static void saveFile(String path, MultipartFile multipartFile) throws Exception {
        File dest = new File(path);
        assert multipartFile != null;
        multipartFile.transferTo(dest);
    }

    /**
     * 网络文件转File
     * @param url
     * @return
     * @throws Exception
     */
    public static File getFile(String url) throws Exception {
        //对本地文件命名
        String fileName = url.substring(url.lastIndexOf("."),url.length());
        File file = null;

        URL urlfile;
        InputStream inStream = null;
        OutputStream os = null;
        try {
            file = File.createTempFile("net_url", fileName);
            //下载
            urlfile = new URL(url);
            inStream = urlfile.openStream();
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os) {
                    os.close();
                }
                if (null != inStream) {
                    inStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
