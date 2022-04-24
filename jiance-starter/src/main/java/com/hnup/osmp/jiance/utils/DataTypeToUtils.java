package com.hnup.osmp.jiance.utils;

import com.hnup.osmp.jiance.enums.DataTypeEnum;
import com.hnup.osmp.jiance.enums.UnitMeasureEnum;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *  数据类型转换工具类
 * @data2021/10/22,10:56
 * @authorsutinghu
 */
public class DataTypeToUtils {



	public static Double stringToDouble(String value){
		return Double.parseDouble(value);
	}

	public static Integer stringToInteger(String value){
		return Integer.parseInt(value);
	}

	public static Boolean stringToBoolean(String value){
		return Boolean.parseBoolean(value);
	}

	public static Object toDataType(Integer type,String value){
		// 浮点型
		if (Objects.equals(DataTypeEnum.DoubleType.getIntValue(),type)) {
			return DataTypeToUtils.stringToDouble(value);
		}
		// 整型
		if (Objects.equals(DataTypeEnum.IntegerType.getIntValue(),type)) {
			return DataTypeToUtils.stringToInteger(value);
		}
		// bool
		if (Objects.equals(DataTypeEnum.BooleanType.getIntValue(),type)) {
			return DataTypeToUtils.stringToBoolean(value);
		}
		// 百分比
		if (Objects.equals(DataTypeEnum.StringType.getIntValue(),type)) {
			return value;
		}
		return null;
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		System.out.println(year);
	}

}
