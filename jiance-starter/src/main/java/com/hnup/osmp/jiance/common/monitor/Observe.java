package com.hnup.osmp.jiance.common.monitor;

/**
 *  观察者类 监听数据源的变化
 * @data2021/10/21,17:52
 * @authorsutinghu
 */
public interface Observe extends MonitorAwire{


	/**
	 * 功能描述:特种兵注册接口
	 * @author sutinghu
	 * @date
	 * @param observer 参数
	 * @return void
	 */
	void registerObserver(SpecialArm observer);

	/**
	 * 功能描述:删除特种兵接口
	 * @author sutinghu
	 * @date
	 * @param observer 参数
	 * @return void
	 */
	void reMoveObserver(SpecialArm observer);

	/**
	 * 功能描述:当数据源状态改变时，该方法调用以通知所有的特种兵
	 * @author sutinghu
	 * @date
	 * @param
	 * @return void
	 */
	void notifyObserver();

}
