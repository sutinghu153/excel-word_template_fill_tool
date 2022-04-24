package com.hnup.osmp.jiance.repository.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateConfig;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface JcOpEvaluateConfigMapper extends BaseMapper<JcOpEvaluateConfig> {


	Integer getTableCount(@Param("sql") String sql);

	Integer getCertificateCount(@Param("businessKey") Long businessKey,@Param("phase") String phase);

}