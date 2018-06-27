package com.portal.common.utils;

import com.xiaoleilu.hutool.date.DateField;
import com.xiaoleilu.hutool.date.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 根据两个时间获取范围内所有日期
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 返回所有日期集合
     */
    public static List<String> getDateList(Date startDate, Date endDate, int dateField){
        String format = "yyyy-MM";
        if(dateField == Calendar.DAY_OF_MONTH) {
            format = "MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        List<String> dateList = new ArrayList<String>();
        dateList.add(sdf.format(startDate));// 把开始时间加入集合
        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(startDate);
        while (true) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(dateField, 1);
            // 测试此日期是否在指定日期之后
            if (endDate.after(cal.getTime())) {
                if(!sdf.format(endDate).equals(sdf.format(cal.getTime()))){//不是同一天才添加
                    dateList.add(sdf.format(cal.getTime()));
                }
            } else {
                break;
            }
        }
        if(!sdf.format(startDate).equals(sdf.format(endDate))){
            dateList.add(sdf.format(endDate));// 把结束时间加入集合
        }
        return dateList;
    }

    /**
     * 根据时间取得该天所有小时,格式yyyy-MM-dd HH
     * @param date 日期
     * @return 返回所有小时集合
     */
    public static List<String> getHourListByDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<String>();
        String day = sdf.format(date);
        String hour = null;
        for(int i = 0; i < 24; i++){
            if(i<10){
                hour = "0"+i;
            }
            else{
                hour = i+"";
            }
            dateList.add(day+" "+hour);
        }
        return dateList;
    }

    /**
     * 根据时间取得该天到当前小时的所有小时(如果不是当天,则返回所有小时),格式yyyy-MM-dd HH
     * @param date 日期
     * @return 返回所有小时集合
     */
    public static List<String> getHourListByNow(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<String>();
        String day = sdf.format(date);
        String hour = null;
        Calendar cal = Calendar.getInstance();
        int maxHour = cal.get(Calendar.HOUR_OF_DAY);
        if(!day.equals(sdf.format(new Date()))){
            maxHour = 23;
        }
        for(int i = 0; i <= maxHour; i++){
            if(i<10){
                hour = "0"+i;
            }
            else{
                hour = i+"";
            }
            dateList.add(day+" "+hour);
        }
        return dateList;
    }

    /**
     * 获取多少天之前到今天的日期集合
     * @param beforeDay 几天之前,如果要计算30天之前,则传入29
     * @return 返回所有日期集合
     */
    public static List<String> getBeforeDateList(int beforeDay){
        if(beforeDay<0){
            return new ArrayList<String>();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dateList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -beforeDay);
        Date startDate = cal.getTime();
        dateList.add(sdf.format(startDate));
        Date now = new Date();
        while (true) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (now.after(cal.getTime())) {
                if(!sdf.format(now).equals(sdf.format(cal.getTime()))){//不是同一天才添加
                    dateList.add(sdf.format(cal.getTime()));
                }
            } else {
                break;
            }
        }
        if(!sdf.format(startDate).equals(sdf.format(now))){
            dateList.add(sdf.format(now));// 把结束时间加入集合
        }
        return dateList;
    }

}
