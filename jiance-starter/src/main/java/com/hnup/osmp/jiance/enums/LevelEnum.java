package com.hnup.osmp.jiance.enums;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  级别枚举
 * @data2021/10/20,15:42
 * @authorsutinghu
 */
public enum LevelEnum implements IEnumInt {
	/**
	 *  1
	 */
	LEVEL_ONE(1,"一级分类"),
	/**
	 *  2
	 */
	LEVEL_TWO(2,"二级分类"),

	/**
	 *  3
	 */
	LEVEL_THREE(3,"三级分类");

	private int id;


	private String desc;


	LevelEnum(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public static Map<Integer, String> levelEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (LevelEnum levelEnum:LevelEnum.values()) {
			map.put(levelEnum.id,levelEnum.desc);
		}
		return map;
	}

	public static String getDesc(Integer id){
		String desc = null;
		Map<Integer, String> map = levelEnumMap();
		desc = map.get(id);
		if (StringUtils.isEmpty(desc)) {
			throw new DeclareException("不存在的枚举");
		}
		return desc;
	}
}
