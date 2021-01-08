package tool.oss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * MD5加密
 *
 * @author Administrator
 */
public class MD5 {
    private final static Logger logger = LoggerFactory.getLogger(MD5.class);

    public static String getMD5Str(String content) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(content.getBytes("UTF-8"));
        } catch (Exception e) {
            logger.error("M5摘要失败，content:" + content, e);
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }

        return md5StrBuff.toString();
    }

    public static byte[] getMD5(String content) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(content.getBytes("UTF-8"));
        } catch (Exception e) {
            logger.error("M5加密失败，content:" + content, e);
        }

        byte[] byteArray = messageDigest.digest();
        return byteArray;
    }

    public static String getHexString(byte[] b) throws Exception {

        StringBuffer result = new StringBuffer("");

        for (int i = 0; i < b.length; i++) {

            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1));

        }

        return result.toString();

    }
}
