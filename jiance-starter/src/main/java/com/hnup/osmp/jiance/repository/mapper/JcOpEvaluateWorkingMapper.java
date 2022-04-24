package com.hnup.osmp.jiance.repository.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateWorking;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface JcOpEvaluateWorkingMapper extends BaseMapper<JcOpEvaluateWorking> {


	List<Map<String, Object>> getTableDetails(@Param("sql") String sql);
}