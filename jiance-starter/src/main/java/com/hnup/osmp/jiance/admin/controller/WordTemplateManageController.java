package com.hnup.osmp.jiance.admin.controller;

import com.hnup.common.security.oauth2.UserPrincipal;
import com.hnup.osmp.jiance.service.file.FileHandleService;
import com.hnup.osmp.jiance.service.file.WordTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 *  word模板处理维护系统接口
 * @data2021/11/18,11:29
 * @authorMSI
 */
@RestController
@Api(value = "维护系统-文件处理",tags = {"维护系统-文件处理"})
@RequestMapping("/sup/admin/fileHandle")
public class WordTemplateManageController {

	@Autowired
	private FileHandleService fileHandleService;
	@Autowired
	private WordTemplateService wordTemplateService;

	@ApiOperation("WORD-分组列表")
	@GetMapping("/getGroup")
	public ResponseEntity getGroup(){
		return ResponseEntity.ok(fileHandleService.getGroup());
	}


	@ApiOperation("新增文件模块")
	@PostMapping("/buildMoudel")
	public ResponseEntity buildMoudel(@RequestParam(value = "belongName") String belongName,
									  @RequestParam(value = "belongDesc") String belongDesc,
									  @RequestParam(value = "modelName") String modelName,
									  UserPrincipal principal){
		return ResponseEntity.ok(wordTemplateService.buildMoudel(belongName,belongDesc,modelName,principal));
	}

	@ApiOperation("获取文件模块")
	@GetMapping("/getMoudelList")
	public ResponseEntity getMoudelList(@RequestParam(value = "modelName") String modelName){
		return ResponseEntity.ok(wordTemplateService.getMoudelList(modelName));
	}

	@ApiOperation("WORD-模板清单")
	@GetMapping("/getemplateList")
	public ResponseEntity getemplateList(@RequestParam("beLongId") Long beLongId){
		return ResponseEntity.ok(fileHandleService.getemplateList(beLongId));
	}

	@ApiOperation("WORD-导出模板")
	@PostMapping("/exportWordTemplate")
	public ResponseEntity exportWordTemplate(@RequestParam("templateId") Long templateId){
		return ResponseEntity.ok(fileHandleService.exportWordTemplate(templateId));
	}

	@ApiOperation("WORD-新增模板")
	@PutMapping("/addWordTemplate")
	public ResponseEntity addWordTemplate(@RequestParam("templetName") String templetName,
										  @RequestParam("file") MultipartFile file,
										  @RequestParam("modelName") String modelName,
										  @RequestParam("belongId") Long belongId,
										  @RequestParam("fileType") String fileType,
										  @RequestParam("wordOrExcel") String wordOrExcel,
										  UserPrincipal principal){
		Assert.notNull(templetName,"模板名称不能为空");
		Assert.notNull(principal.getUsername(),"创建者不能为空");
		Assert.notNull(file,"模板不能为空");
		fileHandleService.addWordTemplate(templetName,
			file,
			modelName,
			belongId,
			fileType,
			wordOrExcel,
			new Date(),
			principal.getUsername());
		return ResponseEntity.ok("上传成功");
	}

	@ApiOperation("WORD-更新模板")
	@PutMapping("/updateWordTemplate")
	public ResponseEntity updateWordTemplate(@RequestParam("templateId") Long templateId,
											 @RequestParam("templetName") String templetName,
										  @RequestParam("file") MultipartFile file,
										  @RequestParam("modelName") String modelName,
										  @RequestParam("belongId") Long belongId,
										  @RequestParam("fileType") String fileType,
										  @RequestParam("wordOrExcel") String wordOrExcel,
										  UserPrincipal principal){
		Assert.notNull(templetName,"模板名称不能为空");
		Assert.notNull(principal.getUsername(),"创建者不能为空");
		Assert.notNull(file,"模板不能为空");
		fileHandleService.updateWordTemplate(templateId,
			templetName,
			file,
			modelName,
			belongId,
			fileType,
			wordOrExcel,
			new Date(),
			principal.getUsername());
		return ResponseEntity.ok("更新成功");
	}

	@ApiOperation("WORD-删除模板")
	@DeleteMapping("/deleteWordTemplate")
	public ResponseEntity deleteWordTemplate(@RequestParam("templeteId") Long templeteId){
		fileHandleService.deleteWordTemplate(templeteId);
		return ResponseEntity.ok("删除成功");
	}

}
