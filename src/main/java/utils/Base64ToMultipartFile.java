package utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * @author fuhouyin
 * @time 2023/1/30 11:21
 */
public class Base64ToMultipartFile implements MultipartFile {

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