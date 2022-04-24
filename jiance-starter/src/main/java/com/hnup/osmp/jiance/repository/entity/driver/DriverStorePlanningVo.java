package com.hnup.osmp.jiance.repository.entity.driver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2022/01/26
 * @Description
 */
@Data
@ApiModel(value ="驾驶舱-规划分仓",description = "驾驶舱-规划分仓")
public class DriverStorePlanningVo {
	@ApiModelProperty("规划编制")
	private DriverStorePlanningOrganVo organVo;

	@ApiModelProperty("规划指标")
	private DriverStorePlanningItemVo itemVo;

	@ApiModelProperty("规划指标-区域")
	private List<DriverStorePlanningItemAreaVo> areaVo;

	@ApiModelProperty("专项规划")
	private DriverStorePlanningSpecialVo specialVo;

	@ApiModelProperty("详细规划")
	private List<DriverStorePlanningDetailVo> detailVo;

	@ApiModelProperty("详细规划-控制性规划")
	private DriverStorePlanningDetailControleVo controleVo;

	@ApiModelProperty("详细规划-村庄规划区域")
	private List<DriverStorePlanningDetailVillageVo> villageVo;
}
