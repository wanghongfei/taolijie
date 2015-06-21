package com.fh.taolijie.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by wynfrith on 15-6-21.
 */
public class UploadUtil {
    private static int _500KB = 1024 * 500;// 500KB


    public  static byte[] writeToBuffer(InputStream in, Long size) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(size.intValue());
        int len = 0;

        // 先读10 bytes
        // 验证是否是png, jpg或gif
        byte[] validationBuf = new byte[10];
        in.read(validationBuf);
        if (Constants.ImageType.UNSUPPORTED == ImageUtils.getImageType(validationBuf)) {
            return null;
        }
        buffer.put(validationBuf);


        byte[] buf = new byte[_500KB];
        while ( (len = in.read(buf)) != -1 ) {
            buffer.put(buf, 0, len);
        }


        return buffer.array();
    }



    /**
     * 获得文件扩展名
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
