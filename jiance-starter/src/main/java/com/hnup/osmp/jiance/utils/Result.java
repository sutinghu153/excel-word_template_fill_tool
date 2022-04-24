/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.hnup.osmp.jiance.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Result extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private final static int SC_INTERNAL_SERVER_ERROR = 500;

	public Result setData(Object data) {
		put("data",data);
		return this;
	}
	public Result() {
		put("code", 0);
		put("msg", "success");
	}
	
	public static Result error() {
		return error(SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static Result error(String msg) {
		return error(SC_INTERNAL_SERVER_ERROR, msg);
	}
	
	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}
	
	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	public static Result ok() {
		return new Result();
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public Integer getCode() {

		return (Integer) this.get("code");
	}

}
