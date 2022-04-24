package com.hnup.osmp.jiance.service.file;

import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.aspose.words.SaveOptions;
import com.google.common.collect.Lists;
import com.hnup.common.export.starter.service.ExportExcelService;
import com.hnup.common.export.starter.vo.ExcelExportVO;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.common.security.oauth2.UserPrincipal;
import com.hnup.common.snowflake.starter.singe.SnowflakeService;
import com.hnup.osmp.jiance.model.dto.HistoryWordDTO;
import com.hnup.osmp.jiance.repository.dao.IJcFileBelongTypeDAO;
import com.hnup.osmp.jiance.repository.dao.IJcLanduserFileDAO;
import com.hnup.osmp.jiance.repository.dao.IJcTemplateConfigDAO;
import com.hnup.osmp.jiance.repository.entity.JcFileBelongType;
import com.hnup.osmp.jiance.repository.entity.JcHistoricWords;
import com.hnup.osmp.jiance.repository.entity.JcLanduserFile;
import com.hnup.osmp.jiance.repository.entity.JcTemplateConfig;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 *  填充模板并输出
 * @data2021/11/17,18:00
 * @author sutinghu
 */
@Service
@Slf4j
public class WordTemplateService {

	@Autowired
	private FeildDataService feildDataService;

	@Autowired
	private IJcTemplateConfigDAO iSupTemplateConfig;

	@Autowired
	private HistoricWordsService historicWordsService;

	@Autowired
	private IJcFileBelongTypeDAO jcFileBelongType;

	@Autowired
	private IJcLanduserFileDAO jcLanduserFile;

	@Autowired
	private SnowflakeService snowflakeService;

	private final String WORD = "word";

	private final String EXCEL = "excel";
	/**
	 * 功能描述: 填充模板
	 * @author sutinghu
	 * @date
	 * @param templateId 参数
	 * @return byte[]
	 */
	@SneakyThrows
	public byte[] buildTemplate(Long templateId){

		// 获取实体
		JcTemplateConfig jcTemplateConfig = this.getEntity(templateId);

		// 获取数据
		List<Map<String, Object>> mapData = feildDataService.buildTemplateData(jcTemplateConfig.getTemplateSql(),false,null,null,null);

		// 获取模板
		byte[] template = jcTemplateConfig.getTemplet();

		// 模板填充
		byte[] values = FillByMergeUtils.buildTemplateWord(template,mapData);

//		if(values != null){
//			String filepath ="C:\\Users\\MSI\\Desktop\\" + "123.docx";
//			File file  = new File(filepath);
//			if(file.exists()){
//				file.delete();
//			}
//			FileOutputStream fos = new FileOutputStream(file);
//			fos.write(values,0,values.length);
//			fos.flush();
//			fos.close();
//		}

		// 前端托管
		return values;
	}

	/**
	 * 功能描述: 根据模板id 获取SQL语句
	 * @author sutinghu
	 * @date
	 * @param templateId 模板id
	 * @return JcTemplateConfig
	 */
	public JcTemplateConfig getEntity(Long templateId){
		List<JcTemplateConfig> jcTemplateConfigs = iSupTemplateConfig.lambdaQuery()
			.eq(JcTemplateConfig::getTempletId,templateId).list();

		if (CollectionUtils.isEmpty(jcTemplateConfigs)) {
			throw new DeclareException("没有配置可查询的SQL");
		}
		return jcTemplateConfigs.get(0);
	}

	/**
	 * 功能描述: 获取历史生成文件
	 * 基本逻辑：根据模板id 年份 月份查找文件，如果存在历史文件 则获取 否则生成
	 * 细节：
	 * （1）本月数据生成的条件是本月已满
	 * （2）如果查询数据已存在 则直接返回 否则调用方法填充模板
	 * （3）
	 * @author sutinghu
	 * @date
	 * @param templeteId
	 * @param fileType
	 * @param year
	 * @param month 参数
	 * @return byte[]
	 */
	public byte[] getWordHistory(Long templeteId,
								 String fileType,
								 String year,
								 String month,
								 String day,
								 Boolean executeDate){

		// 校验
		historicWordsService.checkDataAvailability(templeteId);
		Boolean aBoolean = DateCheckService.checkDate(year, month, day);
		byte[] values = null;
		if (aBoolean) {
			// 获取历史缓存数据
			JcHistoricWords words = historicWordsService.getWordsByTemplateId(templeteId, fileType, year, month, day);

			// 如果有缓存数据 则返回
			if (Objects.nonNull(words)) {
				return words.getWordData();
			}

			// 如果是空的 就根据对应的模板生成文件 并缓存
			List<JcTemplateConfig> templateConfigs = iSupTemplateConfig.lambdaQuery().eq(JcTemplateConfig::getTempletId, templeteId).list();

			if (CollectionUtils.isEmpty(templateConfigs)) {
				throw new DeclareException("不存在对应模板 请检查配置！");
			}

			JcTemplateConfig jcTemplateConfig = templateConfigs.get(0);

			// 根据SQL配置生成数据
			List<Map<String, Object>> mapData = feildDataService.buildTemplateData(jcTemplateConfig.getTemplateSql(),executeDate,year,month,day);

			// 获取模板
			byte[] template = jcTemplateConfig.getTemplet();

			// 模板填充
			values = FillByMergeUtils.buildTemplateWord(template,mapData);

			// 缓存数据
			historicWordsService.saveOrupdateWordsByDay(templeteId,fileType,values, year,  month, day,jcTemplateConfig.getTempletName());
		}else {
			throw new DeclareException(year+"年"+month+"月"+"时间未结束，暂不支持数据统计");
		}

		// 返回结果
		return values;

	}

	/**
	 * 功能描述: 根据参数获取文件列表
	 * @author sutinghu
	 * @date
	 * @param belongId
	 * @param year
	 * @param month
	 * @param day 参数
	 * @return java.util.List<com.hnup.osmp.jiance.model.dto.HistoryWordDTO>
	 */
	public List<HistoryWordDTO> getFileList(Long belongId, String year, String month, String day) {

		// 查询配置
		List<JcTemplateConfig> templateConfigs = iSupTemplateConfig
			.lambdaQuery()
			.eq(JcTemplateConfig::getBelongId, belongId)
			.list();

		if (CollectionUtils.isEmpty(templateConfigs)) {
			return Lists.newArrayList();
		}
		List<HistoryWordDTO> result = new ArrayList<>();
		// 构造列表参数
		templateConfigs.forEach(e->{
			HistoryWordDTO historyWord = new HistoryWordDTO();
			historyWord.setDay(day);
			historyWord.setMonth(month);
			historyWord.setYear(year);
			historyWord.setFileType(e.getFileType());
			historyWord.setTempleteId(e.getTempletId());
			historyWord.setFileName(e.getTempletName());
			historyWord.setWordOrExcel(e.getWordOrExcel());
			result.add(historyWord);
		});

		return result;
	}

	/**
	 * 功能描述: 根据参数生成文件
	 * @author sutinghu
	 * @date
	 * @param templeteId
	 * @param wordOrExcel
	 * @param fileType
	 * @param year
	 * @param month
	 * @param day 参数
	 * @return byte[]
	 */
	public byte[] getHistoryFileWord(Long templeteId,
									 String wordOrExcel,
									 String fileType,
									 String year,
									 String month,
									 String day,
									 Boolean executeDate) {

		if (Objects.equals(WORD,wordOrExcel)) {

			return 	this.getWordHistory(templeteId, fileType, year, month, day,executeDate);

		}else if (Objects.equals(EXCEL,wordOrExcel)) {

			return this.getExcelHistory(templeteId, fileType, year, month, day,executeDate);

		}else {

			throw new DeclareException("不存在的类型");

		}

	}

	@SneakyThrows
	public byte[] getHistoryFilePdf(Long templeteId,
									String wordOrExcel,
									String fileType,
									String year,
									String month,
									String day,
									Boolean executeDate) {

		if (Objects.equals(WORD,wordOrExcel)) {

			byte[] wordHistory = this.getWordHistory(templeteId, fileType, year, month, day,executeDate);

			InputStream is = new ByteArrayInputStream(wordHistory);

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			Document doc = new Document(is);

			doc.save(out, SaveOptions.createSaveOptions(SaveFormat.PDF));

			return out.toByteArray();

		}else if (Objects.equals(EXCEL,wordOrExcel)) {

			byte[] excelHistory = this.getExcelHistory(templeteId, fileType, year, month, day,executeDate);

			PdfSaveOptions xlsSaveOption = new PdfSaveOptions();
			xlsSaveOption.setAllColumnsInOnePagePerSheet(true);

			Workbook workbook = null;
			ByteArrayOutputStream handledStream = new ByteArrayOutputStream();
			try {
				ByteArrayOutputStream baos;
				InputStream fileStream = new ByteArrayInputStream(excelHistory);
				workbook = new Workbook(fileStream);
				workbook.save(handledStream, xlsSaveOption);
			} catch (Exception ex) {
				log.error("Excel 文件流转换错误", ex);
			} finally {
				if (workbook != null) {
					workbook.dispose();
				}
			}

			return handledStream.toByteArray();

		}else {

			throw new DeclareException("不存在的类型");

		}

	}

	@SneakyThrows
	public byte[] getExcelHistory(Long templeteId, String fileType, String year, String month, String day,Boolean executeDate){

		// 校验
		historicWordsService.checkDataAvailability(templeteId);
		Boolean aBoolean = DateCheckService.checkDate(year, month, day);
		byte[] values = null;
		// 获取一次
		if (aBoolean) {
			// 获取历史缓存数据
			JcHistoricWords words = historicWordsService.getWordsByTemplateId(templeteId, fileType, year, month, day);
			// 如果有缓存数据 则返回
			if (Objects.nonNull(words)) {
				return words.getWordData();
			}

			// 如果是空的 就根据对应的模板生成文件 并缓存
			List<JcTemplateConfig> templateConfigs = iSupTemplateConfig.lambdaQuery().eq(JcTemplateConfig::getTempletId, templeteId).list();

			if (CollectionUtils.isEmpty(templateConfigs)) {
				throw new DeclareException("不存在对应模板 请检查配置！");
			}

			JcTemplateConfig jcTemplateConfig = templateConfigs.get(0);

			// 根据SQL配置生成数据
			List<Map<String, Object>> list = feildDataService.buildTemplateData(jcTemplateConfig.getTemplateSql(),executeDate,year,month,day);

			if (CollectionUtils.isEmpty(list)) {
				return null;
			}

			// 模板填充
			values = FillByMergeUtils.buildTemplateExcel(jcTemplateConfig.getTemplet(),list);

			// 缓存数据
			historicWordsService.saveOrupdateWordsByDay(templeteId,fileType,values,year,month,day,jcTemplateConfig.getTempletName());

			// 更新策略
		}else {
			throw new DeclareException(year+"年"+month+"月"+"时间未结束，暂不支持数据统计");
		}

		return values;
	}

	/**
	 * 功能描述: 构造excel
	 * @author sutinghu
	 * @date
	 * @param list 参数
	 * @return com.hnup.common.export.starter.vo.ExcelExportVO<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	public ExcelExportVO<Map<String, Object>> buildExcelExportVO(List<Map<String, Object>> list){

		ExcelExportVO<Map<String, Object>> excelExportVO = new ExcelExportVO<>();
		List<Map<String, Object>> exportNewData = new ArrayList<>(16);

		// 获取列
		Map<String, Object> objectMap = list.get(0);
		for (Map.Entry<String, Object> map:objectMap.entrySet()) {
			excelExportVO.addColumnMap(map.getKey(),map.getKey(),30);
		}

		// 填充excel
		for (Map<String, Object> map : list) {
			Map<String, Object> maps = new HashMap<>(16);
			for (Map.Entry<String, Object> mapSec:map.entrySet()) {
				maps.put(mapSec.getKey(),mapSec.getValue());
				exportNewData.add(maps);
			}
		}

		excelExportVO.setContentList(exportNewData);

		return excelExportVO;
	}

	public static final byte[] input2byte(InputStream inStream)
		throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = inStream.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}

	public List<JcFileBelongType> getMoudelList(String modelName) {

		return jcFileBelongType.lambdaQuery().eq(JcFileBelongType::getMoudelName,modelName).list();

	}

	public JcLanduserFile getLandUserImage(String year) {

		List<JcLanduserFile> list = jcLanduserFile.lambdaQuery().eq(JcLanduserFile::getYear, year).list();

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}else {
			return list.get(0);
		}

	}

	@SneakyThrows
	public void saveLandUserImage(String year, MultipartFile file) {

		JcLanduserFile jcLanduser = new JcLanduserFile();
		jcLanduser.setId(snowflakeService.nextId());
		jcLanduser.setFileName(file.getOriginalFilename());
		jcLanduser.setYear(year);
		jcLanduser.setFileType(file.getContentType().trim());
		jcLanduser.setFileOut(file.getBytes());

		jcLanduserFile.saveOrUpdate(jcLanduser);

	}

	public Boolean buildMoudel(String belongName, String belongDesc, String modelName, UserPrincipal principal) {

		Date date = new Date();
		JcFileBelongType belongType = new JcFileBelongType();
		belongType.setBelongDesc(belongDesc);
		belongType.setBelongName(belongName);
		belongType.setCreateTime(date);
		belongType.setCreateUserId(principal.getUserId());
		belongType.setCreateUserName(principal.getUsername());
		belongType.setUpdateTime(date);
		belongType.setMoudelName(modelName);
		belongType.setId(snowflakeService.nextId());
		jcFileBelongType.save(belongType);

		return true;
	}
}
