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
@ApiModel(value ="驾驶舱-土地分仓-对比情况",description = "驾驶舱-土地分仓-对比情况")
public class DriverStoreLandRatioVo {
	@ApiModelProperty("批地")
	private Double approve = 100d;
	@ApiModelProperty("供地")
	private Double supply = 200d;
	@ApiModelProperty("储备地")
	private Double storage = 300d;
	@ApiModelProperty("年份")
	private Integer year;

}
