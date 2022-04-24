package com.hnup.osmp.jiance.service.file;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shuangyuewu
 * @createTime 2022/03/04
 * @Description
 */
public class ExcelFillUtils {


	/**
	 * 功能描述: 根据模板填充excel
	 * @author sutinghu
	 * @date
	 * @param file
	 * @param fieldMap
	 * @param datas 参数
	 * @return byte[]
	 */
	public static byte[] fillExcel(byte[] file,
								   Map<String, Object> fieldMap,
								   List<Map<String, Object>> datas){

		InputStream is = new ByteArrayInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 工作薄对象
		ExcelWriter excelWriter = EasyExcel.write(out, Map.class).withTemplate(is).build();
		// 工作区对象
		WriteSheet writeSheet = EasyExcel.writerSheet().build();
		// 填充一般字段数据fieldMap
		//  填充列表数据
		if (MapUtils.isNotEmpty(fieldMap)) {
			excelWriter.fill(fieldMap, writeSheet);
		}
		// 填充list数据datas
		if (CollectionUtils.isNotEmpty(datas)) {
			excelWriter.fill(datas, writeSheet);
		}
		// 如果都是空的 就把它干掉
		if (datas==null && fieldMap==null) {
			excelWriter.fill(new HashMap<>(1), writeSheet);
		}
		//  填充列表数据
		excelWriter.finish();

		// 输出
		return out.toByteArray();
	}
}

