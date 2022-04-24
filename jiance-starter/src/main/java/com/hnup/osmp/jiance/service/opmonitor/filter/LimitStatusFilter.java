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
 * 	限制状态过滤器
 * @data2021/12/9,16:25
 * @authorsutinghu
 */
public class LimitStatusFilter implements Filter{

	private final static String CQ = "超期";
	private final static String WCQ = "未超期";
	private final static String ALL = "总数";
	private final static String OTHER = "其他";

	private MonitorQueryVO query ;

	LimitStatusFilter(MonitorQueryVO query){
		this.query = query;
	}

	@Override
	public List<OpMonitorDTO> excute(List<OpMonitorDTO> opMonitors, String request) {
		if (Objects.equals(request,CQ)){
			return this.cqHandle(opMonitors);
		}else if(Objects.equals(request,WCQ)){
			return this.wcqHandle(opMonitors);
		}else if(Objects.equals(request,ALL)){
			return this.allHandle(opMonitors);
		}else if(Objects.equals(request,OTHER)){
			return this.otherHandle(opMonitors);
		}else {
			throw new DeclareException("限制状态请求参数不合理");
		}
	}

	/**
	 * 功能描述:超期处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> cqHandle(List<OpMonitorDTO> opMonitors){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		// 过滤超期
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getTheoryTime()!=null && opMonitorDTO.getSpentTime() != null){
				return opMonitorDTO.getTheoryTime()<opMonitorDTO.getSpentTime();
			}else {
				return false;
			}
		}).collect(Collectors.toList());

		return opMonitors;
	}
	/**
	 * 功能描述:未超期处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> wcqHandle(List<OpMonitorDTO> opMonitors){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		// 过滤未超期
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getTheoryTime()!=null && opMonitorDTO.getSpentTime() != null){
				return opMonitorDTO.getTheoryTime()>=opMonitorDTO.getSpentTime();
			}else {
				return false;
			}
		}).collect(Collectors.toList());

		return opMonitors;
	}

	/**
	 * 功能描述:其他处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> otherHandle(List<OpMonitorDTO> opMonitors){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		// 过滤未超期
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getTheoryTime()!=null && opMonitorDTO.getSpentTime() != null){
				return opMonitorDTO.getTheoryTime()>=opMonitorDTO.getSpentTime();
			}else {
				return false;
			}
		}).collect(Collectors.toList());

		return opMonitors;
	}

	/**
	 * 功能描述:总数处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> allHandle(List<OpMonitorDTO> opMonitors){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}

		return opMonitors;
	}

}
