package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.assess.UrbanAssessmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  市委市政府考核指标
 * @data2021/11/3,8:57
 * @authorsutinghu
 */
@RestController
@Api(value = "考核指标-市委市政府考核",tags = {"考核指标-市委市政府考核"})
@RequestMapping("/sup/urbanassess")
public class UrbanAssessmentController {


	@Autowired
	private UrbanAssessmentService urbanAssessmentService;

	@ApiOperation("获取市委市政府考核列表")
	@PostMapping("/getUrbanAssessList")
	public ResponseEntity getUrbanAssessList(){
		return ResponseEntity.ok(urbanAssessmentService.getUrbanAssessList());
	}
}
