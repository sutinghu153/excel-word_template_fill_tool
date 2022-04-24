package com.hnup.osmp.jiance.service.opmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  过滤链
 * @data2021/12/9,16:37
 * @author sutinghu
 */
public class FilterChain {

	private List<Map<String, Filter>> filterMap = new ArrayList<>(5);
	private List<OpMonitorDTO> opMonitors;

	public List<OpMonitorDTO> getOpMonitors() {
		return opMonitors;
	}

	public FilterChain(List<OpMonitorDTO> opMonitor) {
		this.opMonitors = opMonitor;
	}

	public void addFilter(Map<String, Filter> map){
		filterMap.add(map);
	}

	public FilterChain build(){
		for (Map<String, Filter> e: filterMap) {
			for (Map.Entry<String, Filter> map : e.entrySet()) {
				this.opMonitors = 	map.getValue().excute(this.opMonitors,map.getKey());
			}
		}
		return this;
	}

}
