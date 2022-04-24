package com.hnup.osmp.jiance.repository.entity.census;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description 统计类型数据
 */
@Data
@ApiModel(value ="HomeCensusColumns",description = "首页统计类型")
@TableName("home_census_columns")
public class HomeCensusColumns {
	@ApiModelProperty("统计类型")
	@TableId(value = "type")
	private String type;

	@ApiModelProperty("父类型")
	@TableId(value = "parent_type")
	private String parentType;

	@ApiModelProperty("名称")
	@TableId(value = "name")
	private String name;
}
