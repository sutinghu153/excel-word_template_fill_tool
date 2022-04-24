package com.hnup.osmp.jiance.task;

import com.hnup.osmp.jiance.service.census.HomeCensusService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shuangyuewu
 * @createTime 2022/02/16
 * @Description
 */
@Slf4j
@Component
public class HomeCensusTaskHandler {

	@Autowired
	private HomeCensusService homeCensusService;

	@XxlJob("homeCensusJob")
	public ReturnT<String> censusJobHandler() {
		log.debug("首页统计数据抽取---start");
		// 耕地保护
		String parentType = "GDBH";
		homeCensusService.syncData(parentType);

		// 行政效能
		parentType = "XZXN";
		homeCensusService.syncData(parentType);

		// 建设用地审批
		parentType = "JSYDSP";
		homeCensusService.syncData(parentType);

		// 土地供应
		parentType = "TDGY";
		homeCensusService.syncData(parentType);

		// 规划许可办理
		parentType = "GHXKBL";
		homeCensusService.syncData(parentType);


		log.debug("首页统计数据抽取---end");
		return ReturnT.SUCCESS;
	}
}
