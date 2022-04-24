package com.hnup.osmp.jiance.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;


@Data
@TableName("jc_op_evaluate_config")
@ApiModel(value ="JcOpEvaluateConfig",description = "")
public class JcOpEvaluateConfig {

    /**
     * 业务类型
     */
    @ApiModelProperty("业务类型")
	@TableId(value = "key_type_code")
    private Integer keyTypeCode;

    /**
     * 地址
     */
    @ApiModelProperty("地址")
    @TableField("redirect_address")
	private String redirectAddress;

    /**
     * 主手阶段
     */
    @ApiModelProperty("主手阶段")
    @TableField("main_hand_phase")
	private String mainHandPhase;

    /**
     * 输出许可证阶段
     */
    @ApiModelProperty("输出许可证阶段")
    @TableField("out_license_phase")
	private String outLicensePhase;

    /**
     * 修改确认阶段
     */
    @ApiModelProperty("修改确认阶段")
    @TableField("modify_confirm_phase")
	private String modifyConfirmPhase;

    /**
     * 检查阶段
     */
    @ApiModelProperty("检查阶段")
    @TableField("check_phase")
	private String checkPhase;

}