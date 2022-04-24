package com.hnup.osmp.jiance.service.processmonitor;

import com.hnup.osmp.jiance.repository.dao.IJcPlanIndexDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @data2022/1/19,14:37
 * @author sutinghu
 */
@Service
public class ProcessMonitorService {

	@Autowired
	private IJcPlanIndexDAO planIndex;

	/*
	---办结
	select * from PROCESS_ENTITY where process_state = 8

		--- 办结超期数据
	SELECT
	pe.*
	FROM
	PROCESS_ENTITY pe,
	process_entity_bpm peb
	WHERE
	pe.key_dig_num_gather = peb.key_dig_num_gather
	AND pe.process_state = 8
	and peb.remain_total_time  <  0


		--- 在办数据
	select * from PROCESS_ENTITY where process_state = 5

		--督办
	select * from supervise_task where del = 'n'
	*/
}
