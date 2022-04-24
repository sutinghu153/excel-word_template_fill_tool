package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("jc_template_config")
@ApiModel(value ="JcTemplateConfig",description = "")
public class JcTemplateConfig {

    /**
     * 模板主键
     */
    @ApiModelProperty("模板主键")
	@TableId(value = "templet_id")
    private Long templetId;

    /**
     * 模板名称
     */
    @ApiModelProperty("模板名称")
    @TableField("templet_name")
	private String templetName;

    /**
     * 模板流
     */
    @ApiModelProperty("模板流")
    @TableField("templet")
	private byte[] templet;

    /**
     * 模板创建时间
     */
    @ApiModelProperty("模板创建时间")
    @TableField("create_time")
	private Date createTime;

    /**
     * 模板更新时间
     */
    @ApiModelProperty("模板更新时间")
    @TableField("update_time")
	private Date updateTime;

    /**
     * 模板创建者
     */
    @ApiModelProperty("模板创建者")
    @TableField("creater")
	private String creater;

	/**
	 * 存放模板填充SQL语句，SQL语句之间以#分割
	 */
	@ApiModelProperty("存放模板填充SQL语句，SQL语句之间以#分割")
	@TableField("template_sql")
	private String templateSql;

	@ApiModelProperty("所属模块")
	@TableField("model_name")
	private String modelName;

	@ApiModelProperty("所属类型")
	@TableField("belong_id")
	private Long belongId;

	@ApiModelProperty("文件类型")
	@TableField("file_type")
	private String fileType;

	@ApiModelProperty("文件类型")
	@TableField("word_or_excel")
	private String wordOrExcel;
}