package com.hnup.osmp.jiance.admin.controller;

import com.hnup.osmp.jiance.service.compute.ComputeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 *  公式配置
 * @data2021/11/19,14:48
 * @authorsutinghu
 */
@RestController
@Api(value = "维护系统-公式配置",tags = {"维护系统-公式配置"})
@RequestMapping("/sup/admin/computerConfig")
public class ComputerConfigController {

	@Autowired
	private ComputeService computeService;

	@ApiOperation("公式配置-公式清单")
	@PostMapping("/getOperationList")
	public ResponseEntity getOperationList(){
		return ResponseEntity.ok(computeService.getOperationList());
	}


	@ApiOperation("公式配置-新增公式")
	@PutMapping("/addOperation")
	public ResponseEntity addOperation(@RequestParam("fields") String fields,
									   @RequestParam("table") String table,
									   @RequestParam("operation") String operation,
									   @RequestParam("desc")String desc,
									   @RequestParam("resultFeild")String resultFeild,
									   @RequestParam("sign")String sign){
		Assert.notNull(fields,"计算字段不能为空");
		Assert.notNull(table,"表名称不能为空");
		Assert.notNull(operation,"公式不能为空");
		Assert.notNull(desc,"公式描述不能为空");
		Assert.notNull(resultFeild,"结果字段不能为空");
		Assert.notNull(sign,"记录标记不能为空");
		computeService.addOperation(fields,table,operation,desc,resultFeild,sign);
		return ResponseEntity.ok("上传成功");
	}

	@ApiOperation("公式配置-更新公式")
	@PostMapping("/updateOperation")
	public ResponseEntity updateOperation(@RequestParam("fields") String fields,
										  @RequestParam("table") String table,
										  @RequestParam("operation") String operation,
										  @RequestParam("desc")String desc,
										  @RequestParam("resultFeild")String resultFeild,
										  @RequestParam("sign")String sign,
										  @RequestParam("operationId") Long operationId){
		Assert.notNull(fields,"计算字段不能为空");
		Assert.notNull(table,"表名称不能为空");
		Assert.notNull(operation,"公式不能为空");
		Assert.notNull(desc,"公式描述不能为空");
		Assert.notNull(resultFeild,"结果字段不能为空");
		Assert.notNull(sign,"记录标记不能为空");
		Assert.notNull(operationId,"公式主键不能为空");
		computeService.updateOperation(fields,table,operation,desc,resultFeild,sign,operationId);
		return ResponseEntity.ok("更新成功");
	}

	@ApiOperation("公式配置-删除公式")
	@DeleteMapping("/deleteOperation")
	public ResponseEntity deleteOperation(@RequestParam("operationId") Long operationId){
		computeService.deleteOperation(operationId);
		return ResponseEntity.ok("删除成功");
	}


}
