package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(资产数据)
 */
@ApiModel(value ="DriverStoreZican",description = "资产数据")
@Data
@TableName("driver_store_zican")
public class DriverStoreZican extends DriverStoreBaseEntity {
	/**
	 * 登记类型(1:不动产 2:农村房地一体确权 3:自然资源确权)
	 */
	@ApiModelProperty("登记类型(1:不动产 2:农村房地一体确权 3:自然资源确权)")
	@TableId(value = "type")
	private Integer type;

	/**
	 * 发证数量
	 */
	@ApiModelProperty("发证数量")
	@TableId(value = "certificate_quantity")
	private Integer certificateQuantity;

	/**
	 * 登记面积(公顷)
	 */
	@ApiModelProperty("登记面积(公顷)")
	@TableId(value = "register_area")
	private Double registerArea;
}
