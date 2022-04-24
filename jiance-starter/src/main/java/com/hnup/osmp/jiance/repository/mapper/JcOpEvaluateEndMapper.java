package com.hnup.osmp.jiance.repository.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnup.osmp.jiance.model.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateEnd;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Mapper
public interface JcOpEvaluateEndMapper extends BaseMapper<JcOpEvaluateEnd> {

	List<Map<String, Object>> getTableDetails(@Param("sql") String sql);

	List<JcOpEvaluateEnd> getJcOpEvaluateEndList();


	Integer getEvaluationSum(@Param("startTime") Date startTime,@Param("endTime")Date endTime);

	Integer getEvaluateSum(@Param("startTime") Date startTime,@Param("endTime")Date endTime);

	Integer getBadEvaluateSum(@Param("startTime") Date startTime,@Param("endTime")Date endTime);

	Integer getGoodEvaluateSum(@Param("startTime") Date startTime,@Param("endTime")Date endTime);

	List<MonthlyEvaluationGlobalViewVO> getMonthlyEvaluationGlobalView(@Param("startTime") Date startTime,@Param("endTime")Date endTime,@Param("keyWord")String keyWord,@Param("sortField")String sortField,@Param("sortOrder")String sortOrder);

	List<Map<String, Object>> getMonthlyEvaluationItemView(@Param("startTime") Date startTime,@Param("endTime")Date endTime,@Param("keyWord")String keyWord, @Param("itemType")Integer itemType,@Param("sortField")String sortField,@Param("sortOrder")String sortOrder);

	List<MonthlyDepartEvaluateItemVO> getDepartEvaluateMonthly(@Param("startTime") Date startTime, @Param("endTime")Date endTime);

	List<MonthlyDepartEvaluateItemViewVO> getMonthlyDepartEvaluateItemView(@Param("startTime") Date startTime, @Param("endTime")Date endTime,@Param("userName")String userName,@Param("keyWord")String keyWord,@Param("signUserDepart")Integer  signUserDepart,@Param("departCode")Integer  departCode, @Param("itemType")Integer itemType,@Param("sortField")String sortField,@Param("sortOrder")String sortOrder);


	List<Map<String, Object>> getUserEvaluateStatistic(@Param("startTime") Date startTime, @Param("endTime")Date endTime,@Param("userName")String userName,@Param("signUserDepart")Integer  signUserDepart);

	List<MonitorGridViewVO> getMonitorGridView(@Param("evaluateDepartType")Integer evaluateDepartType,@Param("satisfactionLevel")Integer  satisfactionLevel,@Param("keyWord")String keyWord,@Param("sortField")String sortField,@Param("sortOrder")String sortOrder,@Param("prjname")String prjname,@Param("keynumgather")String keynumgather ,@Param("startTime")Date startTime,@Param("endTime")Date endTime);

	List<MonitorItemViewVO> getMonitorItemView(@Param("keyWord")String keyWord);


	List<UnEvaluateOpsVO> getUnEvaluateOps(@Param("keyWord")String keyWord);

	List<MonitorItemWorkingViewVO> getMonitorItemWorkingView(@Param("keyWord")String keyWord);

	List<DepartEvaluatePercentVO> getDepartEvaluatePercent();

	List<Map<String, Object>>  getPersonGoodDegreeEvaluate();

	List<PersonDetailOpsVO> getPersonDetailOps(@Param("keyWord")String keyWord);
	List<PersonDetailVO> exportPersonDetailOps(@Param("keyWord")String keyWord);

}