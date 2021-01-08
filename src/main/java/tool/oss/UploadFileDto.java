package tool.oss;

import lombok.Data;

@Data

public class UploadFileDto {

    private String userid;

    private String pwd;

    private String timestamp;

    private String sign;

    private String sequence;

    private String ext;

    private String file;

    public UploadFileDto(String userid,String pwd,String timestamp,String sign, String sequence,String ext,  String file) {
        this.userid = userid;
        this.pwd = pwd;
        this.timestamp = timestamp;
        this.sign = sign;
        this.sequence = sequence;
        this.ext = ext;
        this.file = file;
    }
}
