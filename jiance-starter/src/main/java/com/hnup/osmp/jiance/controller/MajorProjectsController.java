package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.majorproject.MajorProjectsSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 *  监管——重大项目接口
 * @data2021/12/17,9:45
 * @author sutinghu
 */
@RestController
@Api(value = "监管首页-重大项目",tags = {"监管首页-重大项目"})
@RequestMapping("/sup/majorProject")
public class MajorProjectsController {

	@Autowired
	private MajorProjectsSerivce majorProjectsSerivce;

	@ApiOperation("重大项目首页")
	@PostMapping("/getMajorIndex")
	public ResponseEntity getMajorIndex(@RequestParam(value = "startTime" ,required = false) Date startTime,
										@RequestParam(value ="endTime",required = false) Date endTime){
		return ResponseEntity.ok(majorProjectsSerivce.getMajorIndex(startTime,endTime));
	}

	@ApiOperation("重大项目详情")
	@PostMapping("/getMajorDetail")
	public ResponseEntity getMajorDetail(@RequestParam(value = "startTime" ,required = false) Date startTime,
										 @RequestParam(value ="endTime",required = false) Date endTime){
		return ResponseEntity.ok(majorProjectsSerivce.getMajorDetail(startTime,endTime));
	}

	@ApiOperation("重大项目-建设状态统计")
	@GetMapping("/getStateCount")
	public ResponseEntity getStateCount(@RequestParam(value = "year" ,required = false) String year){
		return ResponseEntity.ok(majorProjectsSerivce.getStateCount(year));
	}

}
