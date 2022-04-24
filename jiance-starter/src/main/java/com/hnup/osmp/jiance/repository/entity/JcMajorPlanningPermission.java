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
@TableName("jc_major_planning_permission")
@ApiModel(value ="JcMajorPlanningPermission",description = "")
public class JcMajorPlanningPermission {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 案卷编号
     */
    @ApiModelProperty("案卷编号")
    @TableField("key_number_gather")
	private String keyNumberGather;

    /**
     * 所属地区
     */
    @ApiModelProperty("所属地区")
    @TableField("region")
	private String region;

    /**
     * 许可证号
     */
    @ApiModelProperty("许可证号")
    @TableField(" xkzh")
	private String xkzh;

    /**
     * 建设单位
     */
    @ApiModelProperty("建设单位")
    @TableField("pro_units")
	private String proUnits;

    /**
     * 项目号
     */
    @ApiModelProperty("项目号")
    @TableField("pro_number")
	private String proNumber;

    /**
     * 建设地址
     */
    @ApiModelProperty("建设地址")
    @TableField("pro_site")
	private String proSite;

    /**
     * 发证面积
     */
    @ApiModelProperty("发证面积")
    @TableField("area")
	private BigDecimal area;

    /**
     * 用地性质
     */
    @ApiModelProperty("用地性质")
    @TableField("land_type")
	private String landType;

    /**
     * 发证时间
     */
    @ApiModelProperty("发证时间")
    @TableField("prove_time")
	private Date proveTime;

    /**
     * 案卷号
     */
    @ApiModelProperty("案卷号")
    @TableField("key_dig_num_gather")
	private Long keyDigNumGather;

	@ApiModelProperty("发证类型")
	@TableField("type")
	private String type;

}