package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.handlingPlanModification.HandlingPlanModificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @data2021/12/17,14:20
 * @authorsutinghu
 */
@RestController
@Api(value = "规划修改办理指标-案卷统计",tags = {"规划修改办理指标-案卷统计"})
@RequestMapping("/sup/handlPlan/")
public class HandlingPlanModificationController {

	@Autowired
	private HandlingPlanModificationService handlingPlanModificationService;

	@ApiOperation("办结案卷-详情")
	@PostMapping("/getFinishList")
	public ResponseEntity getFinishList(@RequestParam("startTime") Date startTime,
										@RequestParam("endTime") Date endTime,
										@RequestParam("keyTypeCode") String keyTypeCode){
		return ResponseEntity.ok(handlingPlanModificationService.getFinishList(startTime,endTime,keyTypeCode));
	}

	@ApiOperation("在办案卷-详情")
	@PostMapping("/getProcessList")
	public ResponseEntity getProcessList(@RequestParam("startTime") Date startTime,
										 @RequestParam("endTime") Date endTime,
										 @RequestParam("keyTypeCode") String keyTypeCode){
		return ResponseEntity.ok(handlingPlanModificationService.getProcessList(startTime,endTime,keyTypeCode));
	}

	@ApiOperation("案卷统计-首页")
	@PostMapping("/getIndex")
	public ResponseEntity getIndex(@RequestParam("startTime") Date startTime,
										 @RequestParam("endTime") Date endTime){
		return ResponseEntity.ok(handlingPlanModificationService.getIndex(startTime,endTime));
	}

}
