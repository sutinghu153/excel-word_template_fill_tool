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
@ApiModel(value ="驾驶舱-土地分仓-建设用地审批情况",description = "驾驶舱-土地分仓-建设用地审批情况")
public class DriverStoreLandBuildAppriveVo {
	@ApiModelProperty("行政区")
	private String name;
	@ApiModelProperty("面积")
	private Double area = 429.23d;
	@ApiModelProperty("农用地专用")
	private Double village = 123.23d;
	@ApiModelProperty("未利用")
	private Double unUsed = 223.23d;
}
