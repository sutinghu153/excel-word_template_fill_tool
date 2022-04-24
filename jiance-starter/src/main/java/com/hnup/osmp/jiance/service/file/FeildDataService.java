package com.hnup.osmp.jiance.service.file;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.service.sqlByMap.SqlByMapService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.IntFunction;

/**
 *  根据SQL获取对应数据
 * @data2021/11/17,17:09
 * @author sutinghu
 */
@Service
public class FeildDataService {

	@Autowired
	private SqlByMapService sqlByMapService;

	/**
	 * 功能描述:构造模板数据
	 * @author sutinghu
	 * @date
	 * @param sql 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public List<Map<String, Object>> buildTemplateData(String sql,
													   Boolean executeDate,
													   String year,
													   String month,
													   String day){

		if (StringUtils.isBlank(sql)) {
			throw new DeclareException("查询条件不能为空");
		}

		List<Map<String, String>> mapList = FeildSqlMap.curSql(sql,executeDate,year,month,day);

		List<Map<String, Object>> resultData = new ArrayList<>(8);
		mapList.forEach(e->{
			if (Objects.equals(e.get("TYPE"), FeildTypeEnum.ORDINARY.getDesc())) {
				Map<String, Object> map = new HashMap<>(2);
				map.put("TYPE",FeildTypeEnum.ORDINARY.getDesc());
				map.put("VALUE",sqlByMapService.getTemplateOrdinary(e.get("SQL")));
				resultData.add(map);
			}else if (Objects.equals(e.get("TYPE"), FeildTypeEnum.TABLE.getDesc())) {
				Map<String, Object> map = new HashMap<>(3);
				List<Map<String, Object>> list = sqlByMapService.getTemplateList(e.get("SQL"));
				map.put("TYPE",FeildTypeEnum.TABLE.getDesc());
				map.put("VALUE",list);
				map.put("TABLE_NAME",e.get("TABLE_NAME"));
				resultData.add(map);
			}else if (Objects.equals(e.get("TYPE"), FeildTypeEnum.CHART.getDesc())){
				Map<String, Object> map = new HashMap<>(3);
				List<Map<String, Object>> list = sqlByMapService.getTemplateList(e.get("SQL"));
				map.put("TYPE",FeildTypeEnum.CHART.getDesc());
				map.put("VALUE",list);
				map.put("CHART_NAME",e.get("CHART_NAME"));
				resultData.add(map);
			}
		});

		if (executeDate && StringUtils.isNotBlank(year)) {

			Map<String, Object> startTime = new HashMap<>(2);
			Map<String, Object> endTime = new HashMap<>(2);

			if (StringUtils.isNotBlank(year)
				&& StringUtils.isNotBlank(month)
				&& StringUtils.isNotBlank(day)) {
				startTime.put("StartTime",year+"年"+month+"月"+day+"日");
				endTime.put("EndTime",year+"年"+month+"月"+day+"日");
			}else if (StringUtils.isNotBlank(year)
				&& StringUtils.isNotBlank(month)
				&& !StringUtils.isNotBlank(day)) {
				startTime.put("StartTime",year+"年"+month+"月"+1+"日");
				endTime.put("EndTime",year+"年"+month+"月"+31+"日");
			}else if (StringUtils.isNotBlank(year)
				&& StringUtils.isNotBlank(month)
				&& !StringUtils.isNotBlank(day)){
				startTime.put("StartTime",year+"年"+1+"月"+1+"日");
				endTime.put("EndTime",year+"年"+12+"月"+31+"日");
			}

			Map<String, Object> mapStartTime = new HashMap<>(2);
			mapStartTime.put("TYPE",FeildTypeEnum.ORDINARY.getDesc());
			mapStartTime.put("VALUE",startTime);
			resultData.add(mapStartTime);

			Map<String, Object> mapEndTime = new HashMap<>(2);
			mapEndTime.put("TYPE",FeildTypeEnum.ORDINARY.getDesc());
			mapEndTime.put("VALUE",endTime);
			resultData.add(mapEndTime);

		}

		return resultData;
	}

	public List<Map<String, Object>> buildTableBySql(String sql){
		return sqlByMapService.getTemplateList(sql);
	}


}
