package com.hnup.osmp.jiance.controller.file;

import com.hnup.osmp.jiance.service.file.FileHandleService;
import com.hnup.osmp.jiance.service.file.WordTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *  文件处理控制层
 * @data2021/11/5,11:26
 * @authorsutinghu
 */
@RestController
@Api(value = "文件处理",tags = {"文件处理"})
@RequestMapping("/sup/fileHandle")
public class FileHandleController {


	@Autowired
	private FileHandleService fileHandleService;

	@Autowired
	private WordTemplateService wordTemplateService;

	@ApiOperation("考核指标-省自然资源厅重点督察-导出文件")
	@PostMapping("/exportExcleBycount")
	public ResponseEntity<InputStreamResource> exportExcleBycount(@RequestParam("countId") Long countId,
																  @RequestParam(value = "countTime",required = false) String countTime,
																  @RequestParam(value = "asExOrIm",required = false)Integer asExOrIm){
		return fileHandleService.exportExcleBycount(countId,countTime,asExOrIm);
	}

	@ApiOperation("考核指标-省自然资源厅重点督察-导入文件")
	@PostMapping("/importExcleBycount")
	public ResponseEntity importExcleBycount(@RequestParam("countId") Long countId,
											 @RequestParam("file") MultipartFile file){
		return ResponseEntity.ok(fileHandleService.importExcleBycount(countId,file));
	}

	@ApiOperation("WORD-导出文件-新查数据")
	@PostMapping("/exportWord")
	public ResponseEntity exportWord(@RequestParam("templeteId") Long templeteId){
		return ResponseEntity.ok(wordTemplateService.buildTemplate(templeteId));
	}

	@ApiOperation("获取历史文件【word】")
	@GetMapping("/getHistoryFileWord")
	public ResponseEntity getHistoryFileWord(@RequestParam(value = "templeteId") Long templeteId,
										 @RequestParam(value = "wordOrExcel") String wordOrExcel,
										 @RequestParam(value = "fileType",required = false) String fileType,
										 @RequestParam(value = "year",required = false) String year,
										 @RequestParam(value = "month",required = false) String month,
										 @RequestParam(value = "day",required = false) String day,
										 @RequestParam(value = "executeDate",required = false) Boolean executeDate){

		if (executeDate==null) {
			executeDate = true;
		}

		return ResponseEntity.ok(wordTemplateService.getHistoryFileWord(templeteId,wordOrExcel,fileType,year,month,day,executeDate));
	}


	@ApiOperation("获取历史文件【pdf】")
	@GetMapping("/getHistoryFilePdf")
	public ResponseEntity getHistoryFilePdf(@RequestParam(value = "templeteId") Long templeteId,
										 @RequestParam(value = "wordOrExcel") String wordOrExcel,
										 @RequestParam(value = "fileType",required = false) String fileType,
										 @RequestParam(value = "year",required = false) String year,
										 @RequestParam(value = "month",required = false) String month,
										 @RequestParam(value = "day",required = false) String day,
										 @RequestParam(value = "executeDate",required = false) Boolean executeDate){

		if (executeDate==null) {
			executeDate = true;
		}

		return ResponseEntity.ok(wordTemplateService.getHistoryFilePdf(templeteId,wordOrExcel,fileType,year,month,day,executeDate));
	}

	@ApiOperation("获取文件模块")
	@GetMapping("/getMoudelList")
	public ResponseEntity getMoudelList(@RequestParam(value = "modelName") String modelName){
		return ResponseEntity.ok(wordTemplateService.getMoudelList(modelName));
	}

	@ApiOperation("获取文件列表")
	@GetMapping("/getFileList")
	public ResponseEntity getFileList(
		@RequestParam(value = "belongId") Long belongId,
		@RequestParam(value = "year",required = false) String year,
		@RequestParam(value = "month",required = false) String month,
		@RequestParam(value = "day",required = false) String day){
		return ResponseEntity.ok(wordTemplateService.getFileList(belongId,year,month,day));
	}

	@ApiOperation("获取土地利用现状的文件")
	@GetMapping("/getLandUserImage")
	public ResponseEntity getLandUserImage(@RequestParam(value = "year")String year){
		return ResponseEntity.ok(wordTemplateService.getLandUserImage(year));
	}

	@ApiOperation("保存土地利用现状的文件")
	@PostMapping("/saveLandUserImage")
	public void saveLandUserImage(@RequestParam(value = "year")String year,
								  @RequestParam("file") MultipartFile file){
		wordTemplateService.saveLandUserImage(year,file);
	}
}
