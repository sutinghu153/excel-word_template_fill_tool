package com.hnup.osmp.jiance.service.compute;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.common.snowflake.starter.singe.SnowflakeService;
import com.hnup.osmp.jiance.operation.computes.ComputeCur;
import com.hnup.osmp.jiance.repository.dao.IJcFieldOfTableDetailDAO;
import com.hnup.osmp.jiance.repository.dao.IJcOperationDAO;
import com.hnup.osmp.jiance.repository.entity.JcOperation;
import com.hnup.osmp.jiance.service.sqlByMap.SqlByMapService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  自定义公式表中计算服务层
 * @data2021/11/11,10:44
 * @authorsutinghu
 */
@Service
public class ComputeService {

	@Autowired
	private IJcOperationDAO iSupOperation;

	@Autowired
	private IJcFieldOfTableDetailDAO iSupFieldOfTableDetail;

	@Autowired
	private SqlByMapService sqlByMapService;

	@Autowired
	private SnowflakeService snowflakeService;

	/**
	 * 功能描述: 根据配置的公式计算结果
	 * @author sutinghu
	 * @date
	 * @param computeId 参数
	 * @return java.lang.Boolean
	 */
	public void computeById(Long computeId) {
		List<JcOperation> jcOperations = iSupOperation.lambdaQuery().eq(JcOperation::getActionId,computeId).list();
		if (CollectionUtils.isEmpty(jcOperations)){
			throw new DeclareException("当前项目没有配置的公式");
		}
		JcOperation jcOperation = jcOperations.get(0);

		// 获取查询语句
		String query = OperationBuild.getQuery(jcOperation.getActionTable(), jcOperation.getActionFields(), jcOperation.getActionSign());

		// 获取数据
		List<Map<String, Object>> value = iSupFieldOfTableDetail.getTableDetails(query);
		List<Map<Object, Object>> mapList = new ArrayList<>();

		// 根据公式计算每列数据
		value.stream().forEach(e->{
			String operation = OperationBuild.getOperation(e, jcOperation.getActionOperation());
			Object result = ComputeCur.getInstance(operation).getResult();
			Map<Object, Object> values = new HashMap<>();
			values.put(e.get(jcOperation.getActionSign()),result);
			mapList.add(values);
		});

		// 更新数据
		sqlByMapService.updateByMap(
			jcOperation.getActionTable(),
			jcOperation.getActionSign(),
			jcOperation.getActionResult(),
			mapList);

	}

	/**
	 * 功能描述: 公式清单
	 * @author sutinghu
	 * @date
	 * @return java.util.List<com.hnup.osmp.jiance.repository.entity.JcOperation>
	 */
	public List<JcOperation> getOperationList() {
		return iSupOperation.lambdaQuery().list();
	}

	/**
	 * 功能描述: 新增公式
	 * @author sutinghu
	 * @date
	 * @param fields
	 * @param table
	 * @param operation
	 * @param desc
	 * @param resultFeild
	 * @param sign 参数
	 * @return void
	 */
	public void addOperation(String fields, String table, String operation, String desc, String resultFeild, String sign) {

		JcOperation jcOperation = new JcOperation();
		jcOperation.setActionDesc(desc);
		jcOperation.setActionFields(fields);
		jcOperation.setActionId(snowflakeService.nextId());
		jcOperation.setActionOperation(operation);
		jcOperation.setActionTable(table);
		jcOperation.setActionSign(sign);
		jcOperation.setActionResult(resultFeild);
		iSupOperation.save(jcOperation);

	}

	/**
	 * 功能描述:更新公式
	 * @author sutinghu
	 * @date
	 * @param fields
	 * @param table
	 * @param operation
	 * @param desc
	 * @param resultFeild
	 * @param sign 参数
	 * @param operationId
	 * @return void
	 */
	public void updateOperation(String fields, String table, String operation, String desc, String resultFeild, String sign, Long operationId) {

		List<JcOperation> jcOperations = iSupOperation.lambdaQuery().eq(JcOperation::getActionId,operationId).list();

		if (CollectionUtils.isEmpty(jcOperations)) {
			throw new DeclareException("公式不存在或已经过期");
		}

		JcOperation jcOperation = jcOperations.get(0);
		jcOperation.setActionDesc(desc);
		jcOperation.setActionFields(fields);
		jcOperation.setActionOperation(operation);
		jcOperation.setActionTable(table);
		jcOperation.setActionSign(sign);
		jcOperation.setActionResult(resultFeild);

		iSupOperation.updateById(jcOperation);
	}

	/**
	 * 功能描述: 删除公式
	 * @author sutinghu
	 * @date
	 * @param operationId 参数
	 * @return void
	 */
	public void deleteOperation(Long operationId) {

		List<JcOperation> jcOperations = iSupOperation.lambdaQuery().eq(JcOperation::getActionId,operationId).list();

		if (CollectionUtils.isEmpty(jcOperations)) {
			throw new DeclareException("公式不存在或已经过期");
		}

		iSupOperation.removeById(operationId);
	}
}
