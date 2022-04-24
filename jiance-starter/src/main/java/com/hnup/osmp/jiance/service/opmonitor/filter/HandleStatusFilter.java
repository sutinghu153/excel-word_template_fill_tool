package com.hnup.osmp.jiance.service.opmonitor.filter;

import com.google.common.collect.Lists;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.model.dto.monitor.MonitorQueryVO;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import com.hnup.osmp.shiju.op.model.constant.enums.OpStateEnum;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  办理状态过滤器
 * @data2021/12/9,15:52
 * @authorsutinghu
 */
public class HandleStatusFilter implements Filter{

	private final static String ZB = "在办";
	private final static String BJ = "办结";
	private MonitorQueryVO query ;
	HandleStatusFilter(MonitorQueryVO query){
		this.query = query;
	}

	@Override
	public List<OpMonitorDTO> excute(List<OpMonitorDTO> opMonitors, String request) {
		if (Objects.equals(request,ZB)){
			return this.zbHandle(opMonitors);
		}else if(Objects.equals(request,BJ)){
			return this.bjHandle(opMonitors);
		}else {
			throw new DeclareException("办理状态请求参数不合理");
		}
	}

	/**
	 * 功能描述:在办处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> zbHandle(List<OpMonitorDTO> opMonitors){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		// 过滤在办
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getState()!=null){
				return Objects.equals(OpStateEnum.ZAIBAN.getId(),opMonitorDTO.getState());
			}else {
				return false;
			}
		}).collect(Collectors.toList());

		return opMonitors;
	}

	/**
	 * 功能描述:办结处理
	 * @author sutinghu
	 * @date
	 * @param opMonitors 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	public  List<OpMonitorDTO> bjHandle(List<OpMonitorDTO> opMonitors){

		if (CollectionUtils.isEmpty(opMonitors)) {
			return Lists.newArrayList();
		}
		// 过滤在办
		opMonitors = opMonitors.stream().filter(opMonitorDTO -> {
			if (opMonitorDTO.getState()!=null){
				return Objects.equals(OpStateEnum.BANJIE.getId(),opMonitorDTO.getState());
			}else {
				return false;
			}
		}).collect(Collectors.toList());

		return opMonitors;
	}

}
