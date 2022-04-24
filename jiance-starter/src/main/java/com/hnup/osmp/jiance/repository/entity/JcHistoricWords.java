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
@TableName("jc_historic_words")
@ApiModel(value ="JcHistoricWords",description = "")
public class JcHistoricWords {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 入库时间
     */
    @ApiModelProperty("入库时间")
    @TableField("create_time")
	private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField("update_time")
	private Date updateTime;

    /**
     * 文件名称：时间+名称
     */
    @ApiModelProperty("文件名称：时间+名称")
    @TableField("word_name")
	private String wordName;

    /**
     * word文件
     */
    @ApiModelProperty("word文件")
    @TableField("word_data")
	private byte[] wordData;

    /**
     * 模板名称
     */
    @ApiModelProperty("模板名称")
    @TableField("template_id")
	private Long templateId;

    /**
     * 年份
     */
    @ApiModelProperty("年份")
    @TableField("year")
	private String year;

    /**
     * 月份
     */
    @ApiModelProperty("月份")
    @TableField("month")
	private String month;

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    @TableField("day")
	private String day;


	/**
	 * 所属模块
	 */
	@ApiModelProperty("doc,docx,xls等")
	@TableField("file_type")
	private String fileType;
}