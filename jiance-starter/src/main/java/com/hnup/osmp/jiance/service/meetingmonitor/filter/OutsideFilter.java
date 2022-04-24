package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @data2022/1/19,9:49
 * @author sutinghu
 */
public class OutsideFilter extends Filter{

	@Override
	void execute() {

		list = list.stream().filter(e -> {

			if (Objects.isNull(e.getType())) {
				return false;
			}else {

				if (Objects.equals(e.getType(),1)) {
					return true;
				}else {
					return false;
				}

			}

		}).collect(Collectors.toList());

	}
}
