package com.hnup.osmp.jiance.utils;

import com.hnup.osmp.jiance.enums.DataTypeEnum;
import com.hnup.osmp.jiance.enums.UnitMeasureEnum;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *  根据数据类型和计量单位 自动进行简单数学运算工具类
 *  因为业务较简单，仅实现百分比与其他数据求和
 * @data2021/10/26,9:57
 * @authorsutinghu
 */
public class SubDataUtils {

	/**
	 * 功能描述: 根据列表求和
	 * @author sutinghu
	 * @date
	 * @param list 求和列表
	 * @return java.lang.String
	 */
	public static String additionByList(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "0.00" ;
		}

		Double result = 0.00;
		for (String value:list) {
			if (StringUtils.isBlank(value) || Objects.equals(ConstantJiance.NULL,value)) {
				continue;
			}
			result = result + Double.parseDouble(value);
		}

		return String.valueOf(result);
	}

	/**
	 * 功能描述: 根据map求和
	 * @author sutinghu
	 * @date
	 * @param map 参数
	 * @return java.lang.String
	 */
	public static String additionByMap(Map<String,String> map) {

		if (MapUtils.isEmpty(map)) {
			return "0.00" ;
		}

		Double result = 0.00;

		for (Map.Entry<String,String> value : map.entrySet()) {
			if (StringUtils.isBlank(value.getValue()) || Objects.equals(ConstantJiance.NULL,value.getValue())) {
				continue;
			}
			result = result + Double.parseDouble(value.getValue());
		}

		return String.valueOf(result);

	}

	/**
	 * 功能描述: 数据求和
	 * @author sutinghu
	 * @date
	 * @param dataType 数据类型
	 * @param unitType 计量单位
	 * @param value 初始值
	 * @param value2 累计值
	 * @return java.lang.Object
	 */
	public static Object addition(Integer dataType,Integer unitType,String value,Object value2){
		Object result = null;
		if (StringUtils.isBlank(value)) {
			return result;
		}
		// 字符串
		if (Objects.equals(DataTypeEnum.StringType.getIntValue(),dataType)) {
			return value;
		}
		// bool
		if (Objects.equals(DataTypeEnum.BooleanType.getIntValue(),dataType)) {
			return DataTypeToUtils.stringToBoolean(value);
		}
		// 其他
		if (Objects.equals(DataTypeEnum.OtherType.getIntValue(),dataType)) {
			return value;
		}
		// 整型
		if (Objects.equals(dataType, DataTypeEnum.IntegerType.getIntValue())) {
			if (Objects.nonNull(value2)) {
				result = (Integer) value2 + DataTypeToUtils.stringToInteger(value);
			}else {
				result = DataTypeToUtils.stringToInteger(value);
			}
		}
		// 浮点型求和
		if (Objects.equals(dataType, DataTypeEnum.DoubleType.getIntValue())) {
			// 百分比情况
			if (Objects.equals(UnitMeasureEnum.PER.getIntValue(),unitType)) {
				if (Objects.nonNull(value2)) {
					Double dou = DataTypeToUtils.stringToDouble(value);
					Double dou1 = (Double) (value2);
					result = ((dou - 100) * 100 + (dou1 - 100) * 100)/200;
				}else {
					result = DataTypeToUtils.stringToDouble(value);
				}
				// 其它情况
			}else {
				if (Objects.nonNull(value2)) {
					result = (Double) value2 + DataTypeToUtils.stringToDouble(value);
				}else {
					result = DataTypeToUtils.stringToDouble(value);
				}
			}
		}
		return result;
	}

}
