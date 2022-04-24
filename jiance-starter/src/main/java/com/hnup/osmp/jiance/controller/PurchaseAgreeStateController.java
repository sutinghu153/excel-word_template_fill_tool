package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.PurchaseAgreeStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @data2021/12/18,10:33
 * @authorsutignhu
 */
@RestController
@Api(value = "编制项目——付费状态",tags = {"编制项目——付费状态"})
@RequestMapping("/sup/purchase/agree/")
public class PurchaseAgreeStateController {

	@Autowired
	private PurchaseAgreeStateService purchaseAgreeStateService;

	@ApiOperation("年份列表")
	@PostMapping("/getYearList")
	public ResponseEntity getYearList() {
		return ResponseEntity.ok(purchaseAgreeStateService.getYearList());
	}

	@ApiOperation("批次列表")
	@PostMapping("/getBatchList")
	public ResponseEntity getBatchList() {
		return ResponseEntity.ok(purchaseAgreeStateService.getBatchList());
	}

	@ApiOperation("处室列表")
	@PostMapping("/getUnitsList")
	public ResponseEntity getUnitsList() {
		return ResponseEntity.ok(purchaseAgreeStateService.getUnitsList());
	}

	@ApiOperation("详情列表")
	@PostMapping("/getDetailList")
	public ResponseEntity getDetailList(@RequestParam("year")String year,
										@RequestParam("batch")String batch,
										@RequestParam("unit")String unit) {
		return ResponseEntity.ok(purchaseAgreeStateService.getDetailList(year,batch,unit));
	}

	@ApiOperation("首页")
	@PostMapping("/getIndex")
	public ResponseEntity getIndex(@RequestParam("year")String year,
								   @RequestParam("batch")String batch) {
		return ResponseEntity.ok(purchaseAgreeStateService.getIndex(year,batch));
	}

}
