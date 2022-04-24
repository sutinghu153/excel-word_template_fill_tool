package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.datudi.EcologicalStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.02.15 15:37
 */
@RestController
@Api(value = "大土地-生态修复统计分析",tags = {"大土地-生态修复统计分析"})
@RequestMapping("/td/ecological")
public class EcologicalStatisticsController {

	@Resource
	private EcologicalStatisticsService ecologicalStatisticsService;

	@ApiOperation("建设用地土壤污染状态调查")
	@GetMapping("/getecologytja")
	public ResponseEntity getecologytja(@RequestParam("areaName") String areaName) {
		return ResponseEntity.ok(ecologicalStatisticsService.getEcologyTja(areaName));
	}
}
