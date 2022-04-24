package com.hnup.osmp.jiance.utils;

import com.hnup.common.lang.exception.DeclareException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @data2021/10/9,8:43
 * @authorsutinghu
 */
public class FileUtils {

	/**
	 * 功能描述: 获取指定文件的文件路径
	 * @author sutinghu
	 * @date
	 * @param fileName 文件名
	 * @return java.lang.String
	 */
	public static String getFilePath(String fileName){
		return new FileUtils().getFileUrl(fileName);
	}

	/**
	 * 功能描述: 获取指定文件的路径
	 * @author sutinghu
	 * @date
	 * @param fileName 文件名称
	 * @return 文件路径
	 */
	public  String getFileUrl(String fileName) {
		// 判断指定文件是否存在
		String url = checkFileExist(fileName);
		if (StringUtils.isBlank(url)){
			throw new DeclareException(fileName + "不存在");
		}
		return url;
	}

	/**
	 * 功能描述: 获取指定文件的路径 如果不存在 则返回空
	 * @author sutinghu
	 * @date
	 * @param fileName 指定文件名
	 * @return String
	 */
	public String checkFileExist(String fileName){
		if (StringUtils.isBlank(fileName)){
			return null;
		}
		URL url = FileUtils.class.getClassLoader().getResource("");
		List<File> fileList = getAllFiles(url.getPath());
		String fileNames = null;
		for (File file:fileList) {
			if (Objects.equals(fileName,file.getName())){
				fileNames = file.getPath();
				break;
			}
		}
		return fileNames;
	}

	/**
	 * 功能描述: 获取指定路径下的所有文件
	 * @author sutinghu
	 * @date
	 * @param path 指定文件路径
	 * @return java.util.List<java.io.File>
	 */
	public List<File> getAllFiles(String path){
		File file = new File(path);
		// 获取文件
		List<File> fileList = new ArrayList<>();
		if (isFileExist(file)) {
			// 获取文件夹下所有文件
			if (file.isDirectory()) {
				File [] files = file.listFiles();
				for (File file1 :files) {
					// 文件夹下递归文件夹
					if (file1.isDirectory()){
						List<File> allFiles = getAllFiles(file1.getPath());
						fileList.addAll(allFiles);
					}else {
						//如果文件是普通文件，则将文件句柄放入集合中
						fileList.add(file1);
					}
				}
			}
		}
		return fileList;
	}

	/**
	 * 功能描述: 判断文件是否存在
	 * @author sutinghu
	 * @date
	 * @param  file 文件
	 * @return boolean
	 */
	public boolean isFileExist(File file){
		if (file == null || !file.exists()) {
			return false;
		}
		return true;
	}
}
