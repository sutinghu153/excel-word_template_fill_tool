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
@ApiModel(value ="驾驶舱-规划分仓-详细规划",description = "驾驶舱-规划分仓-详细规划")
public class DriverStorePlanningDetailVo {

	@ApiModelProperty("类型(1：控制性 2：村庄)")
	private Integer type;

	@ApiModelProperty("立项")
	private Long establishment = 1L;

	@ApiModelProperty("报审")
	private Long approvalOfReport = 1L;

	@ApiModelProperty("批复")
	private Long approved = 1L;

	@ApiModelProperty("入库")
	private Long inStore = 1L;
}
