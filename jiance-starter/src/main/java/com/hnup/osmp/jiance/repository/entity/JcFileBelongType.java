package com.hnup.osmp.jiance.repository.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Data
@TableName("jc_file_belong_type")
@ApiModel(value ="JcFileBelongType",description = "")
public class JcFileBelongType {

	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	@TableId(value = "id")
	private Long id;

	/**
	 * 类型名称
	 */
	@ApiModelProperty("类型名称")
	@TableField("belong_name")
	private String belongName;

	/**
	 * 类型描述
	 */
	@ApiModelProperty("类型描述")
	@TableField("belong_desc")
	private String belongDesc;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@TableField("create_time")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	@TableField("update_time")
	private Date updateTime;

	/**
	 * 创建人
	 */
	@ApiModelProperty("创建人")
	@TableField("create_user_name")
	private String createUserName;

	/**
	 * 所属模块
	 */
	@ApiModelProperty("所属模块")
	@TableField("moudel_name")
	private String moudelName;

	/**
	 * 创建人id
	 */
	@ApiModelProperty("创建人id")
	@TableField("create_user_id")
	private Long createUserId;

}