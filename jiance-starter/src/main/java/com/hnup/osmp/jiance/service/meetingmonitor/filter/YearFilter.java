package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;
import com.hnup.osmp.jiance.utils.DateUtils;

import java.util.List;

/**
 *  年度时间筛选
 * @data2022/1/19,9:23
 * @author sutinghu
 */
public class YearFilter extends Filter{

	YearFilter(){
		this.startTime = DateUtils.getYearDayStart();
		this.endTime = DateUtils.getYearDayEnd();
	}

	@Override
	void execute() {
		this.filterByDate();
	}
}
