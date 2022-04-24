package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("jc_urban_assessment")
@ApiModel(value ="JcUrbanAssessment",description = "")
public class JcUrbanAssessment {

	/**
	 * 市区考核主键
	 */
	@ApiModelProperty("市区考核主键")
	@TableId(value = "city_assess_id")
	private Long cityAssessId;

	/**
	 * 考核指标
	 */
	@ApiModelProperty("考核指标")
	@TableField("assess_indicative")
	private String assessIndicative;

	/**
	 * 考核项目
	 */
	@ApiModelProperty("考核项目")
	@TableField("assess_project")
	private String assessProject;

	/**
	 * 指标性质
	 */
	@ApiModelProperty("指标性质")
	@TableField("indicative_nature")
	private String indicativeNature;

	/**
	 * 分值
	 */
	@ApiModelProperty("分值")
	@TableField("score")
	private Integer score;

	/**
	 * 考核要点及说明
	 */
	@ApiModelProperty("考核要点及说明")
	@TableField("inspection_points")
	private String inspectionPoints;

	/**
	 * 责任处室
	 */
	@ApiModelProperty("责任处室")
	@TableField("duty_office_depart")
	private String dutyOfficeDepart;

	/**
	 * 配合部门
	 */
	@ApiModelProperty("配合部门")
	@TableField("match_office_depart")
	private String matchOfficeDepart;

	/**
	 * 责任领导
	 */
	@ApiModelProperty("责任领导")
	@TableField("duty_leader")
	private String dutyLeader;

	/**
	 * 考核任务完成情况
	 */
	@ApiModelProperty("考核任务完成情况")
	@TableField("assess_performance")
	private String assessPerformance;

	/**
	 * 完成率
	 */
	@ApiModelProperty("完成率")
	@TableField("percentage")
	private String percentage;

	/**
	 * 更新时间
	 */
	@ApiModelProperty("更新时间")
	@TableField("update_time")
	private Date updateTime;

	/**
	 * 督办项目号
	 */
	@ApiModelProperty("督办项目号")
	@TableField("sup_num")
	private String supNum;

	/**
	 * 督办键
	 */
	@ApiModelProperty("督办键")
	@TableField("sup_id")
	private String supId;

	/**
	 * 创建者
	 */
	@ApiModelProperty("创建者")
	@TableField("create_user")
	private Long createUser;

	/**
	 * 入库时间
	 */
	@ApiModelProperty("入库时间")
	@TableField("create_time")
	private Date createTime;

}