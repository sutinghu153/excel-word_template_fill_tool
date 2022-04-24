package com.hnup.osmp.jiance.repository.entity.driver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2022/01/26
 * @Description
 */
@Data
@ApiModel(value ="驾驶舱-土地分仓-土地审批",description = "驾驶舱-土地分仓-土地审批")
public class DriverStoreLandApproveVo {
	@ApiModelProperty("审批总面积")
	private Double totalArea = 617.94d;
	@ApiModelProperty("建设用地面积")
	private Double constructLandArea = 372.94d;

}
