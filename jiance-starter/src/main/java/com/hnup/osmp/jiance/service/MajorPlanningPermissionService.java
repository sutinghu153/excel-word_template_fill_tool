package com.hnup.osmp.jiance.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hnup.common.export.starter.service.ExportExcelService;
import com.hnup.common.export.starter.vo.ExcelExportVO;
import com.hnup.common.lang.util.date.ChinaDateFormatHelper;
import com.hnup.osmp.jiance.model.dto.UnEvaluateOpsVO;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcMajorPlanningPermissionDAO;
import com.hnup.osmp.jiance.repository.entity.JcMajorPlanningPermission;
import com.hnup.osmp.jiance.utils.DateUtils;
import com.hnup.osmp.jiance.utils.JiancePageUtils;
import com.hnup.osmp.tz.client.TzListFeignClient;
import com.hnup.osmp.tz.model.dto.ConditionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @data2021/12/17,16:08
 * @authorsutinghu
 */
@Service
public class MajorPlanningPermissionService {

	@Autowired
	private IJcMajorPlanningPermissionDAO iJcMajorPlanningPermission;

	private static final String QB = "全部";

	private static final Integer TZ_GUIHUA_XUKE = 3253;

	@Autowired
	private TzListFeignClient tzListFeignClient;

	@Autowired
	private ExportExcelService exportExcelService;

	public List<ConditionDTO> buildConditions(String region,String type){
		List<ConditionDTO> conditions = new ArrayList<>(3);
		// 构造查询条件——案卷编号不为空
		ConditionDTO conditionNotNull = new ConditionDTO();
		conditionNotNull.setFldId("fld_10570_19");
		conditionNotNull.setFldOp("不为空");
		conditionNotNull.setFldCombo(1);
		conditions.add(conditionNotNull);

		if (org.apache.commons.lang3.StringUtils.isNotBlank(type)) {
			// 构造查询条件——发证类型
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("fld_10570_7");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(type);
			conditions.add(condition);
		}

		if (org.apache.commons.lang3.StringUtils.isNotBlank(region)) {

			if (!Objects.equals("全部",region)) {
				// 构造查询条件——地区
				ConditionDTO condition = new ConditionDTO();
				condition.setFldId("fld_10570_12");
				condition.setFldOp("等于");
				condition.setFldCombo(1);
				condition.setFldValue(region);
				conditions.add(condition);
			}
		}
		return conditions;
	}


	public List<HashMap<String, Object>> getDataRource(List<ConditionDTO> conditions){
		return tzListFeignClient.getTzDataList(conditions, TZ_GUIHUA_XUKE);
	}


	public List<JcMajorPlanningPermission> buildJcMajorPlanningPermissions(String region, String type){

		List<HashMap<String, Object>> resultData = null;

		// 构造查询条件 获取数据源
		resultData = this.getDataRource(this.buildConditions(region,type));

		// 构造对象
		if (org.apache.commons.collections.CollectionUtils.isEmpty(resultData)) {
			return Lists.newArrayList();
		}

		List<JcMajorPlanningPermission> jcMajorPlanningPermissions = new ArrayList<>();

		for (HashMap<String, Object> map : resultData) {
			JcMajorPlanningPermission jcMajorPlanningPermission = new JcMajorPlanningPermission();
			// 案卷编号
			jcMajorPlanningPermission.setKeyNumberGather(this.getValueByString(map.get("fld_10570_9")));

			// 所属地区
			jcMajorPlanningPermission.setRegion(this.getValueByString(map.get("fld_10570_12")));

			// 建设单位
			jcMajorPlanningPermission.setProUnits(this.getValueByString(map.get("fld_10570_10")));

			// 项目号
			jcMajorPlanningPermission.setProNumber(this.getValueByString(map.get("fld_10570_8")));
			;
			// 建设地址
			jcMajorPlanningPermission.setProSite(this.getValueByString(map.get("fld_10570_13")));

			// 发证面积
			jcMajorPlanningPermission.setArea(this.getValueByDouble(map.get("fld_10570_14")));

			// 用地性质
			jcMajorPlanningPermission.setLandType(this.getValueByString(map.get("fld_10570_18")));

			// 发证时间
			Date date = DateUtils.str2Date((String) map.get("fld_10570_17")) ;
			jcMajorPlanningPermission.setProveTime(date);

			// 案卷号
			jcMajorPlanningPermission.setKeyDigNumGather(this.getValueByLong(map.get("fld_10570_19")));

			// 发证类型
			jcMajorPlanningPermission.setType(this.getValueByString(map.get("fld_10570_7")));

			// 许可证号
			jcMajorPlanningPermission.setXkzh(this.getValueByString(map.get("fld_10570_16")));

			jcMajorPlanningPermissions.add(jcMajorPlanningPermission);
		}

		return jcMajorPlanningPermissions;
	}

	public List<Map<String, Object>> getIndex(String region) {

		List<JcMajorPlanningPermission> jcMajorPlanningPermissions = this.buildJcMajorPlanningPermissions(null,null);
//			iJcMajorPlanningPermission.lambdaQuery()
//			.list();

		if (CollectionUtils.isEmpty(jcMajorPlanningPermissions)) {
			return Lists.newArrayList();
		}

		if (!StringUtils.isEmpty(region)) {
			if (!Objects.equals(QB,region)) {
				jcMajorPlanningPermissions = jcMajorPlanningPermissions
					.stream()
					.filter(e-> Objects.equals(e.getRegion(),region))
					.collect(Collectors.toList());
			}
		}

		Map<String, List<JcMajorPlanningPermission>> collect = jcMajorPlanningPermissions.stream().collect(Collectors.groupingBy(JcMajorPlanningPermission::getType));

		List<Map<String, Object>> list = new ArrayList<>();
		for (Map.Entry<String, List<JcMajorPlanningPermission>>map:collect.entrySet()) {
			Map<String, Object> m = new HashMap<>();
			m.put("size",map.getValue().size());
			m.put("name",map.getKey());

			List<JcMajorPlanningPermission> value = map.getValue();

			BigDecimal all = new BigDecimal(0);
			for (JcMajorPlanningPermission jcMajorPlanningPermission:value) {
				if (jcMajorPlanningPermission.getArea() != null) {
					all = ComputeBase.addCompute(all,jcMajorPlanningPermission.getArea());
				}else {
					continue;
				}
			}
			m.put("all",all);
			list.add(m);
		}

		return list;
	}

	public Map<String, Object> getList(Date startTime, Date endTime, String region, String type, Integer pageIndex, Integer pageSize,String keyWords) {

		List<JcMajorPlanningPermission> jcMajorPlanningPermissions = this.buildJcMajorPlanningPermissions(region,type);


//			iJcMajorPlanningPermission.lambdaQuery()
//			.le(endTime != null, JcMajorPlanningPermission::getProveTime, endTime)
//			.ge(startTime != null, JcMajorPlanningPermission::getProveTime, startTime)
//			.eq(type!=null,JcMajorPlanningPermission::getType,type)
//			.list();

		if ( startTime != null && endTime != null) {

			jcMajorPlanningPermissions = jcMajorPlanningPermissions.stream().filter(jcMajorPlanningPermission -> {

				if (jcMajorPlanningPermission.getProveTime() == null) {
					return false;
				}else {

					if (jcMajorPlanningPermission.getProveTime().before(endTime) && jcMajorPlanningPermission.getProveTime().after(startTime)) {
						return true;
					}else {
						return false;
					}

				}
			}).collect(Collectors.toList());

		}

		if (CollectionUtils.isEmpty(jcMajorPlanningPermissions)) {
			return Maps.newHashMap();
		}

		if (!StringUtils.isEmpty(keyWords)) {
			jcMajorPlanningPermissions = jcMajorPlanningPermissions
				.stream()
				.filter(e-> e.toString().contains(keyWords))
				.collect(Collectors.toList());
		}

		if (CollectionUtils.isEmpty(jcMajorPlanningPermissions)) {
			return Maps.newHashMap();
		}

		if (!StringUtils.isEmpty(region)) {
			if (!Objects.equals(QB,region)) {
				jcMajorPlanningPermissions = jcMajorPlanningPermissions
					.stream()
					.filter(e-> Objects.equals(e.getRegion(),region))
					.collect(Collectors.toList());
			}
		}

		Map<String, Object> result = new HashMap<>();

		result.put("data",JiancePageUtils.startPage(jcMajorPlanningPermissions,pageIndex,pageSize));
		result.put("toal",jcMajorPlanningPermissions.size());

		return result;
	}

	public ResponseEntity<InputStreamResource> exportList(Date startTime, Date endTime, String region, String type,String keyWords){
		List<JcMajorPlanningPermission> jcMajorPlanningPermissions = this.buildJcMajorPlanningPermissions(region,type);

		ExcelExportVO<Map<String, Object>> excelExportVO = ExcelExportVO.buildDefaultExcelExportVO();

		excelExportVO.addColumnMap("keyNumberGather", "案卷编号", 30);
		excelExportVO.addColumnMap("region", "所属地区", 40);
		excelExportVO.addColumnMap("xkzh", "许可证号", 30);
		excelExportVO.addColumnMap("proUnits", "建设单位", 30);
		excelExportVO.addColumnMap("proNumber", "项目号", 30);
		excelExportVO.addColumnMap("proSite", "建设地址", 30);
		excelExportVO.addColumnMap("area", "发证面积", 30);
		excelExportVO.addColumnMap("landType", "用地性质", 30);
		excelExportVO.addDateColumnMap("proveTime", "发证时间", 30, ChinaDateFormatHelper.FORMAT_SECOND);
		excelExportVO.addColumnMap("type", "发证类型", 30);

		if ( startTime != null && endTime != null) {
			jcMajorPlanningPermissions = jcMajorPlanningPermissions.stream().filter(jcMajorPlanningPermission -> {
				if (jcMajorPlanningPermission.getProveTime() == null) {
					return false;
				}else {
					if (jcMajorPlanningPermission.getProveTime().before(endTime) && jcMajorPlanningPermission.getProveTime().after(startTime)) {
						return true;
					}else {
						return false;
					}
				}
			}).collect(Collectors.toList());
		}

		if (CollectionUtils.isEmpty(jcMajorPlanningPermissions)) {
			return exportExcelService.exportExcelResult(excelExportVO, "规划许可明细.xlsx");
		}

		if (!StringUtils.isEmpty(region)) {
			if (!Objects.equals(QB,region)) {
				jcMajorPlanningPermissions = jcMajorPlanningPermissions
					.stream()
					.filter(e-> Objects.equals(e.getRegion(),region))
					.collect(Collectors.toList());
			}
		}
		if (!StringUtils.isEmpty(keyWords)) {
			jcMajorPlanningPermissions = jcMajorPlanningPermissions
				.stream()
				.filter(e-> e.toString().contains(keyWords))
				.collect(Collectors.toList());
		}

		if (CollectionUtils.isEmpty(jcMajorPlanningPermissions)) {
			return exportExcelService.exportExcelResult(excelExportVO, "规划许可明细.xlsx");
		}

		if(org.apache.commons.collections.CollectionUtils.isEmpty(jcMajorPlanningPermissions)) {
			return exportExcelService.exportExcelResult(excelExportVO, "待评价案卷办结清单.xlsx");
		}
		List<Map<String, Object>> contentList = new ArrayList<>(8);
		for (JcMajorPlanningPermission permission : jcMajorPlanningPermissions) {
			Map<String, Object> map = new HashMap<>(8);
			map.put("keyNumberGather",permission.getKeyNumberGather());
			map.put("region",permission.getRegion());
			map.put("xkzh",permission.getXkzh());
			map.put("proUnits",permission.getProUnits());
			map.put("proNumber",permission.getProNumber());
			map.put("proSite",permission.getProSite());
			map.put("area",permission.getArea());
			map.put("landType",permission.getLandType());
			map.put("proveTime",permission.getProveTime());
			map.put("type",permission.getType());
			contentList.add(map);
		}
		excelExportVO.setContentList(contentList);
		return exportExcelService.exportExcelResult(excelExportVO, "待评价案卷办结清单.xlsx");
	}

	public List<String> getRegions() {

		List<JcMajorPlanningPermission> jcMajorPlanningPermissions =  this.buildJcMajorPlanningPermissions(null,null);

//		iJcMajorPlanningPermission.lambdaQuery().list();
		List<String> regions = new ArrayList<>();
		regions = jcMajorPlanningPermissions
			.stream()
			.map(JcMajorPlanningPermission::getRegion)
			.distinct()
			.collect(Collectors.toList());
		regions.add(0,"全部");
		return regions;
	}

	public BigDecimal getValueByDouble(Object fieldValue){

		if (org.apache.commons.lang3.StringUtils.isNotBlank((String) fieldValue)) {
			return new BigDecimal((String) fieldValue);
		}else {
			return new BigDecimal("0");
		}

	}

	public String getValueByString(Object fieldValue){

		if (org.apache.commons.lang3.StringUtils.isNotBlank((String) fieldValue)) {
			return (String) fieldValue;
		}else {
			return null;
		}

	}

	public Long getValueByLong(Object fieldValue){

		if (org.apache.commons.lang3.StringUtils.isNotBlank((String) fieldValue)) {
			return Long.parseLong((String) fieldValue) ;
		}else {
			return null;
		}

	}

}
