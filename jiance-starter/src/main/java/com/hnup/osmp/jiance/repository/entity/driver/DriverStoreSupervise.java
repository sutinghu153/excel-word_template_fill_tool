package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(督办事物数据)
 */
@ApiModel(value ="DriverStoreSupervise",description = "督办事物数据")
@Data
@TableName("driver_store_supervise")
public class DriverStoreSupervise extends DriverStoreBaseEntity{
	/**
	 * 登记量(件)
	 */
	@ApiModelProperty("登记量(件)")
	@TableId(value = "register_quantity")
	private Integer registerQuantity;

	/**
	 * 办结量(件)
	 */
	@ApiModelProperty("办结量(件)")
	@TableId(value = "finish_quantity")
	private Integer finishQuantity;

	/**
	 * 正点率
	 */
	@ApiModelProperty("正点率")
	@TableId(value = "on_time_rate")
	private Double onTimeRate;
}
