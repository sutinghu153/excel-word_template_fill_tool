package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(会议数据)
 */
@ApiModel(value ="DriverStoreMeeting",description = "会议数据")
@Data
@TableName("driver_store_meeting")
public class DriverStoreMeeting extends DriverStoreBaseEntity{
	/**
	 * 会议数量
	 */
	@ApiModelProperty("会议数量")
	@TableId(value = "quantity")
	private Integer quantity;
}
