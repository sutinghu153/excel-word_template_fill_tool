package com.hnup.osmp.jiance.service.constant;

/**
 *  常量类
 *  我把所有可能涉及到的常量都统一使用唯一类管理，一是提高某些代码的复用率，二是提高代码的规范，三是适应业务需要
 *  本类中涉及的大部分常量，都与数据库字典表中一一对应，如果后续有业务拓展需求，需要同时在数据库和本常量类中同步
 *  更新拓展。建议阅读本项目<sql-script>文件夹下<readme>文件。
 * @data2021/10/25,13:29
 * @authorsutinghu
 */
public final class ConstantJiance {

	// SQL
	public static final String SELECT = " SELECT " ;
	public static final String FROM = " FROM " ;
	public static final String WHERE = " WHERE " ;
	public static final String NULL = "null" ;

	// 符号
	public static final String CUR_SIGN = "#" ;
	public static final String CUR_LEFT = "\\{" ;
	public static final String CUR_RIGHT = "\\}" ;
	public static final String CHECK_CUR_LEFT = "{" ;
	public static final String CHECK_CUR_RIGHT = "}" ;

	public static final String CUR_TABLE_LEFT = "\\[" ;
	public static final String CUR_TABLE_RIGHT = "\\]" ;
	public static final String CHECK_TABLE_CUR_LEFT = "[" ;
	public static final String CHECK_TABLE_CUR_RIGHT = "]" ;

	// 字段
	public static final String COUNT_TIME = " count_time = ";
	public static final String ID = "主键" ;
	public static final String RANKING = "ranking" ;

	public static final String EXPORT = "导出" ;
	public static final String IMPORT = "导入" ;
	public static final String IMPORT_TEMPLATE = "导出模板" ;

	// 常用字符
	public static final Integer ZERO = 0;
	public static final Integer ONE = 1;
	public static final Integer TZ_CAIGOU = 3120;

	public static final String CHANGSHA_CODE = "430100";
	public static final String GD_CODE = "耕地";
	public static final String SUM = "sum";
}
