package com.hnup.osmp.jiance.common.query.defintion;

import com.hnup.osmp.jiance.common.constant.ConstantQuery;
import com.hnup.osmp.jiance.common.context.ContextSelect;
import com.hnup.osmp.jiance.common.context.DataContext;

/**
 *  select 条件
 * @data2021/10/21,19:15
 * @authorsutinghu
 */
public class SelectQuery extends BuildQuery{

	private ContextSelect contextSelect;

	@Override
	public void BuildSql() {
		if (contextSelect.getFieldNames() == null){
			this.resetFlag(false);
			return;
		}
		String sql = ConstantQuery.getSELECT();
		for (String e:contextSelect.getFieldNames()) {
			sql = sql + " " +e + " , ";
		}
		this.setSql(sql);
	}

	@Override
	public void reSetContext(DataContext context) {
		if (this.getFlag()){
			contextSelect = (ContextSelect) context;
		}
	}

	@Override
	boolean allowed(DataContext context) {
		this.resetFlag(context instanceof ContextSelect);
		if (!this.getFlag()){
			return this.getFlag();
		}
		this.reSetContext(context);
		this.BuildSql();
		return this.getFlag();
	}


}
