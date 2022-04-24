package com.hnup.osmp.jiance.service.landuser;

import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcManagementControDAO;
import com.hnup.osmp.jiance.repository.entity.JcManagementContro;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import com.hnup.osmp.mapserver.client.*;
import com.hnup.osmp.mapserver.dto.*;
import com.hnup.osmp.mapserver.vo.UseClassifyVo;
import com.hnup.osmp.mapserver.vo.UseDetailVo;
import com.hnup.osmp.mapserver.vo.UseSecondaryVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  土地利用现状
 * @data2021/11/11,17:53
 * @authorsutinghu
 */
@Service
public class LanduserService {

	@Autowired
	private XzqFeignClient xzqFeignClient;

	@Autowired
	private LandUseFeignClient landUseFeignClient;

	@Autowired
	private IJcManagementControDAO iSupManagementContro;

	/**
	 * 功能描述:
	 * @author sutinghu
	 * @date code 行政区编码
	 * @return com.hnup.osmp.onemap.dto.OmXzqDTO
	 */
	public OmXzqDTO getXzqTree(String code){
		return xzqFeignClient.getXzqTree(code);
	}

	/**
	 * 功能描述:获取年份
	 * @author sutinghu
	 * @date
	 * @return java.util.List<com.hnup.osmp.onemap.dto.TbLayerConfig>
	 */
	public List<TbLayerConfig> getYear() {
		return landUseFeignClient.getAllYDLayer();
	}

	/**
	 * 功能描述:获取区县表头数据
	 * @author sutinghu
	 * @date
	 * @param xzqCode
	 * @param year
	 * @param useType 参数
	 * @return com.hnup.osmp.onemap.vo.UseDetailVo
	 */
	public UseClassifyVo getRegionTitleData(String xzqCode, String year, String useType) {
		return landUseFeignClient.getLandUseRegionSum(xzqCode, year, useType);
	}

	/**
	 * 功能描述: 获取区县表数据
	 * @author sutinghu
	 * @date
	 * @param xzqCode
	 * @param year
	 * @param useType
	 * @param rightType 参数
	 * @return com.hnup.osmp.onemap.vo.UseDetailVo
	 */
	public UseDetailVo getLandUseRegionDetail(String xzqCode, String year, String useType, String rightType) {
		return landUseFeignClient.getLandUseRegionDetail(xzqCode, year, useType,rightType,false);
	}

	/**
	 * 功能描述:获取区县二级土地利用数据
	 * @author sutinghu
	 * @date
	 * @param xzqCode
	 * @param year
	 * @param useType 参数
	 * @return java.util.List<com.hnup.osmp.onemap.vo.UseSecondaryVo>
	 */
	public List<UseSecondaryVo> getLevelTwo(String xzqCode, String year, String useType) {
		return landUseFeignClient.getLandUseSecondary(xzqCode, year, useType);
	}

	/**
	 * 功能描述: 获取详情页-控制管理数
	 * @author sutinghu
	 * @date
	 * @param xzqCode
	 * @param year
	 * @param useType 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getControlXzqData(String xzqCode, String year, String useType) {

		List<JcManagementContro> jcManagementContros = iSupManagementContro.lambdaQuery()
			.eq(JcManagementContro::getRegionCode,xzqCode)
			.eq(JcManagementContro::getYear,year)
			.eq(JcManagementContro::getLanduserOne,useType)
			.list();

		if (CollectionUtils.isEmpty(jcManagementContros)) {
			return Maps.newHashMap();
		}
		Map<String, List<JcManagementContro>> map = jcManagementContros
			.stream()
			.collect(Collectors.groupingBy(JcManagementContro::getRegionSecName));
		Map<String, Object> value = new HashMap<>();
		List<Map<String, Object>> result = new ArrayList<>();
		final BigDecimal[] countryNums = {new BigDecimal(0)};
		final BigDecimal[] statuNums = {new BigDecimal(0)};
		BigDecimal allNums = new BigDecimal(0);
		map.forEach((k,v)->{
			Map<String, Object> maps = new HashMap<>();
			maps.put("region",k);
			BigDecimal all = new BigDecimal(0);
			for (JcManagementContro jcManagementContro :v) {
				all = ComputeBase.addCompute(all, jcManagementContro.getAllNum());
				countryNums[0] = ComputeBase.addCompute(countryNums[0], jcManagementContro.getContryNum());
				statuNums[0] = ComputeBase.addCompute(statuNums[0], jcManagementContro.getStatuNum());
				maps.put("code", jcManagementContro.getRegionSecCode());
			}
			maps.put("area",all);
			result.add(maps);
		});
		allNums = allNums.add(statuNums[0]).add(countryNums[0]);
		value.put("柱状图",result);
		value.put("总面积",allNums);
		value.put("国家所有",countryNums);
		value.put("集体所有",statuNums);
		return value;
	}

	/**
	 * 功能描述: 控制管理数二级土地利用
	 * @author sutinghu
	 * @date
	 * @param xzqCode
	 * @param year
	 * @param useType 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getControlLevelTwo(String xzqCode, String year, String useType) {
		Map<String, Object> result = null;
		List<JcManagementContro> jcManagementContros = null;
		// 如果是长沙市
		if (Objects.equals(ConstantJiance.CHANGSHA_CODE,xzqCode)) {
			jcManagementContros = iSupManagementContro.lambdaQuery()
				.eq(JcManagementContro::getRegionCode,xzqCode)
				.eq(JcManagementContro::getYear,year)
				.eq(JcManagementContro::getLanduserOne,useType)
				.list();
			if (CollectionUtils.isEmpty(jcManagementContros)) {
				return Maps.newHashMap();
			}
			Map<String, List<JcManagementContro>> map = jcManagementContros
				.stream()
				.collect(Collectors.groupingBy(JcManagementContro::getLanduserTwo));
			result = new HashMap<>();
			for (Map.Entry<String, List<JcManagementContro>> data:map.entrySet()) {
				BigDecimal bigDecimal = new BigDecimal(0);
				for (JcManagementContro jcManagementContro :data.getValue()) {
					bigDecimal = bigDecimal.add(jcManagementContro.getAllNum());
				}
				result.put(data.getKey(),bigDecimal);
			}
		}else {
			jcManagementContros = iSupManagementContro.lambdaQuery()
				.eq(JcManagementContro::getRegionSecCode,xzqCode)
				.eq(JcManagementContro::getYear,year)
				.eq(JcManagementContro::getLanduserOne,useType)
				.list();
			//
			if (CollectionUtils.isEmpty(jcManagementContros)) {
				return Maps.newHashMap();
			}
			result = jcManagementContros
				.stream()
				.collect(Collectors.toMap(JcManagementContro::getLanduserTwo, JcManagementContro::getAllNum,(k1, k2)->k1));
		}
		return result;
	}

	/**
	 * 功能描述: 获取首页列表
	 * @author sutinghu
	 * @date
	 * @param year
	 * @param xzqCode 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public List<Map<String, Object>> getHomeList(String xzqCode, String year) {

		List<UseClassifyVo> useClassifyVos = landUseFeignClient.getLandUseClassify(xzqCode, year);

		List<JcManagementContro> jcManagementContros = null;
		// 如果是长沙市
		if (Objects.equals(ConstantJiance.CHANGSHA_CODE,xzqCode)) {
			jcManagementContros = iSupManagementContro.lambdaQuery()
				.eq(JcManagementContro::getRegionCode,xzqCode)
				.eq(JcManagementContro::getYear,year)
				.list();

		}else {
			jcManagementContros = iSupManagementContro.lambdaQuery()
				.eq(JcManagementContro::getRegionSecCode,xzqCode)
				.eq(JcManagementContro::getYear,year)
				.list();
		}

		List<UseClassifyVo> manageList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(jcManagementContros)){
			Map<String,List<JcManagementContro>> listMap = jcManagementContros
				.stream()
				.collect(Collectors.groupingBy(JcManagementContro::getLanduserOne));
			listMap.forEach((k,v)->{
				if (CollectionUtils.isEmpty(v)) {
					return;
				}
				UseClassifyVo useClassifyVo = new UseClassifyVo();
				useClassifyVo.setUseType(k);
				BigDecimal all = new BigDecimal(0);
				BigDecimal contry = new BigDecimal(0);
				BigDecimal statu = new BigDecimal(0);
				for (JcManagementContro jcManagementContro : v) {
					all =    ComputeBase.addCompute(all, jcManagementContro.getAllNum());
					contry = ComputeBase.addCompute(contry, jcManagementContro.getContryNum());
					statu =  ComputeBase.addCompute(statu, jcManagementContro.getStatuNum());
				}
				useClassifyVo.setAreaSum(Double.valueOf(String.valueOf(all)));
				useClassifyVo.setAreaGroup(Double.valueOf(String.valueOf(statu)));
				useClassifyVo.setAreaState(Double.valueOf(String.valueOf(contry)));
				manageList.add(useClassifyVo);
			});
		}

		List<UseClassifyVo> diffList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(manageList) && CollectionUtils.isNotEmpty(useClassifyVos)) {
			Map<String,List<UseClassifyVo>> listMap = manageList
				.stream()
				.collect(Collectors.groupingBy(UseClassifyVo::getUseType));
			useClassifyVos.stream().forEach(e->{

				List<UseClassifyVo> v = listMap.get(e.getUseType());
				if (CollectionUtils.isEmpty(v)) {
					return;
				}

				UseClassifyVo useClassifyVo = new UseClassifyVo();
				useClassifyVo.setUseType(e.getUseType());
				BigDecimal all = new BigDecimal(0);
				BigDecimal contry = new BigDecimal(0);
				BigDecimal statu = new BigDecimal(0);

				for (UseClassifyVo useClassifyVo1 : v) {
					all =    ComputeBase.addCompute(all, BigDecimal.valueOf(useClassifyVo1.getAreaSum()));
					contry = ComputeBase.addCompute(contry, BigDecimal.valueOf(useClassifyVo1.getAreaState()));
					statu =  ComputeBase.addCompute(statu, BigDecimal.valueOf(useClassifyVo1.getAreaGroup()));
				}

				all = ComputeBase.reduceCompute(all,BigDecimal.valueOf(e.getAreaSum()));
				contry = ComputeBase.reduceCompute(contry,BigDecimal.valueOf(e.getAreaState()));
				statu = ComputeBase.reduceCompute(statu,BigDecimal.valueOf(e.getAreaGroup()));


				useClassifyVo.setAreaSum(all.doubleValue());
				useClassifyVo.setAreaGroup(statu.doubleValue());
				useClassifyVo.setAreaState(contry.doubleValue());

				diffList.add(useClassifyVo);
			});
		}

		Map<String, Object> diffListMap = new HashMap<>();
		diffListMap.put("name","省厅下发图斑与省厅下发数据差异");
		diffListMap.put("data",diffList);
		Map<String, Object> manageListMap = new HashMap<>();
		manageListMap.put("name","省厅下发数据(管理控制数)");
		manageListMap.put("data",manageList);
		Map<String, Object> useClassifyVosMap = new HashMap<>();
		useClassifyVosMap.put("name","省厅下发图斑数据(自动统计)");
		useClassifyVosMap.put("data",useClassifyVos);

		List<Map<String, Object>> resultList = new ArrayList<>();
		resultList.add(useClassifyVosMap);
		resultList.add(manageListMap);
		resultList.add(diffListMap);
		return resultList;
	}


}
