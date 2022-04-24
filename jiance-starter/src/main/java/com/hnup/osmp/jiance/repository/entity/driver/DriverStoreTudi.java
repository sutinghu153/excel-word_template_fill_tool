package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(土地数据)
 */
@ApiModel(value ="DriverStoreTudi",description = "土地数据")
@Data
@TableName("driver_store_tudi")
public class DriverStoreTudi extends DriverStoreBaseEntity {
	/**
	 * 土地审批新增面积
	 */
	@ApiModelProperty("土地审批新增面积")
	@TableId(value = "new_area")
	private Double newArea;

	/**
	 * 土地审批计划面积
	 */
	@ApiModelProperty("土地审批计划面积")
	@TableId(value = "plan_area")
	private Double planArea;

	/**
	 * 征收拆迁完成项目数
	 */
	@ApiModelProperty("征收拆迁完成项目数")
	@TableId(value = "finish_quantity")
	private Integer finishQuantity;

	/**
	 * 征收拆迁受理项目数
	 */
	@ApiModelProperty("征收拆迁受理项目数")
	@TableId(value = "accept_quantity")
	private Integer acceptQuantity;

	/**
	 * 土地储备在库总量
	 */
	@ApiModelProperty("土地储备在库总量")
	@TableId(value = "on_store_quantity")
	private Double onStoreQuantity;

	/**
	 * 土地供应已供面积
	 */
	@ApiModelProperty("土地供应已供面积")
	@TableId(value = "had_supply_Area")
	private Double hadSupplyArea;

	/**
	 * 耕地保护保有量现状数
	 */
	@ApiModelProperty("耕地保护保有量现状数")
	@TableId(value = "hold_now_quantity")
	private Double holdNowQuantity;

	/**
	 * 耕地保护保有量目标数
	 */
	@ApiModelProperty("耕地保护保有量目标数")
	@TableId(value = "hold_aim_quantity")
	private Double holdAimQuantity;

	/**
	 * 生态修复已完成项目数
	 */
	@ApiModelProperty("生态修复已完成项目数")
	@TableId(value = "finish_repair_quantity")
	private Integer finishRepairQuantity;

	/**
	 * 生态修复受理项目数
	 */
	@ApiModelProperty("生态修复受理项目数")
	@TableId(value = "accept_repair_quantity")
	private Integer acceptRepairQuantity;
}
