package com.hnup.osmp.jiance.repository.entity.census;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description
 */
@Data
@ApiModel(value ="HomeCensusColumnsVo",description = "首页统计数据")
public class HomeCensusColumnsVo {
	@ApiModelProperty("统计类型")
	private String type;

	@ApiModelProperty("父类型")
	private String parentType;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("子类型")
	private List<HomeCensusColumnsVo> child;

	@ApiModelProperty("数据")
	private List<HomeCensusColumnsData> datas;
}
