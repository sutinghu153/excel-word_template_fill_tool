package com.hnup.osmp.jiance.controller;

import com.hnup.osmp.jiance.enums.EvaluateDepartTypeEnum;
import com.hnup.osmp.jiance.enums.EvaluateSatisfactionEnum;
import com.hnup.osmp.jiance.model.dto.*;
import com.hnup.osmp.jiance.service.evaluate.EvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.hnup.osmp.jiance.model.dto.EvaluateInfoDTO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  评价控制接口
 * @author zouronghua
 * @date 2021/11/20
 */
@RestController
@Api(value = "大监管-满意度评价统计-评价控制",tags = {"大监管-满意度评价统计-评价控制"})
@RequestMapping("/sup/evaluate")
public class EvaluateController {

	@Autowired
	private EvaluateService evaluateService;

	@ApiOperation("判断评价是在发证前还是在发证后")
	@GetMapping("/getOpState")
	public Integer getOpState(@RequestParam("keyDigNumGather") String keyDigNumGather){
		return  evaluateService.getOpState(keyDigNumGather);
	}

	@ApiOperation("提交评价信息")
	@PostMapping("/saveEvaluate")
	public void saveEvaluate(EvaluateInfoDTO evaluateInfoDTO){
		  evaluateService.saveEvaluate(evaluateInfoDTO);
	}


	@ApiOperation("月度评价统计")
	@GetMapping("/getEvaluateMonthly")
	public EvaluateMonthlyVO getEvaluateMonthly(@RequestParam("monthTime")  Date monthTime){
	   return evaluateService.getEvaluateMonthly(monthTime);
	}


	@ApiOperation("全局参评项目总数")
	@PostMapping("/getMonthlyEvaluationGlobalView")
	public Map<String, Object> getMonthlyEvaluationGlobalView(@RequestBody PageParamDTO pageParamDTO,
															  @RequestParam("monthTime") Date monthTime,
															  @RequestParam(value = "keyWord",required = false)String keyWord){
		 return evaluateService.getMonthlyEvaluationGlobalView(pageParamDTO,monthTime,keyWord);
	}

	@ApiOperation("全局参评个数")
	@PostMapping("/getMonthlyEvaluationItemView")
	public Map<String, Object> getMonthlyEvaluationItemView(@RequestBody PageParamDTO pageParamDTO,
															@RequestParam("monthTime") Date monthTime,
															@RequestParam(value = "keyWord",required = false)String keyWord,
															@RequestParam("itemType") Integer itemType){
		return evaluateService.getMonthlyEvaluationItemView(pageParamDTO,monthTime,keyWord,itemType);
	}

	@ApiOperation("月度处室参评统计")
	@PostMapping("/getDepartEvaluateMonthly")
	public DepartEvaluateMonthlyVO getDepartEvaluateMonthly(@RequestParam("monthTime") Date monthTime){
		return evaluateService.getDepartEvaluateMonthly(monthTime);
	}

	@ApiOperation("处室月度统计")
	@PostMapping("/getMonthlyDepartEvaluateView")
	public MonthlyDepartEvaluateVO getMonthlyDepartEvaluateView(@RequestBody PageParamDTO pageParamDTO,
																@RequestParam("monthTime") Date monthTime){
		return evaluateService.getMonthlyDepartEvaluateView(pageParamDTO,monthTime);
	}

	@ApiOperation("处室月度参评（参评，差评）")
	@PostMapping("/getMonthlyDepartEvaluateItemView")
	public Map<String, Object> getMonthlyDepartEvaluateItemView(@RequestBody PageParamDTO pageParamDTO,
																@RequestParam("monthTime") Date monthTime,
																@RequestParam(value = "keyWord",required = false)String keyWord,
																@RequestParam(value = "departCode",required = false)Integer departCode,
																@RequestParam("itemType")Integer itemType){
		return evaluateService.getMonthlyDepartEvaluateItemView(pageParamDTO,monthTime,keyWord,departCode,itemType);
	}

	@ApiOperation("个人统计月度参评（参评，差评）")
	@PostMapping("/getMonthlyEvaluateByUserView")
	public MonthlyEvaluateByUserVO getMonthlyEvaluateByUserView(@RequestBody PageParamDTO pageParamDTO,
																@RequestParam("monthTime") Date monthTime,
																@RequestParam("userName")String userName,
																@RequestParam("signUserDepart")Integer signUserDepart,
																@RequestParam("itemType")Integer itemType){
		return evaluateService.getMonthlyEvaluateByUserView(pageParamDTO,monthTime,userName,signUserDepart,itemType);
	}

	@ApiOperation("部门统计（办结）》统计")
	@GetMapping("/getPieChartData")
	public List<SfactionDegreeVO> getPieChartData(){
		return evaluateService.getPieChartData();
	}

	/**
	 * 部门统计（办结）》详表
	 * @param evaluateDepartTypeEnum 评价部门类型
	 * @param satisfactionLevel 满意度等级
	 * @param keyWord 关键字
	 */
	@ApiOperation("部门统计（办结）》详表")
	@PostMapping("/getMonitorGridView")
	public  Map<String, Object>  getMonitorGridView(@RequestBody PageParamDTO pageParamDTO,
													@RequestParam("evaluateDepartTypeEnum")Integer evaluateDepartTypeEnum,
													@RequestParam("satisfactionLevel") Integer satisfactionLevel,
													@RequestParam(value = "keyWord",required = false)String keyWord,
													@RequestParam(value = "prjname",required = false)String prjname,
													@RequestParam(value = "keynumgather",required = false)String keynumgather,
													@RequestParam(value = "startTime",required = false)Date startTime,
													@RequestParam(value = "endTime",required = false)Date endTime){
		return evaluateService.getMonitorGridView(pageParamDTO,evaluateDepartTypeEnum,satisfactionLevel,keyWord, prjname, keynumgather, startTime, endTime);
	}



	/**
	 * 已评价案卷（办结）》清单
	 * @param keyWord 关键字
	 */
	@ApiOperation("已评价案卷（办结）》清单")
	@PostMapping("/getMonitorItemView")
	public Map<String, Object> getMonitorItemView(@RequestBody PageParamDTO pageParamDTO,
												  @RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.getMonitorItemView(pageParamDTO,keyWord);
	}

	/**
	 * 待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷
	 * @param keyWord 关键字
	 */
	@ApiOperation("待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷")
	@PostMapping("/getUnEvaluateOps")
	public Map<String, Object> getUnEvaluateOps(@RequestBody PageParamDTO pageParamDTO,
												@RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.getUnEvaluateOps(pageParamDTO,keyWord);
	}

	/**
	 * 反馈意见箱（办理中）
	 * @param keyWord 关键字
	 */
	@ApiOperation("反馈意见箱（办理中）")
	@PostMapping("/getMonitorItemWorkingView")
	public Map<String, Object> getMonitorItemWorkingView(@RequestBody PageParamDTO pageParamDTO,
														 @RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.getMonitorItemWorkingView(pageParamDTO,keyWord);
	}

	/**
	 * 处室参评率统计（办结)
	 */
	@ApiOperation("处室参评率统计（办结)")
	@GetMapping("/getDepartEvaluatePercent")
	public List<DepartEvaluatePercentVO> getDepartEvaluatePercent(){
		return evaluateService.getDepartEvaluatePercent();
	}


	/**
	 * 经办人满意度评价（办结）》统计表
	 */
	@ApiOperation("经办人满意度评价（办结）》统计表")
	@GetMapping("/getPersonGoodDegreeEvaluate")
	public List<Map<String, Object>> getPersonGoodDegreeEvaluate(){
		return evaluateService.getPersonGoodDegreeEvaluate();
	}

	/**
	 * 经办人满意度评价（办结）》祥表
	 */
	@ApiOperation("经办人满意度评价（办结）》祥表")
	@PostMapping("/GetPersonDetailOps")
	public Map<String, Object> getPersonDetailOps(@RequestBody PageParamDTO pageParamDTO,
												  @RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.getPersonDetailOps(pageParamDTO,keyWord);
	}
	/**
	 * 部门统计（办结）》详表 导出
	 */
	@ApiOperation("部门统计（办结）》详表 导出")
	@PostMapping("/exportMonitorGridView")
	public ResponseEntity<InputStreamResource> exportMonitorGridView(@RequestParam("evaluateDepartTypeEnum")Integer evaluateDepartTypeEnum,
																	 @RequestParam("satisfactionLevel") Integer satisfactionLevel,
																	 @RequestParam(value = "keyWord",required = false)String keyWord,
																	 @RequestParam(value = "sortField",required = false)String sortField,
																	 @RequestParam(value = "sortOrder",required = false)String sortOrder,
																	 @RequestParam(value = "prjname",required = false)String prjname,
																	 @RequestParam(value = "keynumgather",required = false)String keynumgather,
																	 @RequestParam(value = "startTime",required = false)Date startTime,
																	 @RequestParam(value = "endTime",required = false)Date endTime){
		return evaluateService.exportMonitorGridView(evaluateDepartTypeEnum,satisfactionLevel,keyWord,sortField,sortOrder, prjname, keynumgather, startTime, endTime);
	}

	/**
	 * 已评价案卷（办结）》清单 导出
	 * @param keyWord 关键字
	 */
	@ApiOperation("已评价案卷（办结）》清单")
	@GetMapping("/exportMonitorItemView")
	public ResponseEntity<InputStreamResource> exportMonitorItemView(@RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.exportMonitorItemView(keyWord);
	}

	/**
	 * 待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷 导出
	 * @param keyWord 关键字
	 */
	@ApiOperation("待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷 导出")
	@GetMapping("/exportUnEvaluateOps")
	public ResponseEntity<InputStreamResource> exportUnEvaluateOps(@RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.exportUnEvaluateOps(keyWord);
	}

	/**
	 * 反馈意见箱（办理中） 导出
	 * @param keyWord 关键字
	 */
	@ApiOperation("反馈意见箱（办理中） 导出")
	@GetMapping("/exportMonitorItemWorkingView")
	public ResponseEntity<InputStreamResource>  exportMonitorItemWorkingView(@RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.exportMonitorItemWorkingView(keyWord);
	}

	/**
	 * 经办人满意度评价（办结）》祥表 导出
	 */
	@ApiOperation("经办人满意度评价（办结）》祥表 导出")
	@GetMapping("/exportPersonDetailOps")
	public ResponseEntity<InputStreamResource>  exportPersonDetailOps(@RequestParam(value = "keyWord",required = false)String keyWord){
		return evaluateService.exportPersonDetailOps(keyWord);
	}
}
