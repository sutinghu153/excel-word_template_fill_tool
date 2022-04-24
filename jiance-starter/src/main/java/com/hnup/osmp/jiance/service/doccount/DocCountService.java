//package com.hnup.osmp.jiance.service.doccount;
//
//import com.hnup.common.core.starter.model.Pagination;
//import com.hnup.osmp.doc.client.DocFeignClient;
//import com.hnup.osmp.doc.model.DocResultDto;
//import com.hnup.osmp.doc.model.DocSearchDto;
//import com.hnup.osmp.doc.client.DocFeignClient;
//import com.hnup.osmp.doc.model.DocResultDto;
//import com.hnup.osmp.doc.model.DocSearchDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// *  规划修改办理-公文统计
// * @data2021/12/1,10:28
// * @authorsutinghu
// */
//@Service
//public class DocCountService {
//
//	@Autowired
//	private DocFeignClient docFeignClient;
//
//	public Pagination<DocResultDto> getXgc(DocSearchDto docSearchDto) {
//		Pagination<DocResultDto> result = docFeignClient.getBoxData(docSearchDto);
//		return result;
//	}
//}
