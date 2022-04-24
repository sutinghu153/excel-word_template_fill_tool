package com.hnup.osmp.jiance.service.meetingmonitor;

import com.hnup.osmp.jiance.model.dto.monitor.MeetingCountVO;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO;
import com.hnup.osmp.jiance.model.dto.monitor.MeetingVo;
import com.hnup.osmp.jiance.repository.dao.IJcPlanIndexDAO;
import com.hnup.osmp.jiance.service.meetingmonitor.filter.DateFilterManage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @data2022/1/19,9:21
 * @author sutinghu
 */
@Service
public class MeetingMonitorService {

	@Autowired
	private IJcPlanIndexDAO planIndex;

	/**
	 * 功能描述: 单位看板
	 * @author sutinghu
	 * @date
	 * @param meetingVo 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public List<MeetingCountVO> getCompanyDataBoard(MeetingVo meetingVo){

		List<MeetingDTO> data = this.getDate();

		// 按科室分组
		Map<Long, List<MeetingDTO>> collect = data
			.stream()
			.filter(e -> e.getDeptId() != null)
			.collect(Collectors.groupingBy(MeetingDTO::getDeptId));

		List<MeetingCountVO> list = new ArrayList<>(8);
		// 按科室统计
		collect.forEach((k,v)->{
			MeetingCountVO meetingCount = new MeetingCountVO();

			meetingCount.setId(k);

			if (CollectionUtils.isEmpty(v)){
				return;
			}

			meetingCount.setName(v.get(0).getDeptName());

			// 年度主办

			int yearSponsorSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "年度", v)
				.buildTypeFilterManage(false, "单位", k)
				.getList().size();

			meetingCount.setYearSponsorSize(yearSponsorSize);
			// 年度外部

			int yearOutSideSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "年度", v)
				.buildTypeFilterManage(true, "单位", k)
				.getList().size();

			meetingCount.setYearOutSideSize(yearOutSideSize);
			// 当前主办

			int nowSponsorSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "当前", v)
				.buildTypeFilterManage(false, "单位", k)
				.getList().size();

			meetingCount.setNowSponsorSize(nowSponsorSize);
			// 当前外部

			int nowOutSideSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "当前", v)
				.buildTypeFilterManage(true, "单位", k)
				.getList().size();

			meetingCount.setNowOutSideSize(nowOutSideSize);

			list.add(meetingCount);
		});

		Collections.sort(list, (o1, o2) -> (o1.getId() - o2.getId()) > 0 ? 1 : 0);

		return list;

	}


	/**
	 * 功能描述: 个人看板
	 * @author sutinghu
	 * @date
	 * @param meetingVo 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public List<MeetingCountVO> getSponsorDataBoard(MeetingVo meetingVo){

		List<MeetingDTO> data = this.getDate();

		// 按科室分组
		Map<Long, List<MeetingDTO>> collect = data
			.stream()
			.filter(e -> e.getDeptId() != null)
			.filter(e -> Objects.equals(e.getDeptId(),meetingVo.getOrganizerId()))
			.filter(e -> e.getOrganizerId() != null)
			.collect(Collectors.groupingBy(MeetingDTO::getApplicant));

		List<MeetingCountVO> list = new ArrayList<>(8);

		// 按人员统计
		collect.forEach((k,v)->{

			MeetingCountVO meetingCount = new MeetingCountVO();

			meetingCount.setId(k);

			if (CollectionUtils.isEmpty(v)){
				return;
			}

			meetingCount.setName(v.get(0).getApplicantName());

			// 年度主办

			int yearSponsorSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "年度", v)
				.buildTypeFilterManage(false, "员工", k)
				.getList().size();

			meetingCount.setYearSponsorSize(yearSponsorSize);

			// 年度外部

			int yearOutSideSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "年度", v)
				.buildTypeFilterManage(true, "员工", k)
				.getList().size();

			meetingCount.setYearOutSideSize(yearOutSideSize);

			// 当前主办

			int nowSponsorSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "当前", v)
				.buildTypeFilterManage(false, "员工", k)
				.getList().size();

			meetingCount.setNowSponsorSize(nowSponsorSize);

			// 当前外部

			int nowOutSideSize = new DateFilterManage(meetingVo.getStartTime(), meetingVo.getEndTime(), "当前", v)
				.buildTypeFilterManage(true, "员工", k)
				.getList().size();

			meetingCount.setNowOutSideSize(nowOutSideSize);

			list.add(meetingCount);
		});

		Collections.sort(list, (o1, o2) -> (o1.getId() - o2.getId()) > 0 ? 1 : 0);

		return list;

	}

	/**
	 * 功能描述: 获取详情列表
	 * @author sutinghu
	 * @date
	 * @param meetingVo 参数
	 * @return java.util.List<com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO>
	 */
	public List<MeetingDTO> getDetailsList(MeetingVo meetingVo) {

		List<MeetingDTO> data = this.getDate();

		return new DateFilterManage(meetingVo.getStartTime(),meetingVo.getEndTime(),meetingVo.getDateType(),data)
			.buildTypeFilterManage(meetingVo.getIsOutSideType(),meetingVo.getType(),meetingVo.getOrganizerId())
			.getList();

	}

	/**
	 * 功能描述: 获取数据源
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<com.hnup.osmp.jiance.model.dto.monitor.MeetingDTO>
	 */
	public List<MeetingDTO> getDate() {

		return planIndex.getDate().stream()
			.filter(e -> 	!Objects.equals(e.getState(),1)
						&& 	!Objects.equals(e.getState(),2)
						&&  !Objects.equals(e.getState(),3))
			.collect(Collectors.toList());

	}

}
