package lgl.androidstart.tool;

import java.text.SimpleDateFormat;

/**
 * @author Only You
 * @version 1.0
 * @date 2015年8月27日
 */
public class DateUtil {
    /**
     * 计算时间差
     *
     * @param startTime
     * @param endTime
     * @param format
     * @return
     */
    public static String TimeDifference(String startTime, String endTime, String format) {
        String str = "";
        try {
            SimpleDateFormat sd = new SimpleDateFormat(format);
            long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
            long nh = 1000 * 60 * 60;// 一小时的毫秒数
            long nm = 1000 * 60;// 一分钟的毫秒数
            long ns = 1000;// 一秒钟的毫秒数long diff;try {
            // 获得两个时间的毫秒时间差异
            long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            long day = diff / nd;// 计算差多少天
            long hour = diff % nd / nh;// 计算差多少小时
            long min = diff % nd % nh / nm;// 计算差多少分钟
            long sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
            if (day > 0) {
                str += day + "天";
            }
            if (hour > 0) {
                str += hour + "小时";
            }
            if (min > 0) {
                str += min + "分钟";
            }
            if (sec > 0) {
                str += sec + "秒";
            }
        } catch (Exception e) {
        }
        return str;
    }
}
