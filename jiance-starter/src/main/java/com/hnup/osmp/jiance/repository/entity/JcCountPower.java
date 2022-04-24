package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 省自然考核指标用户导入导出权限控制表
 */
@Data
@TableName("jc_count_power")
@ApiModel(value ="JcCountPower",description = "省自然考核指标用户导入导出权限控制表")
public class JcCountPower {

    /**
     * 权限id
     */
    @ApiModelProperty("权限id")
	@TableId(value = "power_id")
    private Long powerId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    @TableField("user_id")
	private Long userId;

    /**
     * 统计id
     */
    @ApiModelProperty("统计id")
    @TableField("count_id")
	private Long countId;

    /**
     * 统计名称
     */
    @ApiModelProperty("统计名称")
    @TableField("count_name")
	private String countName;

    /**
     * 导出权限 1 有 0 无
     */
    @ApiModelProperty("导出权限 1 有 0 无")
    @TableField("export_power")
	private Integer exportPower;

    /**
     * 导入权限 1 有 0 无
     */
    @ApiModelProperty("导入权限 1 有 0 无")
    @TableField("import_power")
	private Integer importPower;

}