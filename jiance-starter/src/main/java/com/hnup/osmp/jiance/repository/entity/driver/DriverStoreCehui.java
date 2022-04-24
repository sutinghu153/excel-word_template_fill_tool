package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(测绘数据)
 */
@ApiModel(value ="DriverStoreCehui",description = "测绘数据")
@Data
@TableName("driver_store_cehui")
public class DriverStoreCehui extends DriverStoreBaseEntity {
	/**
	 * 类型(1:基础测绘项目 2:基础调查项目 3:工程测绘项目)
	 */
	@ApiModelProperty("类型(1:基础测绘项目 2:基础调查项目 3:工程测绘项目)")
	@TableId(value = "type")
	private Integer type;

	/**
	 * 项目数量
	 */
	@ApiModelProperty("项目数量")
	@TableId(value = "prj_quantity")
	private Integer prjQuantity;

	/**
	 * 投资金额/项目面积
	 */
	@ApiModelProperty("投资金额/项目面积")
	@TableId(value = "invest_money_or_area")
	private Double investMoneyOrArea;
}
