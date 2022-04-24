package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @data2022/2/24,11:22
 * @author sutinghu
 */
@Data
@TableName("jc_landuser_file")
@ApiModel(value ="JcLanduserFile",description = "")
public class JcLanduserFile {

	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	@TableId(value = "id")
	private Long id;

	/**
	 * 文件名称
	 */
	@ApiModelProperty("文件名称")
	@TableField("file_name")
	private String fileName;

	/**
	 * 文件类型
	 */
	@ApiModelProperty("文件类型")
	@TableField("file_type")
	private String fileType;

	/**
	 * 文件流
	 */
	@ApiModelProperty("文件流")
	@TableField("file_out")
	private byte[] fileOut;

	/**
	 * 年份
	 */
	@ApiModelProperty("年份")
	@TableField("year")
	private String year;

}
