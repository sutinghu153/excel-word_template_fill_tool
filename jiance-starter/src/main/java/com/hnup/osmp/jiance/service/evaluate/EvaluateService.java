package com.hnup.osmp.jiance.service.evaluate;


import com.alibaba.fastjson.JSONObject;
import com.hnup.common.export.starter.service.ExportExcelService;
import com.hnup.common.export.starter.vo.ExcelExportVO;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.common.lang.util.date.ChinaDateFormatHelper;
import com.hnup.osmp.jiance.enums.EvaluateDepartTypeEnum;

import com.hnup.osmp.jiance.enums.EvaluateSatisfactionEnum;
import com.hnup.osmp.jiance.model.dto.*;

import com.hnup.osmp.jiance.operation.computes.ComputeBase;
import com.hnup.osmp.jiance.repository.dao.*;

import com.hnup.osmp.jiance.service.compute.DateUtils;
import com.hnup.osmp.jiance.utils.JiancePageUtils;
import com.hnup.osmp.shiju.op.model.dto.OpSubmitParamDto;
import io.swagger.annotations.ApiModelProperty;
import net.minidev.json.JSONArray;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import com.hnup.osmp.shiju.op.client.statical.*;
import org.springframework.util.Base64Utils;
import com.hnup.osmp.shiju.op.model.constant.enums.*;
import org.springframework.util.Assert;
import com.hnup.osmp.jiance.repository.entity.*;


/**
 *  大监管-满意度评价统计-评价控制 服务层
 * @author zouronghua
 * @date 2021/11/20
 */
@Service
public class EvaluateService {
	@Autowired
	private IJcOpEvaluateConfigDAO iJcOpEvaluateConfigDAO;

	@Autowired
	private IJcOpEvaluateEndDAO iJcOpEvaluateEndDAO;

	@Autowired
	private IJcOpEvaluateWorkingDAO iJcOpEvaluateWorkingDAO;

	@Autowired
	private OpHandleFeignClient opHandleFeignClient;

	@Autowired
	private OpHistoryFeignClient opHistoryFeignClient;
	@Autowired
	private ExportExcelService exportExcelService;

	/**
	 * 判断评价是在发证前还是在发证后
	 * @param keyDigNumGather
	 * @return
	 */
	public Integer getOpState(String keyDigNumGather){
	    Assert.notNull(keyDigNumGather, "案卷编号不能为空");
		keyDigNumGather= new String(Base64Utils.decodeFromString(keyDigNumGather),StandardCharsets.UTF_8);
		Long businessKey= Long.parseLong(keyDigNumGather);
		OpSubmitParamDto opParams = opHandleFeignClient.getOpFlowData(businessKey);
		if (opParams == null) {
			throw new DeclareException("未找到对应编号的案卷：" + businessKey);
		}
		if (opParams.getStateEnum() == OpStateEnum.BANJIE.getIntValue() || opParams.getStateEnum() == BusinessStateEnum.old.getIntValue())
		{
			return 2;
		}
		JcOpEvaluateConfig evaluateConfig = iJcOpEvaluateConfigDAO.getById(opParams.getKeyTypeCode());
		if (evaluateConfig == null) {
			throw new DeclareException("未找到对应满意度评价配置的业务号：" + opParams.getKeyTypeCode());
		}

		String phase = evaluateConfig.getOutLicensePhase();
		Integer countPhases=iJcOpEvaluateConfigDAO.getCertificateCount(businessKey,phase);
		//发证后
		if (countPhases > 0)
		{
			return 2;
		}
		//发证前
		return 1;
	}

	/**
	 * 提交评价信息
	 * @param evaluateInfoDTO
	 */
	public void saveEvaluate(EvaluateInfoDTO evaluateInfoDTO){
		String keyDigNumGather= new String(Base64Utils.decodeFromString(evaluateInfoDTO.getKeyDigNumGather()),StandardCharsets.UTF_8);
		Long businessKey= Long.parseLong(keyDigNumGather);
		OpSubmitParamDto opParams = opHandleFeignClient.getOpFlowData(businessKey);
		if (opParams == null) {
			throw new DeclareException("未找到对应编号的案卷：" + businessKey);
		}
		if(evaluateInfoDTO.getOpState()==1){
			saveEvaluateWorking(evaluateInfoDTO,businessKey,opParams.getKeyTypeCode());
		}else{
			saveEvaluateEnd(evaluateInfoDTO,businessKey,opParams.getKeyTypeCode());
		}
	}

	/**
	 * 发证前评价
	 * @param evaluateInfoDTO
	 */
	private void saveEvaluateWorking(EvaluateInfoDTO evaluateInfoDTO,Long businessKey,Integer keyTypeCode){
		JcOpEvaluateWorking jcOpEvaluateWorking= iJcOpEvaluateWorkingDAO.getById(businessKey);
		if(jcOpEvaluateWorking!=null){
			throw new DeclareException("该事项无法重复评价");
		}
		jcOpEvaluateWorking=new JcOpEvaluateWorking();
		jcOpEvaluateWorking.setKeyDigNumGather(businessKey);
		jcOpEvaluateWorking.setKeyTypeCode(keyTypeCode);
		jcOpEvaluateWorking.setWindowService(evaluateInfoDTO.getWindowService());
		jcOpEvaluateWorking.setWindowServiceNote(evaluateInfoDTO.getWindowServiceNote());
		jcOpEvaluateWorking.setOpService(evaluateInfoDTO.getOpService());
		jcOpEvaluateWorking.setOpServiceNote(evaluateInfoDTO.getOpServiceNote());
		jcOpEvaluateWorking.setKance(evaluateInfoDTO.getKanCe());
		jcOpEvaluateWorking.setKanceNote(evaluateInfoDTO.getKanCeNote());
		jcOpEvaluateWorking.setInfoCenter(evaluateInfoDTO.getInfoCenter());
		jcOpEvaluateWorking.setInfoCenterNote(evaluateInfoDTO.getInfoCenterNote());
		jcOpEvaluateWorking.setOpinion(evaluateInfoDTO.getOpinion());

		jcOpEvaluateWorking.setEvaluateTime(new Date());
		iJcOpEvaluateWorkingDAO.save(jcOpEvaluateWorking);

	}

	/**
	 * 发证后评价
	 * @param evaluateInfoDTO
	 */
	private  void  saveEvaluateEnd(EvaluateInfoDTO evaluateInfoDTO,Long businessKey,Integer keyTypeCode){

		JcOpEvaluateEnd jcOpEvaluateEnd=  iJcOpEvaluateEndDAO.getById(businessKey);
		if(jcOpEvaluateEnd!=null){
			throw new DeclareException("该事项无法重复评价");
		}
		jcOpEvaluateEnd=new JcOpEvaluateEnd();
		jcOpEvaluateEnd.setKeyDigNumGather(businessKey);
		jcOpEvaluateEnd.setKeyTypeCode(keyTypeCode);
		jcOpEvaluateEnd.setWindowService(evaluateInfoDTO.getWindowService());
		jcOpEvaluateEnd.setWindowServiceNote(evaluateInfoDTO.getWindowServiceNote());
		jcOpEvaluateEnd.setOpService(evaluateInfoDTO.getOpService());
		jcOpEvaluateEnd.setOpServiceNote(evaluateInfoDTO.getOpServiceNote());
		jcOpEvaluateEnd.setKance(evaluateInfoDTO.getKanCe());
		jcOpEvaluateEnd.setKanceNote(evaluateInfoDTO.getKanCeNote());
		jcOpEvaluateEnd.setInfoCenter(evaluateInfoDTO.getInfoCenter());
		jcOpEvaluateEnd.setInfoCenterNote(evaluateInfoDTO.getInfoCenterNote());
		jcOpEvaluateEnd.setOpinion(evaluateInfoDTO.getOpinion());
		jcOpEvaluateEnd.setEvaluateTime(new Date());
		iJcOpEvaluateEndDAO.save(jcOpEvaluateEnd);
	}


	/**
	 * 月度评价统计
	 * @param monthTime 时间
	 * @return
	 */
	public EvaluateMonthlyVO getEvaluateMonthly(Date monthTime){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);

		//案卷评价数
		Integer evaluationSum=iJcOpEvaluateEndDAO.getEvaluationSum(startTime,endTime);
		//参与评价数
		Integer evaluateSum=iJcOpEvaluateEndDAO.getEvaluateSum(startTime,endTime);
		//差评数
		Integer badEvaluateSum=iJcOpEvaluateEndDAO.getBadEvaluateSum(startTime,endTime);
		//好评数
		Integer goodEvaluateSum=iJcOpEvaluateEndDAO.getGoodEvaluateSum(startTime,endTime);

		BigDecimal evaluateRate = new BigDecimal(0);
		BigDecimal badEvaluateRate = new BigDecimal(0);
		BigDecimal goodEvaluateRate = new BigDecimal(0);
		if(evaluationSum>0){
			evaluateRate = ComputeBase.percentageCompute(evaluateSum,evaluationSum);
		}
		if(evaluateSum>0){
			badEvaluateRate =ComputeBase.percentageCompute(badEvaluateSum,evaluateSum);
			goodEvaluateRate =ComputeBase.percentageCompute(goodEvaluateSum,evaluateSum);
		}

		EvaluateMonthlyVO  evaluateMonthlyVO=new EvaluateMonthlyVO();
		evaluateMonthlyVO.setEvaluateTotal(evaluationSum);
		evaluateMonthlyVO.setEvaluateSum(evaluateSum);
		evaluateMonthlyVO.setEvaluateRate(evaluateRate);
		evaluateMonthlyVO.setBadEvaluateSum(badEvaluateSum);
		evaluateMonthlyVO.setBadEvaluateRate(badEvaluateRate);
		evaluateMonthlyVO.setGoodEvaluateSum(goodEvaluateSum);
		evaluateMonthlyVO.setGoodEvaluateRate(goodEvaluateRate);
		return evaluateMonthlyVO;
	}

	/**
	 *全局参评项目总数
	 */
	public 	Map<String, Object> getMonthlyEvaluationGlobalView(PageParamDTO pageParamDTO,Date monthTime,String keyWord){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);
		String sortField=pageParamDTO.getSortField();
		String sortOrder=pageParamDTO.getSortOrder();
		List<MonthlyEvaluationGlobalViewVO> list= iJcOpEvaluateEndDAO.getMonthlyEvaluationGlobalView(startTime,endTime,keyWord,sortField,sortOrder);
		Integer count=list.size();
		List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("count",count);
		dictionary.put("pageList",pageList);
		return  dictionary;

	}

	/**
	 *全局参评个数
	 */
	public Map<String, Object> getMonthlyEvaluationItemView(PageParamDTO pageParamDTO,Date monthTime,String keyWord,Integer itemType){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);
		String sortField=pageParamDTO.getSortField();
		String sortOrder=pageParamDTO.getSortOrder();
		List<Map<String, Object>> list= iJcOpEvaluateEndDAO.getMonthlyEvaluationItemView(startTime,endTime,keyWord,itemType,sortField,sortOrder);
		Integer count=list.size();
		List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("count",count);
		dictionary.put("pageList",pageList);
		return  dictionary;
	}


	/**
	 * 月度处室参评统计
	 * @param monthTime 时间
	 * @return
	 */
	public DepartEvaluateMonthlyVO getDepartEvaluateMonthly(Date monthTime){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);
		List<MonthlyDepartEvaluateItemVO> formDataList= iJcOpEvaluateEndDAO.getDepartEvaluateMonthly(startTime,endTime);
		//总数
		Integer evaluateSum = 0;
		//参评数
		Integer sumEvaluteCount = 0;
		Integer sumBadEvaluteCount = 0;
		//差评率
		//double PercentRate = 0.0;
		//最小差评率
		BigDecimal minPercentRate =new BigDecimal(0);
		//最大差评率
		BigDecimal maxPercentRate = new BigDecimal(0);
        List<BigDecimal> rates=new  ArrayList<BigDecimal>();
		if (CollectionUtils.isNotEmpty(formDataList)) {
			for (MonthlyDepartEvaluateItemVO form : formDataList) {
				evaluateSum+=form.getEndOpCount();
				sumEvaluteCount+=form.getSumEvaluteCount();
				sumBadEvaluteCount+=form.getSumBadEvaluteCount();
				//当前差评率
				BigDecimal curRate = form.getSumEvaluteCount()==0?new BigDecimal("0.00"):ComputeBase.percentageCompute(form.getSumBadEvaluteCount(),form.getSumEvaluteCount());
				if (curRate.compareTo(BigDecimal.ZERO) > 0) {
					rates.add(curRate);
				}
			}
		}
		if(rates.size()>0){
			minPercentRate=rates.stream().min((x1, x2) -> x1.compareTo(x2)).get();
			maxPercentRate=rates.stream().max((x1, x2) -> x1.compareTo(x2)).get();
		}
		//评价率
		String badEvaluateRate=minPercentRate.toString();
		if(minPercentRate.compareTo(maxPercentRate)!=0){
			badEvaluateRate=minPercentRate+" - "+maxPercentRate;
		}
		DepartEvaluateMonthlyVO departEvaluateMonthlyVO=new DepartEvaluateMonthlyVO();
		departEvaluateMonthlyVO.setDepartSum(evaluateSum);
		departEvaluateMonthlyVO.setEvaluateSum(sumEvaluteCount);
		departEvaluateMonthlyVO.setBadEvaluateSum(sumBadEvaluteCount);
		departEvaluateMonthlyVO.setBadEvaluateRate(badEvaluateRate);
        return departEvaluateMonthlyVO;
	}

	/**
	 *处室月度统计
	 * @param pageParamDTO
	 * @param monthTime
	 * @return
	 */
	public  MonthlyDepartEvaluateVO  getMonthlyDepartEvaluateView(PageParamDTO pageParamDTO,Date monthTime){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);
		List<MonthlyDepartEvaluateItemVO> formDataList= iJcOpEvaluateEndDAO.getDepartEvaluateMonthly(startTime,endTime);
		int windowSumEvaluteCount=0;
		int windowSumBadEvaluteCount=0;
		BigDecimal windowPercentRate = new BigDecimal(0.0);
		if(formDataList.size()>0) {
			 windowSumEvaluteCount = formDataList.stream().map(e -> e.getSumEvaluteCount()).reduce(Integer::sum).get();
			 windowSumBadEvaluteCount = formDataList.stream().map(e -> e.getSumBadEvaluteCount()).reduce(Integer::sum).get();

			if (windowSumEvaluteCount > 0) {
				windowPercentRate = ComputeBase.percentageCompute(windowSumBadEvaluteCount, windowSumEvaluteCount);
			}
		}

		MonthlyDepartEvaluateVO monthlyDepartEvaluateVO=new MonthlyDepartEvaluateVO();
		monthlyDepartEvaluateVO.setEvaluateSum(windowSumEvaluteCount);
		monthlyDepartEvaluateVO.setBadEvaluateSum(windowSumBadEvaluteCount);
		monthlyDepartEvaluateVO.setBadEvaluateRate(windowPercentRate);
		monthlyDepartEvaluateVO.setTotal(formDataList.size());

		List pageList= JiancePageUtils.startPage(formDataList,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		//分页
		monthlyDepartEvaluateVO.setData(pageList);

		return monthlyDepartEvaluateVO;
	}


	/**
	 *处室月度参评（参评，差评）
	 * @param pageParamDTO
	 * @param monthTime 月度
	 * @param keyWork 关键字
	 * @param departCode 处室
	 * @param itemType 参评类型（0=参评，2=差评）
	 */
	public Map<String, Object> getMonthlyDepartEvaluateItemView(PageParamDTO pageParamDTO,Date monthTime,String keyWork,Integer departCode,Integer itemType){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);
		String sortField=pageParamDTO.getSortField();
		String sortOrder=pageParamDTO.getSortOrder();
		List<MonthlyDepartEvaluateItemViewVO> formDataList= iJcOpEvaluateEndDAO.getMonthlyDepartEvaluateItemView(startTime,endTime,null,keyWork,-1,departCode,itemType,sortField,sortOrder);
		Integer count=formDataList.size();
		List pageList= JiancePageUtils.startPage(formDataList,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("count",count);
		dictionary.put("pageList",pageList);
		return dictionary;
	}
	/**
	 *个人统计月度参评（参评，差评）
	 * @param pageParamDTO
	 * @param monthTime 月度
	 * @param userName 用户
	 * @param signUserDepart 用户办理位置：0=审批，1=窗口
	 * @param itemType 参评类型（0=参评，2=差评）
	 */
	public MonthlyEvaluateByUserVO getMonthlyEvaluateByUserView(PageParamDTO pageParamDTO,Date monthTime,String userName,Integer signUserDepart,Integer itemType){
		Date startTime= DateUtils.firstDay(monthTime,DateUtils.MONTH);
		Date endTime=DateUtils.getMonthLastDay(monthTime);
		String sortField=pageParamDTO.getSortField();
		String sortOrder=pageParamDTO.getSortOrder();
		List<MonthlyDepartEvaluateItemViewVO> formDataList= iJcOpEvaluateEndDAO.getMonthlyDepartEvaluateItemView(startTime,endTime,userName,null,signUserDepart,0,itemType,sortField,sortOrder);
		Integer total=formDataList.size();
		//个人办理案卷统计
		List<Map<String, Object>> list= iJcOpEvaluateEndDAO.getUserEvaluateStatistic(startTime,endTime,userName,signUserDepart);
		Integer endOpCount=0;
		Integer isEvaluate=0;
		Integer badEvaluate=0;
		BigDecimal percentRate=new BigDecimal(0);
		for (Map<String, Object> form : list) {
			endOpCount=Integer.parseInt(form.get("endopcount").toString());
			isEvaluate=Integer.parseInt(form.get("isevaluate").toString());
			badEvaluate=Integer.parseInt(form.get("badevaluate").toString());
		}
		if(endOpCount>0) {
			percentRate = ComputeBase.percentageCompute(badEvaluate, endOpCount);
		}

		MonthlyEvaluateByUserVO monthlyEvaluateByUser=new MonthlyEvaluateByUserVO();
		monthlyEvaluateByUser.setEvaluateSum(isEvaluate);
		monthlyEvaluateByUser.setBadEvaluateSum(badEvaluate);
		monthlyEvaluateByUser.setBadEvaluateRate(percentRate);
		monthlyEvaluateByUser.setTotal(total);

		List pageList= JiancePageUtils.startPage(formDataList,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		monthlyEvaluateByUser.setData(pageList);
		return monthlyEvaluateByUser;
	}
	/**
	 * 部门统计总表（办结）
	 * @return
	 */
	public List<SfactionDegreeVO> getPieChartData(){

		List<SfactionDegreeVO> chartItems =new ArrayList<SfactionDegreeVO>();


		List<JcOpEvaluateEnd> evaluateEndList = iJcOpEvaluateEndDAO.getJcOpEvaluateEndList();
		Integer goodDegreeSum = evaluateEndList.size();
		//窗口服务
		Map<Integer, List<JcOpEvaluateEnd>> windowList=evaluateEndList.stream().filter(c->c.getWindowService()!=null).collect(Collectors.groupingBy(JcOpEvaluateEnd::getWindowService));
		chartItems.addAll(getPieChart(EvaluateDepartTypeEnum.WINDOWSERVICE,goodDegreeSum,windowList));
		//行政审批
		Map<Integer, List<JcOpEvaluateEnd>> opServiceList=evaluateEndList.stream().filter(c->c.getOpService()!=null).collect(Collectors.groupingBy(JcOpEvaluateEnd::getOpService));
		chartItems.addAll(getPieChart(EvaluateDepartTypeEnum.OPSERVICE,goodDegreeSum,opServiceList));
		//勘测院
		Map<Integer, List<JcOpEvaluateEnd>> kanceList=evaluateEndList.stream().filter(c->c.getKance()!=null).collect(Collectors.groupingBy(JcOpEvaluateEnd::getKance));
		chartItems.addAll(getPieChart(EvaluateDepartTypeEnum.KANCE,goodDegreeSum,kanceList));
		//信息中心
		Map<Integer, List<JcOpEvaluateEnd>> infoCenterList=evaluateEndList.stream().filter(c->c.getInfoCenter()!=null).collect(Collectors.groupingBy(JcOpEvaluateEnd::getInfoCenter));
		chartItems.addAll(getPieChart(EvaluateDepartTypeEnum.INFO_CENTER,goodDegreeSum,infoCenterList));

		return chartItems;
	}

	/**
	 * 部门统计总表（办结） 统计计算
	 * @param evaluateDepartTypeEnum
	 * @param goodDegreeSum
	 * @param mapList
	 * @return
	 */
	private List<SfactionDegreeVO> getPieChart(EvaluateDepartTypeEnum evaluateDepartTypeEnum,Integer goodDegreeSum,Map<Integer, List<JcOpEvaluateEnd>> mapList){
		Integer goodDegree4=getEvaluatesatiSfactioCount(EvaluateSatisfactionEnum.GREATSATISFACTION,mapList);
		//基本满意
		Integer goodDegree3=getEvaluatesatiSfactioCount(EvaluateSatisfactionEnum.SATISFACTORY,mapList);
		//一般
		Integer goodDegree2=getEvaluatesatiSfactioCount(EvaluateSatisfactionEnum.GENERAL,mapList);
		//不满意
		Integer goodDegree1=getEvaluatesatiSfactioCount(EvaluateSatisfactionEnum.DISSATISFIED,mapList);
		//非常不满意
		Integer goodDegree0=getEvaluatesatiSfactioCount(EvaluateSatisfactionEnum.VERYDISSATISFIED,mapList);

		String percent4 = "非常满意:0%";
		String percent3 = "满意:0%";
		String percent2 = "基本满意:0%";
		String percent1 = "不满意:0%";
		String percent0 = "非常不满意:0%";
		if (goodDegreeSum > 0)
		{
			percent4 = "非常满意:"+ComputeBase.percentageCompute(goodDegree4,goodDegreeSum)+"%";
			percent3 = "满意:"+ComputeBase.percentageCompute(goodDegree3,goodDegreeSum)+"%";
			percent2 = "基本满意:"+ComputeBase.percentageCompute(goodDegree2,goodDegreeSum)+"%";
			percent1 = "不满意:"+ComputeBase.percentageCompute(goodDegree1,goodDegreeSum)+"%";
			percent0 = "非常不满意:"+ComputeBase.percentageCompute(goodDegree0,goodDegreeSum)+"%";
		}
		String departName=EvaluateDepartTypeEnum.getDesc(evaluateDepartTypeEnum.getIntValue());
		List<SfactionDegreeVO> items = new ArrayList<SfactionDegreeVO>();
		items.add(new SfactionDegreeVO(departName,percent4,goodDegree4));
		items.add(new SfactionDegreeVO(departName,percent3,goodDegree3) );
		items.add(new SfactionDegreeVO(departName,percent2,goodDegree2) );
		items.add(new SfactionDegreeVO(departName,percent1,goodDegree1) );
		items.add(new SfactionDegreeVO(departName,percent0,goodDegree0) );
		return items;
	}

	/**
	 * 获取满意度数量
	 * @param evaluatesatiSfactionEnum 评价满意度枚举
	 * @param mapList
	 * @return
	 */
	private Integer getEvaluatesatiSfactioCount(EvaluateSatisfactionEnum evaluatesatiSfactionEnum,Map<Integer, List<JcOpEvaluateEnd>> mapList){
		Integer goodDegree=0;
		List<JcOpEvaluateEnd> greatSatisfactionList=mapList.get(evaluatesatiSfactionEnum.getIntValue());
		if(greatSatisfactionList!=null){
			goodDegree=greatSatisfactionList.size();
		}
		return goodDegree;
	}

	/**
	 * 部门统计（办结）》详表
	 * @param evaluateDepartType 评价部门 1.窗口服务 2.行政审批 3.勘测院 4.信息中心
	 * @param satisfactionLevel 满意度 -1.非常不满意 1.不满意 2.一般 3.较满意 4.非常满意
	 * @param keyWord 关键字
	 * @return
	 */
   public  Map<String, Object> getMonitorGridView(PageParamDTO pageParamDTO,Integer evaluateDepartType, Integer satisfactionLevel,String keyWord,String prjname,String keynumgather,Date startTime,Date endTime){
	   String sortField=pageParamDTO.getSortField();
	   String sortOrder=pageParamDTO.getSortOrder();
	     List<MonitorGridViewVO> list= iJcOpEvaluateEndDAO.getMonitorGridView(evaluateDepartType,satisfactionLevel,keyWord,sortField,sortOrder, prjname, keynumgather, startTime, endTime);
	   Integer count=list.size();
	   List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
	   Map<String, Object> dictionary = new HashMap<String, Object>();
	   dictionary.put("count",count);
	   dictionary.put("pageList",pageList);
		return  dictionary;
   }

	/**
	 * 已评价案卷（办结）》清单
	 * @param keyWord 关键字
	 * @return
	 */
   public Map<String, Object> getMonitorItemView(PageParamDTO pageParamDTO,String keyWord){
	   List<MonitorItemViewVO> list= iJcOpEvaluateEndDAO.getMonitorItemView(keyWord);
	   Integer count=list.size();
	   List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
	   Map<String, Object> dictionary = new HashMap<String, Object>();
	   dictionary.put("count",count);
	   dictionary.put("pageList",pageList);
	   return  dictionary;
   }

	/**
	 *  待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷
	 * @param keyWord 关键字
	 * @return
	 */
	public Map<String, Object> getUnEvaluateOps(PageParamDTO pageParamDTO,String keyWord){
		List<UnEvaluateOpsVO> list= iJcOpEvaluateEndDAO.getUnEvaluateOps(keyWord);
		Integer count=list.size();
		List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("count",count);
		dictionary.put("pageList",pageList);
		return  dictionary;
	}

	/**
	 *  反馈意见箱（办理中）
	 * @param keyWord 关键字
	 * @return
	 */
	public Map<String, Object>  getMonitorItemWorkingView(PageParamDTO pageParamDTO,String keyWord){
		List<MonitorItemWorkingViewVO> list= iJcOpEvaluateEndDAO.getMonitorItemWorkingView(keyWord);
		Integer count=list.size();
		List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("count",count);
		dictionary.put("pageList",pageList);
		return  dictionary;
	}

	/**
	 *  处室参评率统计（办结)
	 * @return
	 */
	public List<DepartEvaluatePercentVO> getDepartEvaluatePercent(){
		List<DepartEvaluatePercentVO> list= iJcOpEvaluateEndDAO.getDepartEvaluatePercent();
		for (DepartEvaluatePercentVO form : list) {
			if(form.getEndopcount()>0){
				BigDecimal evalutePercent = ComputeBase.percentageCompute(form.getSumevalutecount(), form.getEndopcount());
				form.setEvalutepercent(evalutePercent);
			}
		}
        return list;
	}

	/**
	 * 经办人满意度评价（办结）》统计表
	 * @return
	 */
	public  List<Map<String, Object>> getPersonGoodDegreeEvaluate(){
		List<Map<String, Object>> list= iJcOpEvaluateEndDAO.getPersonGoodDegreeEvaluate();
		return list;
	}

	/**
	 * 经办人满意度评价（办结）》祥表
	 * @param keyWord
	 * @return
	 */
	public Map<String, Object> getPersonDetailOps(PageParamDTO pageParamDTO,String keyWord){
		List<PersonDetailOpsVO> list= iJcOpEvaluateEndDAO.getPersonDetailOps(keyWord);
		Integer count=list.size();
		List pageList= JiancePageUtils.startPage(list,pageParamDTO.getPageIndex(),pageParamDTO.getPageSize());
		Map<String, Object> dictionary = new HashMap<String, Object>();
		dictionary.put("count",count);
		dictionary.put("pageList",pageList);
		return  dictionary;
	}

	/**
	 * 部门统计（办结）》详表
	 * @param evaluateDepartType 评价部门 1.窗口服务 2.行政审批 3.勘测院 4.信息中心
	 * @param satisfactionLevel 满意度 -1.非常不满意 1.不满意 2.一般 3.较满意 4.非常满意
	 * @param keyWord 关键字
	 * @return
	 */
	public ResponseEntity<InputStreamResource> exportMonitorGridView(Integer evaluateDepartType, Integer satisfactionLevel, String keyWord, String sortField, String sortOrder,String prjname,String keynumgather,Date startTime,Date endTime){
		List<MonitorGridViewVO> list= iJcOpEvaluateEndDAO.getMonitorGridView(evaluateDepartType,satisfactionLevel,keyWord,sortField,sortOrder,prjname, keynumgather, startTime, endTime);

		ExcelExportVO<MonitorGridViewVO> excelExportVO = ExcelExportVO.buildDefaultExcelExportVO();
		excelExportVO.addColumnMap("keydignumgather", "案卷号", 30);
		excelExportVO.addColumnMap("keynumgather", "案卷编号", 40);
		excelExportVO.addDateColumnMap("finishtime", "办结时间", 30, ChinaDateFormatHelper.FORMAT_SECOND);
		excelExportVO.addColumnMap("prjname", "项目名称", 30);

		excelExportVO.addColumnMap("prjcompany", "建设单位名称", 30);
		excelExportVO.addColumnMap("optypename", "业务名称", 30);
		excelExportVO.addColumnMap("handusername", "阶段用户名称", 40);
		excelExportVO.addColumnMap("handdepartname", "阶段部门名称", 40);
		excelExportVO.addColumnMap("opinion", "评价内容", 40);
		excelExportVO.addColumnMap("windowandnote", "窗口评价", 40);
		excelExportVO.addColumnMap("opservicandnote", "行政审批评价", 30);
		excelExportVO.addColumnMap("kanceandnote", "勘测院评价", 30);
		excelExportVO.addColumnMap("infocenterandnote", "信息中心", 30);

		excelExportVO.setContentList(list);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return exportExcelService.exportExcelResult(excelExportVO, "部门统计办结清单.xlsx");
		}
		return exportExcelService.exportExcelResult(excelExportVO, "部门统计办结清单.xlsx");
	}

	/**
	 * 已评价案卷（办结）》清单
	 * @param keyWord
	 * @return
	 */
	public ResponseEntity<InputStreamResource> exportMonitorItemView(String keyWord){
		List<MonitorItemViewVO> list= iJcOpEvaluateEndDAO.getMonitorItemView(keyWord);
		ExcelExportVO<MonitorItemViewVO> excelExportVO = ExcelExportVO.buildDefaultExcelExportVO();
		excelExportVO.addColumnMap("keydignumgather", "案卷号", 30);
		excelExportVO.addColumnMap("windowandnote", "窗口评价", 40);
		excelExportVO.addColumnMap("opservicandnote", "行政审批评价", 30);
		excelExportVO.addColumnMap("kanceandnote", "勘测院评价", 30);
		excelExportVO.addColumnMap("infocenterandnote", "信息中心", 30);
		excelExportVO.addColumnMap("opinion", "评价内容", 40);
	    excelExportVO.addColumnMap("keynumgather", "案卷号", 40);
	    excelExportVO.addDateColumnMap("finishtime", "办结时间", 30, ChinaDateFormatHelper.FORMAT_SECOND);
/*		excelExportVO.addColumnMap("opstate", "案卷状态", 40);*/
	    excelExportVO.addColumnMap("prjid", "项目号", 30);
	    excelExportVO.addColumnMap("prjname", "项目名称", 30);
		excelExportVO.addColumnMap("prjcompany", "建设单位名称", 30);
		excelExportVO.addColumnMap("optypename", "案卷类型", 30);
		excelExportVO.setContentList(list);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return exportExcelService.exportExcelResult(excelExportVO, "已评价案卷办结清单.xlsx");
		}
		return exportExcelService.exportExcelResult(excelExportVO, "已评价案卷办结清单.xlsx");
	}

	/**
	 *  待评价案卷（办结）》清单获取出窗后三天未评价的案卷,接件时间在2018年国庆节后的案卷 导出
	 * @param
	 * @return
	 */
	public ResponseEntity<InputStreamResource> exportUnEvaluateOps(String keyWord){
		List<UnEvaluateOpsVO> list= iJcOpEvaluateEndDAO.getUnEvaluateOps(keyWord);

		ExcelExportVO<UnEvaluateOpsVO> excelExportVO = ExcelExportVO.buildDefaultExcelExportVO();
		excelExportVO.addColumnMap("keydignumgather", "案卷号", 30);
		excelExportVO.addColumnMap("keynumgather", "案卷编号", 40);
		excelExportVO.addDateColumnMap("finishtime", "办结时间", 30, ChinaDateFormatHelper.FORMAT_SECOND);
		excelExportVO.addColumnMap("optypename", "案卷类型", 30);
		excelExportVO.addColumnMap("prjid", "项目号", 30);
		excelExportVO.addColumnMap("prjname", "项目名称", 30);
		excelExportVO.addColumnMap("prjcompany", "建设单位名称", 30);
		excelExportVO.addColumnMap("applyusername", "报建员姓名", 30);
		excelExportVO.addColumnMap("applyusertel", "报建员联系电话", 30);
		excelExportVO.addColumnMap("hasfinishdays", "已办结天数", 30);
		excelExportVO.setContentList(list);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return exportExcelService.exportExcelResult(excelExportVO, "待评价案卷办结清单.xlsx");
		}
		return exportExcelService.exportExcelResult(excelExportVO, "待评价案卷办结清单.xlsx");
	}

	/**
	 *  反馈意见箱（办理中） 导出
	 * @param keyWord
	 * @return
	 */
	public ResponseEntity<InputStreamResource> exportMonitorItemWorkingView(String keyWord){
		List<MonitorItemWorkingViewVO> list= iJcOpEvaluateEndDAO.getMonitorItemWorkingView(keyWord);

		ExcelExportVO<MonitorItemWorkingViewVO> excelExportVO = ExcelExportVO.buildDefaultExcelExportVO();
		excelExportVO.addColumnMap("keydignumgather", "案卷号", 30);
		excelExportVO.addColumnMap("keynumgather", "案卷编号", 40);
		excelExportVO.addColumnMap("optypename", "案卷类型", 30);
		excelExportVO.addColumnMap("prjid", "项目号", 30);
		excelExportVO.addColumnMap("prjname", "项目名称", 30);
		excelExportVO.addColumnMap("opinion", "反馈意见", 40);
		excelExportVO.setContentList(list);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return exportExcelService.exportExcelResult(excelExportVO, "反馈意见箱办理中清单.xlsx");
		}
		return exportExcelService.exportExcelResult(excelExportVO, "反馈意见箱办理中清单.xlsx");
	}

	/**
	 *  经办人满意度评价（办结）》祥表 导出
	 * @param keyWord
	 * @return
	 */
	public ResponseEntity<InputStreamResource> exportPersonDetailOps(String keyWord){
		List<PersonDetailVO> list=iJcOpEvaluateEndDAO.exportPersonDetailOps(keyWord);

		ExcelExportVO<PersonDetailVO> excelExportVO = ExcelExportVO.buildDefaultExcelExportVO();
		excelExportVO.addDateColumnMap("createtime", "业务建立时间", 30, ChinaDateFormatHelper.FORMAT_SECOND);
		excelExportVO.addColumnMap("keynumgather", "案卷号", 40);
		excelExportVO.addColumnMap("optypename", "案卷类型", 30);
	    excelExportVO.addColumnMap("deptname", "经办处室", 40);
		excelExportVO.addColumnMap("username", "经办人", 40);
		excelExportVO.addColumnMap("goodname", "满意度", 30);
		excelExportVO.addColumnMap("opinion", "意见", 30);
		excelExportVO.addColumnMap("prjid", "项目号", 30);
		excelExportVO.addColumnMap("prjname", "项目名称", 30);
		excelExportVO.addColumnMap("prjcompany", "建设单位名称", 30);

		excelExportVO.setContentList(list);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(list)) {
			return exportExcelService.exportExcelResult(excelExportVO, "经办人满意度评价办结清单.xlsx");
		}
		return exportExcelService.exportExcelResult(excelExportVO, "经办人满意度评价办结清单.xlsx");
	}




}