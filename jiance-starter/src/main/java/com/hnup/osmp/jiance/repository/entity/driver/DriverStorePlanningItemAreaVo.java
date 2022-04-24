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
@ApiModel(value ="驾驶舱-规划分仓-规划指标-区域",description = "驾驶舱-规划分仓-规划指标-区域")
public class DriverStorePlanningItemAreaVo {
	@ApiModelProperty("区域名称")
	private String areaName;

	@ApiModelProperty("区域数量")
	private Double amount;

	public DriverStorePlanningItemAreaVo(String areaName, Double amount) {
		this.areaName = areaName;
		this.amount = amount;
	}
}
