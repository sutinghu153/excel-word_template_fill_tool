package com.hnup.osmp.jiance.controller.process;

import com.hnup.osmp.jiance.service.processmonitor.ProcessMonitorService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @data2022/1/19,14:24
 * @author sutinghu
 */
@Api(value = "事务监控", tags = {"事务监控"})
@RestController
@RequestMapping("/process/monitor")
public class ProcessMonitorController {

	@Autowired
	private ProcessMonitorService processMonitorService;

}
