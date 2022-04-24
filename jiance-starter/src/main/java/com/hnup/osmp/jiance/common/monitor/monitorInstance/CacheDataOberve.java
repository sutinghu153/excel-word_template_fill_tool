package com.hnup.osmp.jiance.common.monitor.monitorInstance;

import com.hnup.osmp.jiance.common.monitor.Observe;
import com.hnup.osmp.jiance.common.monitor.SpecialArm;

import java.util.ArrayList;
import java.util.List;

/**
 *  数据源观察者类
 * @data2021/10/21,18:06
 * @authorsutinghu
 */
public class CacheDataOberve implements Observe {

	// 特种兵队列
	private List<SpecialArm> specialArms;

	public CacheDataOberve(){
		specialArms = new ArrayList<>(16);
	}

	@Override
	public void registerObserver(SpecialArm observer) {
		specialArms.add(observer);
	}

	@Override
	public void reMoveObserver(SpecialArm observer) {
		specialArms.remove(observer);
	}

	/**
	 * 功能描述: 数据源变化时，通知所有特种兵执行关注任务
	 * @author sutinghu
	 * @date
	 * @param
	 * @return void
	 */
	@Override
	public void notifyObserver() {
		for (SpecialArm specialArm:specialArms) {
			specialArm.excute();
		}
	}
}
