package utils;

import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author fuhouyin
 */
public class DateTimeUtils {

    /**
     * 计算两个日期之间相差的天数
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算临期/超期天数
     * 临期 包括今天
     * 超期 不包括今天
     * @param endDate 截至时间
     * @param finishDate 完成时间
     * @return
     */
    public static TimeOverdueAdventPojo overdueAdvent(Date endDate, Date finishDate) throws ParseException {

        TimeOverdueAdventPojo timeOverdueAdventPojo = new TimeOverdueAdventPojo();

        SimpleDateFormat sdf_YMD2359 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        SimpleDateFormat sdf_YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf_YMD = new SimpleDateFormat("yyyy-MM-dd");

        String format = sdf_YMD2359.format(endDate);

        //当前时间
        Date nowTime = new Date(Timestamp.from(Instant.now()).getTime());
        Date endTime = new Date(sdf_YMD.parse(format).getTime());
        Date endTime_flag = new Date(sdf_YMDHMS.parse(format).getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTime(endTime);
        long timeEnd = cal.getTimeInMillis();
        cal.setTime(nowTime);
        long timeNow = cal.getTimeInMillis();

        if (Objects.isNull(finishDate)){
            if (nowTime.after(endTime_flag)){
                long between_days = (timeEnd - timeNow) / (1000 * 3600 * 24);
                timeOverdueAdventPojo.setFlag((byte) 1);
                timeOverdueAdventPojo.setOaFlag("逾期");
                timeOverdueAdventPojo.setDays(Integer.parseInt(String.valueOf(between_days)));
            }else {
                long between_days = (endTime_flag.getTime() - nowTime.getTime()) / (1000 * 3600 * 24);
                timeOverdueAdventPojo.setFlag((byte) 0);
                timeOverdueAdventPojo.setOaFlag("临期");
                timeOverdueAdventPojo.setDays(Integer.parseInt(String.valueOf(between_days + 1)));
            }
        }else {
            Date finishTime = new Date(finishDate.getTime());
            cal.setTime(finishTime);
            long timeFinish = cal.getTimeInMillis();
            if (finishDate.after(endTime_flag)){
                long between_days = (timeEnd - timeFinish) / (1000 * 3600 * 24);
                timeOverdueAdventPojo.setFlag((byte) 1);
                timeOverdueAdventPojo.setOaFlag("逾期");
                timeOverdueAdventPojo.setDays(Integer.parseInt(String.valueOf(between_days)));
            }else {
                long between_days = (endTime_flag.getTime() - timeFinish) / (1000 * 3600 * 24);
                timeOverdueAdventPojo.setFlag((byte) 0);
                timeOverdueAdventPojo.setOaFlag("临期");
                timeOverdueAdventPojo.setDays(Integer.parseInt(String.valueOf(between_days + 1)));
            }
        }

        return timeOverdueAdventPojo;
    }

    @Data
    public static class TimeOverdueAdventPojo {

        private int days;
        private byte flag; //未逾期 0 逾期 1
        private String oaFlag; //临期 advent 逾期 overdue
    }
}
