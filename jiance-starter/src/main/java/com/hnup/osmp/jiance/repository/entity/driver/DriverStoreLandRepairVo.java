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
@ApiModel(value ="驾驶舱-土地分仓-土地修复",description = "驾驶舱-土地分仓-土地修复")
public class DriverStoreLandRepairVo {
	@ApiModelProperty("类型 1：土地全域 2:土地复垦 3:增减挂钩 4:生态修复")
	private Integer type;
	@ApiModelProperty("面积")
	private Double area = 601.23d;
	@ApiModelProperty("总数")
	private Long amount = 21L;
	@ApiModelProperty("实施中")
	private Long countOfEffect = 10L;
	@ApiModelProperty("已验收")
	private Long countOfCheck = 8L;
}
