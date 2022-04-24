package com.hnup.osmp.jiance.controller.driver;

import com.hnup.osmp.jiance.repository.entity.driver.*;
import com.hnup.osmp.jiance.service.driver.DriverStoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description
 */
@RestController
@Api(value = "驾驶舱",tags = {"驾驶舱"})
@RequestMapping("/driver")
public class DriverStoreController {

	@Autowired
	private DriverStoreService driverStoreService;

	@ApiOperation("驾驶舱-统计数据")
	@PostMapping("/viewData")
	public DriverStoreVo viewData(@RequestParam("year") Integer year, @RequestParam("unit") Long unit) {
		if(unit == null) {
			unit = 1L;
		}
		return driverStoreService.loadDriverStoreData(year, unit);
	}

	@ApiOperation("驾驶舱-规划分仓")
	@PostMapping("/planningData")
	public DriverStorePlanningVo planningData() {
		// TODO 分仓数据目前没确定，数据暂时写死
		return driverStoreService.loadPlanningStore();
	}

	@ApiOperation("驾驶舱-土地分仓")
	@PostMapping("/landData")
	public DriverStoreLandVo landData() {
		// TODO 分仓数据目前没确定，数据暂时写死
		return driverStoreService.loadLandStore();
	}

	@ApiOperation("驾驶舱-保存土地数据(土地管理)")
	@PostMapping("/saveTudi")
	public void saveTudi(@RequestBody DriverStoreTudi tudi) {
		driverStoreService.saveTudi(tudi);
	}

	@ApiOperation("驾驶舱-保存地矿数据(地质矿产)")
	@PostMapping("/saveDikuang")
	public void saveDikuang(@RequestBody DriverStoreDikuang dikuang) {
		driverStoreService.saveDikuang(dikuang);
	}

	@ApiOperation("驾驶舱-保存项目数据(项目管理)")
	@PostMapping("/saveProject")
	public void saveProject(@RequestBody DriverStoreProject project) {
		driverStoreService.saveProject(project);
	}

	@ApiOperation("驾驶舱-保存项目排行榜数据(项目管理)")
	@PostMapping("/saveProjectRank")
	public void saveProjectRank(@RequestBody List<DriverStoreProjectRank> ranks) {
		driverStoreService.saveProjectRank(ranks);
	}

	@ApiOperation("驾驶舱-保存测绘数据(测绘调查)")
	@PostMapping("/saveCehui")
	public void saveCehui(@RequestBody DriverStoreCehui cehui) {
		driverStoreService.saveCehui(cehui);
	}

	@ApiOperation("驾驶舱-规划编制项目总览(规划管控)")
	@PostMapping("/saveGuihuaPrj")
	public void saveGuihuaPrj(@RequestBody DriverStoreGuihuaPrj guihuaPrj) {
		driverStoreService.saveGuihuaPrj(guihuaPrj);
	}

	@ApiOperation("驾驶舱-规划编制项目状态(规划管控)")
	@PostMapping("/saveGuihuaGain")
	public void saveGuihuaGain(@RequestBody DriverStoreGuihuaGain guihuaGain) {
		driverStoreService.saveGuihuaGain(guihuaGain);
	}

	@ApiOperation("驾驶舱-不动产统一登记/农村房地一体确权登记(资产产权)")
	@PostMapping("/saveZican")
	public void saveZican(@RequestBody DriverStoreZican zican) {
		driverStoreService.saveZican(zican);
	}
}
