package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.service.assess.PronviceNaturalReourceSuperviseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  省自然资源厅重点督察任务接口
 * @data2021/11/4,9:56
 * @authorsutinghu
 */
@RestController
@Api(value = "考核指标-省自然资源厅重点督察",tags = {"考核指标-省自然资源厅重点督察"})
@RequestMapping("/sup/pronvicenaturalreource")
public class PronviceNaturalReourceSuperviseController {

	@Autowired
	private PronviceNaturalReourceSuperviseService pronviceNaturalReourceSuperviseService;


	@ApiOperation("获取目录树结构")
	@PostMapping("/tree")
	public ResponseEntity getTree(){
		return ResponseEntity.ok(pronviceNaturalReourceSuperviseService.getTree());
	}

}
