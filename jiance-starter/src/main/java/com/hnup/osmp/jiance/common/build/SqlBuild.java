package com.hnup.osmp.jiance.common.build;

import com.hnup.osmp.jiance.common.context.ContextFrom;
import com.hnup.osmp.jiance.common.context.ContextSelect;
import com.hnup.osmp.jiance.common.query.defintion.FromQuery;
import com.hnup.osmp.jiance.common.query.defintion.SelectQuery;
import com.hnup.osmp.jiance.common.resolve.ResolveEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  查询语句创建
 * @data2021/10/22,9:42
 * @authorsutinghu
 */
public class SqlBuild {


	private static ContextFrom contextFrom = new ContextFrom();
	private static ContextSelect contextSelect = new ContextSelect();

	public static List<String> sqlBuilds(Class<?>...classes){
		List<String> sqls = new ArrayList<>();
		List<Map<String, List<String>>> stringListMap = ResolveEntity.resolveIndex(classes);
		stringListMap.forEach(e->{
			e.forEach((k,v)->{
				List<String> list = new ArrayList<>();
				list.add(k);
				contextFrom.setTableNames(list);
				contextSelect.setFieldNames(v);
				String sql = new SelectQuery().getQuery(contextSelect) + new FromQuery().getQuery(contextFrom);
				sqls.add(sql);
			});
		});
		return sqls;
	}

}
