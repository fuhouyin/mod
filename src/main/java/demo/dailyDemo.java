package demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import mail.Mail;
import org.springframework.web.client.RestTemplate;
import utils.DateTimeUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fuhouyin
 * @time 2023/3/1 16:11
 * 日报
 */
public class dailyDemo {

    public static void main(String[] args) throws Exception{
        Mail.sendMail("发件邮箱","密钥","收件邮箱","标题",html());
    }

    /**发薪日*/
    public static String salDay() throws Exception {

        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, 15);
        c1.set(Calendar.HOUR_OF_DAY, 23);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND,0);
        c1.set(Calendar.MILLISECOND, 0);

        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.MONTH, 1);
        c2.set(Calendar.DAY_OF_MONTH, 15);
        c2.set(Calendar.HOUR_OF_DAY, 23);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND,0);
        c2.set(Calendar.MILLISECOND, 0);

        Date toDay = new Date().after(c1.getTime()) ? c2.getTime() : c1.getTime();
        int days = DateTimeUtils.daysBetween(new Date(), toDay);

        String msg = "【距离发薪日】：还有"+days+"天";

        return msg;
    }

    /**获取tophub热门数据*/
    public static List<news> news(){
        String url = "https://open.tophub.today/hot";
        JSONArray ja =  new RestTemplate().getForObject(url, JSONObject.class).getJSONObject("data").getJSONArray("items");
        List<news> ls = JSONObject.parseArray(ja.toJSONString(),news.class);
        return ls;
    }

    @Data
    public class news implements Serializable {
        String title; //标题
        String description; //简介
        String thumbnail; //头图
        String url; //链接
        String extra; //热度
        Timestamp time; //时间 不准
        String logo;
    }

    /**组装html*/
    public static String html() throws Exception{

        LocalDate ld = LocalDate.now();

        String str = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\">\n</head>\n<body>\n";
        str += "<h1 style=\"text-align: center\">"+ld.getYear()+"-"+ld.getMonthValue()+"-"+ld.getDayOfMonth()+"日报</h1>\n";
        str += "<h4 style=\"text-align: center\">"+salDay()+"</h4>\n";
        String ls = news().stream().limit(30).map(e->{
            String s = "";
            s += "<li><a href=\""+e.getUrl()+"\"><h3>"+e.getTitle()+"</h3></a></li>\n";
            return s;
        }).collect(Collectors.joining());
        str += "<div>\n<ul>\n"+ls+"\n</ul>\n</div>\n";
        str += "</body>\n</html>";

        return str;
    }
}
