package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.assess.CountryNaturalResourcesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  省自然资源厅重点督察工作接口
 * @data2021/11/5,9:28
 * @authorsutinghu
 */
@RestController
@Api(value = "考核指标-省自然资源厅重点督察-项目详情表",tags = {"考核指标-省自然资源厅重点督察-项目详情表"})
@RequestMapping("/sup/pronvice/table")
public class CountryNaturalResourcesController {

	@Autowired
	private CountryNaturalResourcesService countryNaturalResourcesService;

	@ApiOperation("获取项目详情表")
	@PostMapping("/getTableByType")
	public ResponseEntity getTableByType(@RequestParam("countId") Long countId,
										 @RequestParam(value = "countTime",required = false) String countTime){
		return ResponseEntity.ok(countryNaturalResourcesService.getTableByType(countId,countTime));
	}

	@ApiOperation("获取项目更新时间")
	@GetMapping("/getUpdateTime")
	public ResponseEntity getUpdateTime(@RequestParam("countId") Long countId){
		return ResponseEntity.ok(countryNaturalResourcesService.getUpdateTime(countId));
	}
}
