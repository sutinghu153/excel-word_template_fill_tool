package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.landuser.LanduserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  土地利用现状
 * @data2021/11/11,17:52
 * @authorsutinghu
 *
 */
@RestController
@Api(value = "现状指标-土地利用现状",tags = {"现状指标-土地利用现状"})
@RequestMapping("/sup/landuser")
public class LanduserController {

	@Autowired
	private LanduserService landuserService;

	@ApiOperation("获取行政区")
	@PostMapping("/getXzqTree")
	public ResponseEntity getXzqTree(@RequestParam("code") String code){
		return ResponseEntity.ok(landuserService.getXzqTree(code));
	}

	@ApiOperation("获取年份")
	@PostMapping("/getYear")
	public ResponseEntity getYear(){
		return ResponseEntity.ok(landuserService.getYear());
	}

	@ApiOperation("获取土地利用现状首页列表")
	@PostMapping("/getHomeList")
	public ResponseEntity getHomeList(@RequestParam("xzqCode") String xzqCode,
									  @RequestParam("year") String year){
		return ResponseEntity.ok(landuserService.getHomeList(xzqCode,year));
	}

	@ApiOperation("获取区县表头数据")
	@PostMapping("/getRegionTitleData")
	public ResponseEntity getRegionTitleData(@RequestParam("xzqCode") String xzqCode,
										@RequestParam("year") String year,
										@RequestParam("useType") String useType){
		return ResponseEntity.ok(landuserService.getRegionTitleData(xzqCode,year,useType));
	}

	@ApiOperation("获取区县表数据")
	@PostMapping("/getLandUseRegionDetail")
	public ResponseEntity getLandUseRegionDetail(@RequestParam("xzqCode") String xzqCode,
											 @RequestParam("year") String year,
											 @RequestParam("useType") String useType,
												 @RequestParam("rightType") String rightType){
		return ResponseEntity.ok(landuserService.getLandUseRegionDetail(xzqCode,year,useType,rightType));
	}

	@ApiOperation("获取图斑数二级地类统计数据")
	@PostMapping("/getLevelTwo")
	public ResponseEntity getLevelTwo(@RequestParam("xzqCode") String xzqCode,
										@RequestParam("year") String year,
										@RequestParam("useType") String useType){
		return ResponseEntity.ok(landuserService.getLevelTwo(xzqCode,year,useType));
	}

	@ApiOperation("获取控制管理数区县数据")
	@PostMapping("/getControlXzqData")
	public ResponseEntity getControlXzqData(@RequestParam("xzqCode") String xzqCode,
										@RequestParam("year") String year,
										@RequestParam("useType") String useType){
		return ResponseEntity.ok(landuserService.getControlXzqData(xzqCode,year,useType));
	}

	@ApiOperation("获取控制管理数二级地类统计数据")
	@PostMapping("/getControlLevelTwo")
	public ResponseEntity getControlLevelTwo(@RequestParam("xzqCode") String xzqCode,
									  @RequestParam("year") String year,
									  @RequestParam("useType") String useType){
		return ResponseEntity.ok(landuserService.getControlLevelTwo(xzqCode,year,useType));
	}

}
