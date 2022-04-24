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

@Data
@TableName("jc_op_evaluate_end")
@ApiModel(value ="JcOpEvaluateEnd",description = "")
public class JcOpEvaluateEnd {

    /**
     * 案卷号
     */
    @ApiModelProperty("案卷号")
	@TableId(value = "key_dig_num_gather")
    private Long keyDigNumGather;

    /**
     * 业务号
     */
    @ApiModelProperty("业务号")
    @TableField("key_type_code")
	private Integer keyTypeCode;

    /**
     * 窗口服务满意度等级
     */
    @ApiModelProperty("窗口服务满意度等级")
    @TableField("window_service")
	private Integer windowService;

    /**
     * 窗口服务说明
     */
    @ApiModelProperty("窗口服务说明")
    @TableField("window_service_note")
	private String windowServiceNote;

    /**
     * 行政审批满意度等级
     */
    @ApiModelProperty("行政审批满意度等级")
    @TableField("op_service")
	private Integer opService;

    /**
     * 行政审批说明
     */
    @ApiModelProperty("行政审批说明")
    @TableField("op_service_note")
	private String opServiceNote;

    /**
     * 勘测院满意度等级
     */
    @ApiModelProperty("勘测院满意度等级")
    @TableField("kance")
	private Integer kance;

    /**
     * 勘测院说明
     */
    @ApiModelProperty("勘测院说明")
    @TableField("kance_note")
	private String kanceNote;

    /**
     * 信息中心满意度等级
     */
    @ApiModelProperty("信息中心满意度等级")
    @TableField("info_center")
	private Integer infoCenter;

    /**
     * 信息中心说明
     */
    @ApiModelProperty("信息中心说明")
    @TableField("info_center_note")
	private String infoCenterNote;

    /**
     * 意见
     */
    @ApiModelProperty("意见")
    @TableField("opinion")
	private String opinion;

    /**
     * 评价时间
     */
    @ApiModelProperty("评价时间")
    @TableField("evaluate_time")
	private Date evaluateTime;

}