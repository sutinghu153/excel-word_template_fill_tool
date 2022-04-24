package com.hnup.osmp.jiance.service.majorproject;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.model.dto.MajorProjectDTO;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcMajorProjectsDAO;
import com.hnup.osmp.jiance.repository.dao.IJcPlanIndexDAO;
import com.hnup.osmp.jiance.repository.entity.JcMajorProjects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  监管——重大项目接口
 * @data2021/12/17,9:46
 * @authorsutinghu
 */
@Service
public class MajorProjectsSerivce {


	@Autowired
	private IJcMajorProjectsDAO iJcMajorProjects;

	@Autowired
	private IJcPlanIndexDAO jcPlanIndex;

	/**
	 * 功能描述:
	 *
	 * @param
	 * @param startTime
	 * @param endTime
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @author sutinghu
	 * @date
	 */
	public Map<String, Object> getMajorIndex(Date startTime, Date endTime) {

		Map<String, Object> result = new HashMap<>(16);

		List<JcMajorProjects> jcMajorProjects = iJcMajorProjects
			.lambdaQuery()
			.ge(startTime != null, JcMajorProjects::getCreatTime, startTime)
			.le(endTime != null, JcMajorProjects::getCreatTime, endTime)
			.list();

		if (CollectionUtils.isEmpty(jcMajorProjects)) {
			return Maps.newHashMap();
		}

		// 建设性质统计
		Map<String, List<JcMajorProjects>> listMap = jcMajorProjects
			.stream()
			.collect(Collectors.groupingBy(JcMajorProjects::getBuildQuality));

		List<Map<String, Object>> buildquilty = new ArrayList<>(3);

		for (Map.Entry<String, List<JcMajorProjects>> map : listMap.entrySet()) {
			Map<String, Object> v = new HashMap<>();
			v.put("value", map.getKey());
			v.put("size", map.getValue().size());
			buildquilty.add(v);
		}

		result.put("建设性质", buildquilty);

		// 建设进度统计
		Map<String, List<JcMajorProjects>> progressMap = jcMajorProjects
			.stream()
			.collect(Collectors.groupingBy(JcMajorProjects::getProgress));

		List<Map<String, Object>> progresses = new ArrayList<>(3);
		for (Map.Entry<String, List<JcMajorProjects>> map : progressMap.entrySet()) {
			Map<String, Object> v = new HashMap<>();
			v.put("value", map.getKey());
			v.put("size", map.getValue().size());
			progresses.add(v);
		}

		result.put("建设进度", progresses);

		// 异常项目统计

		return result;
	}


	/**
	 * 功能描述:
	 *
	 * @param startTime
	 * @param endTime   参数
	 * @return java.util.Map<java.lang.String, java.lang.Object>
	 * @author sutinghu
	 * @date
	 */
	public Map<String, Object> getMajorDetail(Date startTime, Date endTime) {

		Map<String, Object> result = new HashMap<>(16);

		List<JcMajorProjects> jcMajorProjects = iJcMajorProjects
			.lambdaQuery()
			.ge(startTime != null, JcMajorProjects::getCreatTime, startTime)
			.le(endTime != null, JcMajorProjects::getCreatTime, endTime)
			.list();

		if (CollectionUtils.isEmpty(jcMajorProjects)) {
			return Maps.newHashMap();
		}

		// 建设性质统计
		Map<String, List<JcMajorProjects>> listMap = jcMajorProjects
			.stream()
			.collect(Collectors.groupingBy(JcMajorProjects::getBuildQuality));

		List<Map<String, Object>> buildquilty = new ArrayList<>(3);

		for (Map.Entry<String, List<JcMajorProjects>> map : listMap.entrySet()) {
			Map<String, Object> v = new HashMap<>();
			v.put("value", map.getKey());
			v.put("size", map.getValue().size());
			BigDecimal all = new BigDecimal(0);
			for (JcMajorProjects jcMajorProject : map.getValue()) {
				if (jcMajorProject.getArea() != null) {
					all = ComputeBase.addCompute(all, jcMajorProject.getArea());
				} else {
					continue;
				}
			}
			v.put("area", all);
			buildquilty.add(v);
		}
		result.put("建设性质", buildquilty);

		// 建设进度统计
		Map<String, List<JcMajorProjects>> progressMap = jcMajorProjects
			.stream()
			.collect(Collectors.groupingBy(JcMajorProjects::getProgress));

		List<Map<String, Object>> progresses = new ArrayList<>(3);
		for (Map.Entry<String, List<JcMajorProjects>> map : progressMap.entrySet()) {
			Map<String, Object> v = new HashMap<>();
			v.put("value", map.getKey());
			v.put("size", map.getValue().size());
			BigDecimal all = new BigDecimal(0);
			for (JcMajorProjects jcMajorProject : map.getValue()) {
				if (jcMajorProject.getArea() != null) {
					all = ComputeBase.addCompute(all, jcMajorProject.getArea());
				} else {
					continue;
				}
			}
			v.put("area", all);
			progresses.add(v);
		}

		result.put("建设进度", progresses);

		return result;
	}

	public  List<Map<String, Object>> getStateCount(String year) {

		List<MajorProjectDTO> majorProjectList = jcPlanIndex.getMajorProjectList();

		if (CollectionUtils.isEmpty(majorProjectList)) {
			return Lists.newArrayList();
		}

		Map<String, List<MajorProjectDTO>> collect = majorProjectList
			.stream()
			.filter(e -> e.getPrjYear() != null)
			.filter(e -> Objects.equals(e.getPrjYear(), year))
			.collect(Collectors.groupingBy(majorProjectDTO -> {
				if (majorProjectDTO.getPrjState()!=null) {
					return majorProjectDTO.getPrjState();
				}else {
					return "其它";
				}
			}));

		List<Map<String, Object>> result = new ArrayList<>(8);

		for (Map.Entry<String, List<MajorProjectDTO>> map : collect.entrySet()) {
			Map<String, Object> stringObjectMap = new HashMap<>(3);
			stringObjectMap.put("name",map.getKey());
			stringObjectMap.put("size",map.getValue().size());
			result.add(stringObjectMap);
		}

		return result;
	}
}

