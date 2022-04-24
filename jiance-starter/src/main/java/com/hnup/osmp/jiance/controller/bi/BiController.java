package com.hnup.osmp.jiance.controller.bi;

import com.hnup.osmp.jiance.repository.entity.driver.DriverStoreVo;
import com.hnup.osmp.jiance.service.bi.XingZhengBiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.04.11 10:58
 */
@RestController
@Api(value = "bi接口",tags = {"bi接口（接口）"})
@RequestMapping("/bi")
public class BiController {

	@Autowired
	private XingZhengBiService xingZhengBiService;

	@ApiOperation("行政仓库")
	@PostMapping("/xingzheng")
	public ResponseEntity xingzhengData(@RequestParam("biType") String biType) {
		return ResponseEntity.ok(xingZhengBiService.getAll(biType));
	}

}
