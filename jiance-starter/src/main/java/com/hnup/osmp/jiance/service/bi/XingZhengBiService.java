package com.hnup.osmp.jiance.service.bi;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.hnup.osmp.jiance.model.dto.bi.DynamicHeadDTO;
import com.hnup.osmp.jiance.repository.mapper.SqlHelperMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description 行政分舵-相关统计接口
 * @ClassName 梁岳凡
 * @Author User
 * @date 2022.04.08 08:25
 */
@Service
public class XingZhengBiService {


	@Autowired
	private SqlHelperMapper sqlHelperMapper;

	/**
	 * 全局督办事务总体情况类型
	 */
	private static String superviseAll="superviseAll";

	/**
	 * 督办事务-处室办结量top5
	 */
	private static String superviseDepTop="superviseDepTop";

	/**
	 * 信访管理
	 */
	private static String petition="petition";

	/**
	 * 收文管理-总量
	 */
	private static String receipt="receipt";

	/**
	 * 收文管理-办理量
	 */
	private static String receiptBanLi="receiptBanLi";

	/**
	 * 收文管理-正点率
	 */
	private static String receiptZhengDianLv="receiptZhengDianLv";

	/**
	 * 发文管理-总量
	 */
	private static String fawen="fawen";

	/**
	 * 发文管理-近半年办理情况
	 */
	private static String fawenBanNian="fawenBanNian";

	/**
	 * 发文管理-处室发文排名top5
	 */
	private static String fawenTop="fawenTop";

	/**
	 * 综合管理-综合登记量
	 */
	private static String comprehensive="comprehensive";

	/**
	 * 会议管理-总量
	 */
	private static String meeting="meeting";

	/**
	 * 会议管理-处室排行
	 */
	private static String meetingTop="meetingTop";

	/**
	 * 档案管理-总量
	 */
	private static String doc="doc";

	/**
	 * 档案管理-排行榜
	 */
	private static String docBorrowTop="docBorrowTop";

	public  HashMap<String,Object> getAll(String biType) {
		Date date = DateUtil.date();
		DateTime ksTime=DateUtil.beginOfYear(DateUtil.offset(date, DateField.YEAR,-1));
		String ksTimeString= DateUtil.format(ksTime,"yyyy-MM-dd 00:00:01");
		DateTime endTime=DateUtil.endOfYear(date);
		String endTimeString= DateUtil.format(endTime,"yyyy-MM-dd 23:59:59");
		if (superviseAll.equals(biType)) {
			ksTime=DateUtil.beginOfYear(DateUtil.offset(date, DateField.YEAR,-1));
			ksTimeString= DateUtil.format(ksTime,"yyyy-MM-dd 00:00:01");
			return superviseAffair(date,ksTimeString,endTimeString);
		} else if (superviseDepTop.equals(biType)) {
			return superviseTop();
		} else if (petition.equals(biType)) {
			return petitionLetter();
		} else if (receipt.equals(biType)) {
			return receiptZt();
		} else if (receiptBanLi.equals(biType)) {
			return receiptDepTop();
		} else if (receiptZhengDianLv.equals(biType)) {
			return receiptDepTopZdlv();
		} else if (fawen.equals(biType)) {
			return faWenZt();
		} else if (fawenBanNian.equals(biType)) {
			return faWenHalfYear();
		} else if (fawenTop.equals(biType)) {
			return faWenDepTop();
		} else if (comprehensive.equals(biType)) {
			return comprehensiveCount();
		} else if (meeting.equals(biType)) {
			return meetingZt();
		} else if (meetingTop.equals(biType)) {
			return meetingDepTop();
		} else if (doc.equals(biType)) {
			return docZt();
		} else if (docBorrowTop.equals(biType)) {
			return docBorrowTop();
		}
		return null;
	}
	//region  督办事务管理

	/**
	 * 全局督办事务总体情况
	 * @return
	 */
	public HashMap<String, Object> superviseAffair(Date date,String ksTimeString,String endTimeString) {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("name");
				setFieldName("类型");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("数量");
				setFieldShow(true);
				setSort(2);
				setSymbol("件");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("compare");
				setFieldName("较去年同期");
				setFieldShow(true);
				setSort(3);
				setSymbol("较去年同期");
			}
		});

		String mysql = String.format("select t1.*,t2.status as sj_status,\n" +
			" (\n" +
			" case when t2.status=0 and (t1.received_date<=t1.end_time or t1.received_date is null) then 1 else 0 end\n" +
			" ) time_state\n" +
			"from supervise t1 \n" +
			"inner join supervise_process_log t2 \n" +
			"on t1.id=t2.sup_id where t1.start_time>='%s' and t1.start_time<='%s' ",ksTimeString,endTimeString);

		List<Map<String, Object>> list = sqlHelperMapper.selectCustomizeSql(mysql);
		Integer allCount = 0;
		Double allLv = 0D;
		Integer allJinYear=0;
		Integer allQuYear=0;
		Integer bjCount = 0;
		Double bjLv = 0D;
		Integer bjJinYear=0;
		Integer bjQuYear=0;
		Integer asCount = 0;
		Double asLv = 0D;
		Integer asJinYear=0;
		Integer asQuYear=0;

		Integer year=DateUtil.year(date);
		Integer yearj=year-1;
		if (list != null && list.size() > 0) {
			for (Map<String, Object> itme : list) {
				allCount++;
				Integer prjYear=Convert.toInt(itme.get("prj_year"));
				Integer sj_status = Convert.toInt(itme.get("sj_status"));
				Integer time_state = Convert.toInt(itme.get("time_state"));
				if(year.equals(prjYear)) {
					allJinYear++;
					if (sj_status == 0) {
						bjCount++;
						bjJinYear++;
					}
					if (time_state == 1) {
						asCount++;
						asJinYear++;
					}
				}else if(yearj.equals(prjYear)){
					allQuYear++;
					if (sj_status == 0) {
						bjCount++;
						bjQuYear++;
					}
					if (time_state == 1) {
						asCount++;
						asQuYear++;
					}
				}
			}
		}
		List<Map<String, Object>> runList = new ArrayList<>();
		Map<String, Object> dic_1 = new HashMap<>(16);
		dic_1.put("name", "督办事务登记量");
		dic_1.put("count", allCount);

		dic_1.put("compare",allQuYear.equals(0)?allJinYear*100:NumberUtil.round(NumberUtil.mul(NumberUtil.div(allJinYear,allQuYear,4),100),2) );
		runList.add(dic_1);
		Map<String, Object> dic_2 = new HashMap<>(16);
		dic_2.put("name", "全部办结量");
		dic_2.put("count", bjCount);
		dic_2.put("compare",bjQuYear.equals(0)?bjJinYear*100: NumberUtil.round(NumberUtil.mul(NumberUtil.div(bjJinYear,bjQuYear,4),100),2));
		runList.add(dic_2);
		Map<String, Object> dic_3 = new HashMap<>(16);
		dic_3.put("name", "按时办结量");
		dic_3.put("count", asCount);
		dic_3.put("compare", asQuYear.equals(0)?asJinYear*100:NumberUtil.round(NumberUtil.mul(NumberUtil.div(asJinYear,asQuYear,4),100),2));
		runList.add(dic_3);
		HashMap<String, Object> run = new HashMap<>(2);
		run.put("headList", headList);
		run.put("dataList", runList);
		return run;
	}

	/**
	 * 处室办结量top5
	 * @return
	 */
	public HashMap<String,Object> superviseTop() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("dept_name");
				setFieldName("处室名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("任务总量(件)");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_count");
				setFieldName("办结量(件)");
				setFieldShow(true);
				setSort(3);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_lv");
				setFieldName("办结率");
				setFieldShow(true);
				setSort(4);
				setSymbol("%");
			}
		});
		String selectSql = "select  \n" +
			"dept_name,\n" +
			"count(*) count,\n" +
			"sum(case when status=0 then 1 else 0 end ) as bj_count, \n" +
			"round(cast(bj_count as numeric) / cast(count as numeric) * 100,2)  as bj_lv\n" +
			"from (\n" +
			"select t1.status,t2.dept_name from supervise_task_process_log t1 \n" +
			"left join user_dept t2 on t1.host_depart=t2.dept_id\n" +
			"where t1.host_depart in(\n" +
			"select host_depart from (\n" +
			"select host_depart,count(*) as ct\n" +
			"from supervise_task_process_log where start_time>=date_trunc('year',now()) " +
			"group by host_depart order by ct desc) v limit 5  ) ) vv GROUP BY dept_name order by count desc ";
		return allMap(headList, selectSql);
	}

	//endregion

	//region 信访管理
	public  HashMap<String,Object> petitionLetter() {
		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("key_type_name");
				setFieldName("信访类型");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("任务总量(件)");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_count");
				setFieldName("办结量(件)");
				setFieldShow(true);
				setSort(3);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_lv");
				setFieldName("办结率");
				setFieldShow(true);
				setSort(4);
				setSymbol("%");
			}
		});
		String selectSql="select vr.*,bpm.bpm_name as key_type_name from (\n" +
			"select \n" +
			"key_type_code,\n" +
			"count(*) count,\n" +
			"sum(case when op_state=5 then 1 else 0 end ) as bj_count, \n" +
			"round(cast(bj_count as numeric) / cast(count as numeric) * 100,2)  as bj_lv\n" +
			"from op_entity  where key_type_code in(1035,1036,1037) \n" +
			"group by key_type_code ) vr left join op_bpm bpm \n" +
			"on vr.key_type_code=bpm.key_type_code";
		return allMap(headList, selectSql);
	}

	//endregion

	// region 收文管理

	/**
	 * 收文总体统计
	 * @return
	 */
	public HashMap<String,Object> receiptZt() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("登记量");
				setFieldShow(true);
				setSort(1);
				setSymbol("件");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_count");
				setFieldName("办结量");
				setFieldShow(true);
				setSort(2);
				setSymbol("件");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_lv");
				setFieldName("办结量");
				setFieldShow(true);
				setSort(3);
				setSymbol("%");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("zd_lv");
				setFieldName("正点率");
				setFieldShow(true);
				setSort(4);
				setSymbol("%");
			}
		});
		String selectSql = "select \n" +
			"count(*) count,\n" +
			"sum(case when t1.op_state=5 then 1 else 0 end ) as bj_count, \n" +
			"sum(case when t2.light_flag=3 then 0 else 1 end ) as zd_count, \n" +
			"round(cast(bj_count as numeric) / cast(count as numeric) * 100,2)  as bj_lv,\n" +
			"round(cast(zd_count as numeric) / cast(count as numeric) * 100,2) as zd_lv\n" +
			"from doc_entity t1 inner join doc_entity_bpm t2 \n" +
			"on t1.key_dig_num_gather=t2.key_dig_num_gather\n" +
			"where t1.script_type=2 and t1.create_time>=date_trunc('year',now()) ";
		return allMap(headList, selectSql);
	}

	/**
	 * 收文处室办理量top5
	 * @return
	 */
	public HashMap<String,Object> receiptDepTop() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("dep_name");
				setFieldName("处室名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("办理量");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		String selectSql = "select \n" +
			"t1.register_user_depart as dep_name,\n" +
			"count(1) as count\n" +
			"from doc_entity t1 \n" +
			"where script_type=2 and create_time>=date_trunc('year',now())\n" +
			"group by register_user_depart\n" +
			"order by count desc\n" +
			"limit 5";
		return allMap(headList, selectSql);
	}

	/**
	 * 收文处室正点率 top5
	 * @return
	 */
	public HashMap<String,Object> receiptDepTopZdlv() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("dep_name");
				setFieldName("处室名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("zd_lv");
				setFieldName("正点率");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		String selectSql = "select \n" +
			"t1.register_user_depart as dep_name,\n" +
			"count(*) count,\n" +
			"sum(case when t2.light_flag=3 then 0 else 1 end ) as zd_count, \n" +
			"round(cast(zd_count as numeric) / cast(count as numeric) * 100,2) as zd_lv\n" +
			"from doc_entity t1 inner join doc_entity_bpm t2 \n" +
			"on t1.key_dig_num_gather=t2.key_dig_num_gather\n" +
			"where t1.script_type=2 and t1.create_time>=date_trunc('year',now())\n" +
			"group by t1.register_user_depart\n" +
			"order by zd_lv desc\n" +
			"limit 5";
		return allMap(headList, selectSql);
	}

	// endregion

	// region 发文管理

	/**
	 * 发文总体统计
	 * @return
	 */
	public HashMap<String,Object> faWenZt() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("发文登记量");
				setFieldShow(true);
				setSort(1);
				setSymbol("件");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_count");
				setFieldName("办结量");
				setFieldShow(true);
				setSort(2);
				setSymbol("件");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("zd_lv");
				setFieldName("正点率");
				setFieldShow(true);
				setSort(3);
				setSymbol("%");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_lv");
				setFieldName("办结率");
				setFieldShow(true);
				setSort(4);
				setSymbol("%");
			}
		});
		String selectSql = "select \n" +
			"count(*) count,\n" +
			"sum(case when op_state=5 then 1 else 0 end ) as bj_count, \n" +
			"sum(case when t2.light_flag=3 then 0 else 1 end ) as zd_count, \n" +
			"round(cast(bj_count as numeric) / cast(count as numeric) * 100,2)  as bj_lv,\n" +
			"round(cast(zd_count as numeric) / cast(count as numeric) * 100,2) as zd_lv\n" +
			"from doc_entity  t1 inner join doc_entity_bpm t2 \n" +
			"on t1.key_dig_num_gather=t2.key_dig_num_gather\n" +
			"where t1.script_type=1 and t1.create_time>=date_trunc('year',now()) ";
		return allMap(headList, selectSql);
	}

	/**
	 * 发文近半年公文办理情况
	 * @return
	 */
	public HashMap<String,Object> faWenHalfYear() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("month_time");
				setFieldName("时间维度");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("登记量");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("bj_count");
				setFieldName("办结量");
				setFieldShow(true);
				setSort(3);
				setSymbol("");
			}
		});
		String selectSql = "\n" +
			"select \n" +
			"v.month_time,\n" +
			"sum(1) as count, \n" +
			"sum(case when op_state=5 then 1 else 0 end ) as bj_count\n" +
			"from (\n" +
			"select op_state,date_trunc('month',create_time) as month_time from doc_entity\n" +
			"where script_type=1 and month_time <= now()::timestamp(0) and month_time >= now()::timestamp(0) + '-6 month'\n" +
			" ) v group by v.month_time order by v.month_time asc";
		return allMap(headList, selectSql);
	}


	/**
	 * 发文处室发文排名top5
	 * @return
	 */
	public HashMap<String,Object> faWenDepTop() {

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("dep_name");
				setFieldName("处室名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("发文量");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		String selectSql = "select \n" +
			"register_user_depart as dep_name ,\n" +
			"sum(1) as count\n" +
			"from doc_entity\n" +
			"where script_type=1 and create_time>=date_trunc('year',now()) group by register_user_depart \n" +
			"order by count desc\n" +
			"limit 5 ";
		return allMap(headList, selectSql);
	}

	// endregion

	// region 综合办理

	public  HashMap<String,Object> comprehensiveCount(){

		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("dep_name");
				setFieldName("处室名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("办理量");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		String selectSql = "select t1.doc_type_name as dep_name,count(1) as count  from doc t1 \n" +
			"left join  doc_entity t2 on t1.key_type_code=t2.key_type_code\n" +
			"where t2.script_type=4  and t2.create_time>=date_trunc('year',now()) \n" +
			"group by t1.doc_type_name ";
		return allMap(headList, selectSql);
	}

	// endregion

	// region 会议管理

	/**
	 * 总体会议
	 * @return
	 */
	 public  HashMap<String,Object> meetingZt(){
		 List<DynamicHeadDTO> headList = new ArrayList<>();
		 headList.add(new DynamicHeadDTO() {
			 {
				 setFieldCode("count");
				 setFieldName("会议总数");
				 setFieldShow(true);
				 setSort(1);
				 setSymbol("场");
			 }
		 });
		 headList.add(new DynamicHeadDTO() {
			 {
				 setFieldCode("dzlxh_count");
				 setFieldName("党政联席会");
				 setFieldShow(true);
				 setSort(2);
				 setSymbol("场");
			 }
		 });
		 headList.add(new DynamicHeadDTO() {
			 {
				 setFieldCode("dzh_count");
				 setFieldName("党组会");
				 setFieldShow(true);
				 setSort(3);
				 setSymbol("场");
			 }
		 });
		 String selectSql = "select \n" +
			 "count(1),\n" +
			 "sum(case when meet_type_id=9 then 1 else 0 end ) as dzlxh_count, \n" +
			 "sum(case when meet_type_id=100 then 1 else 0 end ) as dzh_count\n" +
			 "from meeting where start_time>=date_trunc('year',now())\n ";
		 return allMap(headList, selectSql);
	 }

	/**
	 * 承办处室top5
	 * @return
	 */
	public  HashMap<String,Object> meetingDepTop(){
		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("dep_name");
				setFieldName("处室名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("会议数量");
				setFieldShow(true);
				setSort(2);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("jy_count");
				setFieldName("纪要数量");
				setFieldShow(true);
				setSort(3);
				setSymbol("");
			}
		});
		String selectSql = "select \n" +
			"vr.dept_name as dep_name,\n" +
			"count(1) as count,\n" +
			"sum(vr.jy_count) as jy_count \n" +
			"from (\n" +
			"select \n" +
			"t2.dept_name as dept_name,\n" +
			"(select count(1) from meeting_file where file_type=3 and meeting_id=t1.id) as jy_count\n" +
			"from meeting t1 \n" +
			"left join user_dept t2\n" +
			"on t1.dept_id=t2.dept_id where t1.start_time>=date_trunc('year',now()) ) vr \n" +
			"group by vr.dept_name order  by count desc\n" +
			"limit 5";
		return allMap(headList, selectSql);
	}

	// endregion

	// region 档案管理

	/**
	 * 获取档案数量
	 * @return
	 */
	 public HashMap<String,Object> docZt(){
		 List<DynamicHeadDTO> headList = new ArrayList<>();
		 headList.add(new DynamicHeadDTO() {
			 {
				 setFieldCode("count");
				 setFieldName("档案数量");
				 setFieldShow(true);
				 setSort(1);
				 setSymbol("");
			 }
		 });
		 String selectSql = "select count(key_dig_num_gather) as count from archive_entity where create_time>=date_trunc('year',now())  ";
		 return allMap(headList, selectSql);
	 }

	/**
	 * 获取档案借阅排名情况
	 * @return
	 */
	public HashMap<String,Object> docBorrowTop(){
		List<DynamicHeadDTO> headList = new ArrayList<>();
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("type_name");
				setFieldName("类型名称");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		headList.add(new DynamicHeadDTO() {
			{
				setFieldCode("count");
				setFieldName("借阅次数");
				setFieldShow(true);
				setSort(1);
				setSymbol("");
			}
		});
		String selectSql = "select t3.archive_type_name as type_name,sum(t1.jy_count) count from (\n" +
			"select \n" +
			"key_dig_num_gather,\n" +
			"count(key_dig_num_gather) jy_count\n" +
			"from archive_borrow_apply \n" +
			"where key_dig_num_gather is not null and apply_time>=date_trunc('year',now()) \n" +
			"group by key_dig_num_gather ) t1 \n" +
			"left join  archive_entity t2 on t1.key_dig_num_gather=t2.key_dig_num_gather\n" +
			"left join archive_type t3 on t2.key_type_code=t3.key_type_code\n" +
			"where t2.key_type_code is not null\n" +
			"group by t2.key_type_code,t3.archive_type_name order  by count desc\n" +
			"limit 10";
		return allMap(headList, selectSql);
	}
	// endregion

	private HashMap<String,Object> allMap(List<DynamicHeadDTO> headList,String selectSql){

		List<Map<String, Object>> list = sqlHelperMapper.selectCustomizeSql(selectSql);
		HashMap<String, Object> run = new HashMap<>(2);
		run.put("headList", headList);
		run.put("dataList", list);
		return run;
	}
}
