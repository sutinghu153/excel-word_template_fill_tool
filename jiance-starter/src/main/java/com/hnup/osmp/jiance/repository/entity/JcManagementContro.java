package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 区县级别二级分类管理控制数
 */
@Data
@TableName("jc_management_contro")
@ApiModel(value ="JcManagementContro",description = "区县级别二级分类管理控制数")
public class JcManagementContro {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 地区名称
     */
    @ApiModelProperty("地区名称")
    @TableField("region_name")
	private String regionName;

    /**
     * 地区代码
     */
    @ApiModelProperty("地区代码")
    @TableField("region_code")
	private String regionCode;

    /**
     * 二级地区名称
     */
    @ApiModelProperty("二级地区名称")
    @TableField("region_sec_name")
	private String regionSecName;

    /**
     * 二级地区代码
     */
    @ApiModelProperty("二级地区代码")
    @TableField("region_sec_code")
	private String regionSecCode;

    /**
     * 一级土地利用类型
     */
    @ApiModelProperty("一级土地利用类型")
    @TableField("landuser_one")
	private String landuserOne;

    /**
     * 二级土地利用类型
     */
    @ApiModelProperty("二级土地利用类型")
    @TableField("landuser_two")
	private String landuserTwo;

    /**
     * 国家
     */
    @ApiModelProperty("国家")
    @TableField("contry_num")
	private BigDecimal contryNum;

    /**
     * 集体
     */
    @ApiModelProperty("集体")
    @TableField("statu_num")
	private BigDecimal statuNum;

    /**
     * 全部
     */
    @ApiModelProperty("全部")
    @TableField("all_num")
	private BigDecimal allNum;

	@ApiModelProperty("年份")
	@TableField("year")
	private String year;

	@ApiModelProperty("目标名称")
	@TableField("target_name")
	private String targetName;

	@ApiModelProperty("目标值")
	@TableField("target_value")
	private BigDecimal targetValue;

}