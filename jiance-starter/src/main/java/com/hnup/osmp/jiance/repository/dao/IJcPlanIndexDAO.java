package com.hnup.osmp.jiance.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnup.osmp.jiance.model.dto.MajorProjectDTO;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;
import com.hnup.osmp.jiance.repository.entity.JcPlanIndex;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface IJcPlanIndexDAO extends IService<JcPlanIndex> {
	List<MeetingDTO> getDate();

	List<MajorProjectDTO> getMajorProjectList();

	List<Map<String, Object>> countHandle();

	List<Map<String, Object>> countTrend();

	List<Map<String, Object>> countDepartmentZdlTopFive();

	List<Map<String, Object>> countDepartmentBjlTopFive();

	List<Map<String, Object>> countRegionAccept();

	List<Map<String, Object>> countRegionFinish();

	List<Map<String, Object>> countMapData();
}







