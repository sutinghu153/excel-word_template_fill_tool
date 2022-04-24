package com.hnup.osmp.jiance.service.datudi;

import com.hnup.osmp.jiance.utils.EcologicalConfig;
import com.hnup.osmp.tz.client.TzListFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.02.15 11:48
 */
@Service
@Slf4j
public class EcologicalStatisticsService {

	@Autowired
	private TzListFeignClient tzListFeignClient;

	@Autowired
	private EcologicalConfig ecologicalConfig;
	/**
	 * 获取建设用地土壤污染状态调查
	 * @param areaName
	 * @return
	 */
	public HashMap<String,Object> getEcologyTja(String areaName) {
		//超标情况
		int allCt = 0;
		int wrOverStandard = 0;
		int wrwOverStandard = 0;
		int notInvestigated = 0;
		List<HashMap<String, Object>> lista = new ArrayList<>();
		HashMap<String, Object> runDt = new HashMap<>(16);
		try {
			lista = tzListFeignClient.getTzDataList(new ArrayList<>(), EcologicalConfig.TYPE_KEY_A);
		}
		catch (Exception ex) {
			runDt.put("error", ex.getMessage());
		}
		HashMap<String, Integer> typeMap = new HashMap<>(16);
		HashMap<String, Integer> dcMap =new HashMap<String,Integer>(8) {
			{
				put("污染状况调查",0);
				put("风险评估",0);
				put("风险管控",0);
				put("风险管控效果评估",0);
				put("修复",0);
				put("修复效果评估",0);
				put("管控及修复",0);
				put("管控及修复效果评估",0);
			}
		};
		for (HashMap<String, Object> itme : lista) {
			String areaNameString= String.valueOf(itme.get("areaname"));
			if(areaName != null && !"".equals(areaName)) {
				if (!areaName.equals(areaNameString)) {
					continue;
				}
			}
			String groupKey = String.valueOf(itme.get("typeuse"));
			groupKey = "".equals(groupKey)?"未分类":groupKey;
			String isOverStandardPlot = String.valueOf(itme.get("isoverstandardplot"));
			if ("是".equals(isOverStandardPlot)) {
				wrOverStandard += 1;
			} else if ("否".equals(isOverStandardPlot)) {
				wrwOverStandard += 1;
			} else if ("未确定".equals(isOverStandardPlot)) {
				notInvestigated += 1;
			}
			Integer mapCount = 1;
			if (typeMap.containsKey(groupKey)) {
				mapCount = typeMap.get(groupKey) + 1;
			}
			typeMap.put(groupKey, mapCount);
			String groupName = String.valueOf(itme.get("stage"));
			if (dcMap.containsKey(groupName)) {
				Integer dcCount = dcMap.get(groupName) + 1;
				dcMap.put(groupName, dcCount);
			}
		}
		runDt.put("text", "建设用地土壤污染状况调查");
		HashMap<String, Object> cbqk = new HashMap<>(16);
		cbqk.put("text", "超标情况");
		cbqk.put("allCt", allCt);
		cbqk.put("wrOverStandard", wrOverStandard);
		cbqk.put("wrwOverStandard", wrwOverStandard);
		cbqk.put("notInvestigated", notInvestigated);
		runDt.put("cbqk", cbqk);
		HashMap<String, Object> ghytfl = new HashMap<>(16);
		ghytfl.put("text", "规划用途分类");
		ghytfl.put("typeMap", typeMap);
		runDt.put("ghytfl", ghytfl);
		HashMap<String, Object> wrztdcjd = new HashMap<>(16);
		wrztdcjd.put("text", "污染状况调查进度");
		wrztdcjd.put("dcMap", dcMap);
		runDt.put("wrztdcjd", wrztdcjd);
		return runDt;
	}
}
