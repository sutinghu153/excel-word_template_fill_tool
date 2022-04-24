package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * 用来控制表字典对应的实体表中，将显示哪些字段
 */
@Data
@TableName("jc_field_of_table_detail")
@ApiModel(value ="JcFieldOfTableDetails",description = "用来控制表字典对应的实体表中，将显示哪些字段")
public class JcFieldOfTableDetails {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "field_id")
    private Long fieldId;

    /**
     * 表名
     */
    @ApiModelProperty("表名")
    @TableField("table_name")
	private String tableName;

    /**
     * 统计id
     */
    @ApiModelProperty("统计id")
    @TableField("count_id")
	private Long countId;

    /**
     * 字段名称
     */
    @ApiModelProperty("字段名称")
    @TableField("field_name")
	private String fieldName;

    /**
     * 字段意义
     */
    @ApiModelProperty("字段意义")
    @TableField("field_meaning")
	private String fieldMeaning;

    /**
     * 字段是否显示，1  显示 0不显示
     */
    @ApiModelProperty("字段是否显示，1  显示 0不显示")
    @TableField("field_show_state")
	private Integer fieldShowState;

    /**
     * 入库时间
     */
    @ApiModelProperty("入库时间")
    @TableField("create_time")
	private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField("update_time")
	private Date updateTime;

	/**
	 *  顺序
	 */
	@ApiModelProperty("字段顺序")
	@TableField("sort_num")
	private Integer sortNum;
}