package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @data2022/1/19,9:57
 * @author sutinghu
 */
public class SponsorFilter extends Filter{



	@Override
	void execute() {


		list = list.stream().filter(e -> {

			if (Objects.isNull(e.getOrganizerId())) {
				return false;
			}else {

				if (Objects.equals(e.getOrganizerId(),this.organizerId)) {
					return true;
				}else {
					return false;
				}

			}

		}).collect(Collectors.toList());

	}


}
