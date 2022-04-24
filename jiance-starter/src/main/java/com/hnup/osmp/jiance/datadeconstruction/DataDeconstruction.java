package com.hnup.osmp.jiance.datadeconstruction;

import com.hnup.osmp.jiance.service.file.ReadExcelFile;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @data2022/4/8,11:29
 * @authorMSI
 */
public class DataDeconstruction {


	public static void main(String[] args) {

		File file = new File("C:\\Users\\MSI\\Desktop\\data.xlsx");

		FileItem fileItem = getMultipartFile(file,"templFileItem");

		MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

		// 读取文件
		List<Map<String, String>> maps = null;

		try {
			maps = ReadExcelFile.redExcel(multipartFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Map<String, String>> mapList = new ArrayList<>();

		// 这里进行第一次处理，内容分割为律师 + 律所
		System.out.println("内容分割为律师 + 律所...");
		maps.stream().forEach(e->{
			Map<String, String> map = new HashMap<>();
			String ls = e.get("律师");
			if (!ls.contains("，")) {
				return;
			}
			String[] split = ls.split("，");
			String userName = split[0];
			map.put("律师",userName);
			String departName = split[1];
			map.put("律所",departName);
			map.put("地区",e.get("地区"));
			map.put("案例",e.get("案例"));
			mapList.add(map);

		});

		// 这里进行分类分组
		System.out.println("分类分组...");
		Map<String, List<Map<String, String>>> collect = mapList.stream().collect(Collectors.groupingBy(stringStringMap -> stringStringMap.get("律师")+"-"+stringStringMap.get("律所")+"-"+stringStringMap.get("地区")));

		// 这里进行合并
		System.out.println("合并案例...");
		List<Map<String, String>> resultList = new ArrayList<>();
		final Integer[] index = {0};
		collect.forEach((k,v)->{
			String result = "";
			for (int i = 0; i < v.size(); i++) {
				// 这里进行格式化处理
				NumberToChar numberToChar = new NumberToChar();
				String s = numberToChar.readExecute(i+1);
				String value = s+"、"+v.get(i).get("案例");
				result = result +value;
			}
			Map<String, String> map = new HashMap<>();
			index[0] = index[0] +1;
			map.put("序号",index[0].toString());
			map.put("律师-律所-地区",k);
			map.put("案例合集",result);
			resultList.add(map);
		});

		// 保存为excel
		creatExcel("C:\\Users\\MSI\\Desktop\\dataResule.xlsx",resultList);

	}


	/**
	 * 功能描述: 输出为excel
	 * @author sutinghu
	 * @date
	 * @param src
	 * @param resultList 参数
	 * @return void
	 */
	public static void creatExcel(String src,List<Map<String, String>> resultList){
		// 导出为EXCEL
		System.out.println("数据转成Excel...");
		//定义一个新的工作簿

		XSSFWorkbook wb = new XSSFWorkbook();
		//创建一个Sheet页

		XSSFSheet sheet = wb.createSheet("First sheet");
		//设置行高

		sheet.setDefaultRowHeight((short) (2 * 256));
		//设置列宽

		sheet.setColumnWidth(0, 4000);

		sheet.setColumnWidth(1, 4000);

		sheet.setColumnWidth(2, 4000);

		XSSFFont font=wb.createFont();

		font.setFontName("宋体");

		font.setFontHeightInPoints((short) 16);//获得表格第一行

		XSSFRow row = sheet.createRow(0);//根据需要给第一行每一列设置标题

		XSSFCell cell = row.createCell(0);

		cell.setCellValue("序号");

		cell= row.createCell(1);

		cell.setCellValue("律师-律所-地区");

		cell= row.createCell(2);

		cell.setCellValue("案例合集");

		XSSFRow rows;

		XSSFCell cells;

		//循环拿到的数据给所有行每一列设置对应的值
		for (int i = 0; i < resultList.size(); i++) {

			//在这个sheet页里创建一行
			rows = sheet.createRow(i + 1);

			//该行创建一个单元格,在该单元格里设置值
			String name  =	resultList.get(i).get("序号");
			String age   =  resultList.get(i).get("律师-律所-地区");
			String sex   =  resultList.get(i).get("案例合集");

			cells= rows.createCell(0);

			cells.setCellValue(name);

			cells= rows.createCell(1);

			cells.setCellValue(age);

			cells= rows.createCell(2);

			cells.setCellValue(sex);

		}try{

			File file= new File(src);

			FileOutputStream fileOutputStream= new FileOutputStream(file);

			wb.write(fileOutputStream);

			wb.close();

			fileOutputStream.close();

		}catch(IOException e) {

			e.printStackTrace();

		}
	}

	/**
	 * 将file转换成fileItem
	 * @param file
	 * @param fieldName
	 * @return
	 */
	private static FileItem  getMultipartFile(File file, String fieldName) {
		FileItemFactory factory = new DiskFileItemFactory(16, null);
		FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		try {
			FileInputStream fis = new FileInputStream(file);
			OutputStream os = item.getOutputStream();
			while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * 功能描述: 序号转换器
	 * @author sutinghu
	 * @date
	 * @param
	 * @return
	 */
	static class NumberToChar{

		private final String[] CHN_NUM_CHAR = {"零","一","二","三","四","五","六","七","八","九"};

		private final String[] CHN_UNIT_CHAR = {"","十","百","千"};

		public  String readExecute(int src){

			String dst = "";

			int count = 0;

			while(src > 0) {

				dst = (CHN_NUM_CHAR[src % 10] + CHN_UNIT_CHAR[count]) + dst;

				src = src / 10;

				count++;

			}

			if(dst.startsWith("一十")) {

				dst = dst.substring(1);

			}

			return dst.replaceAll("零[千百十]", "零").replaceAll("零+万", "万")
				.replaceAll("零+亿", "亿").replaceAll("亿万", "亿零")
				.replaceAll("零+", "零").replaceAll("零$", "");
		}

	}

}
