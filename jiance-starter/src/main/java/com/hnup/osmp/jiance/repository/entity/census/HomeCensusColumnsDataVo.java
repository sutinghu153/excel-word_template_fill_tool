package com.hnup.osmp.jiance.repository.entity.census;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description
 */
@Data
@ApiModel(value ="HomeCensusColumnsData",description = "首页统计数据")
public class HomeCensusColumnsDataVo {
	private String type;
	private String name;
	private List<HomeCensusColumnsData> datas;
}
