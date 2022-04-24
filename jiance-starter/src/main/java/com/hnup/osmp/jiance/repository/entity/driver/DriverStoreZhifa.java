package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(执法数据)
 */
@ApiModel(value ="DriverStoreZhifa",description = "执法数据")
@Data
@TableName("driver_store_zhifa")
public class DriverStoreZhifa extends DriverStoreBaseEntity {
	/**
	 * 类型(1:督察反馈问题任务 2:月清三地任务)
	 */
	@ApiModelProperty("类型(1:督察反馈问题任务 2:月清三地任务)")
	@TableId(value = "type")
	private Integer type;

	/**
	 * 子类型
	 * type->1 (1:国家 2:省厅)
	 * type->2 (1:批而未供 2:违法用地 3:闲置土地)
	 */
	@ApiModelProperty("子类型 type->1 (1:国家 2:省厅) type->2 (1:批而未供 2:违法用地 3:闲置土地)")
	@TableId(value = "sub_type")
	private Integer subType;

	/**
	 * 总任务数
	 */
	@ApiModelProperty("总任务数")
	@TableId(value = "total_task_quantity")
	private Integer totalTaskQuantity;

	/**
	 * 已完成
	 */
	@ApiModelProperty("已完成")
	@TableId(value = "finish_task_quantity")
	private Integer finishTaskQuantity;

	/**
	 * 完成率
	 */
	@ApiModelProperty("完成率")
	@TableId(value = "finish_rate")
	private Double finishRate;
}
