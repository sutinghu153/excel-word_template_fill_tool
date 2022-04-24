package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;

import java.util.Date;
import java.util.List;

/**
 * @data2022/1/19,9:37
 * @author sutinghu
 */
public class NowFilter extends Filter{

	NowFilter(Date startTime,Date endTime){
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	void execute() {

		 this.filterByDate();

	}
}
