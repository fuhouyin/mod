package feishu;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fuhouyin
 * @time 2023/3/21 10:08
 * 飞书自定义机器人发送消息
 */
public class FeiShu {

    private static final RestTemplate restTemplate = new RestTemplate();

    /**
     * 卡片消息-带按钮
     * @param hearTitle 标题
     * @param content 内容
     * @param actionsTitle 按钮标题
     * @param actionsUrl 按钮链接
     */
    public static void cardMsg(String url,String hearTitle,String content,String actionsTitle,String actionsUrl){

        Map<String,Object> header = new HashMap<>();
        Map<String,Object> header_title = new HashMap<>();
        header_title.put("content",hearTitle);
        header_title.put("tag","plain_text");
        header.put("title",header_title);


        Map<String,Object> elements_1 = new HashMap<>();
        Map<String,Object> elements_1_text = new HashMap<>();
        elements_1_text.put("content",content);
        elements_1_text.put("tag", "lark_md");
        elements_1.put("tag","div");
        elements_1.put("text",elements_1_text);

        Map<String,Object> elements_2 = new HashMap<>();
        Map<String,Object> elements_2_actions = new HashMap<>();
        Map<String,Object> elements_2_actions_value = new HashMap<>();
        Map<String,Object> elements_2_actions_text = new HashMap<>();
        elements_2_actions_text.put("content",actionsTitle);//"更多景点介绍 :玫瑰:"
        elements_2_actions_text.put("tag","lark_md");
        elements_2_actions.put("tag","button");
        elements_2_actions.put("text",elements_2_actions_text);
        elements_2_actions.put("url",actionsUrl);
        elements_2_actions.put("type","default");
        elements_2_actions.put("value",elements_2_actions_value);
        Map[] actions = new HashMap[1];
        actions[0] = elements_2_actions;
        elements_2.put("actions",actions);
        elements_2.put("tag","action");

        Map[] elements = new HashMap[2];
        elements[0] = elements_1;
        elements[1] = elements_2;

        Map<String,Object> card = new HashMap<>();
        card.put("elements",elements);
        card.put("header",header);

        Map<String,Object> map = new HashMap<>();
        map.put("msg_type","interactive");
        map.put("card",card);

        restTemplate.postForObject(url, new JSONObject(map), JSONObject.class);
    }

    /**
     * 卡片消息
     * @param hearTitle 标题
     * @param content 内容
     */
    public static void cardMsg(String url,String hearTitle,String content){

        Map<String,Object> header = new HashMap<>();
        Map<String,Object> header_title = new HashMap<>();
        header_title.put("content",hearTitle);
        header_title.put("tag","plain_text");
        header.put("title",header_title);
        header.put("template","blue");

        Map<String,Object> elements_1 = new HashMap<>();
        Map<String,Object> elements_1_text = new HashMap<>();
        elements_1_text.put("content",content);
        elements_1_text.put("tag", "lark_md");
        elements_1.put("tag","div");
        elements_1.put("text",elements_1_text);
        Map[] elements = new HashMap[1];
        elements[0] = elements_1;

        Map<String,Object> config = new HashMap<>();
        config.put("wide_screen_mode",true);

        Map<String,Object> card = new HashMap<>();
        card.put("elements",elements);
        card.put("header",header);
        card.put("config",config);

        Map<String,Object> map = new HashMap<>();
        map.put("msg_type","interactive");
        map.put("card",card);

        restTemplate.postForObject(url, new JSONObject(map), JSONObject.class);
    }
}
