package com.hnup.osmp.jiance.service.landusedbuilding;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcLandusedBuildingOpDAO;
import com.hnup.osmp.jiance.repository.entity.JcLandusedBuildingOp;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import com.hnup.osmp.jiance.utils.DateUtils;
import com.hnup.osmp.tz.client.TzListFeignClient;
import com.hnup.osmp.tz.model.dto.ConditionDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/** 监管首页-建设用地审批
 * @data2021/12/17,11:26
 * @authorsutinghu
 */
@Service
public class LandusedbuildingOpService {


	@Autowired
	private IJcLandusedBuildingOpDAO jcLandusedBuildingOp;

	@Autowired
	private TzListFeignClient tzListFeignClient;

	private static final Integer TZ_BUILD_OP = 3247;

	private static final String Country_Land = "国有用地";

	private static final String Collective_Land = "集体用地";

	private static final String Build_Land_Op = "建设用地审批";

	private static final String CHANG_SHA = "长沙市";

	private static final List<Integer> GUO_YOU = new ArrayList<Integer>(5){
		{
			add(781);add(782);add(783);add(290);add(784);
		}
	};

	private static final List<Integer> JI_TI = new ArrayList<Integer>(2){
		{
			add(571);add(575);
		}
	};

	/**
	 * 功能描述: 构造查询参数
	 * @author sutinghu
	 * @date
	 * @param year 年份
	 * @param cityName 城市
	 * @return java.util.List<com.hnup.osmp.tz.model.dto.ConditionDTO>
	 */
	public List<ConditionDTO> buildConditions(String year, String cityName){
		List<ConditionDTO> conditions = new ArrayList<>(3);
		// 构造查询条件——案卷编号不为空
		ConditionDTO conditionNotNull = new ConditionDTO();
		conditionNotNull.setFldId("fld_10552_7");
		conditionNotNull.setFldOp("不为空");
		conditionNotNull.setFldCombo(1);
		conditions.add(conditionNotNull);

		if (StringUtils.isNotBlank(year)) {
			// 构造查询条件——时间等于
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("fld_10552_26");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(year);
			conditions.add(condition);
		}

		if (StringUtils.isNotBlank(cityName)) {
			// 构造查询条件——地区
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("fld_10552_9");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(cityName);
			conditions.add(condition);
		}
		return conditions;
	}

	/**
	 * 功能描述: 获取数据源
	 * @author sutinghu
	 * @date
	 * @param conditions 参数
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 */
	public List<HashMap<String, Object>> getDataRource(List<ConditionDTO> conditions){
		return tzListFeignClient.getTzDataList(conditions, TZ_BUILD_OP);
	}

	/**
	 * 功能描述: 将台账数据构造为建设用地审批数据
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcLandusedBuildingOp>
	 */
	public List<JcLandusedBuildingOp> buildingOps(String year, String cityName){

		List<HashMap<String, Object>> resultData = null;
		if (Objects.equals(cityName,"长沙市")) {
			resultData = this.getDataRource(this.buildConditions(year, null));
		}else {
			// 构造查询条件 获取数据源
			resultData = this.getDataRource(this.buildConditions(year, cityName));
		}

		// 构造对象
		if (CollectionUtils.isEmpty(resultData)) {
			return Lists.newArrayList();
		}

		List<JcLandusedBuildingOp> landusedBuildingOps = new ArrayList<>(8);

		for (HashMap<String, Object> map : resultData) {
			JcLandusedBuildingOp jcLandusedBuildingOp = new JcLandusedBuildingOp();
			// 获取项目名称
			jcLandusedBuildingOp.setProName(this.getValueByString(map.get("fld_10552_8")));
			// 获取建设单位
			jcLandusedBuildingOp.setProUnits(this.getValueByString(map.get("fld_10552_11")));
			// 获取一级行政单位——长沙市
			jcLandusedBuildingOp.setRegionLevel1(CHANG_SHA);
			// 获取二级行政单位
			jcLandusedBuildingOp.setRegionLevel2(this.getValueByString(map.get("fld_10552_9")));
			Integer from_key_type_code = this.getValueByIntger(map.get("from_key_type_code"));
			if (GUO_YOU.contains(from_key_type_code)) {
				// 获取所有权，国家建设用地审批
				jcLandusedBuildingOp.setStateOwnedType("国家所有");
			}else if (JI_TI.contains(from_key_type_code)) {
				// 获取所有权，集体建设用地审批
				jcLandusedBuildingOp.setStateOwnedType("集体所有");
			}else {
				continue;
			}
			// 获取所属年份
			Date date = DateUtils.str2Date((String) map.get("fld_10552_26"));
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			jcLandusedBuildingOp.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			// 获取建设用地
			jcLandusedBuildingOp.setLandusedBuilding(this.getValueByDouble(map.get("fld_10552_20")));
			// 获取农用地
			jcLandusedBuildingOp.setAgriculturalLand(this.getValueByDouble(map.get("fld_10552_14")));
			// 获取未利用地
			jcLandusedBuildingOp.setUntakingLand(this.getValueByDouble(map.get("fld_10552_21")));
			// 获取新增用地
			jcLandusedBuildingOp.setNewAdd(this.getValueByDouble(map.get("fld_10552_22")));
			// 获取耕地
			jcLandusedBuildingOp.setCultivatedLand(this.getValueByDouble(map.get("fld_10552_15")));
			// 获取园地
			jcLandusedBuildingOp.setGardenLand(this.getValueByDouble(map.get("fld_10552_16")));
			// 获取林地
			jcLandusedBuildingOp.setForsetLand(this.getValueByDouble(map.get("fld_10552_17")));
			// 获取草地
			jcLandusedBuildingOp.setGrassLand(this.getValueByDouble(map.get("fld_10552_18")));
			// 获取其它用地
			jcLandusedBuildingOp.setOtherLand(this.getValueByDouble(map.get("fld_10552_19")));

			landusedBuildingOps.add(jcLandusedBuildingOp);
		}

		return landusedBuildingOps;
	}

	/**
	 * 功能描述: 监管首页——建设审批用地Index
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getIndex() {


		List<JcLandusedBuildingOp> jcLandusedBuildingOps = this.buildingOps(null,null);

		List<Integer> years = jcLandusedBuildingOps.stream().filter(e -> e.getYear() != null).map(e -> Integer.parseInt(e.getYear())).collect(Collectors.toList());

		Integer year =  Collections.max(years);

		if (CollectionUtils.isNotEmpty(jcLandusedBuildingOps)) {
			jcLandusedBuildingOps = jcLandusedBuildingOps.stream().filter(e->e.getYear()!=null && Objects.equals(e.getYear(),String.valueOf(year))).collect(Collectors.toList());
		}

//		jcLandusedBuildingOp
//			.lambdaQuery()
//			.eq(JcLandusedBuildingOp::getYear,year)
//			.list();

		if (CollectionUtils.isEmpty(jcLandusedBuildingOps)) {
			return Maps.newHashMap();
		}

		BigDecimal jsyd = new BigDecimal(0);
		BigDecimal nyd = new BigDecimal(0);
		BigDecimal wlyd = new BigDecimal(0);
		BigDecimal all = new BigDecimal(0);
		BigDecimal newadd = new BigDecimal(0);

		for (JcLandusedBuildingOp jcLandusedBuildingOp:jcLandusedBuildingOps) {

			if (jcLandusedBuildingOp.getLandusedBuilding()!=null) {
				jsyd = ComputeBase.addCompute(jcLandusedBuildingOp.getLandusedBuilding(),jsyd);
			}
			if (jcLandusedBuildingOp.getAgriculturalLand()!=null) {
				nyd = ComputeBase.addCompute(jcLandusedBuildingOp.getAgriculturalLand(),nyd);
			}
			if (jcLandusedBuildingOp.getUntakingLand()!=null) {
				wlyd = ComputeBase.addCompute(jcLandusedBuildingOp.getUntakingLand(),wlyd);
			}

		}

		newadd = ComputeBase.addCompute(wlyd,nyd);
		all = ComputeBase.addCompute(newadd,jsyd);

		Map<String, Object> result = new HashMap<>(5);

		result.put("建设用地",jsyd);
		result.put("农用地",nyd);
		result.put("未利用地",wlyd);
		result.put("建设用地审批面积",all);
		result.put("新增建设用地",newadd);
		result.put("年份",year);

		return result;

	}

	/**
	 * 功能描述: 建设用地审批详情
	 * @author sutinghu
	 * @date
	 * @param cityName
	 * @param year 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getAreaDetails(String cityName, String year) {

		List<JcLandusedBuildingOp> jcLandusedBuildingOps = null;

		if (Objects.equals(cityName,"长沙市")) {

			jcLandusedBuildingOps = this.buildingOps(null,null);

//			jcLandusedBuildingOps = jcLandusedBuildingOp
//				.lambdaQuery()
//				.eq(JcLandusedBuildingOp::getRegionLevel1,cityName)
//				.eq(JcLandusedBuildingOp::getYear,year)
//				.list();
		}else {

			jcLandusedBuildingOps = this.buildingOps(null,cityName);

//			jcLandusedBuildingOps = jcLandusedBuildingOp
//				.lambdaQuery()
//				.eq(JcLandusedBuildingOp::getRegionLevel2,cityName)
//				.eq(JcLandusedBuildingOp::getYear,year)
//				.list();
		}

		if (CollectionUtils.isNotEmpty(jcLandusedBuildingOps)) {
			jcLandusedBuildingOps = jcLandusedBuildingOps.stream().filter(e->e.getYear()!=null && Objects.equals(e.getYear(),String.valueOf(year))).collect(Collectors.toList());
		}

		if (CollectionUtils.isEmpty(jcLandusedBuildingOps)) {
			return Maps.newHashMap();
		}

		Map<String, List<JcLandusedBuildingOp>> collect = jcLandusedBuildingOps
			.stream()
			.collect(Collectors.groupingBy(jcLandusedBuildingOp -> {

				if (jcLandusedBuildingOp.getStateOwnedType()==null) {
					return "其它";
				}else {
					return jcLandusedBuildingOp.getStateOwnedType();
				}

			}));

		Map<String, Object> result = new HashMap<>(16);

		BigDecimal areasSp = new BigDecimal(0);
		BigDecimal jsydSp = new BigDecimal(0);
		BigDecimal wlydSp = new BigDecimal(0);
		BigDecimal nydSp = new BigDecimal(0);
		BigDecimal gdSp = new BigDecimal(0);
		BigDecimal ldSp = new BigDecimal(0);
		BigDecimal cdSp = new BigDecimal(0);
		BigDecimal ydSp = new BigDecimal(0);
		BigDecimal qtSp = new BigDecimal(0);
		BigDecimal areaSp = new BigDecimal(0);

		for (Map.Entry<String, List<JcLandusedBuildingOp>> map:collect.entrySet()) {
			List<JcLandusedBuildingOp> value = map.getValue();

			BigDecimal areas = new BigDecimal(0);
			BigDecimal jsyd = new BigDecimal(0);
			BigDecimal wlyd = new BigDecimal(0);
			BigDecimal nyd = new BigDecimal(0);
			BigDecimal gd = new BigDecimal(0);
			BigDecimal ld = new BigDecimal(0);
			BigDecimal cd = new BigDecimal(0);
			BigDecimal yd = new BigDecimal(0);
			BigDecimal qt = new BigDecimal(0);


			for (JcLandusedBuildingOp jcLandusedBuildingOp : value) {
				// 建设用地
				jsyd = ComputeBase.addCompute(jsyd,jcLandusedBuildingOp.getLandusedBuilding());
				// 农用地
				nyd = ComputeBase.addCompute(nyd,jcLandusedBuildingOp.getAgriculturalLand());
				// 未利用地
				wlyd = ComputeBase.addCompute(wlyd,jcLandusedBuildingOp.getUntakingLand());
				// 耕地
				gd = ComputeBase.addCompute(gd,jcLandusedBuildingOp.getCultivatedLand());
				// 草地
				cd = ComputeBase.addCompute(cd,jcLandusedBuildingOp.getGrassLand());
				// 园地
				yd = ComputeBase.addCompute(yd,jcLandusedBuildingOp.getGardenLand());
				// 林地
				ld = ComputeBase.addCompute(ld,jcLandusedBuildingOp.getForsetLand());
				// 其它地
				qt = ComputeBase.addCompute(qt,jcLandusedBuildingOp.getOtherLand());
			}

//			areasSp =  ComputeBase.addCompute(jsyd,areasSp);
			jsydSp = ComputeBase.addCompute(jsyd,jsydSp);
			wlydSp =  ComputeBase.addCompute(wlyd,wlydSp);
			nydSp =  ComputeBase.addCompute(nyd,nydSp);
			gdSp =  ComputeBase.addCompute(gd,gdSp);
			ldSp =  ComputeBase.addCompute(ld,ldSp);
			cdSp =  ComputeBase.addCompute(cd,cdSp);
			ydSp =  ComputeBase.addCompute(yd,ydSp);
			qtSp =  ComputeBase.addCompute(qt,qtSp);

			Map<String, Object> areaLevel1 = new HashMap<>(3);
			areaLevel1.put("建设用地",jsyd);
			areaLevel1.put("未利用地",wlyd);
			areaLevel1.put("农用地",nyd);

			result.put(map.getKey()+"一级",areaLevel1);

			Map<String, Object> areaLevel2 = new HashMap<>(6);
			areaLevel2.put("耕地",gd);
			areaLevel2.put("草地",cd);
			areaLevel2.put("园地",yd);
			areaLevel2.put("林地",ld);
			areaLevel2.put("其它",qt);

			result.put(map.getKey()+"二级",areaLevel2);

			areas = ComputeBase.addCompute(jsyd,areas);
			areas = ComputeBase.addCompute(wlyd,areas);
			areas = ComputeBase.addCompute(nyd,areas);

			areaSp = ComputeBase.addCompute(areaSp,areas);

			result.put(map.getKey()+"总体",areas);
		}
		Map<String, Object> areaLevel1 = new HashMap<>(3);
		areaLevel1.put("建设用地",jsydSp);
		areaLevel1.put("未利用地",wlydSp);
		areaLevel1.put("农用地",nydSp);
		result.put("建设用地审批一级",areaLevel1);
		Map<String, Object> areaLevel2 = new HashMap<>(6);
		areaLevel2.put("耕地",gdSp);
		areaLevel2.put("草地",cdSp);
		areaLevel2.put("园地",ydSp);
		areaLevel2.put("林地",ldSp);
		areaLevel2.put("其它",qtSp);
		result.put("建设用地审批二级",areaLevel2);
		result.put("建设用地审批总体",areaSp);
		return result;

	}


	public Map<String, Object> getSpreadDetails(String year) {

		List<JcLandusedBuildingOp> jcLandusedBuildingOps = null;

		jcLandusedBuildingOps = this.buildingOps(null,null);

		if (CollectionUtils.isNotEmpty(jcLandusedBuildingOps)) {
			jcLandusedBuildingOps = jcLandusedBuildingOps.stream().filter(e->e.getYear()!=null && Objects.equals(e.getYear(),year)).collect(Collectors.toList());
		}

//		jcLandusedBuildingOp
//			.lambdaQuery()
//			.eq(JcLandusedBuildingOp::getYear,year)
//			.list();

		if (CollectionUtils.isEmpty(jcLandusedBuildingOps)) {
			return Maps.newHashMap();
		}

		Map<String, List<JcLandusedBuildingOp>> collect = jcLandusedBuildingOps.stream().collect(Collectors.groupingBy(JcLandusedBuildingOp::getRegionLevel2));

		Map<String, Object> result = new HashMap<>(16);

		collect.forEach((k,v)->{
			Map<String, Object> city = new HashMap<>(16);
			BigDecimal allSp = new BigDecimal(0);
			Map<String, List<JcLandusedBuildingOp>> listMap = v.stream().collect(Collectors.groupingBy(jcLandusedBuildingOp -> {
				if (jcLandusedBuildingOp.getStateOwnedType()==null) {
					return "其它";
				}else {
					return jcLandusedBuildingOp.getStateOwnedType();
				}
			}));
			for (Map.Entry<String, List<JcLandusedBuildingOp>>map:listMap.entrySet()) {
				List<JcLandusedBuildingOp> value = map.getValue();
				BigDecimal all = new BigDecimal(0);
				for (JcLandusedBuildingOp jcLandusedBuildingOp : value) {
					// 建设用地
					all = ComputeBase.addCompute(all,jcLandusedBuildingOp.getLandusedBuilding());
					allSp = ComputeBase.addCompute(allSp,jcLandusedBuildingOp.getLandusedBuilding());
					// 农用地
					all = ComputeBase.addCompute(all,jcLandusedBuildingOp.getAgriculturalLand());
					allSp = ComputeBase.addCompute(allSp,jcLandusedBuildingOp.getAgriculturalLand());
					// 未利用地
					all = ComputeBase.addCompute(all,jcLandusedBuildingOp.getUntakingLand());
					allSp = ComputeBase.addCompute(allSp,jcLandusedBuildingOp.getUntakingLand());
				}
				city.put(map.getKey(),all);
			}
			city.put("建设用地审批",allSp);
			result.put(k,city);
		});
		return result;
	}

	public Map<String, Object> gettrendAnalysis(String cityName) {

		List<JcLandusedBuildingOp> jcLandusedBuildingOps = null;
		if (Objects.equals(cityName,"长沙市")) {
			jcLandusedBuildingOps = this.buildingOps(null,null);
//			jcLandusedBuildingOps = jcLandusedBuildingOp
//				.lambdaQuery()
//				.eq(JcLandusedBuildingOp::getRegionLevel1,cityName)
//				.list();
		}else {
			jcLandusedBuildingOps = this.buildingOps(null,cityName);

//			jcLandusedBuildingOps = jcLandusedBuildingOp
//				.lambdaQuery()
//				.eq(JcLandusedBuildingOp::getRegionLevel2,cityName)
//				.list();
		}
		if (CollectionUtils.isEmpty(jcLandusedBuildingOps)) {
			return Maps.newHashMap();
		}
		Map<String, List<JcLandusedBuildingOp>> collect = jcLandusedBuildingOps.stream().collect(Collectors.groupingBy(JcLandusedBuildingOp::getYear));
		Map<String, Object> result = new HashMap<>(16);
		// 年份
		for (Map.Entry<String, List<JcLandusedBuildingOp>> map : collect.entrySet()) {
			List<JcLandusedBuildingOp> value = map.getValue();
			Map<String, List<JcLandusedBuildingOp>> listMap = value.stream().collect(Collectors.groupingBy(jcLandusedBuildingOp -> {
				if (jcLandusedBuildingOp.getStateOwnedType()==null) {
					return "其它";
				}else {
					return jcLandusedBuildingOp.getStateOwnedType();
				}
			}));

			Map<String, Object> type = new HashMap<>(16);
			BigDecimal allSp = new BigDecimal(0);
			// 用地类型
			for (Map.Entry<String, List<JcLandusedBuildingOp>> maps : listMap.entrySet()) {
				List<JcLandusedBuildingOp> mapsValue = maps.getValue();
				BigDecimal all = new BigDecimal(0);
				for (JcLandusedBuildingOp jcLandusedBuildingOp : mapsValue) {
					// 建设用地
					all = ComputeBase.addCompute(all,jcLandusedBuildingOp.getLandusedBuilding());
					allSp = ComputeBase.addCompute(allSp,jcLandusedBuildingOp.getLandusedBuilding());
					// 农用地
					all = ComputeBase.addCompute(all,jcLandusedBuildingOp.getAgriculturalLand());
					allSp = ComputeBase.addCompute(allSp,jcLandusedBuildingOp.getAgriculturalLand());
					// 未利用地
					all = ComputeBase.addCompute(all,jcLandusedBuildingOp.getUntakingLand());
					allSp = ComputeBase.addCompute(allSp,jcLandusedBuildingOp.getUntakingLand());
				}
				type.put(maps.getKey(),all);
			}
			type.put("建设用地审批",allSp);
			result.put(map.getKey(),type);

			}

		return result;

	}

	/**
	 * 功能描述: 建设用地审批数量
	 * @author sutinghu
	 * @date
	 * @param cityName
	 * @param year 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> getSize(String cityName, String year) {

		List<JcLandusedBuildingOp> jcLandusedBuildingOps = null;

		if (Objects.equals(cityName,"长沙市")) {
			jcLandusedBuildingOps = jcLandusedBuildingOp
				.lambdaQuery()
				.eq(JcLandusedBuildingOp::getRegionLevel1,cityName)
				.list();
		}else {
			jcLandusedBuildingOps = jcLandusedBuildingOp
				.lambdaQuery()
				.eq(JcLandusedBuildingOp::getRegionLevel2,cityName)
				.list();
		}

		if (CollectionUtils.isEmpty(jcLandusedBuildingOps)) {
			return Lists.newArrayList();
		}

		Map<String, List<JcLandusedBuildingOp>> collect = jcLandusedBuildingOps.stream().collect(Collectors.groupingBy(jcLandusedBuildingOp -> {

			if (jcLandusedBuildingOp.getStateOwnedType()==null) {
				return "其它";
			}else {
				return jcLandusedBuildingOp.getStateOwnedType();
			}

		}));
		List<Map<String, Object>> result = new ArrayList<>();
		final Integer[] size = {0};
		collect.forEach((k,v)->{
			Map<String, Object> map = new HashMap<>();
			size[0] = size[0] + v.size();
			map.put("name",k);
			map.put("size",v.size());
			result.add(map);
		});
		Map<String, Object> map = new HashMap<>();
		map.put("name","建设用地审批");
		map.put("size",size[0]);
		result.add(map);
		return result;
	}


	public BigDecimal getValueByDouble(Object fieldValue){

		if (StringUtils.isNotBlank((String) fieldValue)) {
			return new BigDecimal((String) fieldValue);
		}else {
			return new BigDecimal("0");
		}

	}

	public String getValueByString(Object fieldValue){

		if (StringUtils.isNotBlank((String) fieldValue)) {
			return (String) fieldValue;
		}else {
			return null;
		}

	}

	public Integer getValueByIntger(Object fieldValue){

		if (fieldValue != null) {
			return Integer.parseInt(String.valueOf(fieldValue)) ;
		}else {
			return null;
		}

	}

}
