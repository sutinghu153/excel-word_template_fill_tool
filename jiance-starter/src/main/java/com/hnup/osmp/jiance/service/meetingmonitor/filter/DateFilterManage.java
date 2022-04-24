package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;
import com.hnup.osmp.jiance.utils.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @data2022/1/19,9:40
 * @author sutinghu
 */
public class DateFilterManage {

	private Filter filter;

	public DateFilterManage(Date startTime, Date endTime , String dateType,List<MeetingDTO> list){

		// 年度
		if (Objects.equals(dateType,"年度")) {

			filter = new YearFilter();

		// 当前
		}else if (Objects.equals(dateType,"当前")){

			// 如果当前时间不为空 取当前时间
			if (Objects.nonNull(startTime) && Objects.nonNull(endTime)) {

				filter = new NowFilter(startTime, endTime);

			}else {
				// 如果当前时间为空 默认为本月开始和结束时间

				filter = new NowFilter(DateUtils.getMonthDayStart(), DateUtils.getMonthDayEnd());

			}

		}else {

			throw new DeclareException("没有对应类型的时间查询");

		}

		filter.list = list;

		this.filter.execute();

	}


	public TypeFilterManage buildTypeFilterManage(Boolean isOutSideType,String type,Long organizerId){

		return new TypeFilterManage( isOutSideType,type, organizerId,filter.list);

	}

	public List<MeetingDTO> getList(){

		return this.filter.list;

	}

}
