package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(规划项目数据)
 */
@ApiModel(value ="DriverStoreGuihuaPrj",description = "规划项目数据")
@Data
@TableName("driver_store_guihua_prj")
public class DriverStoreGuihuaPrj extends DriverStoreBaseEntity {

	/**
	 * 项目总数
	 */
	@ApiModelProperty("项目总数")
	@TableId(value = "total_quantity")
	private Integer totalQuantity;

	/**
	 * 预算金额
	 */
	@ApiModelProperty("预算金额")
	@TableId(value = "budget_money")
	private Double budgetMoney;

	/**
	 * 合同金额
	 */
	@ApiModelProperty("合同金额")
	@TableId(value = "contract_money")
	private Double contractMoney;

	/**
	 * 已采购
	 */
	@ApiModelProperty("已采购")
	@TableId(value = "purchased")
	private Double purchased;
}
