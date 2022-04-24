package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(规划入库成功数据)
 */
@ApiModel(value ="DriverStoreGuihuaGain",description = "规划入库数据")
@Data
@TableName("driver_store_guihua_gain")
public class DriverStoreGuihuaGain extends DriverStoreBaseEntity {
//	/**
//	 * 总体规划
//	 */
//	@ApiModelProperty("总体规划")
//	@TableId(value = "ensemble_quantity")
//	private Integer ensembleQuantity;
//
//	/**
//	 * 专项规划
//	 */
//	@ApiModelProperty("专项规划")
//	@TableId(value = "special_quantity")
//	private Integer specialQuantity;
//
//	/**
//	 * 控制性详细规划
//	 */
//	@ApiModelProperty("控制性详细规划")
//	@TableId(value = "control_quantity")
//	private Integer controlQuantity;
//
//	/**
//	 * 村庄规划
//	 */
//	@ApiModelProperty("村庄规划")
//	@TableId(value = "village_quantity")
//	private Integer villageQuantity;

	@ApiModelProperty("项目立项")
	@TableId(value = "approve_quantity")
	private Integer approveQuantity;

	@ApiModelProperty("招标投标")
	@TableId(value = "bidding_quantity")
	private Integer biddingQuantity;

	@ApiModelProperty("项目建设")
	@TableId(value = "build_quantity")
	private Integer buildQuantity;

	@ApiModelProperty("结题验收")
	@TableId(value = "accept_quantity")
	private Integer acceptQuantity;

	@ApiModelProperty("项目入库")
	@TableId(value = "in_store_quantity")
	private Integer inStoreQuantity;
}
