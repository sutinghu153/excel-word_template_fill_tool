package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(监测数据)
 */
@ApiModel(value ="DriverStoreJiance",description = "监测数据")
@Data
@TableName("driver_store_jiance")
public class DriverStoreJiance extends DriverStoreBaseEntity {
	/**
	 * 类型(1:国土空间总体规划 2:净地攻坚(公顷) 3:土地确权登记)
	 */
	@ApiModelProperty("类型(1:国土空间总体规划 2:净地攻坚(公顷) 3:土地确权登记)")
	@TableId(value = "type")
	private Integer type;

	/**
	 * 总任务数
	 */
	@ApiModelProperty("总任务数")
	@TableId(value = "total_task_quantity")
	private Integer totalTaskQuantity;

	/**
	 * 完成任务数
	 */
	@ApiModelProperty("完成任务数")
	@TableId(value = "finish_task_quantity")
	private Integer finishTaskQuantity;

	/**
	 * 完成率
	 */
	@ApiModelProperty("完成率")
	@TableId(value = "finish_rate")
	private Double finishRate;

	/**
	 * 任务完成时限
	 */
	@ApiModelProperty("任务完成时限")
	@TableId(value = "finish_limit")
	private String finishLimit;
}
