package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.assess.FarmlandProtectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  省资源管理-耕地保护接口
 * @data2021/11/3,10:30
 * @authorsutinghu
 */
@RestController
@Api(value = "考核指标-耕地保护",tags = {"考核指标-耕地保护"})
@RequestMapping("/sup/farmlandprotection")
public class FarmlandProtectionController {

	@Autowired
	private FarmlandProtectionService farmlandProtectionService;

	@ApiOperation("获取耕地保护列表")
	@PostMapping("/getFarmlandProtectionList")
	public ResponseEntity getFarmlandProtectionList(){
		return ResponseEntity.ok(farmlandProtectionService.getFarmlandProtectionList());
	}

}
