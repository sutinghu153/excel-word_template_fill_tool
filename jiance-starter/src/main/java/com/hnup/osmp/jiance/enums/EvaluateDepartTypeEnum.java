package com.hnup.osmp.jiance.enums;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  评价部门枚举类
 */
public enum EvaluateDepartTypeEnum implements IEnumInt {
	/**
	 *  窗口服务
	 */
	WINDOWSERVICE(1,"窗口服务"),
	/**
	 *  行政审批
	 */
	OPSERVICE(2,"行政审批"),

	/**
	 *  勘测院
	 */
	KANCE(3,"勘测院"),

	/**
	 *  信息中心
	 */
	INFO_CENTER(4,"信息中心");


	private Integer id;


	private String desc;


	EvaluateDepartTypeEnum(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public static Map<Integer, String> EvaluateDepartTypeEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (EvaluateDepartTypeEnum evaluateDepartTypeEnum:EvaluateDepartTypeEnum.values()) {
			map.put(evaluateDepartTypeEnum.id,evaluateDepartTypeEnum.desc);
		}
		return map;
	}

	public static String getDesc(Integer id){
		String desc = null;
		Map<Integer, String> map = EvaluateDepartTypeEnumMap();
		desc = map.get(id);
		if (StringUtils.isEmpty(desc)) {
			throw new DeclareException("不存在的枚举");
		}
		return desc;
	}
}
