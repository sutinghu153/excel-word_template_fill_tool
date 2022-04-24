package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("jc_indicative_prov_manage")
@ApiModel(value ="JcIndicativeProvManage",description = "")
public class JcIndicativeProvManage {

    /**
     * 自然资源管理主键
     */
    @ApiModelProperty("自然资源管理主键")
	@TableId(value = "prov_assess_id")
    private Long provAssessId;

    /**
     * 指标名称及分值
     */
    @ApiModelProperty("指标名称及分值")
    @TableField("indicative_name")
	private String indicativeName;

    /**
     * 考核内容及计分方法
     */
    @ApiModelProperty("考核内容及计分方法")
    @TableField("examination_content")
	private String examinationContent;

    /**
     * 考核要点
     */
    @ApiModelProperty("考核要点")
    @TableField("inspection_points")
	private String inspectionPoints;

    /**
     * 责任处室局
     */
    @ApiModelProperty("责任处室局")
    @TableField("duty_office")
	private String dutyOffice;

    /**
     * 分管局领导
     */
    @ApiModelProperty("分管局领导")
    @TableField("sub_office_leader")
	private String subOfficeLeader;

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
     * 考核完成情况
     */
    @ApiModelProperty("考核完成情况")
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
     * 督办主键
     */
    @ApiModelProperty("督办主键")
    @TableField("sup_id")
	private String supId;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    @TableField("create_user")
	private String createUser;

    /**
     * 入库时间
     */
    @ApiModelProperty("入库时间")
    @TableField("create_time")
	private Date createTime;

}