package com.hnup.osmp.jiance.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateConfig;

import java.util.List;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface IJcOpEvaluateConfigDAO extends IService<JcOpEvaluateConfig> {
	/**
	 * 获取数量
	 * @param sql
	 * @return
	 */
	Integer getTableCount(String sql);

	/**
	 * 全局参评项目
	 * @param businessKey 业务编号
	 * @param phase 发证阶段
	 * @return
	 */
	Integer getCertificateCount(Long businessKey,String phase);
}







