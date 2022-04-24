package com.hnup.osmp.jiance.service.driver;

import com.hnup.osmp.jiance.repository.dao.IJcPlanIndexDAO;
import com.hnup.osmp.jiance.repository.mapper.driver.DriverStoreOpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @data2022/4/14,14:31
 * @author sutinghu
 */
@Service
public class DriverStoreOpManageService {

	@Autowired
	private IJcPlanIndexDAO jcPlanIndexDao;

	/**
	 * 功能描述: 全局受理情况统计
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> countHandle() {
		return jcPlanIndexDao.countHandle();
	}

	/**
	 * 功能描述: 业务办理趋势统计
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> countTrend() {
		return jcPlanIndexDao.countTrend();
	}

	/**
	 * 功能描述: 处室办理正点率TOP5统计
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> countDepartmentZdlTopFive() {
		return jcPlanIndexDao.countDepartmentZdlTopFive();
	}

	/**
	 * 功能描述: 处室办理办结量TOP5统计
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> countDepartmentBjlTopFive() {
		return jcPlanIndexDao.countDepartmentBjlTopFive();
	}

	/**
	 * 功能描述: 业务受理量地区统计
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> countRegionAccept() {
		return jcPlanIndexDao.countRegionAccept();
	}

	/**
	 * 功能描述: 业务办结量地区统计
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> countRegionFinish() {
		return jcPlanIndexDao.countRegionFinish();
	}

	public List<Map<String, Object>> countMapData() {
		return jcPlanIndexDao.countMapData();
	}
}

