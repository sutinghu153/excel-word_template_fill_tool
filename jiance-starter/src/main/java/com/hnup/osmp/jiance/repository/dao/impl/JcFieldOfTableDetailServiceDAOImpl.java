package com.hnup.osmp.jiance.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnup.osmp.jiance.repository.dao.IJcFieldOfTableDetailDAO;
import com.hnup.osmp.jiance.repository.entity.JcFieldOfTableDetails;
import com.hnup.osmp.jiance.repository.mapper.SupFieldOfTableDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Service
public class JcFieldOfTableDetailServiceDAOImpl extends ServiceImpl<SupFieldOfTableDetailMapper, JcFieldOfTableDetails> implements IJcFieldOfTableDetailDAO {


	@Override
	public List<Map<String, Object>> getTableDetails(String sql) {
		return this.baseMapper.getTableDetails(sql);
	}

	@Override
	public void insertByMap(String tableName, Map<String, Object> columnMap, List<Map<String, Object>> mapList) {
		this.baseMapper.insertByMap(tableName, columnMap, mapList);
	}

	@Override
	public void updateByMap(String tableName, String id, String resultName, List<Map<Object, Object>> mapList) {
		this.baseMapper.updateByMap(tableName, id, resultName, mapList);
	}

	@Override
	public Map<String, Object> getTemplateOrdinary(String sql) {
		return this.baseMapper.getTemplateOrdinary(sql);
	}

	@Override
	public List<Map<String, Object>> getTemplateList(String sql) {
		return this.baseMapper.getTemplateList(sql);
	}


}