package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.keypro.KeyFoverBasicFormlandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  监管首页-永久基本农田
 * @data2021/11/19,9:09
 * @author sutinghu
 */
@RestController
@Api(value = "监管首页-永久基本农田",tags = {"监管首页-永久基本农田"})
@RequestMapping("/sup/key/foverBasicFormland")
public class KeyFoverBasicFormlandController {

	@Autowired
	private KeyFoverBasicFormlandService keyFoverBasicFormlandService;

	@ApiOperation("永久基本农田-详情总览图")
	@PostMapping("/getbasicbar")
	public ResponseEntity getbasicbar(@RequestParam("year")String year){
		return ResponseEntity.ok(keyFoverBasicFormlandService.getbasicbar(year));
	}

	@ApiOperation("永久基本农田-首页总览图")
	@PostMapping("/getHomebasicbar")
	public ResponseEntity getHomebasicbar(){
		return ResponseEntity.ok(keyFoverBasicFormlandService.getBasicBarHome());
	}

	@ApiOperation("永久基本农田-实存面积,储备区面积,划定面积")
	@PostMapping("/getDetailList")
	public ResponseEntity getDetailList(@RequestParam("year")String year){
		return ResponseEntity.ok(keyFoverBasicFormlandService.getDetailList(year));
	}
}
