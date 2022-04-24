package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @data2022/1/19,9:22
 * @author sutinghu
 */
public abstract class Filter {

	public Date startTime;

	public Date endTime;

	public Long organizerId;

	public List<MeetingDTO> list;

	protected void filterByDate(){

		list = list.stream().filter(e -> {

			if (Objects.isNull(e.getStartTime())) {
				return false;
			}else {
				if (e.getStartTime().before(endTime) && e.getStartTime().after(startTime)) {
					return true;
				}else {
					return false;
				}
			}
		}).collect(Collectors.toList());

	}

	abstract void execute();

}
