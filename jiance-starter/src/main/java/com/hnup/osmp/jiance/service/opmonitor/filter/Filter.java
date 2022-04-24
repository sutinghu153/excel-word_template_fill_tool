package com.hnup.osmp.jiance.service.opmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;


import java.util.List;

/**
 * @data2021/12/9,15:50
 * @authorMSI
 */
public interface Filter {

	List<OpMonitorDTO> excute(List<OpMonitorDTO> opMonitors, String request);

}
