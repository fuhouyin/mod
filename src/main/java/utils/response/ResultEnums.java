package utils.response;

/**
 * @author fuhouyin
 * @time 2023/2/17 18:03
 */
public enum ResultEnums {
    TRUE_200(200, "成功"),
    FALSE_300(300, "失败"),
    FALSE_400(400, "内部错误"),
    FALSE_401(401, "需要用户验证"),
    FALSE_403(403, "请求数据时发生异常"),
    FALSE_408(408, "请求超时"),
    FALSE_410(410, "内部错误"),
    FALSE_411(411, "删除时参数错误"),
    FALSE_423(423, "数据不存在"),
    FALSE_424(424, "数据已存在"),
    FALSE_425(425, "超出限制"),
    FALSE_426(426, "数据不完整"),
    FALSE_427(427, "不允许进行该操作"),
    FALSE_500(500, "超过规定时间"),
    FALSE_600(600, "session超时或未登录"),
    FALSE_10001(10001, "参数异常"),
    FALSE_10002(10002, "接口服务处理异常"),
    FALSE_10003(10003, "访问权限受控");

    private int code;
    private String message;

    ResultEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
