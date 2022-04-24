package com.hnup.osmp.jiance.common.context;

import java.util.List;

/**
 * @data2021/10/22,9:32
 * @authorsutinghu
 */
public class ContextFrom implements DataContext{

	private List<String> tableNames;

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}
}
