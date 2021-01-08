package tool.oss;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class OssUploadUtil {
    private static final String RESULT = "result";
    private static final String OSS_URL = "ossurl";
    public static String upload(File file, OssInfo ossInfo) {
        if (file == null){
            return null;
        }
        try {
            // 设置时间戳
            String timestamp = new SimpleDateFormat("MMddHHmmss").format(Calendar.getInstance().getTime());
            // 设置密码
            String md5Pwd = MD5.getMD5Str(ossInfo.getUserName().toUpperCase() + "00000000" + ossInfo.getPwd() + timestamp);
            // 设置序列号
            String sequence = IdUtil.simpleUUID();
            // 文件拓展名
            String fileName = file.getName();
            String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            String fileBase64 = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            // 数字签名
            String sign = MD5.getMD5Str(ossInfo.getUserName().toUpperCase() + ossInfo.getPwd() + timestamp + fileBase64);
            // 组装实体类
            UploadFileDto uploadFileDto = new UploadFileDto(ossInfo.getUserName(), md5Pwd, timestamp, sign, sequence, ext, fileBase64);
            // 调用上传接口
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(ossInfo.getOssUrl(), uploadFileDto, String.class);
            if (HttpStatus.OK != responseEntity.getStatusCode()) {
                log.error("调用上传接口失败，网络异常，请求报文{}，网络状态码{}", uploadFileDto, responseEntity.getStatusCode());
                return "";
            }
            JSONObject resultJson = JSONObject.parseObject(responseEntity.getBody());
            if (resultJson.getInteger(RESULT) != 0) {
                log.error("调用上传接口失败，请求报文{}，响应报文{}", uploadFileDto, resultJson);
                return "";
            }
            return resultJson.getString(OSS_URL);
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
        return "";
    }

    public static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static Set<String> listFilesUsingFileWalkAndVisitor(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!Files.isDirectory(file)) {
                    fileList.add(file.toAbsolutePath().toString());
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return fileList;
    }
}
