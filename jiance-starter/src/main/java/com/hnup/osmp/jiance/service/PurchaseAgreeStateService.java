package com.hnup.osmp.jiance.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.IJcPurchaseAgreeStateDAO;
import com.hnup.osmp.jiance.repository.entity.JcPurchaseAgreeState;
import com.hnup.osmp.jiance.utils.DateUtils;
import com.hnup.osmp.tz.client.TzListFeignClient;
import com.hnup.osmp.tz.model.dto.ConditionDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @data2021/12/18,10:34
 * @authorsutinghu
 */
@Service
public class PurchaseAgreeStateService {

	@Autowired
	private IJcPurchaseAgreeStateDAO purchaseAgreeState;

	@Autowired
	private TzListFeignClient tzListFeignClient;

	private static final Integer TZ_PAINT_MONEY = 3254;

	private static final String QB = "全部";


	public List<ConditionDTO> buildConditions(String year,
											  String batch,
											  String unit){

		List<ConditionDTO> conditions = new ArrayList<>(3);
		// 构造查询条件——案卷编号不为空
		ConditionDTO conditionNotNull = new ConditionDTO();
		conditionNotNull.setFldId("fld_10573_8");
		conditionNotNull.setFldOp("不为空");
		conditionNotNull.setFldCombo(1);
		conditions.add(conditionNotNull);

		if (StringUtils.isNotBlank(year)) {
			// 构造查询条件——时间等于
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("fld_10573_16");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(year);
			conditions.add(condition);
		}

		if (StringUtils.isNotBlank(batch)) {
			// 构造查询条件——批次
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("fld_10573_17");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(batch);
			conditions.add(condition);
		}

		if (StringUtils.isNotBlank(unit)) {
			// 构造查询条件——设计单位
			ConditionDTO condition = new ConditionDTO();
			condition.setFldId("fld_10573_10");
			condition.setFldOp("等于");
			condition.setFldCombo(1);
			condition.setFldValue(unit);
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
		return tzListFeignClient.getTzDataList(conditions, TZ_PAINT_MONEY);
	}


	public List<JcPurchaseAgreeState> buildJcPurchaseAgreeStates(String year,
																 String batch,
																 String unit){

		List<HashMap<String, Object>> resultData = null;

		resultData = this.getDataRource(this.buildConditions(year, batch,unit));

		// 构造对象
		if (org.apache.commons.collections.CollectionUtils.isEmpty(resultData)) {
			return Lists.newArrayList();
		}
		List<JcPurchaseAgreeState> jcPurchaseAgreeStates = new ArrayList<>(8);
		for (HashMap<String, Object> map : resultData) {
			JcPurchaseAgreeState jcPurchaseAgreeState = new JcPurchaseAgreeState();
			// 项目采购类型
			jcPurchaseAgreeState.setProPurchaseType(this.getValueByString(map.get("fld_10573_7")));
			// 政府采购编号
			jcPurchaseAgreeState.setGovPurchaseNum(this.getValueByString(map.get("fld_10573_8")));
			// 项目名称
			jcPurchaseAgreeState.setProName(this.getValueByString(map.get("fld_10573_9")));
			// 设计单位
			jcPurchaseAgreeState.setProUnits(this.getValueByString(map.get("fld_10573_10")));
			// 经办处
			jcPurchaseAgreeState.setHandleDepartment(this.getValueByString(map.get("fld_10573_11")));
			// 合同金额
			jcPurchaseAgreeState.setAgreeAll(this.getValueByDouble(map.get("fld_10573_12")));
			// 已支付金额
			jcPurchaseAgreeState.setAlreadyAgree(this.getValueByDouble(map.get("fld_10573_13")));
			// 待支付金额
			jcPurchaseAgreeState.setWaitAgree(this.getValueByDouble(map.get("fld_10573_14")));
			// 拟支付金额
			jcPurchaseAgreeState.setBatchMoney(this.getValueByDouble(map.get("fld_10573_15")));
			// 付费批次
			jcPurchaseAgreeState.setPayBatch(this.getValueByString(map.get("fld_10573_17")));
			// 更新时间
			Date date = DateUtils.str2Date((String) map.get("fld_10573_18"));
			jcPurchaseAgreeState.setCreateTime(date);
			// 统计年份
			jcPurchaseAgreeState.setCountYear(this.getValueByString(map.get("fld_10573_16")));

			jcPurchaseAgreeStates.add(jcPurchaseAgreeState);
		}

		return jcPurchaseAgreeStates;
	}


	public List<String> getYearList() {

		List<JcPurchaseAgreeState> yearList = this.buildJcPurchaseAgreeStates(null,null,null);

//			purchaseAgreeState.lambdaQuery().list();

		if (CollectionUtils.isEmpty(yearList)) {
			return Lists.newArrayList();
		}

		List<String> strings = yearList.stream().map(JcPurchaseAgreeState::getCountYear).distinct().collect(Collectors.toList());

		strings.add("全部");

		Collections.sort(strings);

		return strings;

	}

	public List<String> getBatchList() {

		List<JcPurchaseAgreeState> yearList = this.buildJcPurchaseAgreeStates(null,null,null);
//			purchaseAgreeState.lambdaQuery().list();

		if (CollectionUtils.isEmpty(yearList)) {
			return Lists.newArrayList();
		}

		List<String> strings = yearList.stream().map(JcPurchaseAgreeState::getPayBatch).distinct().collect(Collectors.toList());

		strings.add("全部");

		Collections.sort(strings);

		return strings;
	}

	public List<String> getUnitsList() {

		List<JcPurchaseAgreeState> yearList = this.buildJcPurchaseAgreeStates(null,null,null);
			//purchaseAgreeState.lambdaQuery().list();

		if (CollectionUtils.isEmpty(yearList)) {
			return Lists.newArrayList();
		}

		List<String> strings = yearList.stream().map(JcPurchaseAgreeState::getProUnits).distinct().collect(Collectors.toList());

		strings.add(0,"全部");

		return strings;

	}

	public List<JcPurchaseAgreeState> getDetailList(String year, String batch, String unit) {

		List<JcPurchaseAgreeState> purchaseAgreeStates = this.buildJcPurchaseAgreeStates(null,null,null);

//		purchaseAgreeState.lambdaQuery().list();

		if (CollectionUtils.isEmpty(purchaseAgreeStates)) {
			return Lists.newArrayList();
		}

		if (StringUtils.isNotBlank(year)) {
			if(!Objects.equals(QB,year)) {
				purchaseAgreeStates = purchaseAgreeStates
					.stream()
					.filter(e->Objects.equals(e.getCountYear(),year))
					.collect(Collectors.toList());
			}
		}

		if (StringUtils.isNotBlank(batch)) {
			if(!Objects.equals(QB,batch)) {
				purchaseAgreeStates = purchaseAgreeStates
					.stream()
					.filter(e->Objects.equals(e.getPayBatch(),batch))
					.collect(Collectors.toList());
			}
		}

		if (StringUtils.isNotBlank(unit)) {
			if(!Objects.equals(QB,unit)) {
				purchaseAgreeStates = purchaseAgreeStates
					.stream()
					.filter(e->Objects.equals(e.getProUnits(),unit))
					.collect(Collectors.toList());
			}
		}

		return purchaseAgreeStates;

	}

	public Map<String, Object> getIndex(String year, String batch) {

		Map<String, Object> result = new HashMap<>();

		List<JcPurchaseAgreeState> purchaseAgreeStates = this.buildJcPurchaseAgreeStates(null,null,null);

//			purchaseAgreeState.lambdaQuery().list();

		if (CollectionUtils.isEmpty(purchaseAgreeStates)) {
			return Maps.newHashMap();
		}

		if (StringUtils.isNotBlank(year)) {
			if(!Objects.equals(QB,year)) {
				purchaseAgreeStates = purchaseAgreeStates
					.stream()
					.filter(e->Objects.equals(e.getCountYear(),year))
					.collect(Collectors.toList());
			}
		}

		if (StringUtils.isNotBlank(batch)) {
			if(!Objects.equals(QB,batch)) {
				purchaseAgreeStates = purchaseAgreeStates
					.stream()
					.filter(e->Objects.equals(e.getPayBatch(),batch))
					.collect(Collectors.toList());
			}
		}

		Map<String, Object> title = new HashMap<>();
		title.put("项目总数",purchaseAgreeStates.size());
		BigDecimal htje = new BigDecimal(0);
		BigDecimal yzfje = new BigDecimal(0);
		BigDecimal dzfje = new BigDecimal(0);
		BigDecimal pcffje = new BigDecimal(0);

		for (JcPurchaseAgreeState purchaseAgreeState:purchaseAgreeStates) {

			// htje
			if (purchaseAgreeState.getAgreeAll()!=null) {
				htje = ComputeBase.addCompute(htje,purchaseAgreeState.getAgreeAll());
			}
			// yzfje
			if (purchaseAgreeState.getAlreadyAgree()!=null) {
				yzfje = ComputeBase.addCompute(yzfje,purchaseAgreeState.getAlreadyAgree());
			}
			// dzfje
			if (purchaseAgreeState.getWaitAgree()!=null) {
				dzfje = ComputeBase.addCompute(dzfje,purchaseAgreeState.getWaitAgree());
			}
			// pcffje
			if (purchaseAgreeState.getBatchMoney()!=null) {
				pcffje = ComputeBase.addCompute(pcffje,purchaseAgreeState.getBatchMoney());
			}

		}

		title.put("合同金额",htje);
		title.put("已支付金额",yzfje);
		title.put("待支付金额",dzfje);
		title.put("批次支付金额",pcffje);

		result.put("title",title);

		Map<String, List<JcPurchaseAgreeState>> listMap = purchaseAgreeStates.stream().collect(Collectors.groupingBy(JcPurchaseAgreeState::getProUnits));
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (Map.Entry<String, List<JcPurchaseAgreeState>> map: listMap.entrySet()) {
			Map<String, Object> value = new HashMap<>();
			value.put("处室名称",map.getKey());
			value.put("项目总数",map.getValue().size());

			BigDecimal cshtje = new BigDecimal(0);
			BigDecimal csyzfje = new BigDecimal(0);
			BigDecimal csdzfje = new BigDecimal(0);
			BigDecimal cspcffje = new BigDecimal(0);

			for (JcPurchaseAgreeState purchaseAgreeState:map.getValue()) {

				// htje
				if (purchaseAgreeState.getAgreeAll()!=null) {
					cshtje = ComputeBase.addCompute(cshtje,purchaseAgreeState.getAgreeAll());
				}
				// yzfje
				if (purchaseAgreeState.getAlreadyAgree()!=null) {
					csyzfje = ComputeBase.addCompute(csyzfje,purchaseAgreeState.getAlreadyAgree());
				}
				// dzfje
				if (purchaseAgreeState.getWaitAgree()!=null) {
					csdzfje = ComputeBase.addCompute(csdzfje,purchaseAgreeState.getWaitAgree());
				}
				// pcffje
				if (purchaseAgreeState.getBatchMoney()!=null) {
					cspcffje = ComputeBase.addCompute(cspcffje,purchaseAgreeState.getBatchMoney());
				}


			}

			value.put("合同金额",cshtje);
			value.put("已支付金额",csyzfje);
			value.put("待支付金额",csdzfje);
			value.put("批次支付金额",cspcffje);

			mapList.add(value);
		}

		result.put("mapList",mapList);

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

}
