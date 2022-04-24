package com.hnup.osmp.jiance.enums;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  土地利用分类流向枚举
 * @data2021/11/2,10:56
 * @authorsutinghu
 */
public enum DirectionEnum implements IEnumInt {
	/**
	 *  流入
	 */
	INFLOW(1,"流入"),
	/**
	 *  流出
	 */
	OUTFLOW(2,"流出");

	private int id;


	private String desc;


	DirectionEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public static Map<Integer, String> dataTypeEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (DirectionEnum dataTypeEnum:DirectionEnum.values()) {
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
