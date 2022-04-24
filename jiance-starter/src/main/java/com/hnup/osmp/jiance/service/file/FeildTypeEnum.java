package com.hnup.osmp.jiance.service.file;

import com.hnup.common.lang.enums.IEnumInt;
import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *  校验字段值类型的枚举
 * @data2021/11/17,14:35
 * @authorsutinghu
 */
public enum  FeildTypeEnum implements IEnumInt {

	/**
	 *  查询结果为模板中普通merge
	 */
	ORDINARY(1,"普通字段"),
	/**
	 *  查询结果为模型中普通表格
	 */
	TABLE(2,"表格字段"),
	/**
	 *  查询结果为模型中普通图表
	 */
	CHART(3,"图表字段"),
	/**
	 *  查询结果为模板中其他情况
	 */
	OTHER(0,"其他");


	private Integer id;

	private String desc;


	FeildTypeEnum(Integer id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	@Override
	public int getIntValue() {
		return this.id;
	}

	public String getDesc() {
		return this.desc;
	}

	public static Map<Integer, String> dataTypeEnumMap(){
		Map<Integer, String> map = new HashMap<>();
		for (FeildTypeEnum feildTypeEnum: values()) {
			map.put(feildTypeEnum.id,feildTypeEnum.desc);
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