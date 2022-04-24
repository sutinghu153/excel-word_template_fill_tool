package com.hnup.osmp.jiance.service.assess;

import com.hnup.osmp.jiance.model.dto.PronviceNaturalReourceTreeVO;
import com.hnup.osmp.jiance.repository.dao.IJcCountDetailDAO;
import com.hnup.osmp.jiance.repository.entity.JcCountDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *  省自然资源厅重点督察服务层
 * @data2021/11/4,9:57
 * @authorsutinghu
 */
@Service
public class PronviceNaturalReourceSuperviseService {

	@Autowired
	private IJcCountDetailDAO iSupCountDetail;

	/**
	 * 功能描述: 获取树结构
	 * @author sutinghu
	 * @date
	 * @param
	 * @return java.util.List<com.hnup.osmp.jiance.model.dto.PronviceNaturalReourceTreeVO>
	 */
	public PronviceNaturalReourceTreeVO getTree() {
		PronviceNaturalReourceTreeVO trees = new PronviceNaturalReourceTreeVO();
		List<JcCountDetail> jcCountDetails = iSupCountDetail.lambdaQuery().list();
		// 获取根节点
		JcCountDetail jcCountDetail = jcCountDetails.stream()
			.filter(e->e.getParentId() == null)
			.collect(Collectors.toList())
			.get(0);
		trees = buildTree(jcCountDetails, jcCountDetail,trees);
		return trees;
	}

	/**
	 * 功能描述: 递归构造统计树
	 * @author sutinghu
	 * @date
	 * @param jcCountDetails 统计列表
	 * @param jcCountDetail 根节点
	 * @return com.hnup.osmp.jiance.model.dto.PronviceNaturalReourceTreeVO
	 */
	public PronviceNaturalReourceTreeVO buildTree(List<JcCountDetail> jcCountDetails, JcCountDetail jcCountDetail, PronviceNaturalReourceTreeVO pronviceNaturalReource){
		pronviceNaturalReource.setCountId(jcCountDetail.getCountId());
		pronviceNaturalReource.setCountName(jcCountDetail.getCountName());
		List<JcCountDetail> supCounts  = jcCountDetails
			.stream()
			.filter(e->Objects.equals(e.getParentId(), jcCountDetail.getCountId()))
			.collect(Collectors.toList());
		// 填充孩子节点 如果没有满足孩子节点的情况则退出
		if (CollectionUtils.isEmpty(supCounts)) {
			return pronviceNaturalReource;
		}
		// 如果有满足孩子节点的情况 则填充
		List<PronviceNaturalReourceTreeVO> children = new ArrayList<>();
		supCounts.stream().forEach(e->{
			PronviceNaturalReourceTreeVO pronviceNaturalReourceTree = new PronviceNaturalReourceTreeVO();
			children.add(buildTree(jcCountDetails,e,pronviceNaturalReourceTree));
			pronviceNaturalReource.setChildren(children);
		});
		return pronviceNaturalReource;
	}

}
