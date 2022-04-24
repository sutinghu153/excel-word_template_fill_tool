package com.hnup.osmp.jiance.service.file;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  解析SQL语句
 * @data2021/11/17,16:21
 * @authorsutinghu
 */
public class FeildSqlMap {


	/**
	 * 功能描述:数据库SQL语句切割为可查询语句
	 * @author sutinghu
	 * @date
	 * @param sqlText 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>
	 */
	public static List<Map<String, String>> curSql(String sqlText,
												   Boolean executeDate,
												   String year,
												   String month,
												   String day){

		String[] strings = sqlText.split(ConstantJiance.CUR_SIGN);

		if (Objects.equals(strings.length,0)) {
			throw new DeclareException("警告！未知异常");
		}

		List<Map<String, String>> result = new ArrayList<>();
		for (String string:strings) {
			if (executeDate) {
				string = replaceDate(string,year,month,day);
			}
			Map<String, String> map = new HashMap<>();
			// 存在表中表结构
			if (string.contains(ConstantJiance.CHECK_CUR_LEFT) && string.contains(ConstantJiance.CHECK_CUR_RIGHT)) {
				map.put("TYPE", FeildTypeEnum.TABLE.getDesc());
				String[] sqls = string.split(ConstantJiance.CUR_LEFT);
				map.put("SQL",sqls[0]);
				map.put("TABLE_NAME",sqls[1].split(ConstantJiance.CUR_RIGHT)[0]);
				// 普通字段
			}else if (!string.contains(ConstantJiance.CHECK_CUR_LEFT) && !string.contains(ConstantJiance.CHECK_CUR_RIGHT)) {
				map.put("TYPE", FeildTypeEnum.ORDINARY.getDesc());
				map.put("SQL",string);
			}else if (string.contains(ConstantJiance.CHECK_TABLE_CUR_LEFT) && string.contains(ConstantJiance.CHECK_TABLE_CUR_RIGHT)){
				map.put("TYPE", FeildTypeEnum.CHART.getDesc());
				String[] sqls = string.split(ConstantJiance.CUR_TABLE_LEFT);
				map.put("SQL",sqls[0]);
				map.put("CHART_NAME",sqls[1].split(ConstantJiance.CUR_TABLE_RIGHT)[0]);
			}else {
				throw new DeclareException("异常SQL，请重新配置");
			}
			result.add(map);
		}
		return result;
	}

	public static String replaceDate(String sql,
									 String year,
									 String month,
									 String day){

		String StartTime = "";
		String EndTime = "";
		if (StringUtils.isNotBlank(year)
			&& StringUtils.isNotBlank(month)
			&& StringUtils.isNotBlank(day)) {

			StartTime = "'"+year+"-"+month+"-"+day+" "+"00:00:00"+"'";
			EndTime   = "'"+year+"-"+month+"-"+day+" "+"23:59:59"+"'";

		}else if (StringUtils.isNotBlank(year)
			&& StringUtils.isNotBlank(month)
			&& !StringUtils.isNotBlank(day)) {

			StartTime = "'"+year+"-"+month+"-"+"01"+" "+"00:00:00"+"'";

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.parseInt(year));
			calendar.set(Calendar.MONTH, Integer.parseInt(month)-1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
			EndTime = "'" + format.format(calendar.getTime())+""+"23:59:59"+"'";
		}else if(StringUtils.isNotBlank(year)
			&& !StringUtils.isNotBlank(month)
			&& !StringUtils.isNotBlank(day)){

			StartTime = "'"+year+"-"+"01"+"-"+"01"+" "+"00:00:00"+"'";
			EndTime   = "'"+year+"-"+"12"+"-"+"31"+" "+"23:59:59"+"'";

		}

		sql = sql.replace("'StartTime'",StartTime);
		sql = sql.replace("'EndTime'",EndTime);

		return sql;
	}

}
