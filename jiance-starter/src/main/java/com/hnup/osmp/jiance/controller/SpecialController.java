package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.PurchaseAgreeStateService;
import com.hnup.osmp.jiance.service.special.SpecialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.03.24 11:01
 */
@RestController
@Api(value = "土地监管",tags = {"土地监管"})
@RequestMapping("/special/")
public class SpecialController {

	@Autowired
	private SpecialService specialService;

	@ApiOperation("获取耕地保护的批次字典")
	@GetMapping("/getWxBatchList")
	public ResponseEntity getWxBatchList() {
		return ResponseEntity.ok(specialService.getWxBatchList());
	}

	@ApiOperation("省厅下发卫星监测图斑指标冻结减扣情况")
	@PostMapping("/satelliteMonitoring")
	public ResponseEntity satelliteMonitoring(@RequestParam("batch")String batch) {
		return ResponseEntity.ok(specialService.satelliteMonitoring(batch));
	}

	@ApiOperation("省厅解冻接口补充耕地指标结果统计")
	@PostMapping("/provincialCultivatedLand")
	public ResponseEntity provincialCultivatedLand(@RequestParam("batch")String batch) {
		return ResponseEntity.ok(specialService.provincialCultivatedLand(batch));
	}

}
