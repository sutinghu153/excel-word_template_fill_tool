package com.hnup.osmp.jiance.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuangyuewu
 * @createTime 2022/03/04
 * @Description
 */
public class ExcelTest {

	public static void fillExcel() {
		String template = "C:\\Users\\MSI\\Desktop\\template.xlsx";
		String target = "C:\\Users\\MSI\\Desktop\\result.xlsx";
		// 工作薄对象
		ExcelWriter excelWriter = EasyExcel.write(target, Map.class).withTemplate(template).build();
		// 工作区对象
		WriteSheet writeSheet = EasyExcel.writerSheet().build();
		// 列表数据
		List<Map<String, Object>> datas = new ArrayList<>();
		for (int i = 0; i <5; i++) {
			Map<String, Object> fillData = new HashMap<>();
			fillData.put("name" , i);
			fillData.put("title" , i);
			datas.add(fillData);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("a", "aaaaa");
		map.put("b", "bbbbb");
		//  填充列表数据
		excelWriter.fill(datas, writeSheet);
		//  填充列表数据
		excelWriter.fill(map, writeSheet);
		excelWriter.finish();
	}

	public static void main(String[] args) {
		fillExcel();
	}
}

