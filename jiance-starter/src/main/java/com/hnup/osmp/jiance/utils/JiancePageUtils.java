package com.hnup.osmp.jiance.utils;

import java.util.List;

/**
 *  分页
 * @data2021/11/11,14:56
 * @authorsutinghu
 */
public class JiancePageUtils {

	/**
	 * 开始分页
	 * @param list
	 * @param pageNum 页码
	 * @param pageSize 每页多少条数据
	 * @return
	 */
	public static List startPage(List list, Integer pageNum,
								 Integer pageSize) {
		if (list == null) {
			return null;
		}
		if (list.size() == 0) {
			return null;
		}
		// 记录总数
		Integer count = list.size();

		// 开始索引
		int fromIndex = 0;
		// 结束索引
		int toIndex = 0;

		if (pageSize*(pageNum) <= count){
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = fromIndex + pageSize;
		}else if (count < pageSize * pageNum && pageSize * (pageNum-1) < count){
			fromIndex = (pageNum - 1) * pageSize;
			toIndex = count;
		}else {
			return null;
		}

		List pageList = list.subList(fromIndex, toIndex);

		return pageList;
	}

}