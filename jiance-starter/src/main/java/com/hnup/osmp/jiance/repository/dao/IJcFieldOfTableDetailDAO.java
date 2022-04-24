package com.hnup.osmp.jiance.repository.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnup.osmp.jiance.repository.entity.JcFieldOfTableDetails;

import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public interface IJcFieldOfTableDetailDAO extends IService<JcFieldOfTableDetails> {
	/**
	 * 功能描述: 获取表数据
	 * @author sutinghu
	 * @date
	 * @param sql 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	List<Map<String, Object>> getTableDetails(String sql);


	void insertByMap(String tableName,
					 Map<String, Object> columnMap,
					 List<Map<String, Object>> mapList);

	void updateByMap(String tableName,
					 String id,
					 String resultName,
					 List<Map<Object, Object>> mapList);

	/**
	 *  返回结果数据结构
	 *  {
	 *   "count": "19"
	 * }
	 * @param sql
	 * @return
	 */
	Map<String, Object> getTemplateOrdinary(String sql);

	/**
	 *  返回结果数据结构
	 *  [
	 *   {
	 *     "parent_name": "省自然资源厅督察工作",
	 *     "count_name": "2020年国家自然资源例行督察反馈问题整改",
	 *     "parent_id": "0",
	 *     "count_id": "1",
	 *     "form_name": "tbcount_countrynaturalresourcesWTZG"
	 *   },
	 *   {
	 *     "parent_name": "省自然资源厅督察工作",
	 *     "count_name": "2020年省厅自然资源例行督察反馈问题整改",
	 *     "parent_id": "0",
	 *     "count_id": "2",
	 *     "form_name": "tbcount_provincenaturalresourcesWTZG"
	 *   }
	 *  ]
	 * @param sql
	 * @return
	 */
	List<Map<String, Object>> getTemplateList( String sql);

}







