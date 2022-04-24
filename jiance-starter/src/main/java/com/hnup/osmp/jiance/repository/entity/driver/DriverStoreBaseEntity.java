package com.hnup.osmp.jiance.repository.entity.driver;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description 驾驶舱(基础数据)
 */
@Data
public class DriverStoreBaseEntity {
	/**
	 * 统计年度
	 */
	@ApiModelProperty("统计年度")
	@TableId(value = "year")
	private Integer year;

	/**
	 * 单位
	 */
	@ApiModelProperty("单位，目前给值1")
	@TableId(value = "unit")
	private Long unit;

	/**
	 * 单位名称
	 */
	@ApiModelProperty("单位名称，目前给值'长沙市自然资源和规划局'")
	@TableId(value = "unit_name")
	private String unitName;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	@TableId(value = "create_time")
	private Date createTime;
}
