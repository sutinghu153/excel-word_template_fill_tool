package com.hnup.osmp.jiance.common.cache;

import com.hnup.osmp.jiance.common.Awire;

import java.util.HashMap;
import java.util.Map;

/**
 *  常用数据缓存池——全局唯一
 * @data2021/10/21,17:26
 * @authorsutinghu
 */
public class DataCachePool implements Awire {

	// 唯一对象
	private static DataCachePool dataCachePool;

	private Map<String, CacheData> cacheMap = new HashMap<>();

	private DataCachePool(){}

	public static synchronized DataCachePool getInstance(){
		if (dataCachePool == null){
			return new DataCachePool();
		}
		return dataCachePool;
	}

	/**
	 * 功能描述: 缓存池缓存对象
	 * @author sutinghu
	 * @date
	 * @param cacheData 缓存对象
	 * @param name 缓存对象名
	 * @return void
	 */
	public void setCacheMap(CacheData cacheData,String name) {
		cacheMap.put(name,cacheData);
	}

	/**
	 * 功能描述: 获取缓存池数据
	 * @author sutinghu
	 * @date
	 * @param name 缓存对象名
	 * @return com.hnup.osmp.jiance.common.cache.CacheData
	 */
	public  CacheData getCacheData(String name) {
		CacheData cacheData = cacheMap.get(name);
		if (cacheData == null) {
			return null;
		}
		return cacheData;
	}

	/**
	 * 功能描述: 销毁缓存池中数据
	 * @author sutinghu
	 * @date
	 * @param
	 * @return void
	 */
	public void reMoveData(){
		this.cacheMap.clear();
	}

}
