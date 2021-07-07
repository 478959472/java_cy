package utils;

import org.jasypt.util.text.BasicTextEncryptor;

public class EncryptorUtils {

    public static void main(String[] args) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword("EbfYkitulv73I2p0mXI50JMXoaxZTKJ7");
        //要加密的数据（数据库的用户名或密码）
//        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.decrypt("MPA5BNHpGbJB/kdKHruIpDI0f/6yCV/v");
        System.out.println("password:"+password);
    }
}
