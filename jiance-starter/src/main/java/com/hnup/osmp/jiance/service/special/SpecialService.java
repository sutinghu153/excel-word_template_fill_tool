package com.hnup.osmp.jiance.service.special;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.tz.client.TzListFeignClient;
import com.hnup.osmp.tz.model.dto.ConditionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.03.23 17:20
 */
@Service
public class SpecialService {

	/**
	 * 台账feign
	 */
	@Autowired
	private TzListFeignClient tzListFeignClient;

	/**
	 * 卫星
	 */
	private static Integer keyTypeCode = 3117;

	/**
	 * 省厅
	 */
	private static Integer stKeyTypeCode = 4006;

	private static  List<String> batchList=new ArrayList<>();
	private static Integer batchCt=0;

	/**
	 * 获取批注集合
	 * @return
	 */
	public List<String> getWxBatchList() {
		if (batchCt >= 20) {
			batchList = new ArrayList<>();
			batchCt = 0;
		}
		if(batchCt<=0) {
			List<HashMap<String, Object>> dtList = tzListFeignClient.getTzDataList(new ArrayList<>(), keyTypeCode);
			if (dtList != null) {
				for (HashMap<String, Object> dic : dtList) {
					String batchString = String.valueOf(dic.get("issuebatch"));
					String nbString = batchList.stream().filter(a -> batchString.equals(a)).findAny().orElse(null);
					if (nbString == null) {
						batchList.add(batchString);
					}
				}
			}
		}
		batchCt++;
		return batchList;
	}

	/**
	 * 卫星监测耕地数量变化图斑
	 * @param batch
	 * @return
	 */
	public Map<String, Object> satelliteMonitoring(String batch) {
		try {
			List<ConditionDTO> whereList = new ArrayList<>();
			if (batch != null || !batch.equals("")) {
				ConditionDTO condition = new ConditionDTO();
				condition.setFldId("issuebatch");
				condition.setFldOp("包含");
				condition.setFldCombo(1);
				condition.setFldValue(batch);
				whereList.add(condition);
			}
			HashMap<String, Object> dt = new HashMap<>(16);
			List<HashMap<String, Object>> dtList = tzListFeignClient.getTzDataList(whereList, keyTypeCode);
			if (dtList == null) {
				return dt;
			}
			//图斑总数
			dt.put("allCount", dtList.size());
			//占用耕地面积
			Double allArea = 0D;
			///扣减总数
			Integer kgCount = 0;
			///扣减面积
			Double kgGdArea = 0D;
			///扣减农民建房
			Double kgNmjfArea = 0D;
			///扣减农村公共服务设施
			Double kgNcggfwssArea = 0D;
			///扣减农村道路
			Double kgNcdlArea = 0D;
			///扣减农村灌溉设施
			Double kgNcggssArea = 0D;
			///冻结
			Integer dgCount = 0;
			///冻结耕地面积
			Double dgGdArea = 0D;
			///疑似设施农用地
			Double dgYsssnydArea = 0D;
			///临时用地
			Double dgLsydArea = 0D;
			///新增违法建设
			Double dgXzwfjsArea = 0D;
			///非农化
			Double dgFnhArea = 0D;
			///坑塘水面
			Double dgKtsmArea = 0D;

			for (HashMap<String, Object> dic : dtList) {
				Double landarea = Convert.toDouble(dic.get("landarea"));
				allArea = NumberUtil.add(allArea,landarea);
				String treatmentmethod = String.valueOf(dic.get("treatmentmethod"));
				if ("扣减".equals(treatmentmethod)) {
					kgCount++;
					kgGdArea =NumberUtil.add(kgGdArea,landarea);
					if (dic.get("farmershouses") != null && "是".equals(String.valueOf(dic.get("farmershouses")))) {
						kgNmjfArea = NumberUtil.add(kgNmjfArea,landarea);
					}
					if (dic.get("serviceffacilities") != null && "是".equals(String.valueOf(dic.get("serviceffacilities")))) {
						kgNcggfwssArea = NumberUtil.add(kgNcggfwssArea,landarea);
					}
					if (dic.get("ruralroad") != null && "是".equals(String.valueOf(dic.get("ruralroad")))) {
						kgNcdlArea = NumberUtil.add(kgNcdlArea,landarea);
					}
					if (dic.get("irrigationffacilities") != null && "是".equals(String.valueOf(dic.get("irrigationffacilities")))) {
						kgNcggssArea =NumberUtil.add(kgNcggssArea,landarea);
					}
				} else if ("冻结".equals(treatmentmethod)) {
					dgCount++;
					dgGdArea =NumberUtil.add(dgGdArea,landarea);
					if (dic.get("agriculturalland") != null && "是".equals(String.valueOf(dic.get("agriculturalland")))) {
						dgYsssnydArea =NumberUtil.add(dgYsssnydArea,landarea);
					}
					if (dic.get("temporaryland") != null && "是".equals(String.valueOf(dic.get("temporaryland")))) {
						dgLsydArea =NumberUtil.add(dgLsydArea,landarea);
					}
					if (dic.get("illegaloccupation") != null && "是".equals(String.valueOf(dic.get("illegaloccupation")))) {
						dgXzwfjsArea =NumberUtil.add(dgXzwfjsArea,landarea);
					}
					if (dic.get("nonagricultural") != null && "是".equals(String.valueOf(dic.get("nonagricultural")))) {
						dgFnhArea=NumberUtil.add(dgFnhArea,landarea);
					}
					if (dic.get("pitpond") != null && "是".equals(String.valueOf(dic.get("pitpond")))) {
						dgKtsmArea =NumberUtil.add(dgKtsmArea,landarea);
					}
				}
			}
			dt.put("kgCount", kgCount);
			dt.put("kgGdArea", kgGdArea);
			dt.put("kgNmjfArea", kgNmjfArea);
			dt.put("kgNcggfwssArea", kgNcggfwssArea);
			dt.put("kgNcdlArea", kgNcdlArea);
			dt.put("kgNcggssArea", kgNcggssArea);
			dt.put("dgCount", dgCount);
			dt.put("dgGdArea", dgGdArea);
			dt.put("dgYsssnydArea", dgYsssnydArea);
			dt.put("dgLsydArea", dgLsydArea);
			dt.put("dgXzwfjsArea", dgXzwfjsArea);
			dt.put("dgFnhArea", dgFnhArea);
			dt.put("dgKtsmArea", dgKtsmArea);
			dt.put("allArea", allArea);
			return dt;
		} catch (Exception ex) {
			throw new DeclareException(ex.getMessage());
		}
	}


	/**
	 * 省厅耕地指标
	 * @return
	 */
	public Map<String,Object>  provincialCultivatedLand(String batch) {
		try {
			List<ConditionDTO> whereList = new ArrayList<>();
			if (batch != null || !batch.equals("")) {
				ConditionDTO condition = new ConditionDTO();
				condition.setFldId("tbxfpc");
				condition.setFldOp("包含");
				condition.setFldCombo(1);
				condition.setFldValue(batch);
				whereList.add(condition);
			}
			HashMap<String, Object> dt = new HashMap<>(16);
			List<HashMap<String, Object>> dtList = tzListFeignClient.getTzDataList(whereList, stKeyTypeCode);
			if (dtList == null) {
				return dt;
			}
			//申请解扣解冻耕地面积
			Double sqjcgdmj=0d;
			//申请解扣解冻永久农用地面积
			Double sqjcntmj=0d;
			//解扣解冻耕地面积
			Double jkjdgdmj=0d;
			//解扣解冻耕地数量指标
			Double jkjdgdzb=0d;
			//解扣解冻粮食产能指标
			Double jkjdlszb =0d;
			//解扣解冻永久农田面积
			Double jkjdntmj =0d;
			//解扣解冻水田规模指标
			Double jkjdstzb=0d;
			for (HashMap<String, Object> dic : dtList) {
				sqjcgdmj =NumberUtil.add(sqjcgdmj,Convert.toDouble(dic.get("sqjcgdmj")));
				sqjcntmj =NumberUtil.add(sqjcntmj,Convert.toDouble(dic.get("sqjcntmj")));
				jkjdgdmj =NumberUtil.add(jkjdgdmj,Convert.toDouble(dic.get("jkjdgdmj")));
				jkjdgdzb =NumberUtil.add(jkjdgdzb,Convert.toDouble(dic.get("jkjdgdzb")));
				jkjdlszb =NumberUtil.add(jkjdlszb,Convert.toDouble(dic.get("jkjdlszb")));
				jkjdntmj =NumberUtil.add(jkjdntmj,Convert.toDouble(dic.get("jkjdntmj")));
				jkjdstzb =NumberUtil.add(jkjdstzb,Convert.toDouble(dic.get("jkjdstzb")));
			}
			dt.put("sqjcgdmj",sqjcgdmj);
			dt.put("sqjcntmj",sqjcntmj);
			dt.put("jkjdgdmj",jkjdgdmj);
			dt.put("jkjdgdzb",jkjdgdzb);
			dt.put("jkjdlszb",jkjdlszb);
			dt.put("jkjdntmj",jkjdntmj);
			dt.put("jkjdstzb",jkjdstzb);
			return dt;
		} catch (Exception ex) {
			throw new DeclareException(ex.getMessage());
		}
	}
}
