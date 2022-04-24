package com.hnup.osmp.jiance.service.assess;

import com.google.common.collect.Lists;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.osmp.jiance.repository.dao.IJcCountDetailDAO;
import com.hnup.osmp.jiance.repository.dao.IJcFieldOfTableDetailDAO;
import com.hnup.osmp.jiance.repository.entity.JcCountDetail;
import com.hnup.osmp.jiance.repository.entity.JcFieldOfTableDetails;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  省自然资源厅重点督察工作服务层
 * @data2021/11/5,9:41
 * @authorsutinghu
 */
@Service
public class CountryNaturalResourcesService {

	@Autowired
	private IJcFieldOfTableDetailDAO iSupFieldOfTableDetail;

	@Autowired
	private IJcCountDetailDAO iSupCountDetail;

	public Map<String, Object> getTableByType(Long countId, String countTime) {

		// 校验
		this.checkCountType(countId);

		// 解析表结构
		Map<String, Object> tableMap = this.buildTableHead(countId);

		// 构造查询条件
		String query = this.buildQuery(tableMap,countTime);

		Map<String, Object> result = new HashMap<>(16);

		List<Map<String, String>> tableField = (List<Map<String, String>>) tableMap.get("fieldMap");

		List<Map<String, String>> value = new ArrayList<>();
		tableField.stream().forEach(e->{
			Map<String, String> map = new HashMap<>();
			for (Map.Entry<String, String> o:e.entrySet()) {
				map.put("key",o.getKey());
				map.put("value",o.getValue());
			}
			value.add(map);
		});

		result.put("relation",value);

		result.put("data",iSupFieldOfTableDetail.getTableDetails(query));

		return result;
	}


	/**
	 * 功能描述: 构造查询语句
	 * @author sutinghu
	 * @date
	 * @param tableMap
	 * @param countTime 参数
	 * @return java.lang.String
	 */
	public String buildQuery(Map<String, Object> tableMap, String countTime){
		String from =ConstantJiance.FROM +"\""+ tableMap.get("tableName")+"\"";
		List<Map<String, String>> fieldMap = (List<Map<String, String>>) tableMap.get("fieldMap");
		String select = ConstantJiance.SELECT;
		for (Map<String, String> mapValue :fieldMap) {
			for (Map.Entry<String, String> map:mapValue.entrySet()) {
				select = select + " " + map.getKey() +  " ," ;
			}
		}
		select = select.substring(ConstantJiance.ZERO, select.length()-ConstantJiance.ONE);
		String where = ConstantJiance.WHERE + ConstantJiance.COUNT_TIME;
		if (StringUtils.isNotBlank(countTime)) {
			where = where + " '" + countTime + "' ";

			return select + from + where;
		}
		return select + from;
	}

	/**
	 * 功能描述: 校验是否对应表存在
	 * @author sutinghu
	 * @date
	 * @param countId 参数
	 * @return void
	 */
	public JcCountDetail checkCountType(Long countId){
		List<JcCountDetail> jcCountDetails = iSupCountDetail
			.lambdaQuery()
			.eq(JcCountDetail::getCountId,countId)
			.list();

		if (CollectionUtils.isEmpty(jcCountDetails)) {
			throw new DeclareException(countId + "对应的实体表不存在");
		}
		return jcCountDetails.get(ConstantJiance.ZERO);
	}

	/**
	 * 功能描述: 获取表信息 - 对应表 及 表头
	 * @author sutinghu
	 * @date
	 * @param  countId
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	public Map<String, Object> buildTableHead(Long countId){
		// 获取对应表 及 表头
		List<JcFieldOfTableDetails> jcFieldOfTableDetails = iSupFieldOfTableDetail.lambdaQuery()
			.eq(JcFieldOfTableDetails::getCountId,countId)
			.eq(JcFieldOfTableDetails::getFieldShowState, ConstantJiance.ONE)
			.list();

		if (CollectionUtils.isEmpty(jcFieldOfTableDetails)) {
			throw new DeclareException(countId + "实体表没有可用字段");
		}

		Map<String, Object> result = new HashMap<>(16);
		// 填充表名
		result.put("tableName", jcFieldOfTableDetails.get(ConstantJiance.ZERO).getTableName());
		// 填充可用字段
		List<Map<String, String>> fieldMap = new ArrayList<>(16);
		// 升序排序先
		jcFieldOfTableDetails.sort(Comparator.comparing(JcFieldOfTableDetails::getSortNum));

		jcFieldOfTableDetails.stream().forEach(e->{
			Map<String, String> map = new HashMap<>(16);
			map.put(e.getFieldName(),e.getFieldMeaning());
			fieldMap.add(map);
		});
		result.put("fieldMap",fieldMap);

		return result;
	}

	public List<Object> getUpdateTime(Long countId) {
		// 校验
		JcCountDetail jcCountDetail = this.checkCountType(countId);
		// 获取表名
		String formName = jcCountDetail.getFormName();
		// 生成sql
		StringBuilder sql = new  StringBuilder(" select DISTINCT(\"count_time\") FROM  ");
		sql.append("\"")
			.append(formName)
			.append("\"")
			.append(";");
		List<Map<String, Object>> tableDetails = iSupFieldOfTableDetail.getTableDetails(sql.toString());
		if (CollectionUtils.isEmpty(tableDetails)) {
			return Lists.newArrayList();
		}
		List<Object> collect = tableDetails.stream().map(e -> {
			if (e.get("count_time") == null) {
				return "其它";
			} else {
				return e.get("count_time");
			}
		}).collect(Collectors.toList());
		return collect;
	}
}
