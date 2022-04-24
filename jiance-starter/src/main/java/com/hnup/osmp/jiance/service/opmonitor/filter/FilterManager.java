package com.hnup.osmp.jiance.service.opmonitor.filter;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.model.dto.monitor.MonitorQueryVO;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 *  过滤链管理器
 * @data2021/12/9,16:49
 * @authorsutinghu
 */
public class FilterManager {

	private FilterChain filterChain;

	private List<String> types;

	public FilterManager(List<OpMonitorDTO> opMonitor, List<String> types){
		this.filterChain = new FilterChain(opMonitor);
		this.types= types;
	}

	public FilterManager buildFirstFilter( MonitorQueryVO query){

		if (CollectionUtils.isEmpty(types)) {
			return this;
		}

		types.stream().forEach(e->{

			if (ParamList.HANDLE_STATUS.contains(e)) {
				filterChain.addFilter(new HashMap<String, Filter>(){{put(e,new HandleStatusFilter(query));}});
			}

			if (ParamList.DATA_STATUS.contains(e)) {
				filterChain.addFilter(new HashMap<String, Filter>(){{put(e,new DataStatusFilter(query));}});
			}

			if (ParamList.LIMIT_STATUS.contains(e)) {
				filterChain.addFilter(new HashMap<String, Filter>(){{put(e,new LimitStatusFilter(query));}});
			}
		});

		return this;
	}

	public List<OpMonitorDTO> excute(){
		if (CollectionUtils.isEmpty(types)) {
			throw new DeclareException("状态参数不能为空");
		}
		return this.filterChain.build().getOpMonitors();
	}
}
