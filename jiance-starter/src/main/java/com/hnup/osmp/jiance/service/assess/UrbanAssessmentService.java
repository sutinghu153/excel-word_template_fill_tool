package com.hnup.osmp.jiance.service.assess;

import com.google.common.collect.Lists;
import com.hnup.osmp.jiance.repository.dao.IJcUrbanAssessmentDAO;
import com.hnup.osmp.jiance.repository.entity.JcUrbanAssessment;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  市委市政府考核指标服务层
 * @data2021/11/3,8:59
 * @authorsutinghu
 */
@Service
public class UrbanAssessmentService {

	@Autowired
	private IJcUrbanAssessmentDAO iSupUrbanAssessment;

	/**
	 * 功能描述: 获取市委市政府考核列表
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcUrbanAssessment>
	 */
	public List<JcUrbanAssessment> getUrbanAssessList() {

		List<JcUrbanAssessment> jcUrbanAssessments = iSupUrbanAssessment.lambdaQuery().list();
		if (CollectionUtils.isEmpty(jcUrbanAssessments)) {
			return Lists.newArrayList();
		}
		return jcUrbanAssessments;
	}

}
