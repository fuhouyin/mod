package wxMod;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wxSendMsg")
public class wxSendMsgController {

    private static final String APPID = "";
    private static final String APPSECRET="";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    private static String TEMP_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
    private static String MSGID = ""; //消息模板

    @PostMapping("/sendMsgMod")
    public void sendMsgMod(String keyword1,String keyword2){
        String openid = ""; //openid
        String access_token = getAccessToken();
        String url = TEMP_URL+access_token;

        Map<String,Object> param1 = new HashMap<>();
        param1.put("value",keyword1);
        Map<String,Object> param2 = new HashMap<>();
        param2.put("value",keyword2);
        Map<String,Object> data = new HashMap<>();
        data.put("keyword1",param1);
        data.put("keyword2",param2);
        Map<String,Object> params = new HashMap<>();
        params.put("touser",openid);
        params.put("template_id",MSGID);
        params.put("data",data);
        JSONObject jsonObject = new JSONObject(params);

        new RestTemplate().postForObject(url,jsonObject.toJSONString(),JSONObject.class);
    }

    /**
     * 获取access_token，值需要做存储处理
     */
    public static String getAccessToken(){
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject json = new RestTemplate().getForObject(url,JSONObject.class);
        String token = null;
        if(json!=null){
            token = json.getString("access_token");
        }
        return token;
    }
}
