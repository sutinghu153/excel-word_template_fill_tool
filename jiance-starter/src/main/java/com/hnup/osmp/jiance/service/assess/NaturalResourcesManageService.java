package com.hnup.osmp.jiance.service.assess;

import com.google.common.collect.Lists;
import com.hnup.osmp.jiance.repository.dao.IJcIndicativeProvManageDAO;
import com.hnup.osmp.jiance.repository.entity.JcIndicativeProvManage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 *  自然资源管理服务层
 * @data2021/11/3,10:06
 * @authorsutinghu
 */
@Service
public class NaturalResourcesManageService {

	@Autowired
	private IJcIndicativeProvManageDAO iSupIndicativeProvManage;

	/**
	 * 功能描述:获取自然资源管理列表
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcIndicativeProvManage>
	 */
	public List<JcIndicativeProvManage> getNaturalManageList() {

		List<JcIndicativeProvManage> jcIndicativeProvManages = iSupIndicativeProvManage.lambdaQuery().list();

		if (CollectionUtils.isEmpty(jcIndicativeProvManages)) {
			return Lists.newArrayList();
		}

		return jcIndicativeProvManages;

	}
}
