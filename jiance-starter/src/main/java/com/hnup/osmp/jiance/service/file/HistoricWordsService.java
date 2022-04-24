package com.hnup.osmp.jiance.service.file;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hnup.common.lang.exception.DeclareException;
import com.hnup.common.snowflake.starter.singe.SnowflakeService;
import com.hnup.osmp.jiance.repository.dao.IJcHistoricWordsDAO;
import com.hnup.osmp.jiance.repository.dao.IJcTemplateConfigDAO;
import com.hnup.osmp.jiance.repository.entity.JcHistoricWords;
import com.hnup.osmp.jiance.repository.entity.JcTemplateConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @data2022/2/14,9:15
 * @author sutinghu
 */
@Service
public class HistoricWordsService {


	@Autowired
	private IJcHistoricWordsDAO jcHistoricWords;

	@Autowired
	private IJcTemplateConfigDAO jcTemplateConfig;

	@Autowired
	private SnowflakeService snowflakeService;

	/**
	 * 功能描述: 校验模板是否存在
	 * @author sutinghu
	 * @date
	 * @param templeteId 参数
	 * @return void
	 */
	public void checkDataAvailability(Long templeteId){

		boolean empty = jcTemplateConfig.lambdaQuery().eq(JcTemplateConfig::getTempletId, templeteId).list().isEmpty();

		if (empty) {
			throw new DeclareException("模板不存在，请检查模板配置！");
		}

	}


	/**
	 * 功能描述: 保存缓存的文件
	 * @author sutinghu
	 * @date
	 * @param templeteId
	 * @param fileType
	 * @param file
	 * @param fileName 参数
	 * @return void
	 */
	public void saveOrupdateWordsByDay(Long templeteId,
									   String fileType ,
									   byte[] file ,
									   String year,
									   String month,
									   String day ,
									   String fileName){


		// 查找是否存在对应文件
		List<JcHistoricWords> jcHistoricWords = this.jcHistoricWords
			.lambdaQuery()
			.eq(JcHistoricWords::getTemplateId, templeteId)
			.eq(JcHistoricWords::getFileType, fileType)
			.eq(year!=null,JcHistoricWords::getYear, year)
			.eq(month!=null,JcHistoricWords::getMonth, month)
			.eq(day!=null,JcHistoricWords::getDay, day)
			.list();

		Date date = new Date();

		// 如果不存在对应的文件则新增
		if (CollectionUtils.isEmpty(jcHistoricWords)) {

			JcHistoricWords historicWords = new JcHistoricWords();
			historicWords.setCreateTime(date);
			historicWords.setFileType(fileType);
			historicWords.setTemplateId(templeteId);
			historicWords.setUpdateTime(date);
			historicWords.setWordData(file);
			historicWords.setWordName(fileName);
			historicWords.setYear(year);
			historicWords.setMonth(month);
			historicWords.setDay(day);
			historicWords.setId(snowflakeService.nextId());
			this.jcHistoricWords.save(historicWords);

		// 否则更新
		}else {

			JcHistoricWords historicWords = jcHistoricWords.get(0);
			historicWords.setUpdateTime(date);
			historicWords.setWordData(file);
			this.jcHistoricWords.updateById(historicWords);

		}

	}

	/**
	 * 功能描述: 查找最新的缓存文件
	 * @author sutinghu
	 * @date
	 * @param templeteId
	 * @param fileType
	 * @param year
	 * @param month
	 * @param day 参数
	 * @return com.hnup.osmp.jiance.repository.entity.JcHistoricWords
	 */
	public JcHistoricWords getWordsByTemplateId(Long templeteId, String fileType, String year, String month, String day){

		Asserts.notNull(templeteId,"模板id不能为空");
		Asserts.notNull(fileType,"文件类型不能为空");

		LambdaQueryWrapper<JcHistoricWords> queryWrapper=new LambdaQueryWrapper();
		queryWrapper.eq(JcHistoricWords::getFileType, fileType);
		queryWrapper.eq(JcHistoricWords::getTemplateId, templeteId);

		if (StringUtils.isNotBlank(year)) {
			queryWrapper.eq(JcHistoricWords::getYear, year);
		}else {
			queryWrapper.isNull(JcHistoricWords::getYear);
		}

		if (StringUtils.isNotBlank(month)) {
			queryWrapper.eq(JcHistoricWords::getMonth, month);
		}else {
			queryWrapper.isNull(JcHistoricWords::getMonth);
		}

		if (StringUtils.isNotBlank(day)) {
			queryWrapper.eq(JcHistoricWords::getDay, day);
		}else {
			queryWrapper.isNull(JcHistoricWords::getDay);
		}
		queryWrapper.orderByAsc(JcHistoricWords::getCreateTime);

		List<JcHistoricWords> list = jcHistoricWords.list(queryWrapper);

		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		return list.get(0);

	}

}
