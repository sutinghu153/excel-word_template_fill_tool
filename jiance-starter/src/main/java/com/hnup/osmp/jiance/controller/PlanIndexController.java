package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.repository.entity.JcPlanIndex;
import com.hnup.osmp.jiance.service.planIndex.PlanIndexService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *  规划指标接口层
 * @data2021/11/11,15:44
 * @authorsutinghu
 */
@RestController
@Api(value = "规划指标",tags = {"规划指标"})
@RequestMapping("/sup/plan")
public class PlanIndexController {

	@Autowired
	private PlanIndexService planIndexService;

	@ApiOperation("根据指标类型获取首页列表")
	@PostMapping("/getPlanIndexList")
	public ResponseEntity getPlanIndexList(@RequestParam("indexType")String indexType) {
		return ResponseEntity.ok(planIndexService.getPlanIndexList(indexType));
	}

	@ApiOperation("新增指标")
	@PostMapping("/addPlanIndex")
	public ResponseEntity addPlanIndex(@RequestBody JcPlanIndex jcPlanIndex) {
		planIndexService.addPlanIndex(jcPlanIndex);
		return ResponseEntity.ok("新增成功");
	}

	@ApiOperation("编辑指标")
	@PostMapping("/updatePlanIndex")
	public ResponseEntity updatePlanIndex(@RequestBody JcPlanIndex jcPlanIndex) {
		planIndexService.updatePlanIndex(jcPlanIndex);
		return ResponseEntity.ok("修改成功");
	}

	@ApiOperation("删除指标")
	@DeleteMapping("/deletePlanIndex")
	public ResponseEntity deletePlanIndex(@RequestParam("id")Long id) {
		planIndexService.deletePlanIndex(id);
		return ResponseEntity.ok("删除成功");
	}
}
