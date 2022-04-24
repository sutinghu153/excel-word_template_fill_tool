package com.hnup.osmp.jiance.task;

import com.hnup.osmp.jiance.service.driver.DriverStoreService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author shuangyuewu
 * @createTime 2022/02/17
 * @Description
 */
@Slf4j
@Component
public class DriverStoreTaskHandler {
	@Autowired
	private DriverStoreService driverStoreService;

	@XxlJob("driverCensusJob")
	public ReturnT<String> censusJobHandler() {
		log.debug("驾驶舱统计数据抽取---start");

		log.debug("驾驶舱公文数据抽取---start");
		driverStoreService.synDocData();
		log.debug("驾驶舱公文数据抽取---end");

		log.debug("驾驶舱督办数据抽取---start");
		driverStoreService.synSuppervise();
		log.debug("驾驶舱督办数据抽取---end");

		log.debug("驾驶舱OP数据抽取---start");
		driverStoreService.synOp();
		log.debug("驾驶舱OP数据抽取---end");

		log.debug("驾驶舱OP排行榜数据抽取---start");
		driverStoreService.synOpRank();
		log.debug("驾驶舱OP排行榜数据抽取---end");

		log.debug("驾驶舱会议数据抽取---start");
		driverStoreService.synMeeting();
		log.debug("驾驶舱会议数据抽取---end");

		log.debug("驾驶舱资产数据(确权登记)数据抽取---start");
		driverStoreService.synZican();
		log.debug("驾驶舱资产数据(确权登记)数据抽取---end");

		log.debug("驾驶舱执法督查数据抽取---start");
		driverStoreService.synZhifa();
		log.debug("驾驶舱执法督查数据抽取---end");


		log.debug("驾驶舱统计数据抽取---end");
		return ReturnT.SUCCESS;
	}
}
