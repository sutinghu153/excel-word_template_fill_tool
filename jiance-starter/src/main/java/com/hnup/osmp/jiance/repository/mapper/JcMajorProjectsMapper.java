package com.hnup.osmp.jiance.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnup.osmp.jiance.model.dto.monitor.OpMonitorDTO;
import com.hnup.osmp.jiance.repository.entity.JcMajorProjects;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface JcMajorProjectsMapper extends BaseMapper<JcMajorProjects> {

	/**
	 * 功能描述: 获取基本数据
	 * @author sutinghu
	 * @date
	 * @param startTime
	 * @param endTime
	 * @param deptName 参数
	 * @return java.util.List<com.hnup.osmp.op.dto.OpMonitorDTO>
	 */
	List<OpMonitorDTO> getDetailList(@Param("startTime") Date startTime,
									 @Param("endTime")  Date endTime,
									 @Param("deptName")  String deptName);


}