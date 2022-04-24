package com.hnup.osmp.jiance.service.compute;

import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  构造SQL语句服务层
 * @data2021/11/11,10:53
 * @authorsutinghu
 */
public class QueryBuild {

	public static String buildQuery(Map<String, Object> tableMap, String countTime){
		String from = ConstantJiance.FROM + tableMap.get("tableName");
		List<Map<String, String>> fieldMap = (List<Map<String, String>>) tableMap.get("fieldMap");
		String select = ConstantJiance.SELECT;
		for (Map<String, String> mapValue :fieldMap) {
			for (Map.Entry<String, String> map:mapValue.entrySet()) {
				select = select + " " + map.getKey() +  " ," ;
			}
		}
		select = select.substring(ConstantJiance.ZERO, select.length()-ConstantJiance.ONE);
		String where = ConstantJiance.WHERE + ConstantJiance.COUNT_TIME;
		if (StringUtils.isNotBlank(countTime)) {
			where = where + " '" + countTime + "' ";
			return select + from + where;
		}
		return select + from;
	}

}
