package com.hnup.osmp.jiance.service.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @data2022/3/22,10:53
 * @author sutinghu
 */
public class DateCheckService {

	/**
	 * 功能描述: 判断传入时间是否是当前的
	 * 			如果当前时间还没结束 则执行更新程序
	 * @author sutinghu
	 * @date
	 * @param year
	 * @param month
	 * @param day 参数
	 * @return java.lang.Boolean
	 */
	public static Boolean checkDate(String year, String month, String day){

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		int years = (calendar.get(Calendar.YEAR));

		int months = (calendar.get(Calendar.MONTH)+1);

		// 如果当前要获取 year month day 的数据 不做处理
		if (StringUtils.isNotBlank(String.valueOf(year))
			&& StringUtils.isNotBlank(String.valueOf(month))
			&& StringUtils.isNotBlank(String.valueOf(day))) {

			return true;
		}
		// 如果当前要获取 year month 的数据 如果对应的month还没有结束 就重新生成
		else if (StringUtils.isNotBlank(String.valueOf(year))
			&& StringUtils.isNotBlank(String.valueOf(month))
			&& !StringUtils.isNotBlank(String.valueOf(day))) {
			int i = Integer.parseInt(month);
			int parseInt = Integer.parseInt(year);
			if (parseInt<years) {
				return true;
			}else if (parseInt==years){
				if (i<months) {
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}
		// 如果当前要获取 year 的数据
		else if (StringUtils.isNotBlank(String.valueOf(year))
			&& !StringUtils.isNotBlank(String.valueOf(month))
			&& !StringUtils.isNotBlank(String.valueOf(day))) {
			if (Integer.parseInt(year)<years) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

}
