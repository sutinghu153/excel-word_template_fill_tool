package com.hnup.osmp.jiance.controller.driver;

import com.hnup.osmp.jiance.service.driver.DriverStoreOpManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @data2022/4/14,14:29
 * @author sutinghu
 */
@RestController
@Api(value = "驾驶舱-审批分舱",tags = {"驾驶舱-审批分舱"})
@RequestMapping("/driverOp")
public class DriverStoreOpManageController {

	@Autowired
	private DriverStoreOpManageService driverStoreOpManageService;

	@ApiOperation("审批分舱-全局受理情况统计")
	@GetMapping("/countHandle")
	public ResponseEntity countHandle() {
		return ResponseEntity.ok(driverStoreOpManageService.countHandle());
	}


	@ApiOperation("审批分舱-业务办理趋势统计")
	@GetMapping("/countTrend")
	public ResponseEntity countTrend() {
		return ResponseEntity.ok(driverStoreOpManageService.countTrend());
	}

	@ApiOperation("审批分舱-处室办理正点率TOP5统计")
	@GetMapping("/countDepartmentZdlTopFive")
	public ResponseEntity countDepartmentZdlTopFive() {
		return ResponseEntity.ok(driverStoreOpManageService.countDepartmentZdlTopFive());
	}

	@ApiOperation("审批分舱-处室办理办结量TOP5统计")
	@GetMapping("/countDepartmentBjlTopFive")
	public ResponseEntity countDepartmentBjlTopFive() {
		return ResponseEntity.ok(driverStoreOpManageService.countDepartmentBjlTopFive());
	}

	@ApiOperation("审批分舱-业务受理量地区统计")
	@GetMapping("/countRegionAccept")
	public ResponseEntity countRegionAccept() {
		return ResponseEntity.ok(driverStoreOpManageService.countRegionAccept());
	}

	@ApiOperation("审批分舱-业务办结量地区统计")
	@GetMapping("/countRegionFinish")
	public ResponseEntity countRegionFinish() {
		return ResponseEntity.ok(driverStoreOpManageService.countRegionFinish());
	}

	@ApiOperation("审批分舱-业务办理情况地图统计")
	@GetMapping("/countMapData")
	public ResponseEntity countMapData() {
		return ResponseEntity.ok(driverStoreOpManageService.countMapData());
	}

}
