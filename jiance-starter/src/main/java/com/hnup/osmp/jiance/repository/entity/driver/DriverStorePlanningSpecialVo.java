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
@ApiModel(value ="驾驶舱-规划分仓-专项规划",description = "驾驶舱-规划分仓-专项规划")
public class DriverStorePlanningSpecialVo {
	@ApiModelProperty("应急避难所")
	private Double shelterArea = 1d;

	@ApiModelProperty("用电量")
	private Double powerUsed = 1d;

	@ApiModelProperty("管廊长度")
	private Double pipeLength = 1d;

	@ApiModelProperty("用水总量")
	private Double waterUsed = 1d;
}
