package com.fh.taolijie.utils;

/**
 * Created by wanghongfei on 15-5-24.
 */
public class ImageUtils {
    public static Constants.ImageType getImageType(byte[] tenBytes) {
        if (tenBytes.length < 10) {
            return Constants.ImageType.UNSUPPORTED;
        }

        if (tenBytes[1] == 'P' && tenBytes[2] == 'N' && tenBytes[3] == 'G') {
            return Constants.ImageType.PNG;
        }

        if (tenBytes[6] == 'J' && tenBytes[7] == 'F' && tenBytes[8] == 'I' && tenBytes[9] == 'F') {
            return Constants.ImageType.JPG;
        }

        if (tenBytes[0] == 'G' && tenBytes[1] == 'I' && tenBytes[2] == 'F') {
            return Constants.ImageType.GIF;
        }

        return Constants.ImageType.UNSUPPORTED;
    }
}
