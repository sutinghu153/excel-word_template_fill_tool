package com.hnup.osmp.jiance.service.file;

import com.hnup.common.export.starter.service.ExportExcelService;
import com.hnup.common.export.starter.vo.ExcelExportVO;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.common.snowflake.starter.singe.SnowflakeService;
import com.hnup.osmp.jiance.repository.dao.IJcFieldOfTableDetailDAO;
import com.hnup.osmp.jiance.repository.dao.IJcFileBelongTypeDAO;
import com.hnup.osmp.jiance.repository.dao.IJcTemplateConfigDAO;
import com.hnup.osmp.jiance.repository.entity.JcCountDetail;
import com.hnup.osmp.jiance.repository.entity.JcFileBelongType;
import com.hnup.osmp.jiance.repository.entity.JcTemplateConfig;
import com.hnup.osmp.jiance.service.assess.CountryNaturalResourcesService;
import com.hnup.osmp.jiance.service.constant.ConstantJiance;
import com.hnup.osmp.jiance.service.sqlByMap.SqlByMapService;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  文件处理服务层
 * @data2021/11/5,11:30
 * @authorsutinghu
 */
@Service
public class FileHandleService {

	@Autowired
	private ExportExcelService exportExcelService;

	@Autowired
	private IJcFieldOfTableDetailDAO iSupFieldOfTableDetail;

	@Autowired
	private CountryNaturalResourcesService countryNaturalResourcesService;

	@Autowired
	private IJcTemplateConfigDAO iSupTemplateConfig;

	@Autowired
	private SnowflakeService snowflakeService;

	@Autowired
	private SqlByMapService sqlByMapService;

	@Autowired
	private IJcFileBelongTypeDAO jcFileBelongType;
	/**
	 * 功能描述: 根据模板及文件导入输入表
	 * @author sutinghu
	 * @date
	 * @param countId
	 * @param file 参数
	 * @return java.lang.Boolean
	 */
	public Boolean importExcleBycount(Long countId, MultipartFile file) {

		// 读取文件
		List<Map<String, String>> maps = null;
		try {
			maps = ReadExcelFile.redExcel(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (CollectionUtils.isEmpty(maps)) {
			return false;
		}

		// 根据countId获取对应的键值对
		// 校验
		countryNaturalResourcesService.checkCountType(countId);
		// 解析表结构
		Map<String, Object> tableMap = countryNaturalResourcesService.buildTableHead(countId);
		List<Map<String, String>> fieldMap = (List<Map<String, String>>) tableMap.get("fieldMap");
		String tableName = (String) tableMap.get("tableName");
		Map<String, Object> columnMap = new HashMap<>();
		List<Map<String, Object>> mapList = new ArrayList<>();

		// 构造表字段
		fieldMap.stream().forEach(o->{
			for (Map.Entry<String, String> value : o.entrySet()) {
				columnMap.put(value.getKey(),value.getValue());
			}
		});


		// 构造批量插入值
		maps.stream().forEach(e->{
			Map<String, Object> map = new HashMap<>();
			String id = null;
			for (Map.Entry<String, Object> field : columnMap.entrySet()) {
				if (!columnMap.values().containsAll(e.keySet())){
					throw new DeclareException("模板不包含某个列："+e.values()+"，请检查模板是否正确");
				}else if(Objects.equals(field.getValue(),"主键")) {
					id = field.getKey();
					map.put(id,snowflakeService.nextId());
				}else if(e.containsKey(field.getValue())) {
					map.put(field.getKey(),e.get(field.getValue()));
				} else {
					map.put(field.getKey(),null);
				}
			}
			mapList.add(map);
		});

		// 批量插入
		sqlByMapService.insertByMap(tableName,columnMap,mapList);

		return true;
	}

	/**
	 * 功能描述: 根据countId 导出表excel
	 * @author sutinghu
	 * @date
	 * @param countId
	 * @param countTime 参数
	 * @param asExOrIm
	 * @return org.springframework.http.ResponseEntity<InputStreamResource>
	 */
	public ResponseEntity<InputStreamResource> exportExcleBycount(Long countId, String countTime, Integer asExOrIm) {

		// 校验
		JcCountDetail jcCountDetail = countryNaturalResourcesService.checkCountType(countId);

		// 解析表结构
		Map<String, Object> tableMap = countryNaturalResourcesService.buildTableHead(countId);

		// 构造查询条件
		String query = countryNaturalResourcesService.buildQuery(tableMap,countTime);

		// 获取结果
		List<Map<String, Object>> resultMap = iSupFieldOfTableDetail.getTableDetails(query);

		List<Map<String, String>> column = (List<Map<String, String>>) tableMap.get("fieldMap");

		ExcelExportVO<Map<String, Object>> exportData = this.buildExcelExportVO(resultMap,column,asExOrIm);

		String name = jcCountDetail.getCountName();

		// 导出
		return exportExcelService.exportExcelResult(exportData, name + ".xlsx");

	}


	/**
	 * 功能描述:构造导出数据
	 * @author sutinghu
	 * @date
	 * @param exportData 待导出的信息
	 * @param column 表头信息
	 * @param asExOrIm
	 * @return com.hnup.common.export.starter.vo.ExcelExportVO<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public ExcelExportVO<Map<String, Object>> buildExcelExportVO(List<Map<String, Object>> exportData,
																 List<Map<String, String>> column, Integer asExOrIm){


		ExcelExportVO<Map<String, Object>> excelExportVO = new ExcelExportVO<>();
		List<Map<String, Object>> exportNewData = new ArrayList<>(16);
		final String[] id = {null};
		column.stream().forEach(e->{
			for (Map.Entry<String, String> map : e.entrySet()) {
				if (ConstantJiance.ID.equals(map.getValue())) {
					id[0] = map.getKey();
					continue;
				}
				if (Objects.equals(ConstantJiance.ONE,asExOrIm)) {
					if (ConstantJiance.RANKING.equals(map.getKey())) {
						continue;
					}
				}
				excelExportVO.addColumnMap(map.getKey(),map.getValue(),30);
			}
		});

		if (Objects.equals(asExOrIm,1)) {
			exportData.stream().forEach(e->{
				Map<String, Object> maps = new HashMap<>(16);
				for (Map.Entry<String, Object> map : e.entrySet()) {
					if (Objects.equals(map.getKey(),id[0])) {
						continue;
					}
					maps.put(map.getKey(),map.getValue());
				}
				exportNewData.add(maps);
			});
		}


		excelExportVO.setContentList(exportNewData);



		return excelExportVO;
	}

	/**
	 * 功能描述: 根据模板ID导出模板流
	 * @author sutinghu
	 * @date
	 * @param templateId 参数
	 * @return Byte[]
	 */
	public byte[] exportWordTemplate(Long templateId) {

		Assert.notNull(templateId,"模板ID不能为空");
		List<JcTemplateConfig> jcTemplateConfigs = iSupTemplateConfig
			.lambdaQuery()
			.eq(JcTemplateConfig::getTempletId,templateId)
			.list();

		if (CollectionUtils.isEmpty(jcTemplateConfigs)) {
			return null;
		}

		return jcTemplateConfigs.get(0).getTemplet();
	}

	/**
	 * 功能描述: 上传模板
	 * @author sutinghu
	 * @date
	 * @param templetName
	 * @param creater
	 * @param file 参数
	 * @param date
	 * @param
	 * @return void
	 */
	@SneakyThrows
	public void addWordTemplate(String templetName,
								MultipartFile file,
								String modelName,
								Long belongId,
								String fileType,
								String wordOrExcel,
								Date date,
								String creater) {

		JcTemplateConfig jcTemplateConfig = new JcTemplateConfig();
		jcTemplateConfig.setCreater(creater);
		byte[] bytes = file.getBytes();

		jcTemplateConfig.setTemplet(bytes);
		jcTemplateConfig.setBelongId(belongId);
		jcTemplateConfig.setModelName(modelName);
		jcTemplateConfig.setFileType(fileType);
		jcTemplateConfig.setWordOrExcel(wordOrExcel);
		jcTemplateConfig.setCreateTime(date);
		jcTemplateConfig.setUpdateTime(date);
		jcTemplateConfig.setTempletName(templetName);
		jcTemplateConfig.setTempletId(snowflakeService.nextId());

		iSupTemplateConfig.save(jcTemplateConfig);

	}

	/**
	 * 功能描述: 删除模板
	 * @author sutinghu
	 * @date
	 * @param templeteId 参数
	 * @return void
	 */
	public void deleteWordTemplate(Long templeteId) {

		Assert.notNull(templeteId,"模板ID不能为空");
		List<JcTemplateConfig> jcTemplateConfigs = iSupTemplateConfig
			.lambdaQuery()
			.eq(JcTemplateConfig::getTempletId,templeteId)
			.list();

		if (CollectionUtils.isEmpty(jcTemplateConfigs)) {
			throw new DeclareException("不存在的模板");
		}

		iSupTemplateConfig.removeById(templeteId);

	}

	/**
	 * 功能描述:更新模板
	 * @author sutinghu
	 * @date
	 * @param templetName
	 * @param templeteId
	 * @param creater
	 * @param file
	 * @param date 参数
	 * @param
	 * @return void
	 */
	@SneakyThrows
	public void updateWordTemplate(Long templeteId,
								   String templetName,
								   MultipartFile file,
								   String modelName,
								   Long belongId,
								   String fileType,
								   String wordOrExcel,
								   Date date,
								   String creater) {

		List<JcTemplateConfig> jcTemplateConfigs = iSupTemplateConfig
			.lambdaQuery()
			.eq(JcTemplateConfig::getTempletId,templeteId)
			.list();

		if (CollectionUtils.isEmpty(jcTemplateConfigs)) {
			throw new DeclareException("该模板已失效，请重新上传");
		}

		JcTemplateConfig jcTemplateConfig = jcTemplateConfigs.get(0);

		jcTemplateConfig.setTemplet(file.getBytes());
		jcTemplateConfig.setTempletName(templetName);
		jcTemplateConfig.setBelongId(belongId);
		jcTemplateConfig.setModelName(modelName);
		jcTemplateConfig.setFileType(fileType);
		jcTemplateConfig.setWordOrExcel(wordOrExcel);
		jcTemplateConfig.setUpdateTime(date);
		jcTemplateConfig.setCreater(creater);

		iSupTemplateConfig.saveOrUpdate(jcTemplateConfig);

	}

	public List<JcTemplateConfig> getemplateList(Long beLongId) {
		return iSupTemplateConfig.lambdaQuery().eq(JcTemplateConfig::getBelongId,beLongId).list();
	}

	public List<Map<String, String>> getGroup() {
		List<JcFileBelongType> list = jcFileBelongType.lambdaQuery().list();
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		List<Map<String, String>> lists = new ArrayList<>();
		Map<String, List<JcFileBelongType>> collect = list.stream().collect(Collectors.groupingBy(JcFileBelongType::getMoudelName));
		collect.forEach((k,v)->{
			Map<String, String> map = new HashMap<>(2);
			map.put("key",k);
			map.put("value",v.get(0).getBelongDesc());
			lists.add(map);
		});
		return lists;
	}
}
