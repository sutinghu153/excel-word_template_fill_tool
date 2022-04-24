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
@ApiModel(value ="驾驶舱-土地分仓-土地供应",description = "驾驶舱-土地分仓-土地供应")
public class DriverStoreLandSupplyVo {
	@ApiModelProperty("供应面积")
	private Double area = 925.94d;
	@ApiModelProperty("房地产用地")
	private Double fdcArea = 925.94d;
	@ApiModelProperty("工矿仓储用地")
	private Double gkccArea = 925.94d;
	@ApiModelProperty("基础设施用地")
	private Double jcssArea = 925.94d;
	@ApiModelProperty("其他用地")
	private Double otherArea = 925.94d;
}
