package com.hnup.osmp.jiance.service.file;

import com.aspose.words.*;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataTable;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.collections.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Aspose.word 通过邮件填充word模板
 * @data2021/10/9,8:43
 * @authorsutinghu
 */
public class FillByMergeUtils {

	private static final String NULL = "null";

	/**
	 * 功能描述:填充 word 模板（object数据格式）
	 * @author sutinghu
	 * @date
	 * @param modelWordByte word模版二进制文件
	 * @param obj    要填充的数据
	 * @return  组合数据之后的word二进制
	 */
	public static byte[] fillWordData(byte[] modelWordByte,
									  Object obj) {
		try {
			Class<?> aClass = obj.getClass();
			Field[] fields = aClass.getDeclaredFields();
			Map<String, Object> data = new HashMap<>(fields.length);
			for (Field field : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), aClass);
				Method method = pd.getReadMethod();
				String key = field.getName();
				Object value = method.invoke(obj);
				if (value != null) {
					data.put(key, value);
				}
			}
			return fillWordDataByMap(modelWordByte, data);
		} catch (Exception e) {
			return new byte[0];
		}
	}

	/**
	 * 功能描述: 获取文档的mergefield字段集
	 * @author sutinghu
	 * @date
	 * @param file 参数
	 * @return java.lang.String[]
	 */
	public static byte[] getFields(byte[] file){

		InputStream is = new ByteArrayInputStream(file);
		String[] tables = null;
		byte[] ret = null;
		try {
			Document doc = new Document(is);
			tables = doc.getMailMerge().getFieldNames();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String[] values = new String[tables.length];
			for (int i = 0;i<tables.length;i++) {
				values[i]="-";
			}

			// 替换
			doc.getMailMerge().execute(tables,values);
			ArrayList<MailMergeRegionInfo> regions = doc.getMailMerge().getRegionsHierarchy().getRegions();
			doc.getMailMerge().getRegionsHierarchy().getRegions().removeAll(regions);


			for (MailMergeRegionInfo e : regions) {
				String name = e.getName();
				Map<String, Object> map = new HashMap<>(2);
				e.getFields().forEach(o->{
					String result = o.getResult();
					String value = "/";
					map.put(result,value);
				});
				//写入表数据
				DataTable dataTable = fillListDataByMap(Arrays.asList(map),name);
				doc.getMailMerge().executeWithRegions(dataTable);
			}

			doc.save(out, SaveOptions.createSaveOptions(SaveFormat.DOC));

			ret = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 功能描述: 插入图表的值
	 * @author sutinghu
	 * @date
	 * @param file
	 * @param value
	 * @param chartName 参数
	 * @return byte[]
	 */
	@SneakyThrows
	public static byte[] fillWordChartByMap(byte[] file, List<Map<String, Object>> value, String chartName){

		if (CollectionUtils.isEmpty(value)) {
			return file;
		}

		InputStream is = new ByteArrayInputStream(file);

		// 获取文件模板
		Document doc = new Document(is);

		// 获取文件建造者
		DocumentBuilder builder = new DocumentBuilder(doc);

		// 找到标签
		builder.moveToBookmark(chartName);

		// 插入一个柱状图
		Shape shape = builder.insertChart(ChartType.COLUMN, 500, 250);

		Chart chart = shape.getChart();

		chart.getSeries().clear();

		chart.getTitle().setText(chartName);

		// 获取数据列 x轴默认为 column
		Set<String> strings = value.get(0).keySet();

		// 获取X轴的值
		List<Object> columnX = null;

		columnX = value.stream().map(stringObjectMap -> {
			if (Objects.isNull(stringObjectMap.get("column")) || Objects.isNull(stringObjectMap.get("COLUMN"))) {
				return "其它";
			} else {
				return stringObjectMap.get("column") != null ? stringObjectMap.get("column") : stringObjectMap.get("COLUMN");
			}
		}).collect(Collectors.toList());

		String[] column_X = (String[]) columnX.toArray();

		// 获取列值 并插入chart中
		for (String s : strings) {

			if (Objects.equals(s,"column") || Objects.equals(s,"COLUMN")) {
				continue;
			}

			// 获取
			double[] column_Y = new double[]{};

			int i = 0;

			for (Map<String, Object> map : value) {

				double o = (double) (map.get(s) == null ? 0.0 : map.get(s));

				column_Y[i] = o;

			}

			chart.getSeries().add(s,column_X,column_Y);
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		doc.save(out, SaveOptions.createSaveOptions(SaveFormat.DOC));

		return out.toByteArray();

	}


	@SneakyThrows
	public static byte[] buildTemplateExcel(byte[] file, List<Map<String, Object>> list){
		if (CollectionUtils.isEmpty(list)) {
			return file;
		}
		byte[] fileResult = file;
		List<Map<String, Object>> listValue = null;
		Map<String, Object> valueOrdinaty = new HashMap<>(8);
		for (Map<String, Object> map : list) {
			String type = (String) map.get("TYPE");
			Object value = map.get("VALUE");
			// 填充的内容是普通字段
			if (Objects.equals(FeildTypeEnum.ORDINARY.getDesc(),type)){
				if (value instanceof Map) {
					valueOrdinaty.putAll((Map<String, Object>)value);
				}
				// 填充的内容是普通表格
			}else if (Objects.equals(FeildTypeEnum.TABLE.getDesc(),type)){
				if (value instanceof List) {
					listValue = (List<Map<String, Object>>) value;
				}
			}
		}
		fileResult = ExcelFillUtils.fillExcel(fileResult, valueOrdinaty,listValue);
		// 将文件中的占位符干掉
//		fileResult = ExcelFillUtils.fillExcel(fileResult, null,null);

		return fileResult;

	}


	/**
	 * 功能描述: 根据要填充的类型 对内容进行填充WORD
	 * @author sutinghu
	 * @date
	 * @param file
	 * @param list 参数
	 * @return byte[]
	 */
	@SneakyThrows
	public static byte[] buildTemplateWord(byte[] file, List<Map<String, Object>> list){


		if (CollectionUtils.isEmpty(list)) {
			return file;
		}

		byte[] fileResult = file;

		for (Map<String, Object> map : list) {

			String type = (String) map.get("TYPE");
			Object value = map.get("VALUE");
			// 填充的内容是普通字段
			if (Objects.equals(FeildTypeEnum.ORDINARY.getDesc(),type)){

				if (value instanceof Map) {

					Map<String, Object> valueOrdinaty = (Map<String, Object>) value;

					fileResult = fillWordDataByMap(fileResult, valueOrdinaty);

				}

			// 填充的内容是普通表格
			}else if (Objects.equals(FeildTypeEnum.TABLE.getDesc(),type)){

				String tableName = (String) map.get("TABLE_NAME");

				if (value instanceof List) {

					fileResult = fillWordDataByMap(fileResult,new HashMap<String, Object>(2) {{ put(tableName,value);}});

				}

				// 填充的内容是普通图表
			}else if (Objects.equals(FeildTypeEnum.CHART.getDesc(),type)){

				String tableName = (String) map.get("CHART_NAME");

				if (value instanceof List) {

					List<Map<String, Object>> list1 = (List<Map<String, Object>>) value;

					fileResult = fillWordChartByMap(fileResult,list1,tableName);

				}

			}

		}

		fileResult = getFields(fileResult);

		return fileResult;

	}


	/**
	 * 功能描述: 填充 word 模板（map数据格式）
	 * @author sutinghu
	 * @date
	 * @param file word二进制
	 * @param data 要填充的数据
	 * @return 组合数据之后的word二进制
	 */
	public static byte[] fillWordDataByMap(byte[] file,
										   Map<String, Object> data) throws Exception {
		byte[] ret = null;

		if (data == null || data.isEmpty()) {
			return ret;
		}
		try (
			InputStream is = new ByteArrayInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			Document doc = new Document(is);
			Map<String, String> toData = new HashMap<>();
			for (Map.Entry<String, Object> en : data.entrySet()) {
				String key = en.getKey();
				Object value = en.getValue();

				if (value instanceof List) {
					//写入表数据
					DataTable dataTable = fillListDataByMap((List) value, key);
					doc.getMailMerge().executeWithRegions(dataTable);
				}

				String valueStr = String.valueOf(en.getValue());
				if (value == null || value.equals(NULL)) {
					continue;
				}

				toData.put(key, valueStr);
			}

			String[] fieldNames = new String[toData.size()];
			String[] values = new String[toData.size()];

			int i = 0;
			for (Map.Entry<String, String> entry : toData.entrySet()) {
				fieldNames[i] = entry.getKey();
				values[i] = entry.getValue();
				i++;
			}

			//合并数据
			doc.getMailMerge().execute(fieldNames, values);
			doc.save(out, SaveOptions.createSaveOptions(SaveFormat.DOC));
			ret = out.toByteArray();
		}

		return ret;
	}

	/**
	 * 功能描述: 封装 list 数据到 word 模板中（word表格）
	 * @author sutinghu
	 * @date
	 * @param list 数据
	 * @param tableName 表格列表变量名称
	 * @return word表格数据DataTable
	 */
	private static DataTable fillListData(List<Object> list,
										  String tableName) throws Exception {

		//创建DataTable,并绑定字段
		DataTable dataTable = new DataTable(tableName);
		for (Object obj : list) {
			//创建DataRow，封装该行数据
			DataRow dataRow = dataTable.newRow();
			Class<?> objClass = obj.getClass();
			Field[] fields = objClass.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				dataTable.getColumns().add(fields[i].getName());
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), objClass);
				Method method = pd.getReadMethod();
				dataRow.set(i, method.invoke(obj));
			}
			dataTable.getRows().add(dataRow);
		}
		return dataTable;
	}


	private static DataTable fillListDataByMap(List<Object> list,
										  String tableName) throws Exception {

		//创建DataTable,并绑定字段
		DataTable dataTable = new DataTable(tableName);

		for (Object obj : list) {
			//创建DataRow，封装该行数据
			DataRow dataRow = dataTable.newRow();
			Map<String, Object> map = (Map<String, Object>) obj;
			for (Map.Entry<String, Object> field:map.entrySet()) {
				if (!dataTable.getColumns().contains(field.getKey())) {
					dataTable.getColumns().add(field.getKey());
				}
				String key = field.getKey();
				Object value = field.getValue();
				if (value == null || value.equals(NULL)) {
					value = "/";
				}
				dataRow.set(key,value);
			}
			dataTable.getRows().add(dataRow);
		}
		return dataTable;
	}


}
