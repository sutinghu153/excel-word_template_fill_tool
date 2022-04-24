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

@Data
@TableName("jc_major_projects")
@ApiModel(value ="JcMajorProjects",description = "")
public class JcMajorProjects {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 业务类型
     */
    @ApiModelProperty("业务类型")
    @TableField("key_type_code")
	private Byte keyTypeCode;

    /**
     * 项目编号
     */
    @ApiModelProperty("项目编号")
    @TableField("key_dig_num_gather")
	private Long keyDigNumGather;

    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    @TableField("pro_name")
	private String proName;

    /**
     * 建设单位
     */
    @ApiModelProperty("建设单位")
    @TableField("pro_units")
	private String proUnits;

    /**
     * 建设性质
     */
    @ApiModelProperty("建设性质")
    @TableField("build_quality")
	private String buildQuality;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField("creat_time")
	private Date creatTime;

    /**
     * 建设面积
     */
    @ApiModelProperty("建设面积")
    @TableField("area")
	private BigDecimal area;

    /**
     * 选址预审
     */
    @ApiModelProperty("当前阶段")
    @TableField("progress")
	private String progress;

    /**
     * 选址预审时间
     */
    @ApiModelProperty("当前阶段开始时间")
    @TableField("progress_time")
	private Date progressTime;


}