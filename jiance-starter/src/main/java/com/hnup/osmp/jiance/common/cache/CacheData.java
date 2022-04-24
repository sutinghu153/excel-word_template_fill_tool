package com.hnup.osmp.jiance.common.cache;

/**
 *  被缓存对象
 * @data2021/10/21,17:30
 * @authorsutinghu
 */
public class CacheData {

	/**
	 *  类型全量名
	 */
	private String onlyName;

	/**
	 *  对象
	 */
	private Object object;

	public CacheData(String onlyName,Object object){
		this.object = object;
		this.onlyName = onlyName;
	}

}
