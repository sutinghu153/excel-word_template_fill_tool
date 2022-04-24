package com.hnup.osmp.jiance.service.sqlByMap;

import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.repository.dao.IJcFieldOfTableDetailDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 *  通过map 批量操作SQL
 * @data2021/11/13,11:51
 * @authorsutinghu
 */
@Service
public class SqlByMapService {

	@Autowired
	private IJcFieldOfTableDetailDAO iSupFieldOfTableDetail;

	/**
	 * 功能描述: 批量插入
	 * @author sutinghu
	 * @date
	 * @param tableName
	 * @param columnMap
	 * @param mapList 参数
	 * @return void
	 */
	public void insertByMap(String tableName,
							Map<String, Object> columnMap,
							List<Map<String, Object>> mapList){

		// 校验
		this.checkInsertParm(tableName,columnMap,mapList);
		// 插入
		iSupFieldOfTableDetail.insertByMap(tableName,columnMap,mapList);
	}

	/**
	 * 功能描述:批量更新
	 * @author sutinghu
	 * @date
	 * @param tableName
	 * @param id
	 * @param resultName
	 * @param mapList 参数
	 * @return void
	 */
	public void updateByMap(String tableName,
							String id,
							String resultName,
							List<Map<Object, Object>> mapList){

		// 校验
		this.checkUpdateParm("\""+tableName+"\"", id, resultName, mapList);
		// 更新
		iSupFieldOfTableDetail.updateByMap("\""+tableName+"\"", id, resultName, mapList);
	}


	/**
	 * 功能描述:校验更新参数合法性
	 * @author sutinghu
	 * @date
	 * @param tableName
	 * @param id
	 * @param resultName
	 * @param mapList 参数
	 * @return void
	 */
	private void checkUpdateParm(String tableName,
								 String id,
								 String resultName,
								 List<Map<Object, Object>> mapList) {

		Assert.notNull(tableName,"表名不能为空");
		Assert.notNull(id,"主键名不能为空");
		Assert.notNull(resultName,"目标字段不能为空");
		Assert.notNull(mapList,"值不能为空");

	}



	/**
	 * 功能描述: 校验插入参数合法性
	 * @author sutinghu
	 * @date
	 * @param tableName
	 * @param columnMap
	 * @param mapList 参数
	 * @return void
	 */
	private void checkInsertParm(String tableName, Map<String, Object> columnMap, List<Map<String, Object>> mapList) {

		Assert.notNull(tableName,"表名不能为空");
		Assert.notNull(columnMap,"插入字段不能为空");
		Assert.notNull(mapList,"值不能为空");

		mapList.forEach(e->{
			for (Map.Entry<String, Object> map:e.entrySet()) {
				Boolean bool = columnMap.containsKey(map.getKey());
				if (!bool) {
					throw new DeclareException("字段列-[key,value]-非法："+map.getKey()+",不存在的表字段");
				}
			}
		});
	}

	/**
	 * 功能描述:根据SQL语句查询一个结果
	 * @author sutinghu
	 * @date
	 * @param sql 参数
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> getTemplateOrdinary(String sql) {

		if(StringUtils.isBlank(sql)) {
			throw new DeclareException("查询条件不能为空");
		}
		return iSupFieldOfTableDetail.getTemplateOrdinary(sql);
	}

	/**
	 * 功能描述: 根据SQL语句查询一个列表
	 * @author sutinghu
	 * @date
	 * @param sql 参数
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public List<Map<String, Object>> getTemplateList( String sql) {
		if(StringUtils.isBlank(sql)) {
			throw new DeclareException("查询条件不能为空");
		}
		return iSupFieldOfTableDetail.getTemplateList(sql);
	}

}
