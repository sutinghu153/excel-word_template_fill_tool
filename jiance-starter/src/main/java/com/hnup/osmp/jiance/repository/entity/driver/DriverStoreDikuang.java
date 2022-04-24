package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(地矿数据)
 */
@ApiModel(value ="DriverStoreDikuang",description = "地矿数据")
@Data
@TableName("driver_store_dikuang")
public class DriverStoreDikuang extends DriverStoreBaseEntity {
	/**
	 * 市级发证矿山数
	 */
	@ApiModelProperty("市级发证矿山数")
	@TableId(value = "cert_mine_quantity_of_city")
	private Integer certMineQuantityOfCity;

	/**
	 * 全市砂石土矿总储量
	 */
	@ApiModelProperty("全市砂石土矿总储量")
	@TableId(value = "dina_reserves_of_city")
	private Double dinaReservesOfCity;

	/**
	 * 地质灾害隐患点
	 */
	@ApiModelProperty("地质灾害隐患点")
	@TableId(value = "geology_damage_point")
	private Integer geologyDamagePoint;

	/**
	 * 综合防治项目数量
	 */
	@ApiModelProperty("综合防治项目数量")
	@TableId(value = "multi_manage_prj_quantity")
	private Integer multiManagePrjQuantity;

	/**
	 * 威胁人数
	 */
	@ApiModelProperty("威胁人数(人)")
	@TableId(value = "menace_peoples")
	private Integer menacePeoples;

	/**
	 * 威胁资产
	 */
	@ApiModelProperty("威胁资产(万元)")
	@TableId(value = "menace_assets")
	private Double menaceAssets;

	/**
	 * 投入金额
	 */
	@ApiModelProperty("投入金额(万元)")
	@TableId(value = "invest_money")
	private Double investMoney;

	/**
	 * 支出金额
	 */
	@ApiModelProperty("支出金额(万元)")
	@TableId(value = "expend_money")
	private Double expendMoney;
}
