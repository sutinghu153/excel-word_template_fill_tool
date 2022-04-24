package com.hnup.osmp.jiance.operation.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  四则运算常量类
 * @data2021/11/10,15:05
 * @authorsutinghu
 */
public  class ConstantOperation {

	/**
	 *  设计运算符优先级
	 */
	public final static Map<String, Integer> OPERATOR_PRIORITY = new HashMap<String, Integer>(){
		{
			put("*", 2);
			put("/", 2);
			put("+", 1);
			put("-", 1);
		}
	};

	/**
	 *  支持的运算符
	 */
	public final static List<String> OPERATOR = new ArrayList<String>(){{
		add("+");
		add("-");
		add("*");
		add("/");
	}};

	/**
	 *  左右括号
	 */
	public final static String BRACKET_LEFT = "(" ;
	public final static String BRACKET_RIGHT = ")" ;


}
