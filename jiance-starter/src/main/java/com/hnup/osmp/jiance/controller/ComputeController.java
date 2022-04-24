package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.compute.ComputeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  自定义公式接口
 * @data2021/11/11,10:45
 * @authorsutinghu
 */
@RestController
@Api(value = "自定义公式",tags = {"自定义公式"})
@RequestMapping("/sup/compute")
public class ComputeController {

	@Autowired
	private ComputeService computeService;

	@ApiOperation("一键计算")
	@PostMapping("/computeById")
	public void computeById(@RequestParam("computeId") Long computeId){
		 computeService.computeById(computeId);
	}

}
