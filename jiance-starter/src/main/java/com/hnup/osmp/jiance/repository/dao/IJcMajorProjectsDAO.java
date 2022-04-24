package com.hnup.osmp.jiance.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import com.hnup.osmp.jiance.repository.entity.JcMajorProjects;

import java.util.Date;
import java.util.List;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface IJcMajorProjectsDAO extends IService<JcMajorProjects> {


	List<OpMonitorDTO> getDetailList(Date startTime,
									 Date endTime,
									 String deptName);

}







