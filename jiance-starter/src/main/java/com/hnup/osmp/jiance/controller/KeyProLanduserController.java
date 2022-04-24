package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.keypro.KeyProLanduserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  监管首页-耕地
 * @data2021/11/15,14:54
 * @authorsutinghu
 */
@RestController
@Api(value = "监管首页-耕地",tags = {"监管首页-耕地"})
@RequestMapping("/sup/key/landuser")
public class KeyProLanduserController {

	@Autowired
	private KeyProLanduserService keyProLanduserService;

	@ApiOperation("监管首页-耕地柱状图")
	@PostMapping("/getFormLandTree")
	public ResponseEntity getFormLandTree(){
		return ResponseEntity.ok(keyProLanduserService.getFormLandTree());
	}

	@ApiOperation("耕地详情-耕地分布")
	@PostMapping("/getFormLandSpread")
	public ResponseEntity getFormLandSpread(@RequestParam("year")String year){
		return ResponseEntity.ok(keyProLanduserService.getFormLandSpread(year));
	}

	@ApiOperation("耕地详情-趋势分析")
	@PostMapping("/trendAnalysis")
	public ResponseEntity trendAnalysis(){
		return ResponseEntity.ok(keyProLanduserService.trendAnalysis());
	}

	@ApiOperation("耕地详情-台账详情")
	@PostMapping("/getFormLandDetail")
	public ResponseEntity getFormLandTz(@RequestParam("year")String year){
		return ResponseEntity.ok(keyProLanduserService.getFormLandTz(year));
	}
}
