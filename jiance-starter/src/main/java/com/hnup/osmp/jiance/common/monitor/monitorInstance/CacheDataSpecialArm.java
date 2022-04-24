package com.hnup.osmp.jiance.common.monitor.monitorInstance;

import com.hnup.osmp.jiance.common.cache.CacheData;
import com.hnup.osmp.jiance.common.cache.DataCachePool;
import com.hnup.osmp.jiance.common.monitor.SpecialArm;

/**
 *  缓存池处理特种兵
 * @data2021/10/21,18:13
 * @authorsutinghu
 */
public class CacheDataSpecialArm implements SpecialArm {

	/**
	 *  清除所有数据源
	 */
	@Override
	public void excute() {
		DataCachePool.getInstance().reMoveData();
	}

	/**
	 *  新增数据源
	 * @param cacheData
	 * @param name
	 */
	public void excuteUpdate(CacheData cacheData, String name) {
		DataCachePool.getInstance().setCacheMap(cacheData, name);
	}

	public CacheData getCacheData(String name) {
		return DataCachePool.getInstance().getCacheData(name);
	}
}
