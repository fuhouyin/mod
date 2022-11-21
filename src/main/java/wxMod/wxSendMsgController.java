package wxMod;

import com.alibaba.fastjson.JSONObject;
import utils.WxUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wxSendMsg")
public class wxSendMsgController {

    private static String TEMP_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    private static String MSGID = ""; //消息模板

    @PostMapping("/sendMsgMod")
    public void sendMsgMod(String keyword1,String keyword2){
        String openid = ""; //openid
        String access_token =  WxUtils.getAccessToken();
        String url = TEMP_URL+access_token;

        Map<String,Object> param1 = new HashMap<>();
        param1.put("value",keyword1);
        Map<String,Object> param2 = new HashMap<>();
        param2.put("value",keyword2);
        Map<String,Object> data = new HashMap<>();
        data.put("keyword1",keyword1);
        data.put("keyword2",keyword2);
        Map<String,Object> params = new HashMap<>();
        params.put("touser",openid);
        params.put("template_id",MSGID);
        params.put("data",data);
        JSONObject jsonObject = new JSONObject(params);

        JSONObject json = WxUtils.doPoststr(url,jsonObject.toJSONString());
        System.out.println(json.toString());
    }
}
