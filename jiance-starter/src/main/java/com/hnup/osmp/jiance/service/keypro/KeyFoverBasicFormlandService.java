package com.hnup.osmp.jiance.service.keypro;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcForeverBasicFormlandDAO;
import com.hnup.osmp.jiance.repository.entity.JcForeverBasicFormland;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  监管首页-永久基本农田
 * @data2021/11/19,9:11
 * @authorsutinghu
 */
@Service
public class KeyFoverBasicFormlandService {

	@Autowired
	private IJcForeverBasicFormlandDAO iJcForeverBasicFormland;

	/**
	 * 功能描述: 详情图
	 * @author sutinghu
	 * @date
	 * @param year 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getbasicbar(String year) {

		List<JcForeverBasicFormland> jcForeverBasicFormlands = iJcForeverBasicFormland.lambdaQuery()
			.eq(JcForeverBasicFormland::getYear,year).list();

		if (CollectionUtils.isEmpty(jcForeverBasicFormlands)) {
			return Maps.newHashMap();
		}

		List<String> regions = new ArrayList<>();
		List<BigDecimal> goals = new ArrayList<>();
		List<BigDecimal> reals = new ArrayList<>();

		BigDecimal realAll = new BigDecimal(0);
		BigDecimal goalAll = new BigDecimal(0);
		BigDecimal richAll = new BigDecimal(0);

		// 组装数据
		for (JcForeverBasicFormland e:jcForeverBasicFormlands) {

			//
			regions.add(e.getRegionSecName());
			goals.add(e.getGoalArea());
			reals.add(e.getRealArea());
			//
			realAll = ComputeBase.addCompute(realAll,e.getRealArea());
			goalAll = ComputeBase.addCompute(goalAll,e.getGoalArea());
			richAll = ComputeBase.addCompute(richAll,e.getRichlyArea());

		}

		Map<String, Object> result = new HashMap<>();
		result.put("regions",regions);
		result.put("goals",goals);
		result.put("reals",reals);
		result.put("realAll",realAll);
		result.put("goalAll",goalAll);
		result.put("richAll",richAll);
		result.put("year",year);

		return result;
	}

	/**
	 * 功能描述: 获取最新的年
	 * @author sutinghu
	 * @date
	 * @return java.lang.String
	 */
	public String getLastYear(){

		List<JcForeverBasicFormland> list = iJcForeverBasicFormland.lambdaQuery().list();

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		List<String> years = list.stream().map(JcForeverBasicFormland::getYear).collect(Collectors.toList());

		List<Integer> yearss = new ArrayList<>();
		years.forEach(e->{
			yearss.add(Integer.valueOf(e));
		});
		Integer maxYear = Collections.max(yearss);

		return String.valueOf(maxYear);
	}

	/**
	 * 功能描述:首页图
	 * @author sutinghu
	 * @date
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getBasicBarHome(){
		String year = this.getLastYear();
		return this.getbasicbar(year);
	}

	/**
	 * 功能描述: 详情表
	 * @author sutinghu
	 * @date
	 * @param year 参数
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcForeverBasicFormland>
	 */
	public List<JcForeverBasicFormland> getDetailList(String year) {

		List<JcForeverBasicFormland> jcForeverBasicFormlands = iJcForeverBasicFormland.lambdaQuery()
			.eq(JcForeverBasicFormland::getYear,year)
			.orderByAsc(JcForeverBasicFormland::getCreateTime)
			.list();

		if (CollectionUtils.isEmpty(jcForeverBasicFormlands)) {
			return Lists.newArrayList();
		}

		return jcForeverBasicFormlands;
	}
}
