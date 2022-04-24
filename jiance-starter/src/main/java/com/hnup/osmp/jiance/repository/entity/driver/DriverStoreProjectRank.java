package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(项目数据)
 */
@ApiModel(value ="DriverStoreProjectRank",description = "项目排行数据")
@Data
@TableName("driver_store_project_rank")
public class DriverStoreProjectRank extends DriverStoreBaseEntity {
	/**
	 * 类型
	 */
	@TableId(value = "type")
	private Integer type;

	/**
	 * 类型名称
	 */
	@TableId(value = "type_name")
	private String typeName;

	/**
	 * 数量
	 */
	@TableId(value = "quantity")
	private Integer quantity;
}
