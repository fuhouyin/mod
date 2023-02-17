package utils.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fuhouyin
 * @time 2023/2/17 18:02
 */
public class VoResult {

    private Integer resultCode;

    private String resultMsg;

    private Map<String,Object> resultData = new HashMap<String,Object>();

    public Map<String, Object> getResultData() {
        return resultData;
    }

    public void setResultData(Map<String, Object> resultData) {
        this.resultData = resultData;
    }


    /**
     * sessionn超时或未登录
     */
    public static VoResult sessionNotExists(){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_600.getCode());
        result.setResultMsg(ResultEnums.FALSE_600.getMessage());
        return result;
    }

    /**
     * 数据已存在
     */
    public static VoResult dataExists(){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_424.getCode());
        result.setResultMsg(ResultEnums.FALSE_424.getMessage());
        return result;
    }

    /**
     * 数据不存在
     */
    public static VoResult dataNotExists(){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_423.getCode());
        result.setResultMsg(ResultEnums.FALSE_423.getMessage());
        return result;
    }

    /**
     * 数据不存在
     */
    public static VoResult dataNotExists(String resultMsg){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_423.getCode());
        result.setResultMsg(resultMsg);
        return result;
    }

    /**
     * 成功
     */
    public static VoResult success(){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.TRUE_200.getCode());
        result.setResultMsg(ResultEnums.TRUE_200.getMessage());
        return result;
    }

    /**
     * 失败
     */
    public static VoResult fail(){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_300.getCode());
        result.setResultMsg(ResultEnums.FALSE_300.getMessage());
        return result;
    }

    /**
     * 内部错误(参数错误)
     */
    public static VoResult errorParam(){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_400.getCode());
        result.setResultMsg(ResultEnums.FALSE_400.getMessage());
        return result;
    }

    /**
     * 如需自定义错误信息,请使用该方法
     */
    public static VoResult errorParam(String msg){
        VoResult result = new VoResult();
        result.setResultCode(ResultEnums.FALSE_400.getCode());
        result.setResultMsg(msg);
        return result;
    }

    public VoResult add(String key, Object value){
        this.getResultData().put(key, value);
        return this;
    }

    public VoResult addData(Object value){
        this.getResultData().put("data", value);
        return this;
    }

    public VoResult add(Map map){
        this.setResultData(map);
        return this;
    }

    public VoResult(){

    }

    public VoResult(Integer resultCode, String resultMsg, Map<String,Object> resultData) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resultData = resultData;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

}
