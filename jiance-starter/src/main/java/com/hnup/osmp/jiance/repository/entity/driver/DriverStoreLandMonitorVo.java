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
@ApiModel(value ="驾驶舱-土地分仓-土地监测",description = "驾驶舱-土地分仓-土地监测")
public class DriverStoreLandMonitorVo {
	@ApiModelProperty("指标")
	private String name;
	@ApiModelProperty("面积")
	private Double area;
	@ApiModelProperty("预警状态 1:绿 2:黄 3:红")
	private Integer light;
}
