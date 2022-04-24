package com.hnup.osmp.jiance.enums;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 评价满意度枚举
 */
public enum EvaluateSatisfactionEnum implements IEnumInt {
	/**
	 *  非常不满意
	 */
	VERYDISSATISFIED(-1,"非常不满意"),
	/**
	 *  不满意
	 */
	DISSATISFIED(1,"不满意"),

	/**
	 *  一般
	 */
	GENERAL(2,"一般"),

	/**
	 *  较满意
	 */
	SATISFACTORY(3,"较满意"),
	/**
	 *  较满意
	 */
	GREATSATISFACTION(4,"非常满意");


	private Integer id;


	private String desc;


	EvaluateSatisfactionEnum(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public static Map<Integer, String> EvaluatesatiSfactionEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (EvaluateSatisfactionEnum ealuatesatiSfactionEnum:EvaluateSatisfactionEnum.values()) {
			map.put(ealuatesatiSfactionEnum.id,ealuatesatiSfactionEnum.desc);
		}
		return map;
	}

	public static String getDesc(Integer id){
		String desc = null;
		Map<Integer, String> map = EvaluatesatiSfactionEnumMap();
		desc = map.get(id);
		if (StringUtils.isEmpty(desc)) {
			throw new DeclareException("不存在的枚举");
		}
		return desc;
	}
}
