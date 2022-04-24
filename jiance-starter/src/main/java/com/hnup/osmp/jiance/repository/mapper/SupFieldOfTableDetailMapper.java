package com.hnup.osmp.jiance.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnup.osmp.jiance.repository.entity.JcFieldOfTableDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface SupFieldOfTableDetailMapper extends BaseMapper<JcFieldOfTableDetails> {

	List<Map<String, Object>> getTableDetails(@Param("sql") String sql);

	/**
	 * 功能描述: 通过map批量插入
	 * @author sutinghu
	 * @date
	 * @param tableName 表名
	 * @param columnMap 字段
	 * @param mapList 批量值
	 * @return void
	 */
	void insertByMap(@Param("tableName")String tableName,
					 @Param("columnMap")Map<String, Object> columnMap,
					 @Param("mapList") List<Map<String, Object>> mapList);

	void updateByMap(@Param("tableName")String tableName,
					 @Param("id")String id,
					 @Param("resultName")String resultName,
					 @Param("mapList") List<Map<Object, Object>> mapList);

	Map<String, Object> getTemplateOrdinary(@Param("sql") String sql);

	List<Map<String, Object>> getTemplateList(@Param("sql") String sql);

}