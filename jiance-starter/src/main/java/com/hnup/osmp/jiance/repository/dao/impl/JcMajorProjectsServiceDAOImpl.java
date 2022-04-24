package com.hnup.osmp.jiance.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import com.hnup.osmp.jiance.repository.dao.IJcMajorProjectsDAO;
import com.hnup.osmp.jiance.repository.entity.JcMajorProjects;
import com.hnup.osmp.jiance.repository.mapper.JcMajorProjectsMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Service
public class JcMajorProjectsServiceDAOImpl extends ServiceImpl<JcMajorProjectsMapper, JcMajorProjects> implements IJcMajorProjectsDAO {


	@Override
	public List<OpMonitorDTO> getDetailList(Date startTime, Date endTime, String deptName) {
		return this.baseMapper.getDetailList(startTime, endTime, deptName);
	}
}