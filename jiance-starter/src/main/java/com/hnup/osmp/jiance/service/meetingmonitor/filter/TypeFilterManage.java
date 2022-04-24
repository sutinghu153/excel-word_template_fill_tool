package com.hnup.osmp.jiance.service.meetingmonitor.filter;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @data2022/1/19,9:55
 * @author sutinghu
 */
public class TypeFilterManage {

	private Filter filter;

	public TypeFilterManage(Boolean isOutSideType,String type,Long organizerId,List<MeetingDTO> list){

		if (Objects.equals(type,"单位")) {
			filter = new CompanyFilter();
		}else if (Objects.equals(type,"员工")) {
			filter = new SponsorFilter();
		}else {
			throw new DeclareException("错误的参数");
		}

		filter.organizerId = organizerId;

		filter.list = list;

		// 如果是外部会议 再过滤一层

		if (isOutSideType) {
			OutsideFilter outsideFilter = new OutsideFilter();
			outsideFilter.list = filter.list;
			outsideFilter.execute();
			filter.list = outsideFilter.list;
		}else {
			filter.list = filter.list.stream().filter(e -> {

				if (Objects.isNull(e.getType())) {
					return false;
				}else {

					if (Objects.equals(e.getType(),0)) {
						return true;
					}else {
						return false;
					}

				}

			}).collect(Collectors.toList());
		}

		this.filter.execute();
	}

	public DateFilterManage buildDateFilterManage(Date startTime, Date endTime , String dateType){
		return new DateFilterManage( startTime,  endTime ,  dateType , filter.list);
	}

	public List<MeetingDTO> getList(){

		return this.filter.list;

	}

}
