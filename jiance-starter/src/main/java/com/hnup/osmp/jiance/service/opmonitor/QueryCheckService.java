package com.hnup.osmp.jiance.service.opmonitor;

import com.hnup.osmp.jiance.model.dto.monitor.MonitorQueryVO;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @data2021/12/9,14:57
 * @authorsutinghu
 */
@Service
public class QueryCheckService {

	/**
	 * 功能描述: 参数校验
	 * @author sutinghu
	 * @date
	 * @param monitorQuery 参数
	 * @return void
	 */
	public  void checkQuery(MonitorQueryVO monitorQuery){
		Assert.notNull(monitorQuery,"参数不能为空");
		Assert.notNull(monitorQuery.getStartTime(),"开始时间不能为空");
		Assert.notNull(monitorQuery.getEndTime(),"结束时间不能为空");
		Assert.notNull(monitorQuery.getDeptName(),"主办单位不能为空");
	}

	public  void checkDetail(MonitorQueryVO monitorQuery){
		this.checkQuery(monitorQuery);
		Assert.notNull(monitorQuery.getTypes(),"跳转参数不能为空");
	}

	public  void checkData(MonitorQueryVO monitorQuery){
		Assert.notNull(monitorQuery,"参数不能为空");
		Assert.notNull(monitorQuery.getStartTime(),"开始时间不能为空");
		Assert.notNull(monitorQuery.getEndTime(),"结束时间不能为空");
	}

	public  void checkAll(MonitorQueryVO monitorQuery){
		Assert.notNull(monitorQuery,"参数不能为空");
		Assert.notNull(monitorQuery.getStartTime(),"开始时间不能为空");
		Assert.notNull(monitorQuery.getEndTime(),"结束时间不能为空");
		Assert.notNull(monitorQuery.getType(),"显示类型不能为空");
	}
}
