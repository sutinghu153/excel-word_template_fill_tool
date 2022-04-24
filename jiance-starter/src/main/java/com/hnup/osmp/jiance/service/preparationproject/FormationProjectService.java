package com.hnup.osmp.jiance.service.preparationproject;

import com.google.common.collect.Lists;
import com.hnup.osmp.jiance.model.dto.FormationProjectDTO;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import com.hnup.osmp.tz.client.TzListFeignClient;
import com.hnup.osmp.tz.model.dto.ConditionDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *  编制项目相关服务层
 * @data2021/11/8,17:56
 * @authorMSI
 */
@Service
public class FormationProjectService {

	@Autowired
	private TzListFeignClient tzListFeignClient;


	/**
	 * 功能描述: 获取数据源
	 * @author sutinghu
	 * @date
	 * @param conditions 参数
	 * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
	 */
	public List<HashMap<String, Object>> getDataRource(List<ConditionDTO> conditions){
		return tzListFeignClient.getTzDataList(conditions, ConstantJiance.TZ_CAIGOU);
	}


	/**
	 * 功能描述: 构造查询参数
	 * @author sutinghu
	 * @date
	 * @param year 年份
	 * @param departName 部门
	 * @return java.util.List<com.hnup.osmp.tz.model.dto.ConditionDTO>
	 */
	public List<ConditionDTO> buildConditions(String year, String departName){
		List<ConditionDTO> conditions = new ArrayList<>(3);
			// 构造查询条件
			ConditionDTO conditionNotNull = new ConditionDTO();
			conditionNotNull.setFldId("infld_1_5");
			conditionNotNull.setFldOp("不为空");
			conditionNotNull.setFldCombo(1);
			conditionNotNull.setFldValue(year);
			conditions.add(conditionNotNull);

		if (StringUtils.isNotBlank(year)) {
			// 构造查询条件
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("infld_1_2");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(year);
			conditions.add(condition);
		}

		if (StringUtils.isNotBlank(departName)) {
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("infld_1_5");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(departName);
			conditions.add(condition);
		}
		return conditions;
	}


	/**
	 * 功能描述: 获取所有编制项目信息
	 * @author sutinghu
	 * @date
	 * @param year 时间
	 * @param departName 部门
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> getProjectList(String year, String departName) {
		List<HashMap<String, Object>> resultData = null;
		if (Objects.equals(departName,"全部")) {
			resultData = this.getDataRource(this.buildConditions(year, null));
		}else {
			// 构造查询条件 获取数据源
			resultData = this.getDataRource(this.buildConditions(year, departName));
		}

		resultData = resultData.stream().filter(stringObjectHashMap -> {

			if (stringObjectHashMap.get("infld_1_3")==null) {
				return false;
			}else {
				return true;
			}

		}).collect(Collectors.toList());

		// 根据责任处室分组
		Map<Object,List<HashMap<String, Object>>> zrcsData = resultData.stream().collect(Collectors.groupingBy(e->e.get("infld_1_5")));
		// 统计结果
		List<Map<String, Object>> result = new ArrayList<>(16);

		zrcsData.forEach((k,v)->{
			Map<String, Object> dataDetails = new HashMap<>(16);
			// 如果结果为空 跳过该责任处室
			if (CollectionUtils.isEmpty(v)) {
				return;
			}
			// 统计结果
			dataDetails.put("责任处室",k);
			dataDetails.put("项目总数",v.size());

			// 统计项
			BigDecimal ysje = new BigDecimal(0);
			BigDecimal hte = new BigDecimal(0);
			BigDecimal yjzf = new BigDecimal(0);
			BigDecimal dzf = new BigDecimal(0);
			// 采购信息
			Integer dcg = 0 , yjcg = 0;
			// 合同情况
			Integer dqht = 0,yqht=0;
			// 入库信息
			Integer yjrk = 0,drk=0;
			// 验收信息
			Integer yjys = 0,dys=0;
			for (HashMap<String, Object> map : v) {
				String s = (String) map.get("infld_1_4");
				if (StringUtils.isNotBlank((String)(map.get("infld_1_4")))) {
					ysje = ysje.add(new BigDecimal(String.valueOf(map.get("infld_1_4"))));
				}else {
					ysje = ysje.add(new BigDecimal(String.valueOf(0)));
				}
				String a = (String) map.get("infld_1_18");
				if (StringUtils.isNotBlank((String)(map.get("infld_1_18")))) {
					hte = hte.add(new BigDecimal(String.valueOf(map.get("infld_1_18"))));
				}else {
					hte = hte.add(new BigDecimal(String.valueOf(0)));
				}
				if (StringUtils.isNotBlank((String)(map.get("infld_1_42")))) {
					yjzf = yjzf.add(new BigDecimal(String.valueOf(map.get("infld_1_42"))));
				}else {
					yjzf = yjzf.add(new BigDecimal(String.valueOf(0)));
				}
				if (StringUtils.isNotBlank((String)(map.get("infld_1_43")))) {
					dzf = dzf.add(new BigDecimal(String.valueOf(map.get("infld_1_43"))));
				}else {
					dzf = dzf.add(new BigDecimal(String.valueOf(0)));
				}
				// 采购信息
				Integer cg = (map.get("infld_1_14") !=null) ? yjcg++ : dcg ++ ;
				// 合同情况
				Integer ht = (map.get("infld_1_13") !=null) ? yqht++ : dqht ++ ;
				// 入库情况
				Integer rk = (map.get("infld_1_48") !=null) ? yjrk++ : drk ++ ;
				// 验收情况
				Integer ys = (map.get("infld_1_55") !=null) ? yjys++ : dys ++ ;
			}
			dataDetails.put("总预算",ysje);
			dataDetails.put("合同额",hte);
			dataDetails.put("已支付",yjzf);
			dataDetails.put("待支付",dzf);
			dataDetails.put("已采购",yjcg);
			dataDetails.put("待采购",dcg);
			dataDetails.put("已签合同",yqht);
			dataDetails.put("待签合同",dqht);
			dataDetails.put("已经入库",yjrk);
			dataDetails.put("待入库",drk);
			dataDetails.put("已经验收",yjys);
			dataDetails.put("待验收",dys);

			v = v.stream().filter(stringObjectHashMap -> {

				if (stringObjectHashMap.get("infld_1_3")==null) {
					return false;
				}else {
					return true;
				}

			}).collect(Collectors.toList());

			// 根据项目类型分组
			Map<Object,List<HashMap<String, Object>>> xmlxData = v.stream().collect(Collectors.groupingBy(e->e.get("infld_1_3")));

			xmlxData.forEach((k1,v1)->{
				// 结题情况
				Integer yjjt = 0,djt=0;
				// 提交情况
				Integer yjtj = 0,dtj=0;
				Integer sy = 0;
				for (HashMap<String, Object> map : v1) {

					if ((map.get("infld_1_45") != null)) {
						yjjt++;
					} else if ((map.get("infld_1_47") != null)) {
						yjtj++;
					}else {
						if (map.get("infld_1_44")!=null) {
							djt++;
						}else {
							dtj++;
						}
					}
					sy++;
				}

				if (yjjt != 0  || djt!=0) {
					dataDetails.put(k1+"待结题",djt);
					dataDetails.put(k1+"已结题",yjjt);
					dataDetails.put(k1+"全部",sy);
				}

				if (yjtj != 0 || dtj!=0) {
					dataDetails.put(k1+"待提交",dtj);
					dataDetails.put(k1+"已提交",yjtj);
					dataDetails.put(k1+"全部",sy);
				}

			});

			result.add(dataDetails);
		});

		return result;
	}

	/**
	 * 功能描述: 获取首页饼图-柱子
	 * @author sutinghu
	 * @date
	 * @param year
	 * @param departName 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getProjectcharts(String year, String departName) {
		List<HashMap<String, Object>> resultData = null;
		if (Objects.equals(departName,"全部")) {
			resultData = this.getDataRource(this.buildConditions(year, null));
		}else {
			// 构造查询条件 获取数据源
			resultData = this.getDataRource(this.buildConditions(year, departName));
		}

		resultData = resultData.stream().filter(stringObjectHashMap -> {

			if (stringObjectHashMap.get("infld_1_3")==null) {
				return false;
			}else {
				return true;
			}

		}).collect(Collectors.toList());

		// 根据类型分组
		Map<Object,List<HashMap<String, Object>>> zrcsData = resultData.stream().collect(Collectors.groupingBy(e->e.get("infld_1_3")));

		Map<String, Object> result = new HashMap<>();

		// 饼图
		List<Map<String, Object>> pieChart = new ArrayList<>();
		zrcsData.forEach((k,v)->{
			Map<String, Object> map = new HashMap<>(2);
			map.put("value",v.size());
			map.put("name",k);
			pieChart.add(map);
		});
		result.put("piedata",pieChart);

		// 柱子图
		// 预算总额
		BigDecimal ysje =  new BigDecimal(0);
		// 项目总数
		Integer nums = resultData.size();
		// 采购信息
		Integer dcg = 0 , yjcg = 0;
		// 合同情况
		Integer dqht = 0,yqht=0;
		// 已经支付
		BigDecimal yjzf =  new BigDecimal(0);
		// 待支付
		BigDecimal dzf =  new BigDecimal(0);
		// 合同金额
		BigDecimal hte =  new BigDecimal(0);
		for (HashMap<String, Object> mapz:resultData) {
			if (StringUtils.isNotBlank((String)(mapz.get("infld_1_4")))) {
				ysje = ysje.add(new BigDecimal(String.valueOf(mapz.get("infld_1_4"))));
			}else {
				ysje = ysje.add(new BigDecimal(String.valueOf(0)));
			}
			Integer cg = (mapz.get("infld_1_14") !=null) ? yjcg++ : dcg ++ ;
			Integer ht = (mapz.get("infld_1_13") !=null) ? yqht++ : dqht ++ ;
			if (StringUtils.isNotBlank((String)(mapz.get("infld_1_42")))) {
				yjzf = yjzf.add(new BigDecimal(String.valueOf(mapz.get("infld_1_42"))));
			}else {
				yjzf = yjzf.add(new BigDecimal(String.valueOf(0)));
			}
			if (StringUtils.isNotBlank((String)(mapz.get("infld_1_43")))) {
				dzf = dzf.add(new BigDecimal(String.valueOf(mapz.get("infld_1_43"))));
			}else {
				dzf = dzf.add(new BigDecimal(String.valueOf(0)));
			}
			if (StringUtils.isNotBlank((String)(mapz.get("infld_1_18")))) {
				hte = hte.add(new BigDecimal(String.valueOf(mapz.get("infld_1_18"))));
			}else {
				hte = hte.add(new BigDecimal(String.valueOf(0)));
			}
		}
		Map<String, Object> map = new HashMap<>(2);
		map.put("预算总额",ysje);
		map.put("项目总数",nums);
		map.put("待采购",dcg);
		map.put("已采购",yjcg);
		map.put("待签合同",dqht);
		map.put("已签合同",yqht);
		map.put("待支付",dzf);
		map.put("已支付",yjzf);
		map.put("合同金额",hte);
		result.put("coldata",map);
		return result;
	}


	/**
	 * 功能描述: 根据构造的数据得到前端的视图数据
	 * @author sutinghu
	 * @date
	 * @param data 实体数据
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> buildProjectDetails(List<HashMap<String, Object>> data){

		if (CollectionUtils.isEmpty(data)) {
			return Lists.newArrayList();
		}

		List<Map<String, Object>> result = new ArrayList<>(16);
		// 构造
		for (HashMap<String, Object> map:data) {
			Map<String, Object> value = new HashMap<>(16);
			value.put("项目名称",map.get("infld_1_1"));
			value.put("项目类型",map.get("infld_1_3"));
			value.put("责任处室",map.get("infld_1_5"));
			value.put("分管领导",map.get("infld_1_7"));
			value.put("经办人",map.get("infld_1_6"));
			String cgxx = map.get("infld_1_14")!=null ? "是":"否";
			value.put("是否采购",cgxx);
			String htxx = map.get("infld_1_13")!=null ? "是":"否";
			value.put("是否签订合同",htxx);
			value.put("政府采购编号",map.get("infld_1_14"));
			value.put("合同签订日期",map.get("infld_1_19"));
			value.put("合同编号",map.get("infld_1_13"));
			value.put("项目预算金额",map.get("infld_1_4"));
			value.put("合同金额",map.get("infld_1_18"));
			value.put("已付金额",map.get("infld_1_42"));
			value.put("待付金额",map.get("infld_1_43"));

			String jtqk = map.get("infld_1_45")!=null ? "是":"否";
			value.put("是否结题",jtqk);
			String tjqk = map.get("infld_1_46")!=null ? "是":"否";
			value.put("是否提交",tjqk);
			String ysqk = map.get("infld_1_55")!=null ? "是":"否";
			value.put("是否验收",ysqk);
			String rkqk = map.get("infld_1_48")!=null ? "是":"否";
			value.put("是否入库",rkqk);
			value.put("验收日期",map.get("infld_1_55"));
			result.add(value);
		}
		return result;
	}


	/**
	 * 功能描述: 根据编制项目-项目/支付状态详情页 筛选数据
	 * @author sutinghu
	 * @date
	 * @param formationProjectDTO 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public List<?> getProjectDetails(FormationProjectDTO formationProjectDTO) {

		if (formationProjectDTO == null) {
			return  buildProjectDetails(this.getDataRource(this.buildConditions(null,null)));
		}

		// 构造查询条件 获取数据源
		List<HashMap<String, Object>> resultData =
			this.getDataRource(this.buildConditions(formationProjectDTO.getYear(), formationProjectDTO.getDepartName()));

		// 项目类型过滤
		if (StringUtils.isNotBlank(formationProjectDTO.getProjectType())) {
			resultData = resultData
				.stream()
				.filter(e->Objects.equals(e.get("infld_1_3"),formationProjectDTO.getProjectType()))
				.collect(Collectors.toList());
		}
		// 验收情况过滤
		if (formationProjectDTO.getCheckState()!=null) {
			// 已验收
			if (Objects.equals(formationProjectDTO.getCheckState(),true)) {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_55")!=null)
					.collect(Collectors.toList());
				// 待验收
			}else {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_55")==null)
					.collect(Collectors.toList());
			}
		}
		// 入库情况过滤
		if (formationProjectDTO.getPutState()!=null) {
			// 已入库
			if (Objects.equals(formationProjectDTO.getPutState(),true)) {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_48")!=null)
					.collect(Collectors.toList());
				// 待入库
			}else {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_48")==null)
					.collect(Collectors.toList());
			}
		}
		// 采购情况过滤
		if (formationProjectDTO.getPurchaseState()!=null) {
			// 已采购
			if (Objects.equals(formationProjectDTO.getPurchaseState(),true)) {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_14")!=null)
					.collect(Collectors.toList());
				// 待采购
			}else {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_14")==null)
					.collect(Collectors.toList());
			}
		}
		// 合同情况过滤
		if (formationProjectDTO.getBargainState()!=null) {
			// 已签订
			if (Objects.equals(formationProjectDTO.getBargainState(),true)) {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_13")!=null)
					.collect(Collectors.toList());
				// 待签订
			}else {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_13")==null)
					.collect(Collectors.toList());
			}
		}
		// 结题情况过滤
		if (formationProjectDTO.getJtState()!=null) {
			// 已jt
			if (Objects.equals(formationProjectDTO.getJtState(),true)) {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_45")!=null)
					.collect(Collectors.toList());
				// 待jt
			}else {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_45")==null)
					.collect(Collectors.toList());
			}
		}
		// 提交情况过滤
		if (formationProjectDTO.getTjState()!=null) {
			// 已jt
			if (Objects.equals(formationProjectDTO.getTjState(),true)) {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_45")!=null)
					.collect(Collectors.toList());
				// 待jt
			}else {
				resultData = resultData
					.stream()
					.filter(e->e.get("infld_1_45")==null)
					.collect(Collectors.toList());
			}
		}

		return this.buildProjectDetails(resultData);
	}

	/**
	 * 功能描述:编制项目-项目/支付状态详情页 查询条件
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getProjectQuery() {

		// 构造查询条件 获取数据源
		List<HashMap<String, Object>> resultData = this.getDataRource(this.buildConditions(null, null));
		// 年份搜索条件
		List<Integer> yearQuery = new ArrayList<>(8);
		// 科室搜索条件
		List<Object> departQuery = new ArrayList<>(8);
		// 项目类型搜索条件
		List<Object> typeQuery = new ArrayList<>(8);

		for (HashMap<String, Object> e:resultData) {
			if (!yearQuery.contains(e.get("infld_1_2"))) {
				if (e.get("infld_1_2")!=null) {
					yearQuery.add((Integer) e.get("infld_1_2"));
				}
			}
			if (!departQuery.contains(e.get("infld_1_5"))) {
				if (e.get("infld_1_5")!=null) {
					departQuery.add(e.get("infld_1_5"));
				}
			}
			if (!typeQuery.contains(e.get("infld_1_3"))) {
				if (e.get("infld_1_3")!=null) {
					typeQuery.add(e.get("infld_1_3"));
				}
			}
		}

		departQuery.add("全部");
		Collections.sort(yearQuery, Collections.reverseOrder());
		Map<String, Object> result = new HashMap<>();
		result.put("yearQuery",yearQuery);
		result.put("departQuery",departQuery);
		result.put("typeQuery",typeQuery);
		return result;
	}
}
