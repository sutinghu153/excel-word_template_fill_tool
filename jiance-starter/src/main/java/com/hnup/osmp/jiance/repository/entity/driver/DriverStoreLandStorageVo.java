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
@ApiModel(value ="驾驶舱-土地分仓-土地储备",description = "驾驶舱-土地分仓-土地储备")
public class DriverStoreLandStorageVo {
	@ApiModelProperty("储备计划")
	private Double storagePlan = 708.21d;

	@ApiModelProperty("入库")
	private Double inStore = 0.75;

	@ApiModelProperty("执行率")
	private Double executeRate = 0.25;
}
