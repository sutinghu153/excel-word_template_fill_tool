package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.assess.NaturalResourcesManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  自然资源管理接口
 * @data2021/11/3,10:05
 * @author sutinghu
 */
@RestController
@Api(value = "考核指标-自然资源管理",tags = {"考核指标-自然资源管理"})
@RequestMapping("/sup/naturalmanage")
public class NaturalResourcesManageController {

	@Autowired
	private NaturalResourcesManageService naturalResourcesManageService;

	@ApiOperation("获取自然资源管理列表")
	@PostMapping("/getNaturalManageList")
	public ResponseEntity getNaturalManageList(){
		return ResponseEntity.ok(naturalResourcesManageService.getNaturalManageList());
	}

}
