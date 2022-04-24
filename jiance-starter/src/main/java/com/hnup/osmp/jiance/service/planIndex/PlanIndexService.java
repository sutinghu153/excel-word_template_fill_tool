package com.hnup.osmp.jiance.service.planIndex;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.common.snowflake.starter.singe.SnowflakeService;
import com.hnup.osmp.jiance.repository.dao.IJcPlanIndexDAO;
import com.hnup.osmp.jiance.repository.entity.JcPlanIndex;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *  规划指标服务层
 * @data2021/11/11,15:48
 * @authorsutinghu
 */
@Service
public class PlanIndexService {

	@Autowired
	private IJcPlanIndexDAO iSupPlanIndex;

	@Autowired
	private SnowflakeService snowflakeService;

	/**
	 * 功能描述: 获取规划指标
	 * @author sutinghu
	 * @date
	 * @param indexType 参数
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcPlanIndex>
	 */
	public List<JcPlanIndex> getPlanIndexList(String indexType) {
		return iSupPlanIndex.lambdaQuery().eq(JcPlanIndex::getClassType,indexType).list();
	}

	/**
	 * 功能描述: xz
	 * @author sutinghu
	 * @date
	 * @param jcPlanIndex 参数
	 * @return void
	 */
	public void addPlanIndex(JcPlanIndex jcPlanIndex) {

		if (Objects.isNull(jcPlanIndex)) {
			throw new DeclareException("不能为空");
		}

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		jcPlanIndex.setBasicTime(date);
		jcPlanIndex.setMonitorTime(date);

		if (Objects.isNull(jcPlanIndex.getId())) {
			jcPlanIndex.setId(snowflakeService.nextId());
			iSupPlanIndex.save(jcPlanIndex);
		}else {
			iSupPlanIndex.updateById(jcPlanIndex);
		}

	}

	/**
	 * 功能描述: xg
	 * @author sutinghu
	 * @date
	 * @param jcPlanIndex 参数
	 * @return void
	 */
	public void updatePlanIndex(JcPlanIndex jcPlanIndex) {

		if (Objects.isNull(jcPlanIndex)) {
			throw new DeclareException("不能为空");
		}

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		jcPlanIndex.setBasicTime(date);
		jcPlanIndex.setMonitorTime(date);


		iSupPlanIndex.updateById(jcPlanIndex);

	}

	/**
	 * 功能描述:sc
	 * @author sutinghu
	 * @date
	 * @param id 参数
	 * @return void
	 */
	public void deletePlanIndex(Long id) {

		Asserts.notNull(id,"主键不能为空");
		iSupPlanIndex.removeById(id);

	}
}
