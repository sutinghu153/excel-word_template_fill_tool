package com.hnup.osmp.jiance.common.annotation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 *  注解管理类
 * @data2021/10/22,16:57
 * @authorsutinghu
 */
public class RelationManger {

	public static Boolean checkoutRelationTable(Class<?> classe){
		RelationTable relationTable = classe.getAnnotation(RelationTable.class);
		if (relationTable == null) {
			return false;
		}
		return true;
	}

	public static Annotation checkoutRelationField(Class<?> classe){
		RelationField relationField = classe.getAnnotation(RelationField.class);
		if (relationField == null) {
			return null;
		}
		return relationField;
	}

	public static Map<String, String> getFeildValue(Class<?> classe){
		Annotation annotation = checkoutRelationField(classe);
		if (annotation == null){
			return null;
		}
		Map<String, String> map = new HashMap<>();
		RelationField relationField = (RelationField) annotation;
		map.put(relationField.tableName(),relationField.fieldName());
		return map;
	}

}
