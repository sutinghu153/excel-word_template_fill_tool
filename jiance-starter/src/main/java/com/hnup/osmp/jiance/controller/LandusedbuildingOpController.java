package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.landusedbuilding.LandusedbuildingOpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @data2021/12/17,11:24
 * @author sutinghu
 */
@RestController
@Api(value = "监管首页-建设用地审批",tags = {"监管首页-建设用地审批"})
@RequestMapping("/sup/landuserbuildop")
public class LandusedbuildingOpController {

	@Autowired
	private LandusedbuildingOpService landusedbuildingOpService;

	@ApiOperation("建设用地审批-首页")
	@GetMapping("/getIndex")
	public ResponseEntity getIndex(){
		return ResponseEntity.ok(landusedbuildingOpService.getIndex());
	}

	@ApiOperation("建设用地审批-面积详情")
	@PostMapping("/getAreaDetails")
	public ResponseEntity getAreaDetails(@RequestParam("cityName")String cityName,
									 @RequestParam("year")String year){
		return ResponseEntity.ok(landusedbuildingOpService.getAreaDetails(cityName,year));
	}

	@ApiOperation("建设用地审批-数量详情")
	@PostMapping("/getSize")
	public ResponseEntity getSize(@RequestParam("cityName")String cityName,
										 @RequestParam("year")String year){
		return ResponseEntity.ok(landusedbuildingOpService.getSize(cityName,year));
	}

	@ApiOperation("建设用地审批-分布详情")
	@PostMapping("/getSpreadDetails")
	public ResponseEntity getSpreadDetails(@RequestParam("year")String year){
		return ResponseEntity.ok(landusedbuildingOpService.getSpreadDetails(year));
	}

	@ApiOperation("建设用地审批-趋势分析")
	@PostMapping("/gettrendAnalysis")
	public ResponseEntity gettrendAnalysis(@RequestParam("cityName")String cityName){
		return ResponseEntity.ok(landusedbuildingOpService.gettrendAnalysis(cityName));
	}

}
