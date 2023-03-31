package utils.response;

/**
 * @author fuhouyin
 * @time 2023/2/17 18:03
 */
public enum ResultEnums {
    TRUE_200(200, "成功"),
    FALSE_300(300, "失败"),
    FALSE_400(400, "内部错误"),
    FALSE_423(423, "数据不存在"),
    FALSE_424(424, "数据已存在"),
    FALSE_500(500, "错误"),
    FALSE_600(600, "session超时或未登录");

    private int code;
    private String message;

    ResultEnums(){}

    ResultEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String valueOfCode(String code) {
        for (ResultEnums obj : ResultEnums.values()) {
            if (java.util.Objects.equals(obj.code, code)) {
                return obj.message;
            }
        }
        return null;
    }

    public static Number valueOfMessage(String message) {
        for (ResultEnums obj : ResultEnums.values()) {
            if (java.util.Objects.equals(obj.message, message)) {
                return obj.code;
            }
        }
        return null;
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
