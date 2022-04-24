package com.hnup.osmp.jiance.controller.meeting;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingVo;
import com.hnup.osmp.jiance.service.meetingmonitor.MeetingMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @data2022/1/19,9:19
 * @author sutinghu
 */
@Api(value = "会议监控", tags = {"会议监控"})
@RestController
@RequestMapping("/meeting/monitor")
public class MeetingMonitorController {

	@Autowired
	private MeetingMonitorService meetingMonitorService;

	@ApiOperation("会议监控单位看板")
	@PostMapping("/getCompanyDataBoard")
	public ResponseEntity getCompanyDataBoard(@RequestBody MeetingVo meetingVo){
		return ResponseEntity.ok(
			meetingMonitorService.getCompanyDataBoard(meetingVo)
		);
	}

	@ApiOperation("会议监控员工看板")
	@PostMapping("/getSponsorDataBoard")
	public ResponseEntity getSponsorDataBoard(@RequestBody MeetingVo meetingVo){
		return ResponseEntity.ok(
			meetingMonitorService.getSponsorDataBoard(meetingVo)
		);
	}

	@ApiOperation("会议监控详情列表")
	@PostMapping("/getDetailsList")
	public ResponseEntity getDetailsList(@RequestBody MeetingVo meetingVo){
		return ResponseEntity.ok(
			meetingMonitorService.getDetailsList(meetingVo)
		);
	}

}
