package com.hnup.osmp.jiance.repository.entity.driver;

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
@ApiModel(value ="DriverStoreVo",description = "驾驶舱数据")
public class DriverStoreVo {
	@ApiModelProperty("公文")
	private List<DriverStoreDoc> docs;

	@ApiModelProperty("督办")
	private DriverStoreSupervise supervise;

	@ApiModelProperty("会议")
	private DriverStoreMeeting meeting;

	@ApiModelProperty("审批")
	private DriverStoreOp op;

	@ApiModelProperty("审批排行榜")
	private List<DriverStoreOpRank> opRanks;

	@ApiModelProperty("规划项目")
	private DriverStoreGuihuaPrj guihuaPrj;

	@ApiModelProperty("规划入库成果")
	private DriverStoreGuihuaGain guihuaGain;

	@ApiModelProperty("资产")
	private List<DriverStoreZican> ziCans;

	@ApiModelProperty("地矿")
	private DriverStoreDikuang diKuang;

	@ApiModelProperty("测绘")
	private List<DriverStoreCehui> ceHuis;

	@ApiModelProperty("监测")
	private List<DriverStoreJiance> jianCes;

	@ApiModelProperty("执法")
	private List<DriverStoreZhifa> zhiFas;

	@ApiModelProperty("项目")
	private DriverStoreProject project;

	@ApiModelProperty("项目排行榜")
	private List<DriverStoreProjectRank> proRanks;

	@ApiModelProperty("土地")
	private DriverStoreTudi tudi;
}
