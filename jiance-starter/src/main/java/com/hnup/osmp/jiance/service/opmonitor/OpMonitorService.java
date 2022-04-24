package com.hnup.osmp.jiance.service.opmonitor;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.model.dto.monitor.MonitorQueryVO;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import com.hnup.osmp.jiance.repository.dao.IJcMajorProjectsDAO;
import com.hnup.osmp.jiance.service.opmonitor.filter.FilterManager;
import com.hnup.osmp.jiance.service.opmonitor.filter.ParamList;
import com.hnup.osmp.jiance.utils.DateUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @data2021/12/9,11:51
 * @authorsutinghu
 */
@Service
public class OpMonitorService {

	@Autowired
	private IJcMajorProjectsDAO iJcMajorProjects;

	@Autowired
	private QueryCheckService queryCheckService;

	/**
	 * 功能描述:员工级数据看板构造方法
	 * @author sutinghu
	 * @date
	 * @param monitorQuery 参数
	 * @return
	 */
	public List<Map<String, Object>> getTable(MonitorQueryVO monitorQuery){
		// 先校验
		queryCheckService.checkAll(monitorQuery);
		// 获取原数据
		List<OpMonitorDTO> opMonitors = this.getList(monitorQuery);
		// 解析
		Map<String, Object> list = new HashMap<>(16);
		if (Objects.equals(monitorQuery.getType(),1)) {
			List<String> depts = opMonitors.stream().map(e -> e.getDeptName()).distinct().collect(Collectors.toList());
			list.put("主办者",depts);
			ParamList.INIT.forEach(e->{
				List<OpMonitorDTO> monitors = new FilterManager(opMonitors, e).buildFirstFilter(monitorQuery).excute();
				Map<String, List<OpMonitorDTO>> collect = monitors
					.stream()
					.collect(Collectors.groupingBy(opMonitorDTO -> {
						if (opMonitorDTO.getDeptName()!=null) {
							return opMonitorDTO.getDeptName();
						}else {
							return"其它";
						}
					}));
				String titleName = StringUtils.join(e,"");
				list.put(titleName,collect);
			});
		}else if (Objects.equals(monitorQuery.getType(),0)) {
			List<String> users = opMonitors.stream().map(e -> e.getUserName()).distinct().collect(Collectors.toList());
			list.put("主办者",users);
			ParamList.INIT.forEach(e->{
				List<OpMonitorDTO> monitors = new FilterManager(opMonitors, e).buildFirstFilter(monitorQuery).excute();
				Map<String, List<OpMonitorDTO>> collect = monitors
					.stream()
					.collect(Collectors.groupingBy(OpMonitorDTO::getUserName));
				String titleName = StringUtils.join(e,"");
				list.put(titleName,collect);
			});
		}
		// 构造
		return this.buildTable(list);
	}

	/**
	 * 功能描述: 根据需要构造参数
	 * @author sutinghu
	 * @date
	 * @param map 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> buildTable(Map<String, Object> map){

		if (MapUtils.isEmpty(map)) {
			return Lists.newArrayList();
		}

		List<String> zb = (List<String>) map.get("主办者");

		if (CollectionUtils.isEmpty(zb)) {
			return Lists.newArrayList();
		}

		List<Map<String, Object>> list = new ArrayList<>(16);

		zb.stream().forEach(e->{

			Map<String, Object> result = new HashMap<>(16);
			result.put("主办者",e);

			ParamList.INIT.forEach(o->{
				String titleName = StringUtils.join(o,"");
				Map<String, List<OpMonitorDTO>> collect = (Map<String, List<OpMonitorDTO>>) map.get(titleName);
				Integer nums = 0;
				if (MapUtils.isEmpty(collect)){
					result.put(titleName,nums);
					return;
				}else {
					if (CollectionUtils.isEmpty(collect.get(e))) {
						result.put(titleName,nums);
						return;
					}else {
						nums = collect.get(e).size();
						result.put(titleName,nums);
					}
				}
			});

			list.add(result);
		});

		return list;
	}


	/**
	 * 功能描述:详情页数据
	 * @author sutinghu
	 * @date
	 * @param monitorQuery 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public List<OpMonitorDTO> getDetailList(MonitorQueryVO monitorQuery){
		// 先校验
		queryCheckService.checkDetail(monitorQuery);
		// 拿数据-时间取最大尺度
		List<OpMonitorDTO> detailList = this.getList(monitorQuery);

		if (Objects.equals(monitorQuery.getType(),1)) {
			detailList = detailList.stream().filter(opMonitorDTO -> {
				if (opMonitorDTO.getDeptName()==null) {
					return false;
				}else {
					return Objects.equals(monitorQuery.getDeptName(),opMonitorDTO.getDeptName());
				}
			}).collect(Collectors.toList());
		}else if (Objects.equals(monitorQuery.getType(),0)) {
			detailList = detailList.stream().filter(opMonitorDTO -> {
				if (opMonitorDTO.getUserName()==null) {
					return false;
				}else {
					return Objects.equals(monitorQuery.getUserName(),opMonitorDTO.getUserName());
				}
			}).collect(Collectors.toList());
		}

		// 构造解析
		return new FilterManager(detailList,monitorQuery.getTypes()).buildFirstFilter(monitorQuery).excute();
	}

	/**
	 * 功能描述: 获取数据
	 * @author sutinghu
	 * @date
	 * @param monitorQuery 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public List<OpMonitorDTO> getList(MonitorQueryVO monitorQuery){
		return iJcMajorProjects.getDetailList(
			monitorQuery.getStartTqTime(),
			monitorQuery.getEndYearTime(),
			monitorQuery.getDeptName());
	}

	/**
	 * 功能描述: 获取年度办结
	 * @author sutinghu
	 * @date
	 * @param year 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> countOpByMonth(String year) {

		// 获取一年的开始和结束时间
		Date yearStart = DateUtils.getAppointTime(year);
		Date yearEnd = DateUtils.dayMove(yearStart,365);

		// 拿数据-时间取最大尺度
		List<OpMonitorDTO> detailList = iJcMajorProjects.getDetailList(
			yearStart,
			yearEnd,
			null);

		if (CollectionUtils.isEmpty(detailList)) {
			return Maps.newHashMap();
		}

		List<String> months = new ArrayList<>(12);
		List<Long> jjData = new ArrayList<>(12);
		List<Long> bjData = new ArrayList<>(12);

		for (Integer i = 1;i<=12;i++) {
			months.add(i.toString());
			// 获取每月的开始和结束时间
			Date monthStartTime = DateUtils.getMonthTime(year, i.toString());
			Date monthEndTime = DateUtils.dayMove(monthStartTime,30);

			// 统计接件
			Long jj = detailList.stream().filter(opMonitorDTO -> {
				if (opMonitorDTO != null && opMonitorDTO.getCreateTime() != null) {
					if (opMonitorDTO.getCreateTime().after(monthStartTime) && opMonitorDTO.getCreateTime().before(monthEndTime)) {
						return true;
					}else {
						return false;
					}
				} else {
					return false;
				}
			}).count();
			jjData.add(jj);
			// 统计办结
			Long bj = detailList.stream().filter(opMonitorDTO -> {
				if (opMonitorDTO!=null && Objects.equals(5,opMonitorDTO.getState())) {
					if (opMonitorDTO.getFinishTime()!=null &&
						opMonitorDTO.getFinishTime().after(monthStartTime) &&
						opMonitorDTO.getFinishTime().before(monthEndTime)) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}).count();
			bjData.add(bj);
		}

		Map<String, Object> map = new HashMap<>(3);

		map.put("xAxisMonth",months);
		map.put("yAxisJjData",jjData);
		map.put("yAxisBjData",bjData);

		return map;

	}
}
