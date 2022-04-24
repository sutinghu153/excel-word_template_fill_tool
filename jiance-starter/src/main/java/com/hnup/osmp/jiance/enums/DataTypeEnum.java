package com.hnup.osmp.jiance.enums;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  数据类型枚举类
 * @data2021/10/20,15:33
 * @authorsutinghu
 */
public enum DataTypeEnum implements IEnumInt {

	/**
	 *  字符串
	 */
	StringType(1,"字符串"),
	/**
	 *  整型
	 */
	IntegerType(2,"整型"),
	/**
	 *  浮点型
	 */
	DoubleType(3,"浮点型"),
	/**
	 *  布尔值
	 */
	BooleanType(4,"布尔值"),
	/**
	 *  其他
	 */
	OtherType(5,"其他");

	private int id;


	private String desc;


	DataTypeEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public static Map<Integer, String> dataTypeEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (DataTypeEnum dataTypeEnum:DataTypeEnum.values()) {
			map.put(dataTypeEnum.id,dataTypeEnum.desc);
		}
		return map;
	}

	public static String getDesc(Integer id){
		String desc = null;
		Map<Integer, String> map = dataTypeEnumMap();
		desc = map.get(id);
		if (StringUtils.isEmpty(desc)) {
			throw new DeclareException("不存在的枚举");
		}
		return desc;
	}
}
