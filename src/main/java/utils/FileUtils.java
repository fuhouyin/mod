package utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
     * @return File
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

    /**
     * base64转MultiFile
     */
    public static class Base64ToMultipartFile implements MultipartFile {

        private final byte[] imgContent;
        private final String header;


        public Base64ToMultipartFile(byte[] imgContent, String header) {
            this.imgContent = imgContent;
            this.header = header.split(";")[0];
        }

        @Override
        public String getName() {
            return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
        }

        @Override
        public String getOriginalFilename() {
            return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
        }

        @Override
        public String getContentType() {
            return header.split(":")[1];
        }

        @Override
        public boolean isEmpty() {
            return imgContent == null || imgContent.length == 0;
        }

        @Override
        public long getSize() {
            return imgContent.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return imgContent;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(imgContent);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            new FileOutputStream(dest).write(imgContent);
        }

        /**
         * base转为MultipartFile base64需要带着头
         */
        public static MultipartFile base64ToMultipart(String base64) {
            String[] baseStrs = base64.split(",");

            Base64.Decoder decoder = Base64.getDecoder();
            byte[] b = new byte[0];
            b = decoder.decode(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new Base64ToMultipartFile(b, baseStrs[0]);
        }

    }

    /**
     * file转MultipartFile的时候会用到MockMultipartFile
     * 当你导入spring-test依赖的时候 会跟某些依赖冲突（暂未找到具体是哪个冲突）
     * 解决方法 重写一个类去实现MultipartFile接口
     * 直接用MockMultipartFile的源码
     */
    public static class MockMultipartFile implements MultipartFile {
        private final String name;

        private String originalFilename;

        private String contentType;

        private final byte[] content;

        /**
         * Create a new MultipartFileDto with the given content.
         * @param name the name of the file
         * @param content the content of the file
         */
        public MockMultipartFile(String name, byte[] content) {
            this(name, "", null, content);
        }

        /**
         * Create a new MultipartFileDto with the given content.
         * @param name the name of the file
         * @param contentStream the content of the file as stream
         * @throws IOException if reading from the stream failed
         */
        public MockMultipartFile(String name, InputStream contentStream) throws IOException {
            this(name, "", null, FileCopyUtils.copyToByteArray(contentStream));
        }

        /**
         * Create a new MultipartFileDto with the given content.
         * @param name the name of the file
         * @param originalFilename the original filename (as on the client's machine)
         * @param contentType the content type (if known)
         * @param content the content of the file
         */
        public MockMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
            this.name = name;
            this.originalFilename = (originalFilename != null ? originalFilename : "");
            this.contentType = contentType;
            this.content = (content != null ? content : new byte[0]);
        }

        /**
         * Create a new MultipartFileDto with the given content.
         * @param name the name of the file
         * @param originalFilename the original filename (as on the client's machine)
         * @param contentType the content type (if known)
         * @param contentStream the content of the file as stream
         * @throws IOException if reading from the stream failed
         */
        public MockMultipartFile(String name, String originalFilename, String contentType, InputStream contentStream)
                throws IOException {

            this(name, originalFilename, contentType, FileCopyUtils.copyToByteArray(contentStream));
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getOriginalFilename() {
            return this.originalFilename;
        }

        @Override
        public String getContentType() {
            return this.contentType;
        }

        @Override
        public boolean isEmpty() {
            return (this.content.length == 0);
        }

        @Override
        public long getSize() {
            return this.content.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return this.content;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(this.content);
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {
            FileCopyUtils.copy(this.content, dest);
        }
    }
}
