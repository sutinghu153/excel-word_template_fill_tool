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
@ApiModel(value ="驾驶舱-规划分仓-规划指标",description = "驾驶舱-规划分仓-规划指标")
public class DriverStorePlanningItemVo {
	@ApiModelProperty("边界")
	private Double amass = 342155d;

	@ApiModelProperty("集中建设区")
	private Double bouncy = 0.5;

	@ApiModelProperty("发展区")
	private Double develop = 0.35;

	@ApiModelProperty("特别用途区")
	private Double special = 0.15;

	@ApiModelProperty("红线")
	private Double redLine = 3254.66;

	@ApiModelProperty("永久基本农田")
	private Double arableLands = 2016.87;
}
