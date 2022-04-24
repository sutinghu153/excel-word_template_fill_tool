package com.hnup.osmp.jiance.service.button;

import com.hnup.common.security.oauth2.UserPrincipal;
import com.hnup.osmp.jiance.repository.dao.IJcCountPowerDAO;
import com.hnup.osmp.jiance.repository.entity.JcCountPower;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *  考核指标-省自然资源厅重点督察-按钮控制 服务层
 * @data2021/11/4,10:57
 * @authorsutinghu
 */
@Service
public class ButtonPowerService {

	@Autowired
	private IJcCountPowerDAO iSupCountPower;

	private Map<String, Boolean> result = new HashMap<String, Boolean>() {
		{
			put(ConstantJiance.EXPORT,true);
			put(ConstantJiance.IMPORT,false);
			put(ConstantJiance.IMPORT_TEMPLATE,false);
		}
	};
	/**
	 * 功能描述: 根据用户权限和统计类型获取导入导出权限
	 * @author sutinghu
	 * @date
	 * @param userPrincipal
	 * @param countId 表id
	 * @return java.util.Map<java.lang.String,java.lang.Boolean>
	 */
	public Map<String, Boolean> getButtonPower(UserPrincipal userPrincipal,Long countId) {

		List<JcCountPower> jcCountPowers = iSupCountPower.lambdaQuery()
			.eq(JcCountPower::getCountId,countId)
			.eq(JcCountPower::getUserId,userPrincipal.getUserId())
			.list();

		if (CollectionUtils.isEmpty(jcCountPowers)) {
			return this.result;
		}
		//TODO:为了减少实施的配置工作量 暂时不做导出按钮的权限控制 默认所有人都有导出权限 若有相关业务需要 再进行业务逻辑的实现
		//this.result.put(ConstantJiance.EXPORT,jcCountPowers.stream().anyMatch(e->Objects.equals(e.getExportPower(),ConstantJiance.ONE)));
		this.result.put(ConstantJiance.IMPORT, jcCountPowers.stream().anyMatch(e->Objects.equals(e.getImportPower(),ConstantJiance.ONE)));
		this.result.put(ConstantJiance.IMPORT_TEMPLATE, jcCountPowers.stream().anyMatch(e->Objects.equals(e.getImportPower(),ConstantJiance.ONE)));
		return this.result;
	}

}
