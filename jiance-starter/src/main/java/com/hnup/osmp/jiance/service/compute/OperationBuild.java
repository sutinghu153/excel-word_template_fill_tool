package com.hnup.osmp.jiance.service.compute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  公式构造服务层
 * @data2021/11/11,11:53
 * @authorsutinghu
 */
public class OperationBuild {

	/**
	 * 功能描述: 根据字段公式 将查询结果替换
	 * @author sutinghu
	 * @date
	 * @param value
	 * @param supOperation 参数
	 * @return java.lang.String
	 */
	public static String getOperation(Map<String, Object> value, String supOperation){
		String operation = supOperation;
		String[] operations = operation.split(",");
		String result = null;
		for (String field : operations) {
			// 如果包括字段值
			if (value.containsKey(field.toLowerCase()) || value.containsKey(field.toUpperCase())){
				// 结果
				String var = String.valueOf(value.get(field));
				result = result + " "  + var  ;
			}else {
				result = result + " "  + field ;
			}
		}
		return result;
	}

	/**
	 * 功能描述: 获取查询语句
	 * @author sutinghu
	 * @date
	 * @return java.lang.String
	 */
	public static String getQuery(String table,String fieldString,String idName){
		Map<String, Object> tableMap = new HashMap<>();
		tableMap.put("tableName","\""+table+"\"");
		String[] fields = fieldString.split(",");
		List<Map<String, String>> fieldList = new ArrayList<>();
		for (String value : fields) {
			Map<String, String> f = new HashMap<>();
			f.put(value,value);
			fieldList.add(f);
		}
		// 设置默认主键
		Map<String, String> id = new HashMap<>();
		id.put(idName,idName);
		fieldList.add(id);
		tableMap.put("fieldMap",fieldList);
		return QueryBuild.buildQuery(tableMap,null);
	}

}
