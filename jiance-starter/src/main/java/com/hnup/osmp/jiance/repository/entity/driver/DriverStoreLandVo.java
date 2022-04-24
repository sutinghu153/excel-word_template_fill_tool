package com.hnup.osmp.jiance.repository.entity.driver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2022/01/26
 * @Description
 */
@Data
@ApiModel(value ="驾驶舱-土地分仓",description = "驾驶舱-土地分仓")
public class DriverStoreLandVo {
	@ApiModelProperty("土地审批")
	private DriverStoreLandApproveVo approveVo;

	@ApiModelProperty("土地供应")
	private DriverStoreLandSupplyVo supplyVo;

	@ApiModelProperty("土地储备")
	private DriverStoreLandStorageVo storageVo;

	@ApiModelProperty("批储供对比")
	private List<DriverStoreLandRatioVo> ratioVos;

	@ApiModelProperty("土地修复")
	private List<DriverStoreLandRepairVo> repairVos;

	@ApiModelProperty("土地监测")
	private List<DriverStoreLandMonitorVo> monitorVos;

	@ApiModelProperty("建设用地审批")
	private List<DriverStoreLandBuildAppriveVo> buildVos;
}
