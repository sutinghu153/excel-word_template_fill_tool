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
@ApiModel(value ="驾驶舱-规划分仓-详细规划-控制性",description = "驾驶舱-规划分仓-详细规划-控制性")
public class DriverStorePlanningDetailControleVo {
	@ApiModelProperty("控规片数")
	private Double panelNum = 1D;
	@ApiModelProperty("覆盖率")
	private Double coverRate = 1D;
	@ApiModelProperty("规划总面积")
	private Double totalArea = 1D;
	@ApiModelProperty("建设用地总面积")
	private Double constructLandArea = 1D;
}
