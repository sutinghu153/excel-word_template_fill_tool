package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 自定义公式
 */
@Data
@TableName("jc_operation")
@ApiModel(value ="JcOperation",description = "自定义公式")
public class JcOperation {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "action_id")
    private Long actionId;

    /**
     * 字段
     */
    @ApiModelProperty("字段")
    @TableField("action_fields")
	private String actionFields;

    /**
     * 表名称
     */
    @ApiModelProperty("表名称")
    @TableField("action_table")
	private String actionTable;

    /**
     * 公式
     */
    @ApiModelProperty("公式")
    @TableField("action_operation")
	private String actionOperation;

	@ApiModelProperty("公式描述")
	@TableField("action_desc")
	private String actionDesc;

	@ApiModelProperty("目标容器")
	@TableField("action_result")
	private String actionResult;

	@ApiModelProperty("主键")
	@TableField("action_sign")
	private String actionSign;
}