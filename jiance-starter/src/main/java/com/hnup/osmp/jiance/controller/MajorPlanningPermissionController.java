package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.MajorPlanningPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @data2021/12/17,16:06
 * @authorsutinghu
 */
@RestController
@Api(value = "监管首页-规划许可",tags = {"监管首页-规划许可"})
@RequestMapping("/sup/planpermission/")
public class MajorPlanningPermissionController {

	@Autowired
	private MajorPlanningPermissionService majorPlanningPermissionService;

	@ApiOperation("规划许可-地区列表")
	@PostMapping("/getRegions")
	public ResponseEntity getRegions(){
		return ResponseEntity.ok(majorPlanningPermissionService.getRegions());
	}

	@ApiOperation("规划许可-首页")
	@PostMapping("/getIndex")
	public ResponseEntity getIndex(){
		return ResponseEntity.ok(majorPlanningPermissionService.getIndex("全部"));
	}


	@ApiOperation("规划许可-详情")
	@PostMapping("/getList")
	public ResponseEntity getList(@RequestParam(value = "startTime",required = false) Date startTime,
								  @RequestParam(value = "endTime",required = false) Date endTime,
								  @RequestParam("region") String region,
								  @RequestParam("type") String type,
								  @RequestParam("pageIndex") Integer pageIndex,
								  @RequestParam("pageSize") Integer pageSize,
								  @RequestParam(value = "keyWords",required = false) String keyWords){
		return ResponseEntity.ok(majorPlanningPermissionService.getList(startTime,endTime,region,type,pageIndex,pageSize,keyWords));
	}

	@ApiOperation("规划许可-导出详情")
	@PostMapping("/exportList")
	public ResponseEntity<InputStreamResource> exportList(@RequestParam(value = "startTime",required = false) Date startTime,
														  @RequestParam(value = "endTime",required = false) Date endTime,
														  @RequestParam("region") String region,
														  @RequestParam("type") String type,
														  @RequestParam(value = "keyWords",required = false) String keyWords){
		return majorPlanningPermissionService.exportList(startTime,endTime,region,type,keyWords);
	}

}
