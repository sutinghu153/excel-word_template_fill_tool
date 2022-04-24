package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(公文数据)
 */
@ApiModel(value ="DriverStoreDoc",description = "公文数据")
@Data
@TableName("driver_store_doc")
public class DriverStoreDoc extends DriverStoreBaseEntity{
	/**
	 * 类型(1:收文 2:发文)
	 */
	@ApiModelProperty("类型(1:收文 2:发文)")
	@TableId(value = "type")
	private Integer type;

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
	 * 平均办理时长(小时)
	 */
	@ApiModelProperty("平均办理时长(小时)")
	@TableId(value = "avg_hours")
	private Double avgHours;

	/**
	 * 正点率
	 */
	@ApiModelProperty("正点率")
	@TableId(value = "on_time_rate")
	private Double onTimeRate;
}
