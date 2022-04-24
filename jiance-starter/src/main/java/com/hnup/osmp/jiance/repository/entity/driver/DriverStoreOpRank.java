package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(审批数据)
 */
@ApiModel(value ="DriverStoreOpRank",description = "审批排行数据")
@Data
@TableName("driver_store_op_rank")
public class DriverStoreOpRank extends DriverStoreBaseEntity {
	/**
	 * 排行榜编号
	 */
	@ApiModelProperty("排行榜编号")
	@TableId(value = "rank_no")
	private Integer rankNo;

	/**
	 * 部门ID
	 */
	@ApiModelProperty("部门ID")
	@TableId(value = "dept_id")
	private Long deptId;

	/**
	 * 部门名称
	 */
	@ApiModelProperty("部门名称")
	@TableId(value = "dept_name")
	private String deptName;

	/**
	 * 数量
	 */
	@ApiModelProperty("数量")
	@TableId(value = "quantity")
	private Integer quantity;
}
