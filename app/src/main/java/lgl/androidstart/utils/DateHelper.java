package lgl.androidstart.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {

    /**
     * 定义常量
     **/
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_ALL_STR = "yyyy-MM-dd HH:mm:ss:SS";//精确到微妙
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";

    /**
     * 将字符串转换为Date
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式（类似正则）
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 和本地时间比较（不考虑相等的情况 相等算历史）
     *
     * @param data_now 需要比较的时间
     * @return 历史false 未来是true
     */
    public static Boolean compareDateWithNow(Date data_now) {
        Date date2 = new Date();
        int rnum = data_now.compareTo(date2);// -1小 1大
        if (rnum > 0)
            return true;
        return false;
    }

    /**
     * 两个时间比较(时间戳比较)
     *
     * @param unixTimestamp
     * @return
     */
    public static int compareDateWithNow(long unixTimestamp) {
        long date2 = getUnixTimestampNow();
        if (unixTimestamp > date2) {
            return 1;
        } else if (unixTimestamp < date2) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 从时间戳中获得时间
     *
     * @param timeStamp
     * @param pattern   默认为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getString4unixTimestamp(long timeStamp, String... pattern) {
        Date date = new Date(timeStamp);
        return getString4Date(date, getDefaultPattern(pattern));
    }

    /**
     * 将Date转为String格式
     *
     * @param date
     * @param pattern 默认为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getString4Date(Date date, String... pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(getDefaultPattern(pattern));
        return dateFormat.format(date);
    }

    /**
     * 获取系统当前时间
     *
     * @param pattern 默认为 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getNowTime(String... pattern) {
        SimpleDateFormat df = new SimpleDateFormat(getDefaultPattern(pattern));
        return df.format(new Date());
    }

    /**
     * 当前的时间戳
     *
     * @return long 当前的时间戳
     */
    public static long getUnixTimestampNow() {
        long timestamp = new Date().getTime();
        return timestamp;
    }

    /**
     * 将指定的日期转换成Unix时间戳
     *
     * @param dateStr date 需要转换的日期 yyyy-MM-dd HH:mm:ss
     * @return long 时间戳
     */
    public static long getUnixTimestampNow(String dateStr, String... pattern) {
        long timestamp = 0;
        try {
            timestamp = new SimpleDateFormat(getDefaultPattern(pattern)).parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将Unix时间戳转换成日期
     *
     * @param timestamp timestamp 时间戳
     * @return String 日期字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getUnixTimestamp2DateString(long timestamp, String... pattern) {
        SimpleDateFormat sd = new SimpleDateFormat(getDefaultPattern(pattern));
        sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return sd.format(new Date(timestamp));
    }

    /**
     * 根据用户穿入的时间格式来判断是否使用默认的格式
     *
     * @param pattern
     * @return
     */
    private static String getDefaultPattern(String... pattern) {
        return pattern.length > 0 ? pattern[0] : DateHelper.DATE_FULL_STR;
    }

}
