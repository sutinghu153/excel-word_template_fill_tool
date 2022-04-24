package com.hnup.osmp.jiance.service.assess;

import com.google.common.collect.Lists;
import com.hnup.osmp.jiance.repository.dao.IJcCountDetailDAO;
import com.hnup.osmp.jiance.repository.entity.JcCountDetail;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  省自然资源厅重点督察首页服务层
 * @data2021/11/8,15:41
 * @authorsutinghu
 */
@Service
public class CountryNaturalSuperviseServise {

	@Autowired
	private IJcCountDetailDAO iSupCountDetail;

	/**
	 * 功能描述: 获取首页列表
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public List<JcCountDetail> getSuperviseTable() {

		List<JcCountDetail> jcCountDetails = iSupCountDetail.lambdaQuery().list();

		if (CollectionUtils.isEmpty(jcCountDetails)) {
			return Lists.newArrayList();
		}

		// 过滤不需要的记录
		jcCountDetails = jcCountDetails.stream().filter(e->e.getFormName() !=null ).collect(Collectors.toList());

		// 修改不需要的结果
		jcCountDetails.stream().forEach(e->{
			if (Objects.equals(e.getParentId(), Long.parseLong(ConstantJiance.ZERO.toString()))) {
				e.setParentName(null);
			}
		});

		return jcCountDetails;
	}
}
