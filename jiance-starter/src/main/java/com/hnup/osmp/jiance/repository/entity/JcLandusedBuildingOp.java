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


@Data
@TableName("jc_landused_building_op")
@ApiModel(value ="JcLandusedBuildingOp",description = "")
public class JcLandusedBuildingOp {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

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
     * 一级行政单位——长沙
     */
    @ApiModelProperty("一级行政单位")
    @TableField("region_level1")
	private String regionLevel1;

    /**
     * 二级行政单位
     */
    @ApiModelProperty("二级行政单位")
    @TableField("region_level2")
	private String regionLevel2;

    /**
     * 国家所有
     */
    @ApiModelProperty("所有类型")
    @TableField("state_owned_type")
	private String stateOwnedType;

    /**
     * 所属年份
     */
    @ApiModelProperty("所属年份")
    @TableField("year")
	private String year;

    /**
     * 建设用地
     */
    @ApiModelProperty("建设用地")
    @TableField("landused_building")
	private BigDecimal landusedBuilding;

    /**
     * 农用地
     */
    @ApiModelProperty("农用地")
    @TableField("agricultural_land")
	private BigDecimal agriculturalLand;

	@ApiModelProperty("新增用地")
	private BigDecimal newAdd;

    /**
     * 未利用地
     */
    @ApiModelProperty("未利用地")
    @TableField("untaking_land")
	private BigDecimal untakingLand;

	@ApiModelProperty("耕地")
	@TableField("cultivated_land")
	private BigDecimal cultivatedLand;

	@ApiModelProperty("园地")
	@TableField("garden_land")
	private BigDecimal gardenLand;

	@ApiModelProperty("林地")
	@TableField("forset_land")
	private BigDecimal forsetLand;

	@ApiModelProperty("草地")
	@TableField("grass_land")
	private BigDecimal grassLand;

	@ApiModelProperty("其它")
	@TableField("other_land")
	private BigDecimal otherLand;

}