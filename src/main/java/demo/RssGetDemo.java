package demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class RssGetDemo {

    private final static SimpleDateFormat rssDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);

    public static void main(String[] args) throws Exception {
        //ruanyifeng();
        //helloGithub();
        //zhihu();
        //solidot();
        //maqianzu();
        //yuanshen();

    }

    public static void ruanyifeng() throws Exception {
        // 从URL获取RSS源
        Document doc = Jsoup.connect("https://www.ruanyifeng.com/blog/atom.xml").get();
        System.out.println(doc);

        // 使用CSS选择器来查找特定的元素
        Elements items = doc.select("entry");

        for (Element item : items) {
            String title = item.select("title").text();
            String link = item.select("link[rel=alternate]").attr("href");
            String description = item.select("summary").text();
            String pubDate = item.select("published").text();
            String content = item.select("content").text();

            DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
            LocalDateTime dateTime = LocalDateTime.parse(pubDate, formatter);
            Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

            System.out.println("Title: " + title);
            System.out.println("Link: " + link);
            System.out.println("Description: " + description);
            System.out.println("Publication Date: " + pubDate);
            System.out.println("content" + content);
        }
    }

    public static void helloGithub() throws Exception {
        getStandardRss("https://hellogithub.com/rss",86400000);
    }

    public static void getStandardRss(String url,long time) throws Exception {

        // 从URL获取RSS源
        Document doc = Jsoup.connect(url).get();
        System.out.println(doc);

        // 使用CSS选择器来查找特定的元素
        Elements items = doc.select("item");

        for (Element item : items) {
            String title = item.select("title").text();
            String link = item.select("link").text();
            String description = item.select("description").text();
            String pubDate = item.select("pubDate").text();
            Date date = rssDateFormat.parse(pubDate);

            System.out.println("Title: " + title);
            System.out.println("Link: " + link);
            System.out.println("Description: " + description);
            System.out.println("Publication Date: " + pubDate);
            System.out.println("date :"+ date);

            long i = new Date().getTime() - date.getTime();
            System.out.println("时差："+i);
        }
    }
}
