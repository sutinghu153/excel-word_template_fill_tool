package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @data2022/1/19,10:12
 * @author sutinghu
 */
public class CompanyFilter extends Filter{
	@Override
	void execute() {

		list = list.stream().filter(e -> {

			if (Objects.isNull(e.getDeptId())) {
				return false;
			}else {

				if (Objects.equals(e.getDeptId(),this.organizerId)) {
					return true;
				}else {
					return false;
				}

			}

		}).collect(Collectors.toList());

	}
}
