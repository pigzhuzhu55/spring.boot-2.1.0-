package com.wolf.dearcc.common.utils;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取当前周的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    /**
     * 获取当前周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_WEEK, 1);
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 7);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    /**
     * 获取当前月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(date);
            cal.set(Calendar.DAY_OF_MONTH, 1);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    /**
     * 获取当前月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);
            cal.set(Calendar.DAY_OF_MONTH, 0);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return cal.getTime();
    }

    /**
     * 获取当前周的第一天,周一为第一天，周日为第七天
     * @param date
     * @return
     */
    public static int getWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK)-1;
        return week<=0?7:week;
    }

    public static int getHour(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinites(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static int getMonth(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 增加时间
     * @param date
     * @param caltype addYears 1 addMonths 2 addWeeks 3 addDays 5 addHours 11 addMinutes 12 addSeconds 13 addMilliseconds 14
     * @param offset
     * @return
     */
    public static Date addtime(Date date,int caltype,int offset)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(caltype,offset);
        return cal.getTime();
    }

    /**
     * 时间区域转换 5分钟算作一个槽,用5个长整型的长度换算为二进制位的标志位01
     * @param date
     * @param minutes
     * @return 大小为5的long数组
     */
    public static long[] TimeTo5MinuteFiexd(Date date,int minutes)
    {
        int m = getMinites(date);
        int h = getHour(date);
        Date date2 = addtime(date,Calendar.MINUTE,minutes);
        int m2 = getMinites(date2);
        int h2 = getHour(date2);
        int begin = h * 12 + m/5;
        int end = h2*12 + (m2+4)/5;

        //0-287 为5分钟一个槽
        StringBuilder sb = new StringBuilder();
        sb.append("00000000000000000000000000000000");
        for(int i = 0; i < 288; i++)
        {
            if(i >= begin && i <= end)
            {
                sb.append(1);
            }
            else
            {
                sb.append(0);
            }
        }

        BigInteger b1 = new BigInteger(sb.substring(0,64), 2);
        BigInteger b2 = new BigInteger(sb.substring(64,128), 2);
        BigInteger b3 = new BigInteger(sb.substring(128,192), 2);
        BigInteger b4 = new BigInteger(sb.substring(192,256), 2);
        BigInteger b5 = new BigInteger(sb.substring(256,320), 2);

        return new long[]{
                Long.parseLong(b1.toString()),
                Long.parseLong(b2.toString()),
                Long.parseLong(b3.toString()),
                Long.parseLong(b4.toString()),
                Long.parseLong(b5.toString())};
    }
}
