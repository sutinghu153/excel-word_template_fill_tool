package com.hnup.osmp.jiance.common.query.defintion;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.common.context.DataContext;
import com.hnup.osmp.jiance.common.query.QueryAwire;
import org.apache.poi.ss.usermodel.DataFormat;

/**
 *  查询条件构造顶层抽象类
 * @data2021/10/21,19:03
 * @authorsutinghu
 */
public abstract class BuildQuery implements QueryAwire {

	/**
	 *  判断器
	 */
	private boolean flag;


	private String sql;


	abstract public void BuildSql();

	abstract public void reSetContext(DataContext context);

	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 *  鉴权器
	 * @param context
	 * @return
	 */
	abstract boolean allowed(DataContext context);

	public String getQuery(DataContext context){
		this.allowed(context);
		if (this.flag){
			return sql;
		}
		return " ";
	}

	/**
	 *  重置判断器
	 * @param flag
	 */
	public void resetFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 *  拿到判断器
	 * @return
	 */
	public boolean getFlag(){
		return flag;
	}

}
