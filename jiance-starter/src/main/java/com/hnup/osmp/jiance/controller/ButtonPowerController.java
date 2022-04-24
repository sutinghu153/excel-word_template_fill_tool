package com.hnup.osmp.jiance.controller;

import com.hnup.common.security.oauth2.UserPrincipal;
import com.hnup.osmp.jiance.service.button.ButtonPowerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *  按钮控制接口
 * @data2021/11/4,10:56
 * @authorsutinghu
 */
@RestController
@Api(value = "考核指标-省自然资源厅重点督察-按钮控制",tags = {"考核指标-省自然资源厅重点督察-按钮控制"})
@RequestMapping("/sup/buttonPower")
public class ButtonPowerController {

	@Autowired
	private ButtonPowerService buttonPowerService;

	@ApiOperation("获取按钮控制权限")
	@PostMapping("/getButtonPower")
	public ResponseEntity getButtonPower(UserPrincipal userPrincipal,@RequestParam("countId") Long countId){
		return ResponseEntity.ok(buttonPowerService.getButtonPower(userPrincipal,countId));
	}

}
