package com.hnup.osmp.jiance.controller.op;


import com.hnup.osmp.jiance.model.dto.monitor.MonitorQueryVO;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import com.hnup.osmp.jiance.service.opmonitor.OpMonitorService;
import com.hnup.osmp.jiance.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @data2021/12/9,11:50
 * @author sutinghu
 */
@Api(value = "案卷监控", tags = {"案卷监控"})
@RestController
@RequestMapping("/op/monitor")
public class OpMonitorController {

	@Autowired
	private OpMonitorService opMonitorService;

	@ApiOperation("首页详情")
	@PostMapping("/getTable")
	public ResponseEntity getTable(@RequestBody MonitorQueryVO monitorQuery){


		// 年度时间-本年度时间
		Date yearStart = DateUtils.getYearDayStart();
		Date yearEnd = DateUtils.getYearDayEnd();

		monitorQuery.setStartYearTime(yearStart);
		monitorQuery.setEndYearTime(yearEnd);

		// 同期时间-当前时间整体前移一年
		Date tqStart = DateUtils.dayMove(monitorQuery.getStartTime(),-365);
		Date tqEnd = DateUtils.dayMove(monitorQuery.getEndTime(),-365);

		monitorQuery.setStartTqTime(tqStart);
		monitorQuery.setEndTqTime(tqEnd);
		return ResponseEntity.ok(opMonitorService.getTable(monitorQuery));
	}

	@ApiOperation("案卷列表详情")
	@PostMapping("/detailList")
	public List<OpMonitorDTO> getDetailList(@RequestBody MonitorQueryVO monitorQuery){

		// 年度时间-本年度时间
		Date yearStart = DateUtils.getYearDayStart();
		Date yearEnd = DateUtils.getYearDayEnd();

		monitorQuery.setStartYearTime(yearStart);
		monitorQuery.setEndYearTime(yearEnd);

		// 同期时间-当前时间整体前移一年
		Date tqStart = DateUtils.dayMove(monitorQuery.getStartTime(),-365);
		Date tqEnd = DateUtils.dayMove(monitorQuery.getEndTime(),-365);

		monitorQuery.setStartTqTime(tqStart);
		monitorQuery.setEndTqTime(tqEnd);
		// 抽数据
		return opMonitorService.getDetailList(monitorQuery);
	}

	@ApiOperation("案卷按月折线统计")
	@PostMapping("/countOpByMonth")
	public ResponseEntity countOpByMonth(@RequestParam(value = "year")String year){
		return ResponseEntity.ok(
			opMonitorService.countOpByMonth(year)
		);
	}

}
