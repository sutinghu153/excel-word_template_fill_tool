package com.hnup.osmp.jiance.controller.home;

import com.hnup.osmp.jiance.repository.entity.census.HomeCensusColumnsVo;
import com.hnup.osmp.jiance.service.census.HomeCensusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2021/12/23
 * @Description
 */
@RestController
@Api(value = "首页统计",tags = {"首页统计"})
@RequestMapping("/home/census")
public class HomeCensusController {

	@Autowired
	private HomeCensusService homeCensusService;

	@ApiOperation("首页-统计数据")
	@GetMapping("/data")
	public List<HomeCensusColumnsVo> censusData(@RequestParam("circle") Integer circle) {
		return homeCensusService.loadHomeCensusData(circle);
	}
}
