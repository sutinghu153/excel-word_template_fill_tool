package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.onemap.OneMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  根据GIS提供的接口开发
 * @data2021/11/10,15:45
 * @authorsutinghu
 */
@RestController
@Api(value = "onemap",tags = {"onemap"})
@RequestMapping("/sup/onemap")
public class OneMapController {

	@Autowired
	private OneMapService oneMapService;

	@ApiOperation("获取年份")
	@PostMapping("/getYearList")
	public ResponseEntity getYearList() {
		return ResponseEntity.ok(oneMapService.getYearList());
	}

}
