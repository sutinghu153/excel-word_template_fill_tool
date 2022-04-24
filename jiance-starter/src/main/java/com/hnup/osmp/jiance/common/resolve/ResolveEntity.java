package com.hnup.osmp.jiance.common.resolve;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  表、字段解析类
 * @data2021/10/22,8:40
 * @authorsutinghu
 */
public class ResolveEntity {

	public static List<Map<String, List<String>>> resolveIndex(Class<?>...classes){
		List<Map<String, List<String>>> value ;
		int length = classes.length;
		value = new ArrayList<>(length);
		for (int i = 0;i<length;i++) {
			Class c = classes[i];
			value.add(resolveTable(c));
		}
		return value;
	}

	public static Map<String, List<String>> resolveTable(Class<?> c){

		Map<String, List<String>> stringListMap = new HashMap<>();
		TableName tableNameAnnotation = c.getAnnotation(TableName.class);
		if (tableNameAnnotation != null){
			String tableName = tableNameAnnotation.value();
			stringListMap.put(tableName,resolveFields(c));
		}
		return stringListMap;

	}

	public static List<String> resolveFields(Class<?> c){
		Field[] fields = c.getDeclaredFields();
		List<String> fieldNames = new ArrayList<>();
		int length = fields.length;
		for (int i = 0;i<length;i++) {
			Field field = fields[i];
			fieldNames.add(getFeild(field));
		}
		return fieldNames;
	}

	public static String getFeild(Field field){
		String fieldName = null;
		TableField tableField = field.getAnnotation(TableField.class);
		if (tableField != null){
			fieldName = tableField.value();
		}else {
			TableId tableField1 = field.getAnnotation(TableId.class);
			if (tableField1 != null){
				fieldName = tableField1.value();
			}
		}
		return fieldName;
	}
}
