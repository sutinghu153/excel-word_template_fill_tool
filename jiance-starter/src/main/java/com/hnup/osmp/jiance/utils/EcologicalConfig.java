package com.hnup.osmp.jiance.utils;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.02.15 14:49
 */
@Service
public class EcologicalConfig {

	/**
	 * 建设用地土壤污染状况调查名录-台账
	 */
	public static Integer TYPE_KEY_A = 4007;//2914;//3251;

	/**
	 * 矿山地质环境恢复治理工程-台账
	 */
	public static Integer TYPE_KEY_B = 3149;

	/**
	 * 矿山生态保护修复方案审查-台账
	 */
	public static Integer TYPE_KEY_C = 3150;



	public static List<String> WR_LIST=new ArrayList<String>(){
		{
			add("污染状况调查");
			add("风险评估");
			add("风险管控");
			add("风险管控效果评估");
			add("修复");
			add("修复效果评估");
			add("管控及修复");
			add("管控及修复效果评估");
		}
	};

}
