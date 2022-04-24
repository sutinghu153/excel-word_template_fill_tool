package com.hnup.osmp.jiance.utils;

import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.IntFunction;

/**
 * <p>
 * The type DateUtils.
 *
 * @author ChenJie
 * @date 2021 -03-19
 */
public final class DateUtils {


	public static void main(String[] args) {

		Map<String, String> map = new HashMap<>();
		map.put("sutinghu1","1");
		map.put("sutinghu2","2");
		map.put("sutinghu3","3");
		Collection<String> values = map.values();
		Set<String> strings = map.keySet();
		System.out.println(strings);
		System.out.println(values);
	}

	/**
     * 获取 Date 的年份
     *
     * @param date date
     * @return year year
     */
    public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return cal.get(Calendar.YEAR);
	}


	@SneakyThrows
	public static Date str2Date(String date){

    	if (date == null) {
    		return null;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.parse(date);

	}

    /**
     * Time different double
     *
     * @param startTime start time
     * @param endTime   end time
     * @return the double
     */
    public static Double timeDifferent(Date startTime,Date endTime){
		long start = startTime.getTime();
		long end = endTime.getTime();
		Double result = Double.valueOf(end-start);
		result = result/(1000*60*60*24);
		return result;
	}


    /**
     * Get week start date
     *
     * @return the date
     */
    public static Date  getWeekStart(){
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_MONTH, 0);
		cal.set(Calendar.DAY_OF_WEEK, 2);
		Date time=cal.getTime();
		return time;
	}

    /**
     * Get week end date
     *
     * @return the date
     */
    public static Date  getWeekEnd(){
		Calendar cal=Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
		cal.add(Calendar.DAY_OF_WEEK, 1);
		Date time=cal.getTime();
		return time;
	}

    /**
     * Get month day start date
     *
     * @return the date
     */
    public static Date getMonthDayStart(){
		LocalDate today = LocalDate.now();
		LocalDate localDate =null;
		localDate = LocalDate.of(today.getYear(), today.getMonth(), 1);
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		return  Date.from(zonedDateTime.toInstant());
	}


    /**
     * Get month day end date
     *
     * @return the date
     */
    public static Date getMonthDayEnd(){
		LocalDate today = LocalDate.now();
		LocalDate localDate =null;
		localDate = today.with(TemporalAdjusters.lastDayOfMonth());
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
		return  Date.from(zonedDateTime.toInstant());

	}

    /**
     * Gets year day start *
     *
     * @return the year day start
     */
    public static Date getYearDayStart() {
		Date date =new Date();
		Calendar currCal=Calendar.getInstance();
		currCal.setTime(date);
		int year =currCal.get(Calendar.YEAR);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return  sdf.parse(year+"-01-01 00:00:00");
		} catch (ParseException e) {

		}
		return null;
	}

    /**
     * Gets year day end *
     *
     * @return the year day end
     */
    public static Date getYearDayEnd()  {
		Date date =new Date();
		Calendar currCal=Calendar.getInstance();
		currCal.setTime(date);
		int year =currCal.get(Calendar.YEAR);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return  sdf.parse(year+"-12-31 23:59:59");
		} catch (ParseException e) {

		}
		return null;
	}


	private static Date setMinutesAndSeconds(Date date,int hh,int mm ,int ss){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		// 将时分秒,毫秒域清零
		cal1.set(Calendar.HOUR_OF_DAY, hh);
		cal1.set(Calendar.MINUTE, mm);
		cal1.set(Calendar.SECOND, ss);
		return cal1.getTime();
	}
//	public static void main(String[] args)  {
//		double hour =8.5;
//		int days= (int) (hour/7);
//		int hours =(int) (hour%7);
//		int mit =(int) (hour%1*60);
//
//		System.err.println(days);
//		System.err.println(hours);
//		System.err.println(mit);
//	}

	/**
	 * 功能描述: 前移-过去，后移，未来
	 * @author sutinghu
	 * @date
	 * @param date
	 * @param days 参数
	 * @return java.util.Date
	 */
	public static Date dayMove(Date date,Integer days){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		// 把日期往后增加一天,整数  往后推,负数往前移动
		calendar.add(Calendar.DATE,days);
		// 这个时间就是日期往后推一天的结果
		date=calendar.getTime();
		return date;
	}

	/**
	 * 获取指定年、月份的第一天
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getMonthTime(String year,String month){
		String time = year+"-"+month+"-01 00:00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime idt = LocalDateTime.parse(time,formatter);
		Date date = Date.from( idt.atZone( ZoneId.systemDefault()).toInstant());
		return date;
	}
	/**
	 * 获取指定年份的第一天
	 * @param year
	 * @return
	 */
	public static Date getAppointTime(String year){
		String time = year+"-01-01 00:00:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime idt = LocalDateTime.parse(time,formatter);
		Date date = Date.from( idt.atZone( ZoneId.systemDefault()).toInstant());
		return date;
	}

}
