package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * 规划指标首页表
 */
@Data
@TableName("jc_plan_index")
@ApiModel(value ="JcPlanIndex",description = "规划指标首页表")
public class JcPlanIndex {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 规划指标类别
     */
    @ApiModelProperty("规划指标类别")
    @TableField("class_type")
	private String classType;

    /**
     * 指标名称
     */
    @ApiModelProperty("指标名称")
    @TableField("index_name")
	private String indexName;

    /**
     * 计量单位
     */
    @ApiModelProperty("计量单位")
    @TableField("unit")
	private String unit;

    /**
     * 近期值
     */
    @ApiModelProperty("近期值")
    @TableField("near_value")
	private String nearValue;

    /**
     * 远期值
     */
    @ApiModelProperty("远期值")
    @TableField("far_value")
	private String farValue;

    /**
     * 近期值时间
     */
    @ApiModelProperty("近期值时间")
    @TableField("near_time")
	private String nearTime;

    /**
     * 远期值时间
     */
    @ApiModelProperty("远期值时间")
    @TableField("far_time")
	private String farTime;

    /**
     * 监测值
     */
    @ApiModelProperty("监测值")
    @TableField("monitor_value")
	private String monitorValue;

    /**
     * 基期值
     */
    @ApiModelProperty("基期值")
    @TableField("basic_value")
	private String basicValue;

    /**
     * 监测值更新时间
     */
    @ApiModelProperty("监测值更新时间")
    @TableField("monitor_time")
	private String monitorTime;

    /**
     * 基期值更新时间
     */
    @ApiModelProperty("基期值更新时间")
    @TableField("basic_time")
	private String basicTime;

	@ApiModelProperty("约束性质")
	@TableField("bound_value")
	private String boundValue;
}