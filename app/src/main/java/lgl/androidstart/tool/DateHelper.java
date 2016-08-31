package lgl.androidstart.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static Date StringToDate(String str) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static String DateToString(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
    }

    private static String defaultDatePattern = "yyyy/MM/dd HH:mm:ss";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return date == null ? " " : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        return new SimpleDateFormat(pattern).parse(strDate);
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

    public static Date getDate(String year, String month, String day)
            throws ParseException {
        String result = year + "- "
                + (month.length() == 1 ? ("0 " + month) : month) + "- "
                + (day.length() == 1 ? ("0 " + day) : day);
        return parse(result);
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
