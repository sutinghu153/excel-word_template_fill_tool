package com.hnup.osmp.jiance.service.assess;

import com.google.common.collect.Lists;
import com.hnup.osmp.jiance.repository.dao.IJcFarmlandProtectionDAO;
import com.hnup.osmp.jiance.repository.entity.JcFarmlandProtection;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  省自然资源管理-耕地保护
 * @data2021/11/3,10:31
 * @authorsutinghu
 */
@Service
public class FarmlandProtectionService {

	@Autowired
	private IJcFarmlandProtectionDAO iSupFarmlandProtection;

	/**
	 * 功能描述: 获取耕地保护表单
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcFarmlandProtection>
	 */
	public List<JcFarmlandProtection> getFarmlandProtectionList() {

		List<JcFarmlandProtection> jcFarmlandProtections = iSupFarmlandProtection.lambdaQuery().list();

		if (CollectionUtils.isEmpty(jcFarmlandProtections)) {
			return Lists.newArrayList();
		}

		return jcFarmlandProtections;
	}
}
