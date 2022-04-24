package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.model.dto.FormationProjectDTO;
import com.hnup.osmp.jiance.service.preparationproject.FormationProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  编制项目相关接口
 * @data2021/11/5,9:28
 * @authorsutinghu
 */
@RestController
@Api(value = "编制项目-项目支付状态",tags = {"编制项目-项目支付状态"})
@RequestMapping("/sup/formation")
public class FormationProjectController {

	@Autowired
	private FormationProjectService formationProjectService;

	@ApiOperation("获取首页列表")
	@PostMapping("/getProjectList")
	public ResponseEntity getProjectList(@RequestParam(value = "year",required = false) String year,
										 @RequestParam(value = "departName",required = false) String departName){
		return ResponseEntity.ok(formationProjectService.getProjectList(year,departName));
	}

	@ApiOperation("获取首页饼图-柱子")
	@PostMapping("/getProjectcharts")
	public ResponseEntity getProjectcharts(@RequestParam(value = "year",required = false) String year,
										   @RequestParam(value = "departName",required = false) String departName){
		return ResponseEntity.ok(formationProjectService.getProjectcharts(year,departName));
	}

	@ApiOperation("编制项目-项目/支付状态详情页")
	@PostMapping("/getProjectDetails")
	public ResponseEntity getProjectDetails(@RequestBody FormationProjectDTO formationProjectDTO){
		return ResponseEntity.ok(formationProjectService.getProjectDetails(formationProjectDTO));
	}

	@ApiOperation("编制项目-项目/支付状态查询条件")
	@PostMapping("/getProjectQuery")
	public ResponseEntity getProjectQuery(){
		return ResponseEntity.ok(formationProjectService.getProjectQuery());
	}

}
