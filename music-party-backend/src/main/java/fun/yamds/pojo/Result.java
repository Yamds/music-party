package fun.yamds.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：Yamds
 * @createtime ：2025/4/26
 * @description：Result
 */

@Data
public class Result implements Serializable {

    private static final long serialVersionUID = 1L; // 或者其他任意 long 值，通常从 1L 开始

    private Boolean success;    // 返回成功或者失败 true成功 false失败
    private Integer code;   // 返回状态码
    private String msg;     // 返回信息
    private Map<String, Object> data;   // 返回数据

    // 私有化构造器，使用链式编程用不上构造器
    private Result() {}

    public static Result ok() {
        Result result = new Result();
        result.success = true;
        result.setCode(200);
        result.setMsg("Success!");
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.success = false;
        result.setCode(500);
        result.setMsg("Error!");
        return result;
    }

    public Result code(Integer code) {
        this.code = code;
        return this;
    }

    public Result msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public Result data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
