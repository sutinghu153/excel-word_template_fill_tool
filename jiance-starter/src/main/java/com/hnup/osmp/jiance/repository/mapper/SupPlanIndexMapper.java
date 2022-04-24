package com.hnup.osmp.jiance.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnup.osmp.jiance.model.dto.MajorProjectDTO;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;
import com.hnup.osmp.jiance.repository.entity.JcPlanIndex;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface SupPlanIndexMapper extends BaseMapper<JcPlanIndex> {

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