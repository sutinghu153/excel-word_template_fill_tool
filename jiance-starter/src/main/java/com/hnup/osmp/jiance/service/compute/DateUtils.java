package com.hnup.osmp.jiance.service.compute;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @data2021/8/12,19:10
 * @authorMSI
 */
public final class DateUtils {

	public final static String YEAR = "年";
	public final static String SEASON = "季";
	public final static String MONTH = "月";
	public final static String WEEK = "周";

	public final static int ZERO = 0;
	public final static int ONE = 1;
	public final static int TWO = 2;

	/**
	 * 获取指定日期的开始时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		instance.set(Calendar.HOUR_OF_DAY, ZERO);
		instance.set(Calendar.MINUTE, ZERO);
		instance.set(Calendar.SECOND, ZERO);
		return instance.getTime();
	}
	/**
	 * 获取当前时间的年、月、季、周的第一天
	 * sutinghu
	 */
	public static Date firstDay(Date nowTime , String type){
		Calendar calendar = new GregorianCalendar();
		if (YEAR.equals(type)){
			calendar.setTime(nowTime);
			calendar.add(Calendar.DAY_OF_YEAR,ZERO);
			calendar.set(Calendar.DAY_OF_YEAR,ONE);
		}
		if (SEASON.equals(type)){
			calendar.setTime(nowTime);
			int month = calendar.get(Calendar.MONTH)+ONE;
			if (month >= ONE && month <= ONE + TWO) {
				calendar.set(Calendar.MONTH,ZERO);
				calendar.set(Calendar.DAY_OF_MONTH,ONE);
			} else if (month >= TWO*TWO && month <= TWO+TWO+TWO) {
				calendar.set(Calendar.MONTH,ONE + TWO);
				calendar.set(Calendar.DAY_OF_MONTH,ONE);
			} else if (month >= TWO+TWO+TWO+ONE && month <= TWO*TWO*TWO+ONE) {
				calendar.set(Calendar.MONTH,TWO+TWO+TWO);
				calendar.set(Calendar.DAY_OF_MONTH,ONE);
			} else {
				calendar.set(Calendar.MONTH,TWO*TWO*TWO+ONE);
				calendar.set(Calendar.DAY_OF_MONTH,ONE);
			}
		}
		if (MONTH.equals(type)){
			calendar.setTime(nowTime);
			calendar.add(Calendar.MONTH,ZERO);
			calendar.set(Calendar.DAY_OF_MONTH,ONE);
		}
		if (WEEK.equals(type)){
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(nowTime);
			calendar.set(Calendar.DAY_OF_WEEK,calendar.getFirstDayOfWeek());
		}
		return getStartDate(calendar.getTime());
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

	/**
	 * 日期前后移动某些天数
	 * sutinghu
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
	 * 获取日期当前月份的最后一天
	 * @return
	 */
	public static Date getMonthLastDay (Date monthTime){
		Calendar starCalendar=Calendar.getInstance();
		starCalendar.setTime(monthTime);
		//月份设置为下个月
		starCalendar.add(Calendar.MONTH, 1);
		//日期设置为1号
		starCalendar.set(Calendar.DAY_OF_MONTH,1);
		//倒回到前一天
		starCalendar.add(Calendar.SECOND, -1);
		return starCalendar.getTime();
	}

}
