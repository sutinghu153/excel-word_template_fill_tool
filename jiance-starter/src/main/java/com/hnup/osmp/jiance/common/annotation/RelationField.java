package com.hnup.osmp.jiance.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  关联字段
 * @data2021/10/22,16:50
 * @authorsutinghu
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RelationField {
	String tableName()  default "";
	String fieldName()  default "";
}
