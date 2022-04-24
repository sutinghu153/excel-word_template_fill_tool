package com.hnup.osmp.jiance.service.keypro;

import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcManagementControDAO;
import com.hnup.osmp.jiance.repository.entity.JcManagementContro;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;

import com.hnup.osmp.mapserver.client.LandUseFeignClient;
import com.hnup.osmp.mapserver.dto.UseDetailDTO;
import com.hnup.osmp.mapserver.vo.UseDetailVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  监管首页-耕地
 * @data2021/11/15,14:56
 * @authorsutinghu
 */
@Service
public class KeyProLanduserService {


	@Autowired
	private IJcManagementControDAO iSupManagementContro;

	@Autowired
	private LandUseFeignClient landUseFeignClient;

	/**
	 * 功能描述:监管首页-耕地柱状图
	 * @author sutinghu
	 * @date
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getFormLandTree() {

		// 获取长沙、耕地下 所有数据
		List<JcManagementContro> jcManagementContros = iSupManagementContro
			.lambdaQuery()
			.eq(JcManagementContro::getLanduserOne,ConstantJiance.GD_CODE)
			.eq(JcManagementContro::getRegionCode,ConstantJiance.CHANGSHA_CODE)
			.list();

		if (CollectionUtils.isEmpty(jcManagementContros)) {
			return Maps.newHashMap();
		}

		Map<String, Object> mapResult = new HashMap<>();

		// 查取最大时间
		List<Integer> years = new ArrayList<>();
			jcManagementContros.stream().forEach(e->{
				if (StringUtils.isNotBlank(e.getYear())) {
					years.add (Integer.parseInt(e.getYear()));
				}
		});
		String maxYear = String.valueOf(Collections.max(years));
		mapResult.put("newTime",maxYear);

		// 过滤
		jcManagementContros = jcManagementContros
			.stream()
			.filter(e->Objects.equals(e.getYear(),maxYear)).collect(Collectors.toList());

		Map<String, List<JcManagementContro>> listMap = jcManagementContros
			.stream()
			.collect(Collectors.groupingBy(JcManagementContro::getRegionSecName));

		// 合并数据
		List<JcManagementContro> newJcManagementContros = new ArrayList<>();
		listMap.forEach((k,v)->{
			JcManagementContro jcManagementContro = new JcManagementContro();
			jcManagementContro.setRegionSecName(k);
			BigDecimal all = new BigDecimal(0);
			BigDecimal baoyouliang = new BigDecimal(0);
			if (CollectionUtils.isEmpty(v)) {
				jcManagementContro.setAllNum(all);
				jcManagementContro.setTargetValue(baoyouliang);
			}else {
				for (JcManagementContro supManagement:v) {
					all = ComputeBase.addCompute(supManagement.getAllNum(),all);
					baoyouliang = ComputeBase.addCompute(supManagement.getTargetValue(),baoyouliang);
				}
				jcManagementContro.setAllNum(all);
				jcManagementContro.setTargetValue(baoyouliang);
			}
			newJcManagementContros.add(jcManagementContro);
		});

		Map<String, Object> manages = newJcManagementContros
			.stream()
			.collect(Collectors.toMap(JcManagementContro::getRegionSecName, JcManagementContro::getAllNum));
		mapResult.put("manages",manages);
		Map<String, Object> targets = newJcManagementContros
			.stream()
			.collect(Collectors.toMap(JcManagementContro::getRegionSecName, JcManagementContro::getTargetValue,(k1, k2)->k1));
		mapResult.put("targets",targets);

		final BigDecimal[] byl = {new BigDecimal(0)};
		targets.forEach((k,v)->{
			byl[0] = ComputeBase.addCompute(byl[0], (BigDecimal) v);
		});
		mapResult.put("byl",byl[0]);
		final BigDecimal[] gxz = {new BigDecimal(0)};
		manages.forEach((k,v)->{
			gxz[0] = ComputeBase.addCompute(byl[0], (BigDecimal) v);
		});
		mapResult.put("gxz",gxz[0]);
		// 获取最新数据
		UseDetailVo useDetailVo = landUseFeignClient
			.getLandUseRegionDetail(
				ConstantJiance.CHANGSHA_CODE,
				maxYear,
				ConstantJiance.GD_CODE,
				ConstantJiance.SUM,
				false);

		List<UseDetailDTO> spotList = useDetailVo.getDetailList();
		Map<String, Object> spotMap = null;
		if (CollectionUtils.isEmpty(spotList)) {
			mapResult.put("spotList",spotMap);
		}else {
			spotMap =spotList.stream().collect(Collectors.toMap(UseDetailDTO::getName,UseDetailDTO::getArea,(k1,k2)->k1));
			mapResult.put("spotList",spotMap);
		}

		List<String> regions = new ArrayList<>();

		spotMap.forEach((k,v)->{
			regions.add(k);
		});

		mapResult.put("regions",regions);

		return mapResult;
	}

	/**
	 * 功能描述: 耕地详情-耕地分布
	 * @author sutinghu
	 * @date
	 * @param year 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getFormLandSpread(String year) {
		// 获取长沙、耕地下 所有数据
		List<JcManagementContro> jcManagementContros = iSupManagementContro
			.lambdaQuery()
			.eq(JcManagementContro::getLanduserOne,ConstantJiance.GD_CODE)
			.eq(JcManagementContro::getRegionCode,ConstantJiance.CHANGSHA_CODE)
			.eq(JcManagementContro::getYear,year)
			.list();

		if (CollectionUtils.isEmpty(jcManagementContros)) {
			return Maps.newHashMap();
		}

		Map<String, Object> mapResult = new HashMap<>();

		Map<String, List<JcManagementContro>> listMap = jcManagementContros
			.stream()
			.collect(Collectors.groupingBy(JcManagementContro::getRegionSecName));

		// 合并数据
		List<JcManagementContro> newJcManagementContros = new ArrayList<>();
		listMap.forEach((k,v)->{
			JcManagementContro jcManagementContro = new JcManagementContro();
			jcManagementContro.setRegionSecName(k);
			BigDecimal all = new BigDecimal(0);
			BigDecimal baoyouliang = new BigDecimal(0);
			if (CollectionUtils.isEmpty(v)) {
				jcManagementContro.setAllNum(all);
				jcManagementContro.setTargetValue(baoyouliang);
			}else {
				for (JcManagementContro supManagement:v) {
					all = ComputeBase.addCompute(supManagement.getAllNum(),all);
					baoyouliang = ComputeBase.addCompute(supManagement.getTargetValue(),baoyouliang);
				}
				jcManagementContro.setAllNum(all);
				jcManagementContro.setTargetValue(baoyouliang);
			}
			newJcManagementContros.add(jcManagementContro);
		});

		Map<String, Object> manages = newJcManagementContros
			.stream()
			.collect(Collectors.toMap(JcManagementContro::getRegionSecName, JcManagementContro::getAllNum));
		mapResult.put("manages",manages);
		Map<String, Object> targets = newJcManagementContros
			.stream()
			.collect(Collectors.toMap(JcManagementContro::getRegionSecName, JcManagementContro::getTargetValue,(k1, k2)->k1));
		mapResult.put("targets",targets);

		// 获取最新数据
		UseDetailVo useDetailVo = landUseFeignClient
			.getLandUseRegionDetail(
				ConstantJiance.CHANGSHA_CODE,
				year,
				ConstantJiance.GD_CODE,
				ConstantJiance.SUM,
				true);

		List<UseDetailDTO> spotList = useDetailVo.getDetailList();
		Map<String, Object> spotMap = null;
		if (CollectionUtils.isEmpty(spotList)) {
			mapResult.put("spotList",spotMap);
		}else {
			spotMap =spotList.stream().collect(Collectors.toMap(UseDetailDTO::getName,UseDetailDTO::getArea,(k1,k2)->k1));
			mapResult.put("spotList",spotMap);
		}

		// 地区
		List<String> regions = new ArrayList<>();
		spotMap.forEach((k,v)->{
			regions.add(k);
		});
		mapResult.put("regions",regions);

		// 总数
		final BigDecimal[] kzs = {new BigDecimal(0)};
		manages.forEach((k,v)->{
			kzs[0] = ComputeBase.addCompute(kzs[0], (BigDecimal) v);
		});

		final BigDecimal[] byl = {new BigDecimal(0)};
		targets.forEach((k,v)->{
			byl[0] = ComputeBase.addCompute(byl[0], (BigDecimal) v);
		});

		final BigDecimal[] tbsl = {new BigDecimal(0)};
		spotMap.forEach((k,v)->{
			tbsl[0] = ComputeBase.addCompute(tbsl[0], BigDecimal.valueOf((Double)v));
		});

		mapResult.put("kzs",kzs[0]);
		mapResult.put("byl",byl[0]);
		mapResult.put("tbs",tbsl[0]);

		return mapResult;
	}

	/**
	 * 功能描述:获取台账详情数据
	 * @author sutinghu
	 * @date
	 * @param year 年份
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getFormLandTz(String year) {

		// 获取长沙、耕地下 所有数据
		List<JcManagementContro> jcManagementContros = iSupManagementContro
			.lambdaQuery()
			.eq(JcManagementContro::getLanduserOne,ConstantJiance.GD_CODE)
			.eq(JcManagementContro::getRegionCode,ConstantJiance.CHANGSHA_CODE)
			.eq(JcManagementContro::getYear,year)
			.list();

		// 获取最新数据
		UseDetailVo useDetailVo = landUseFeignClient
			.getLandUseRegionDetail(
				ConstantJiance.CHANGSHA_CODE,
				year,
				ConstantJiance.GD_CODE,
				ConstantJiance.SUM,false);

		Map<String, List<JcManagementContro>> listMap = jcManagementContros
			.stream()
			.collect(Collectors.groupingBy(JcManagementContro::getRegionSecName));

		listMap.forEach((k,v)->{

		});

		return null;
	}

	/**
	 * 功能描述: 趋势分析
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> trendAnalysis() {

		// 获取长沙、耕地下 所有数据
		List<JcManagementContro> jcManagementContros = iSupManagementContro
			.lambdaQuery()
			.eq(JcManagementContro::getLanduserOne,ConstantJiance.GD_CODE)
			.eq(JcManagementContro::getRegionCode,ConstantJiance.CHANGSHA_CODE)
			.list();

		// 获取最新数据
		UseDetailVo useDetailVo = landUseFeignClient
			.getLandUseRegionDetail(
				ConstantJiance.CHANGSHA_CODE,
				null,
				ConstantJiance.GD_CODE,
				ConstantJiance.SUM,
				false);

		List<UseDetailDTO> detailList = useDetailVo.getDetailList();

		Map<String, List<JcManagementContro>> collect = jcManagementContros
			.stream()
			.filter(e -> e.getYear() != null)
			.collect(Collectors.groupingBy(JcManagementContro::getYear));

		collect.forEach((k,v)->{



		});

		return null;

	}
}
