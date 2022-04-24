package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 考核指标，省自然资源厅督察工作统计类型表
 */
@Data
@TableName("jc_count_detail")
@ApiModel(value ="JcCountDetail",description = "考核指标，省自然资源厅督察工作统计类型表")
public class JcCountDetail {

	/**
	 * 统计类型id
	 */
	@ApiModelProperty("统计类型id")
	@TableId(value = "count_id")
	private Long countId;

	/**
	 * 统计类型名称
	 */
	@ApiModelProperty("统计类型名称")
	@TableField("count_name")
	private String countName;

	/**
	 * 数据存放表
	 */
	@JsonIgnore
	@ApiModelProperty("数据存放表")
	@TableField("form_name")
	private String formName;

	/**
	 * 所属类别
	 */
	@ApiModelProperty("所属类别")
	@TableField("parent_id")
	private Long parentId;

	/**
	 * 所属名称
	 */
	@ApiModelProperty("所属名称")
	@TableField("parent_name")
	private String parentName;

	/**
	 * 责任处室
	 */
	@ApiModelProperty("责任处室")
	@TableField("department")
	private String department;

	/**
	 * 完成时限
	 */
	@ApiModelProperty("完成时限")
	@TableField("finish_time")
	private String finishTime;

	/**
	 * 责任领导
	 */
	@ApiModelProperty("责任领导")
	@TableField("duty_leader")
	private String dutyLeader;

	/**
	 * 总任务
	 */
	@ApiModelProperty("总任务")
	@TableField("all_tasks")
	private String allTasks;

	/**
	 * 完成情况
	 */
	@ApiModelProperty("完成情况")
	@TableField("finish_state")
	private String finishState;

	/**
	 * 完成比例
	 */
	@ApiModelProperty("完成比例")
	@TableField("finish_scale")
	private String finishScale;

	/**
	 * 数据统计时间
	 */
	@ApiModelProperty("数据统计时间")
	@TableField("data_count_time")
	private String dataCountTime;

}