package com.hnup.osmp.jiance.repository.dao.impl;

import com.hnup.osmp.jiance.model.dto.*;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateEnd;
import com.hnup.osmp.jiance.repository.mapper.JcOpEvaluateEndMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hnup.osmp.jiance.repository.dao.IJcOpEvaluateEndDAO;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Service
public class JcOpEvaluateEndServiceDAOImpl extends ServiceImpl<JcOpEvaluateEndMapper, JcOpEvaluateEnd>  implements  IJcOpEvaluateEndDAO {

	@Override
	public List<Map<String, Object>> getTableDetails(String sql) {
		return this.baseMapper.getTableDetails(sql);
	}
	@Override
	public List<JcOpEvaluateEnd> getJcOpEvaluateEndList() {
		return this.baseMapper.getJcOpEvaluateEndList();
	}

	@Override
	public Integer getEvaluationSum(Date startTime, Date endTime) {
		return this.baseMapper.getEvaluationSum(startTime,endTime);
	}

	@Override
	public Integer getEvaluateSum(Date startTime, Date endTime) {
		return this.baseMapper.getEvaluateSum(startTime,endTime);
	}

	@Override
	public Integer getBadEvaluateSum(Date startTime, Date endTime) {
		return this.baseMapper.getBadEvaluateSum(startTime,endTime);
	}
	@Override
	public Integer getGoodEvaluateSum(Date startTime, Date endTime) {
		return this.baseMapper.getGoodEvaluateSum(startTime,endTime);
	}

	@Override
	public List<MonthlyEvaluationGlobalViewVO> getMonthlyEvaluationGlobalView(Date startTime, Date endTime, String keyWord,String sortField, String sortOrder) {
		return this.baseMapper.getMonthlyEvaluationGlobalView(startTime,endTime,keyWord,sortField,sortOrder);
	}

	@Override
	public List<Map<String, Object>> getMonthlyEvaluationItemView(Date startTime, Date endTime, String keyWord,Integer itemType,String sortField, String sortOrder) {
		return this.baseMapper.getMonthlyEvaluationItemView(startTime,endTime,keyWord,itemType,sortField,sortOrder);
	}

	@Override
	public List<MonthlyDepartEvaluateItemVO> getDepartEvaluateMonthly(Date startTime, Date endTime) {
		return this.baseMapper.getDepartEvaluateMonthly(startTime,endTime);
	}

	@Override
	public List<MonthlyDepartEvaluateItemViewVO> getMonthlyDepartEvaluateItemView(Date startTime,Date endTime,String userName,String keyWord,Integer signUserDepart,Integer  departCode,Integer itemType,String sortField, String sortOrder) {
		return this.baseMapper.getMonthlyDepartEvaluateItemView(startTime,endTime,userName,keyWord,signUserDepart,departCode,itemType,sortField,sortOrder);
	}


	@Override
	public List<Map<String, Object>> getUserEvaluateStatistic(Date startTime,Date endTime,String userName,Integer signUserDepart)
	{
		return this.baseMapper.getUserEvaluateStatistic(startTime,endTime,userName,signUserDepart);
	}
	@Override
	public List<MonitorGridViewVO> getMonitorGridView(Integer evaluateDepartType,Integer satisfactionLevel,String keyWord,String sortField, String sortOrder,String prjname,String keynumgather,Date startTime,Date endTime)
	{
		return this.baseMapper.getMonitorGridView(evaluateDepartType,satisfactionLevel,keyWord,sortField,sortOrder, prjname, keynumgather, startTime, endTime);
	}
	@Override
	public List<MonitorItemViewVO> getMonitorItemView(String keyWord)
	{
		return this.baseMapper.getMonitorItemView(keyWord);
	}

	@Override
	public List<UnEvaluateOpsVO> getUnEvaluateOps(String keyWord)
	{
		return this.baseMapper.getUnEvaluateOps(keyWord);
	}
	@Override
	public List<MonitorItemWorkingViewVO> getMonitorItemWorkingView(String keyWord)
	{
		return this.baseMapper.getMonitorItemWorkingView(keyWord);
	}
	@Override
	public List<DepartEvaluatePercentVO> getDepartEvaluatePercent()
	{
		return this.baseMapper.getDepartEvaluatePercent();
	}

	@Override
	public List<Map<String, Object>> getPersonGoodDegreeEvaluate()
	{
		return this.baseMapper.getPersonGoodDegreeEvaluate();
	}
	@Override
	public List<PersonDetailOpsVO> getPersonDetailOps(String keyWord)
	{
		return this.baseMapper.getPersonDetailOps(keyWord);
	}
	@Override
	public List<PersonDetailVO> exportPersonDetailOps(String keyWord)
	{
		return this.baseMapper.exportPersonDetailOps(keyWord);
	}


}