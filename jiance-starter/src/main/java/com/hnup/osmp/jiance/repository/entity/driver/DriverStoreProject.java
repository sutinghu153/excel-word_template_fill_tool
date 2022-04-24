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
@ApiModel(value ="DriverStoreProject",description = "项目数据")
@Data
@TableName("driver_store_project")
public class DriverStoreProject extends DriverStoreBaseEntity {

	/**
	 * 项目总数
	 */
	@TableId(value = "total_quantity")
	private Integer totalQuantity;
}
