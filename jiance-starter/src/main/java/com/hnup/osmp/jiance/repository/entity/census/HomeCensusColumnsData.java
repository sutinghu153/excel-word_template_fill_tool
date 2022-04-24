package com.hnup.osmp.jiance.repository.entity.census;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description
 */
@Data
@ApiModel(value ="HomeCensusColumnsData",description = "首页统计数据")
@TableName("home_census_columns_data")
public class HomeCensusColumnsData {
	@ApiModelProperty("统计类型")
	@TableId(value = "type")
	private String type;

	@ApiModelProperty("统计周期(1:本周 2:本月 3:本季 4:本年)")
	@TableId(value = "census_circle")
	private Integer censusCircle;

	@ApiModelProperty("字段描述")
	@TableId(value = "column_desc")
	private String columnDesc;

	@ApiModelProperty("字段数据")
	@TableId(value = "column_data")
	private String columnData;

	@ApiModelProperty("创建时间")
	@TableId(value = "create_time")
	private Date createTime;
}
