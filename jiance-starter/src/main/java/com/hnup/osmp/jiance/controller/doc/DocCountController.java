package com.hnup.osmp.jiance.controller.doc;
//
//import com.hnup.osmp.doc.model.DocSearchDto;
//import com.hnup.osmp.jiance.service.doccount.DocCountService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @data2021/12/1,10:27
// * @authorsutinghu
// */
//@RestController
//@Api(value = "规划修改办理-公文统计",tags = {"规划修改办理-公文统计"})
//@RequestMapping("/sup/doc/count")
//public class DocCountController {
//
//	@Autowired
//	private DocCountService docCountService;
//
//	@ApiOperation("详规处在办")
//	@PostMapping("/getXgc")
//	public ResponseEntity getXgc(@RequestBody DocSearchDto docSearchDto){
//		return ResponseEntity.ok(docCountService.getXgc(docSearchDto));
//	}
//
//}
