package lgl.androidstart.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author LGL
 */
public class DateHelper {

    /**
     * 定义常量
     **/
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_ALL_STR = "yyyy-MM-dd HH:mm:ss:SS";//精确到微妙
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    public static final String DATE_SHOT_STR = "HH:mm:ss";
    public static final String DATE_SHOT2_STR = "HH:mm";

    /**
     * 将字符串转换为Date
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式（类似正则）
     * @return
     */
    public static Date parse(String strDate, String... pattern) {
        SimpleDateFormat df = new SimpleDateFormat(getDefaultPattern(pattern));
        try {
//            ParsePosition pos = new ParsePosition(0);//后面的参数代表从第几位开始  防止不干净的时间字符串
//            return df.parse(strDate,pos);
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
     * @param unixTimestamp 时间戳
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
     * @param timeStamp 时间戳
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
     * 得到现在几点
     *
     * @param how   <0  Hour      >=0  Min
     * @return
     */
    public static String getHour(int how) {
        String dateString = getNowTime("yyyy-MM-dd HH:mm:ss");
        if (how <0) {
            String hour = dateString.substring(11, 13);
            return hour;
        } else {
            String min = dateString.substring(14, 16);
            return min;
        }
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
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String getUnixTimestampNow2() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
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


    /**
     * 强Date转换成String
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String Date2String(Date date, String... pattern) {
        return new SimpleDateFormat(getDefaultPattern(pattern)).format(date);
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static String getLastDayOfMonth(String year, String month) {
        Calendar cal = Calendar.getInstance();
        // 年
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        // 月，因为Calendar里的月是从0开始，所以要-1
        // cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        // 日，设为一号
        cal.set(Calendar.DATE, 1);
        // 月份加一，得到下个月的一号
        cal.add(Calendar.MONTH, 1);
        // 下一个月减一为本月最后一天
        cal.add(Calendar.DATE, -1);
        return String.valueOf(cal.get(Calendar.DAY_OF_MONTH));// 获得月末是几号
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return 组合而成的时间Date对象
     */
    public static Date getDate(String year, String month, String day) {
        String result = year + "- " + (month.length() == 1 ? ("0 " + month) : month) + "- " + (day.length() == 1 ? ("0 " + day) : day);
        return parse(result);
    }

    /**
     * 计算和现在时间相差多少时间
     *
     * @param endDates 需要比较的日期
     * @return eg: 15分钟前、 刚刚
     */
    public static String twoDateDistance(String endDates, String... pattern) {
        Date endDate = parse(endDates, pattern);
        //计算和现在的时间时间差（秒）
        long timeLong = (endDate.getTime() - new Date().getTime()) / -1000;// 单位毫秒，除以1000转换成秒
        // 单位秒
        long month = 60 * 60 * 24 * 7 * 4;// 月
        long week = 60 * 60 * 24 * 7;// 周
        long day = 60 * 60 * 24;// 天
        long hour = 60 * 60;// 小时
        long minute = 60; // 分钟
        long second = 1; // 秒

        if (timeLong < minute)//《一分钟
            return timeLong / second + "秒前";
        else if (timeLong < hour) {//《一小时
            timeLong = timeLong / minute;
            if (timeLong < 10) return "刚刚";
            else return timeLong + "分钟前";
        } else if (timeLong < day) {//《一小时
            timeLong = timeLong / hour;
            return timeLong + "小时前";
        } else if (timeLong < week) {
            timeLong = timeLong / day;
            return timeLong + "天前";
        } else if (timeLong < month) {
            timeLong = timeLong / week;
            return timeLong + "周前";
        } else {
            // 如果超过几周则显示日期
            String str = new String(endDates);
            String date1[] = str.split(" ");
            return date1[0];
        }
    }


    /**
     * 从生日获得岁
     * @param birthStr
     * @param pattern
     * @return   18岁
     */
    public static String getAgeFormBirth(String birthStr, String... pattern){
        int age;
        Date now = parse(birthStr,pattern);
        Date date =parse(getNowTime(pattern));
        long l = date.getTime() - now.getTime();
        long day = l/(24*60*60*1000);
        if (day%365 == 0) {
            age = (int) (day/365);
        }else{
            age = (int) (day/365)+1;
        }

        return String.valueOf(age)+" 岁";
    }

    // SimpleDateFormat函数语法：
    // G 年代标志符
    // y 年
    // M 月
    // d 日
    // h 时 在上午或下午 (1~12)
    // H 时 在一天中 (0~23)
    // m 分
    // s 秒
    // S 毫秒
    // E 星期
    // D 一年中的第几天
    // F 一月中第几个星期几
    // w 一年中第几个星期
    // W 一月中第几个星期
    // a 上午 / 下午 标记符
    // k 时 在一天中 (1~24)
    // K 时 在上午或下午 (0~11)
    // z 时区
    // 常见标准的写法"yyyy-MM-dd HH:mm:ss",注意大小写，时间是24小时制，24小时制转换成12小时制只需将HH改成hh,不需要另外的函数。

}
