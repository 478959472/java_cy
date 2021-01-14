package netty.rcs;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果集
 *
 * @author :  raymond
 * @version :  1.0
 * @date :  2019/10/18
 */
@Data
@NoArgsConstructor
public class Result {
    /**
     * 状态
     */
    private String code;
    /**
     * 信息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
