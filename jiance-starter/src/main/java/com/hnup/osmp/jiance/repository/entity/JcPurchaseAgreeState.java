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
@TableName("jc_purchase_agree_state")
@ApiModel(value ="JcPurchaseAgreeState",description = "")
public class JcPurchaseAgreeState {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
	@TableId(value = "id")
    private Long id;

    /**
     * 项目采购类型
     */
    @ApiModelProperty("项目采购类型")
    @TableField("pro_purchase_type")
	private String proPurchaseType;

    /**
     * 政府采购编号
     */
    @ApiModelProperty("政府采购编号")
    @TableField("gov_purchase_num")
	private String govPurchaseNum;

    /**
     * 项目名称
     */
    @ApiModelProperty("项目名称")
    @TableField("pro_name")
	private String proName;

    /**
     * 设计单位为
     */
    @ApiModelProperty("设计单位为")
    @TableField("pro_units")
	private String proUnits;

    /**
     * 经办处
     */
    @ApiModelProperty("经办处")
    @TableField("handle_department")
	private String handleDepartment;

    /**
     * 合同金额
     */
    @ApiModelProperty("合同金额")
    @TableField("agree_all")
	private BigDecimal agreeAll;

    /**
     * 已支付金额
     */
    @ApiModelProperty("已支付金额")
    @TableField("already_agree")
	private BigDecimal alreadyAgree;

    /**
     * 待支付金额
     */
    @ApiModelProperty("待支付金额")
    @TableField("wait_agree")
	private BigDecimal waitAgree;

    /**
     * 付费批次
     */
    @ApiModelProperty("付费批次")
    @TableField("pay_batch")
	private String payBatch;

    /**
     * 拟支付金额
     */
    @ApiModelProperty("拟支付金额")
    @TableField("batch_money")
	private BigDecimal batchMoney;

    /**
     * 更新司机
     */
    @ApiModelProperty("更新司机")
    @TableField("create_time")
	private Date createTime;

    /**
     * 统计年份
     */
    @ApiModelProperty("统计年份")
    @TableField("count_year")
	private String countYear;

}