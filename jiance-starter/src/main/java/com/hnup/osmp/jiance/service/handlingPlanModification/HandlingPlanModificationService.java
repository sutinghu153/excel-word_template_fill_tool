package com.hnup.osmp.jiance.service.handlingPlanModification;

import com.hnup.osmp.shiju.op.client.statical.OpBoxFeignClient;
import com.hnup.osmp.shiju.op.model.dto.common.Pagination;
import com.hnup.osmp.shiju.op.model.vo.box.BoxColumnInfoVo;
import com.hnup.osmp.shiju.op.model.vo.box.BoxQueryParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @data2021/12/17,14:22
 * @authorsutinghu
 */
@Service
public class HandlingPlanModificationService {

	@Autowired
	private OpBoxFeignClient opBoxFeignClient;

	private Long bjBoxId = Long.valueOf(120);
	private Long zbBoxId = Long.valueOf(130);
	
	public Map<String, Object> getFinishList(Date startTime, Date endTime,String keyTypeCode) {

		Assert.notNull(startTime,"开始时间不能为空");
		Assert.notNull(endTime,"结束时间不能为空");
		Assert.notNull(keyTypeCode,"业务编号不能为空");

		BoxQueryParamVo boxQueryParamVo = new BoxQueryParamVo();
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList(keyTypeCode));
		boxQueryParamVo.setPageIndex(1);
		boxQueryParamVo.setPageSize(100000);
		boxQueryParamVo.setCreateTimeS(startTime);
		boxQueryParamVo.setCreateTimeE(endTime);
		Pagination<Map<String, Object>> boxData = opBoxFeignClient.getBoxData(bjBoxId, boxQueryParamVo);

		Map<String, Object> map = new HashMap<>();
		map.put("value",boxData.getRecords());

		List<BoxColumnInfoVo> boxColumns = opBoxFeignClient.getBoxColumns(bjBoxId);
		map.put("columns",boxColumns);

		return map;
	}

	public Map<String, Object> getProcessList(Date startTime, Date endTime, String keyTypeCode) {

		Assert.notNull(startTime,"开始时间不能为空");
		Assert.notNull(endTime,"结束时间不能为空");
		Assert.notNull(keyTypeCode,"业务编号不能为空");

		BoxQueryParamVo boxQueryParamVo = new BoxQueryParamVo();
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList(keyTypeCode));
		boxQueryParamVo.setPageIndex(1);
		boxQueryParamVo.setPageSize(100000);
		boxQueryParamVo.setCreateTimeS(startTime);
		boxQueryParamVo.setCreateTimeE(endTime);
		Pagination<Map<String, Object>> boxData = opBoxFeignClient.getBoxData(zbBoxId, boxQueryParamVo);

		Map<String, Object> map = new HashMap<>();
		map.put("value",boxData.getRecords().size());

		List<BoxColumnInfoVo> boxColumns = opBoxFeignClient.getBoxColumns(zbBoxId);
		map.put("columns",boxColumns);

		return map;

	}

	public Map<String, Object> getIndex(Date startTime, Date endTime) {

		Assert.notNull(startTime,"开始时间不能为空");
		Assert.notNull(endTime,"结束时间不能为空");

		// 统计办结
		BoxQueryParamVo boxQueryParamVo = new BoxQueryParamVo();
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("15"));
		boxQueryParamVo.setPageIndex(1);
		boxQueryParamVo.setPageSize(100000);
		boxQueryParamVo.setCreateTimeS(startTime);
		boxQueryParamVo.setCreateTimeE(endTime);

		// T0
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("15"));
		Pagination<Map<String, Object>> boxData_15 = opBoxFeignClient.getBoxData(bjBoxId, boxQueryParamVo);
		// T1
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("46"));
		Pagination<Map<String, Object>> boxData_46 = opBoxFeignClient.getBoxData(bjBoxId, boxQueryParamVo);
		// T2
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("47"));
		Pagination<Map<String, Object>> boxData_47 = opBoxFeignClient.getBoxData(bjBoxId, boxQueryParamVo);

		Integer bj_15 = boxData_15.getRecords().size();
		Integer bj_46 = boxData_46.getRecords().size();
		Integer bj_47 = boxData_47.getRecords().size();

		Map<String, Object> bjmap = new HashMap<>();

		bjmap.put("15",bj_15);
		bjmap.put("46",bj_46);
		bjmap.put("47",bj_47);
		bjmap.put("all",bj_15+bj_46+bj_47);

		// T0
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("15"));
		Pagination<Map<String, Object>> zbboxData_15 = opBoxFeignClient.getBoxData(zbBoxId, boxQueryParamVo);
		// T1
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("46"));
		Pagination<Map<String, Object>> zbboxData_46 = opBoxFeignClient.getBoxData(zbBoxId, boxQueryParamVo);
		// T2
		boxQueryParamVo.setKeyTypeCodeList(Arrays.asList("47"));
		Pagination<Map<String, Object>> zbboxData_47 = opBoxFeignClient.getBoxData(zbBoxId, boxQueryParamVo);

		Integer zb_15 = zbboxData_15.getRecords().size();
		Integer zb_46 = zbboxData_46.getRecords().size();
		Integer zb_47 = zbboxData_47.getRecords().size();

		Map<String, Object> zbmap = new HashMap<>();

		zbmap.put("15",zb_15);
		zbmap.put("46",zb_46);
		zbmap.put("47",zb_47);
		zbmap.put("all",zb_15+zb_46+zb_47);

		Map<String, Object> map = new HashMap<>();
		map.put("办结",bjmap);
		map.put("在办",zbmap);
		return map;

	}
}
