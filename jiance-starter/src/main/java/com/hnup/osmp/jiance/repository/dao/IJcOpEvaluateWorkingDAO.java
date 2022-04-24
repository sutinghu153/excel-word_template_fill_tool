package com.hnup.osmp.jiance.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateWorking;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface IJcOpEvaluateWorkingDAO extends IService<JcOpEvaluateWorking> {
	/**
	 * 功能描述: 获取表数据
	 * @author sutinghu
	 * @date
	 * @param sql 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	List<Map<String, Object>> getTableDetails(String sql);
}







