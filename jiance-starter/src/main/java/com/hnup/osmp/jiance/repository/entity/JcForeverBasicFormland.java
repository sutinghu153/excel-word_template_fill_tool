package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 永久基本农田详情表
 */
@Data
@TableName("jc_forever_basic_formland")
@ApiModel(value ="JcForeverBasicFormland",description = "永久基本农田详情表")
public class JcForeverBasicFormland {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 一级地区编码
     */
    @ApiModelProperty("一级地区编码")
    @TableField("region_parent_code")
	private String regionParentCode;

    /**
     * 一级地区名称
     */
    @ApiModelProperty("一级地区名称")
    @TableField("region_parent_name")
	private String regionParentName;

    /**
     * 二级地区编码
     */
    @ApiModelProperty("二级地区编码")
    @TableField("region_sec_code")
	private String regionSecCode;

    /**
     * 二级地区名称
     */
    @ApiModelProperty("二级地区名称")
    @TableField("region_sec_name")
	private String regionSecName;

    /**
     * 统计年份
     */
    @ApiModelProperty("统计年份")
    @TableField("year")
	private String year;

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
     * 实存值
     */
    @ApiModelProperty("实存值")
    @TableField("real_area")
	private BigDecimal realArea;

    /**
     * 目标值
     */
    @ApiModelProperty("目标值")
    @TableField("goal_area")
	private BigDecimal goalArea;

    /**
     * 储备值
     */
    @ApiModelProperty("储备值")
    @TableField("reserve_area")
	private BigDecimal reserveArea;

    /**
     * 实存值-目标值
     */
    @ApiModelProperty("实存值-目标值")
    @TableField("richly_area")
	private BigDecimal richlyArea;

	/**
	 * 实存值-目标值
	 */
	@ApiModelProperty("划定值")
	@TableField("delimit_area")
	private BigDecimal delimitArea;

}