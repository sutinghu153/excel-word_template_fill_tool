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
@ApiModel(value ="驾驶舱-规划分仓-规划编制",description = "驾驶舱-规划分仓-规划编制")
public class DriverStorePlanningOrganVo {
	@ApiModelProperty("立项")
	private Long establishment = 33L;

	@ApiModelProperty("报审")
	private Long approvalOfReport = 21L;

	@ApiModelProperty("批复")
	private Long approved = 10L;

	@ApiModelProperty("入库")
	private Long inStore = 8L;
}
