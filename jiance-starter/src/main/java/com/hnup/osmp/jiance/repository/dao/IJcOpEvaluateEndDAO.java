package com.hnup.osmp.jiance.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnup.osmp.jiance.model.dto.*;
import com.hnup.osmp.jiance.repository.entity.JcOpEvaluateEnd;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface IJcOpEvaluateEndDAO extends IService<JcOpEvaluateEnd> {
	/**
	 * 功能描述: 获取表数据
	 * @author sutinghu
	 * @date
	 * @param sql 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	List<Map<String, Object>> getTableDetails(String sql);

	/**
	 * 获取所有办结案卷的满意度评价
	 * @return
	 */
	List<JcOpEvaluateEnd> getJcOpEvaluateEndList();


	/**
	 * 评项案卷数量
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	Integer getEvaluationSum(Date startTime, Date endTime);

	/**
	 * 参与评项数量
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	Integer getEvaluateSum(Date startTime, Date endTime);

	/**
	 * 差评数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	Integer getBadEvaluateSum(Date startTime, Date endTime);

	/**
	 * 好评数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	Integer getGoodEvaluateSum(Date startTime, Date endTime);


	/**
	 * 全局参评项目
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param keyWord 关键字
	 * @param sortField 排序字段
	 * @param sortOrder 排序方式
	 * @return
	 */
	List<MonthlyEvaluationGlobalViewVO> getMonthlyEvaluationGlobalView(Date startTime, Date endTime, String keyWord,String sortField, String sortOrder);

	/**
	 *全局参评个数
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param keyWord 关键字
	 * @param itemType 评价(0=参评，1=好评，2=差评)
	 * @return
	 */
	List<Map<String, Object>> getMonthlyEvaluationItemView(Date startTime, Date endTime, String keyWord, Integer itemType,String sortField, String sortOrder);


	/**
	 *月度处室评价统计
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	List<MonthlyDepartEvaluateItemVO> getDepartEvaluateMonthly(Date startTime, Date endTime);

	/**
	 * 处室月度参评（参评，差评）
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param userName 用户名称
	 * @param keyWord 关键字
	 * @param signUserDepart 用户办理位置：0=审批，1=窗口
	 * @param departCode 部门
	 * @param itemType 评价(0=参评，1=好评，2=差评)
	 * @param sortField 排序字段
	 * @param sortOrder 排序方式
	 * @return
	 */
	List<MonthlyDepartEvaluateItemViewVO> getMonthlyDepartEvaluateItemView(Date startTime,Date endTime,String userName,String keyWord,Integer signUserDepart,Integer departCode,Integer itemType,String sortField, String sortOrder);

	/**
	 *个人办理案卷统计
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param userName 用户名称
	 * @param signUserDepart 用户办理位置：0=审批，1=窗口
	 * @return
	 */
	List<Map<String, Object>> getUserEvaluateStatistic(Date startTime,Date endTime,String userName,Integer signUserDepart);

	/**
	 *  部门统计（办结）》详表
	 * @param evaluateDepartType 评价部门 1.窗口服务 2.行政审批 3.勘测院 4.信息中心
	 * @param satisfactionLevel 满意度 -1.非常不满意 1.不满意 2.一般 3.较满意 4.非常满意
	 * @param keyWord 关键字
	 * @param sortField 排序字段
	 * @param sortOrder 排序方式
	 * @return
	 */
	List<MonitorGridViewVO> getMonitorGridView(Integer evaluateDepartType,Integer satisfactionLevel,String keyWord,String sortField, String sortOrder,String prjname,String keynumgather,Date startTime,Date endTime);

	/**
	 *   已评价案卷（办结）
	 * @param keyWord 关键字
	 * @return
	 */
	List<MonitorItemViewVO> getMonitorItemView(String keyWord);
	/**
	 *   待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷
	 * @param keyWord 关键字
	 * @return
	 */
	List<UnEvaluateOpsVO> getUnEvaluateOps(String keyWord);
	/**
	 *   反馈意见箱（办理中）
	 * @param keyWord 关键字
	 * @return
	 */
	List<MonitorItemWorkingViewVO> getMonitorItemWorkingView(String keyWord);


	/**
	 * 处室参评率统计
	 * @return
	 */
	List<DepartEvaluatePercentVO> getDepartEvaluatePercent();

	/**
	 * 经办人满意度评价（办结）》统计表
	 * @return
	 */
	List<Map<String, Object>> getPersonGoodDegreeEvaluate();

	/**
	 * 经办人满意度评价（办结）》祥表
	 * @param keyWord 关键字
	 * @return
	 */
	List<PersonDetailOpsVO> getPersonDetailOps(String keyWord);
	/**
	 * 导出经办人满意度评价（办结）》祥表
	 * @param keyWord 关键字
	 * @return
	 */
	List<PersonDetailVO> exportPersonDetailOps(String keyWord);

}







