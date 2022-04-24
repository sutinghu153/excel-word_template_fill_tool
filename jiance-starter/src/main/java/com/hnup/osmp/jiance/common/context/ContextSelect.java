package com.hnup.osmp.jiance.common.context;

import java.util.List;

/**
 * @data2021/10/21,19:25
 * @authorMSI
 */
public class ContextSelect implements DataContext{

	private List<String> fieldNames;

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}
}
