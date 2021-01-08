package tool.oss;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author montnets
 */
@Data
public class OssInfo {
    @ApiModelProperty(value = "oss服务器地址")
    private String ossUrl;
    @ApiModelProperty(value = "oss userName")
    private String userName;
    @ApiModelProperty(value = "oss 用户密码")
    private String pwd;

}
