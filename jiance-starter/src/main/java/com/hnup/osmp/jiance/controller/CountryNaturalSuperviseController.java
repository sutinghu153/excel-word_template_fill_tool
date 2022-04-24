package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.assess.CountryNaturalSuperviseServise;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  省自然资源厅重点督察首页工作督导
 * @data2021/11/5,9:28
 * @authorsutinghu
 */
@RestController
@Api(value = "考核指标-省自然资源厅重点督察-首页数据表",tags = {"考核指标-省自然资源厅重点督察-首页数据表"})
@RequestMapping("/sup/pronvice/")
public class CountryNaturalSuperviseController {

	@Autowired
	private CountryNaturalSuperviseServise countryNaturalSuperviseServise;

	@ApiOperation("项目首页详情表")
	@PostMapping("/getSuperviseTable")
	public ResponseEntity getSuperviseTable(){
		return ResponseEntity.ok(countryNaturalSuperviseServise.getSuperviseTable());
	}

}
