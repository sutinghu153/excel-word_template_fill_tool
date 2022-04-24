package com.hnup.osmp.jiance.service.opmonitor.filter;

import com.google.common.collect.Lists;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.model.dto.monitor.MonitorQueryVO;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  时间状态过滤器
 * @data2021/12/9,16:19
 * @authorsutinghu
 */
public class DataStatusFilter implements Filter{

	private final static String DQ = "当前";
	private final static String TQ = "同期";
	private final static String ND = "年度";

	private MonitorQueryVO query ;

	DataStatusFilter(MonitorQueryVO query){
		this.query = query;
	}

	@Override
	public List<OpMonitorDTO> excute(List<OpMonitorDTO> opMonitors, String request) {
		if (Objects.equals(request,DQ)){
			return this.dqHandle(opMonitors,query);
		}else if(Objects.equals(request,TQ)){
			return this.tqHandle(opMonitors,query);
		}else if(Objects.equals(request,ND)){
			return this.ndHandle(opMonitors,query);
		}else {
			throw new DeclareException("时间状态请求参数不合理");
		}
	}

	/**
	 * 功能描述:年度处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> ndHandle(List<OpMonitorDTO> opMonitors, MonitorQueryVO query){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getCreateTime()==null) {
				return false;
			}else {
				return query.getStartYearTime().before(opMonitorDTO.getCreateTime())&&query.getEndYearTime().after(opMonitorDTO.getCreateTime());
			}
		}).collect(Collectors.toList());
		return opMonitors;
	}

	/**
	 * 功能描述:当前处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> dqHandle(List<OpMonitorDTO> opMonitors, MonitorQueryVO query){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getCreateTime()==null) {
				return false;
			}else {
				return query.getStartTime().before(opMonitorDTO.getCreateTime())&&query.getEndTime().after(opMonitorDTO.getCreateTime());
			}
		}).collect(Collectors.toList());
		return opMonitors;

	}

	/**
	 * 功能描述:同期处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> tqHandle(List<OpMonitorDTO> opMonitors, MonitorQueryVO query){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getCreateTime()==null) {
				return false;
			}else {
				return query.getStartTqTime().before(opMonitorDTO.getCreateTime())&&query.getEndTqTime().after(opMonitorDTO.getCreateTime());
			}
		}).collect(Collectors.toList());
		return opMonitors;
	}

}
