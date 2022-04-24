package com.hnup.osmp.jiance.common.query.defintion;

import com.hnup.osmp.jiance.common.constant.ConstantQuery;
import com.hnup.osmp.jiance.common.context.ContextFrom;
import com.hnup.osmp.jiance.common.context.DataContext;

/**
 *  from类
 * @data2021/10/22,9:30
 * @authorsutinghu
 */
public class FromQuery extends BuildQuery{

	private ContextFrom contextFrom;

	@Override
	public void BuildSql() {
		if (contextFrom.getTableNames() == null){
			this.resetFlag(false);
			return;
		}

		String sql = ConstantQuery.getFROM() ;
		for (String e:contextFrom.getTableNames()) {
			sql = sql + " " +e + " ";
		}
		this.setSql(sql);
	}

	@Override
	public void reSetContext(DataContext context) {
		if (this.getFlag()){
			contextFrom = (ContextFrom) context;
		}
	}

	@Override
	boolean allowed(DataContext context) {
		this.resetFlag(context instanceof ContextFrom);
		if (!this.getFlag()){
			return this.getFlag();
		}
		this.reSetContext(context);
		this.BuildSql();
		return this.getFlag();
	}
}
