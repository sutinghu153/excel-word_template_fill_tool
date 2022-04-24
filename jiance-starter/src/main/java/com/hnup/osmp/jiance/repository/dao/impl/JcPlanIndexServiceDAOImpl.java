package com.hnup.osmp.jiance.repository.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnup.osmp.jiance.model.dto.MajorProjectDTO;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;
import com.hnup.osmp.jiance.repository.dao.IJcPlanIndexDAO;
import com.hnup.osmp.jiance.repository.entity.JcPlanIndex;
import com.hnup.osmp.jiance.repository.mapper.SupPlanIndexMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Service
public class JcPlanIndexServiceDAOImpl extends ServiceImpl<SupPlanIndexMapper, JcPlanIndex> implements IJcPlanIndexDAO {


	@Override
	public List<MeetingDTO> getDate() {
		return this.baseMapper.getDate();
	}

	@Override
	public List<MajorProjectDTO> getMajorProjectList() {
		return this.baseMapper.getMajorProjectList();
	}

	@Override
	public List<Map<String, Object>> countHandle() {
		return this.baseMapper.countHandle();
	}

	@Override
	public List<Map<String, Object>> countTrend() {
		return this.baseMapper.countTrend();
	}

	@Override
	public List<Map<String, Object>> countDepartmentZdlTopFive() {
		return this.baseMapper.countDepartmentZdlTopFive();
	}

	@Override
	public List<Map<String, Object>> countDepartmentBjlTopFive() {
		return this.baseMapper.countDepartmentBjlTopFive();
	}

	@Override
	public List<Map<String, Object>> countRegionAccept() {
		return this.baseMapper.countRegionAccept();
	}

	@Override
	public List<Map<String, Object>> countRegionFinish() {
		return this.baseMapper.countRegionFinish();
	}

	@Override
	public List<Map<String, Object>> countMapData() {
		return this.baseMapper.countMapData();
	}
}