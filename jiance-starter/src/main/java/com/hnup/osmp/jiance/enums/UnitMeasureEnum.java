package com.hnup.osmp.jiance.enums;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  计量单位枚举类
 * @data2021/10/20,15:45
 * @authorsutinghu
 */
public enum  UnitMeasureEnum implements IEnumInt {

	/**
	 *  kg
	 */
	KG(1,"千克"),
	/**
	 *  km
	 */
	KM(2,"千米"),
	/**
	 *  百分比
	 */
	PER(3,"百分比"),
	/**
	 *  平方公里
	 */
	KM2(4,"平方公里"),
	/**
	 *  平方米
	 */
	M2(5,"平方米"),
	/**
	 *  米
	 */
	M(6,"米"),
	/**
	 *  米
	 */
	TIME(7,"时间"),

	OTHER(8,"其它"),

	PEO(9,"万人");

	private int id;

	private String desc;

	UnitMeasureEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public String getDesc(){
		return this.desc;
	}

	public static Map<Integer, String> unitMeasureEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (UnitMeasureEnum unitMeasureEnum:UnitMeasureEnum.values()) {
			map.put(unitMeasureEnum.id,unitMeasureEnum.desc);
		}
		return map;
	}

	public static String getDesc(Integer id){
		String desc = null;
		Map<Integer, String> map = unitMeasureEnumMap();
		desc = map.get(id);
		if (StringUtils.isEmpty(desc)) {
			throw new DeclareException("不存在的枚举");
		}
		return desc;
	}
}
