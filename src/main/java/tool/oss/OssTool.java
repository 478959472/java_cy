package tool.oss;

import java.io.File;

public class OssTool {


    public static void main(String[] args) throws Exception {
        if(args.length != 3){
            System.out.println("请输入oss服务信息： 用户名 密码 服务地址");
        }
        System.out.println("开始上传oss");
        OssInfo ossInfo = new OssInfo();
        ossInfo.setUserName(args[0]);
        ossInfo.setPwd(args[1]);
        ossInfo.setOssUrl(args[2]);
        String filePath = "/temp/1.png";
        File file =  ClipboardUtil.copyTo(filePath);
        String ossUrl = OssUploadUtil.upload(file, ossInfo);
        System.out.println(ossUrl);
        ClipboardUtil.setClipboard(ossUrl);
    }

}
