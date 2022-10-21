import Pojo.TimeOverdueAdventPojo;
import utils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws Exception{
        daysBetweenTest();
        overdueAdventTest();
    }

    /**
     * DateTimeUtils.daysBetween 使用演示
     */
    public static void daysBetweenTest() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=sdf.parse("2022-09-26 23:59:59");
        Date d2=sdf.parse("2022-09-28 10:00:00");
        System.out.println(DateTimeUtils.daysBetween(d1,d2));
    }

    /**
     * DateTimeUtils.overdueAdvent 使用演示
     */
    public static void overdueAdventTest() throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date endTime = sdf.parse("2022-10-12 10:07:49"); //截至日期
        Date finishTime = null; //未完成

        TimeOverdueAdventPojo timeOverdueAdventPojo = DateTimeUtils.overdueAdvent(endTime,finishTime);
        System.out.println(timeOverdueAdventPojo);
    }
}
