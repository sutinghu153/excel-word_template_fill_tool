package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 该表用于记录地区乡镇级别行政单元
 */
@Data
@TableName("jc_village")
@ApiModel(value ="JcVillage",description = "该表用于记录地区乡镇级别行政单元")
public class JcVillage {

	/**
	 * 乡镇唯一主键
	 */
	@ApiModelProperty("乡镇唯一主键")
	@TableId(value = "village_id")
	private Long villageId;

	/**
	 * 乡镇名称
	 */
	@ApiModelProperty("乡镇名称")
	@TableField("village_name")
	private String villageName;

	/**
	 * 乡镇所属地区主键
	 */
	@ApiModelProperty("乡镇所属地区主键")
	@TableField("village_parent")
	private Long villageParent;

	/**
	 * 乡镇所属地区名称
	 */
	@ApiModelProperty("乡镇所属地区名称")
	@TableField("parent_name")
	private String parentName;

	/**
	 * 地区入库时间
	 */
	@ApiModelProperty("地区入库时间")
	@TableField("create_time")
	private Date createTime;

	/**
	 * 地区库中更新时间
	 */
	@ApiModelProperty("地区库中更新时间")
	@TableField("update_time")
	private Date updateTime;

}
