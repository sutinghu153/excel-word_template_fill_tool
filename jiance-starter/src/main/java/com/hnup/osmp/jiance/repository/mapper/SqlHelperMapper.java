package com.hnup.osmp.jiance.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnup.osmp.jiance.repository.entity.JcFileBelongType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface SqlHelperMapper extends BaseMapper{

	Map<String,Object> selectMap(@Param("sql") String sql);

	List<Map<String,Object>> selectCustomizeSql(@Param("sql") String sql);

}