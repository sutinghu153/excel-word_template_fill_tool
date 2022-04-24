package com.hnup.osmp.jiance.service.driver;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hnup.osmp.jiance.repository.entity.driver.*;
import com.hnup.osmp.jiance.repository.mapper.driver.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2021/12/21
 * @Description
 */
@Service
public class DriverStoreService {
	@Autowired
	private DriverStoreCehuiMapper driverStoreCehuiMapper;
	@Autowired
	private DriverStoreDikuangMapper driverStoreDikuangMapper;
	@Autowired
	private DriverStoreDocMapper driverStoreDocMapper;
	@Autowired
	private DriverStoreGuihuaGainMapper driverStoreGuihuaGainMapper;
	@Autowired
	private DriverStoreGuihuaPrjMapper driverStoreGuihuaPrjMapper;
	@Autowired
	private DriverStoreJianceMapper driverStoreJianceMapper;
	@Autowired
	private DriverStoreMeetingMapper driverStoreMeetingMapper;
	@Autowired
	private DriverStoreOpMapper driverStoreOpMapper;
	@Autowired
	private DriverStoreOpRankMapper driverStoreOpRankMapper;
	@Autowired
	private DriverStoreProjectMapper driverStoreProjectMapper;
	@Autowired
	private DriverStoreProjectRankMapper driverStoreProjectRankMapper;
	@Autowired
	private DriverStoreSuperviseMapper driverStoreSuperviseMapper;
	@Autowired
	private DriverStoreTudiMapper driverStoreTudiMapper;
	@Autowired
	private DriverStoreZhifaMapper driverStoreZhifaMapper;
	@Autowired
	private DriverStoreZicanMapper driverStoreZicanMapper;

	@Autowired
	private DriverStoreDataMapper driverStoreDataMapper;

	public DriverStoreVo loadDriverStoreData(Integer year, Long unit) {
		DriverStoreVo vo = new DriverStoreVo();
		// 公文
		LambdaQueryWrapper<DriverStoreDoc> docWrapper = Wrappers.<DriverStoreDoc>lambdaQuery()
			.eq(DriverStoreDoc::getYear, year)
			.eq(DriverStoreDoc::getUnit, unit);
		List<DriverStoreDoc> docs = driverStoreDocMapper.selectList(docWrapper);
		vo.setDocs(docs);

		// 督办事务
		LambdaQueryWrapper<DriverStoreSupervise> superviseWrapper = Wrappers.<DriverStoreSupervise>lambdaQuery()
			.eq(DriverStoreSupervise::getYear, year)
			.eq(DriverStoreSupervise::getUnit, unit);
		DriverStoreSupervise supervise = driverStoreSuperviseMapper.selectOne(superviseWrapper);
		vo.setSupervise(supervise);

		// 会议
		LambdaQueryWrapper<DriverStoreMeeting> meetWrapper = Wrappers.<DriverStoreMeeting>lambdaQuery()
			.eq(DriverStoreMeeting::getYear, year)
			.eq(DriverStoreMeeting::getUnit, unit);
		DriverStoreMeeting meeting = driverStoreMeetingMapper.selectOne(meetWrapper);
		vo.setMeeting(meeting);

		// 审批
		LambdaQueryWrapper<DriverStoreOp> opWrapper = Wrappers.<DriverStoreOp>lambdaQuery()
			.eq(DriverStoreOp::getYear, year)
			.eq(DriverStoreOp::getUnit, unit);
		DriverStoreOp op = driverStoreOpMapper.selectOne(opWrapper);
		vo.setOp(op);

		// 审批排行榜
		LambdaQueryWrapper<DriverStoreOpRank> opRankWrapper = Wrappers.<DriverStoreOpRank>lambdaQuery()
			.eq(DriverStoreOpRank::getYear, year)
			.eq(DriverStoreOpRank::getUnit, unit)
			.orderByAsc(DriverStoreOpRank::getRankNo);
		List<DriverStoreOpRank> opRanks = driverStoreOpRankMapper.selectList(opRankWrapper);
		vo.setOpRanks(opRanks);

		// 规划项目总览
		LambdaQueryWrapper<DriverStoreGuihuaPrj> guihuaPrjWrapper = Wrappers.<DriverStoreGuihuaPrj>lambdaQuery()
			.eq(DriverStoreGuihuaPrj::getYear, year)
			.eq(DriverStoreGuihuaPrj::getUnit, unit);
		DriverStoreGuihuaPrj guihuaPrj = driverStoreGuihuaPrjMapper.selectOne(guihuaPrjWrapper);
		vo.setGuihuaPrj(guihuaPrj);

		// 规划入库成果
		LambdaQueryWrapper<DriverStoreGuihuaGain> guihuaGainWrapper = Wrappers.<DriverStoreGuihuaGain>lambdaQuery()
			.eq(DriverStoreGuihuaGain::getYear, year)
			.eq(DriverStoreGuihuaGain::getUnit, unit);
		DriverStoreGuihuaGain guihuaGain = driverStoreGuihuaGainMapper.selectOne(guihuaGainWrapper);
		vo.setGuihuaGain(guihuaGain);

		// 资产产权
		LambdaQueryWrapper<DriverStoreZican> zichanWrapper = Wrappers.<DriverStoreZican>lambdaQuery()
			.eq(DriverStoreZican::getYear, year)
			.eq(DriverStoreZican::getUnit, unit);
		List<DriverStoreZican> ziCans = driverStoreZicanMapper.selectList(zichanWrapper);
		vo.setZiCans(ziCans);

		// 地矿
		LambdaQueryWrapper<DriverStoreDikuang> diKuangWrapper = Wrappers.<DriverStoreDikuang>lambdaQuery()
			.eq(DriverStoreDikuang::getYear, year)
			.eq(DriverStoreDikuang::getUnit, unit);
		DriverStoreDikuang diKuang = driverStoreDikuangMapper.selectOne(diKuangWrapper);
		vo.setDiKuang(diKuang);

		// 测绘调查
		LambdaQueryWrapper<DriverStoreCehui> ceHuiWrapper = Wrappers.<DriverStoreCehui>lambdaQuery()
			.eq(DriverStoreCehui::getYear, year)
			.eq(DriverStoreCehui::getUnit, unit);
		List<DriverStoreCehui> ceHuis = driverStoreCehuiMapper.selectList(ceHuiWrapper);
		vo.setCeHuis(ceHuis);

		// 监测
		LambdaQueryWrapper<DriverStoreJiance> jianCeWrapper = Wrappers.<DriverStoreJiance>lambdaQuery()
			.eq(DriverStoreJiance::getYear, year)
			.eq(DriverStoreJiance::getUnit, unit);
		List<DriverStoreJiance> jianCes = driverStoreJianceMapper.selectList(jianCeWrapper);
		vo.setJianCes(jianCes);

		// 执法
		LambdaQueryWrapper<DriverStoreZhifa> zhiFaWrapper = Wrappers.<DriverStoreZhifa>lambdaQuery()
			.eq(DriverStoreZhifa::getYear, year)
			.eq(DriverStoreZhifa::getUnit, unit);
		List<DriverStoreZhifa> zhiFas = driverStoreZhifaMapper.selectList(zhiFaWrapper);
		vo.setZhiFas(zhiFas);

		// 项目
		LambdaQueryWrapper<DriverStoreProject> projectWrapper = Wrappers.<DriverStoreProject>lambdaQuery()
			.eq(DriverStoreProject::getYear, year)
			.eq(DriverStoreProject::getUnit, unit);
		DriverStoreProject project = driverStoreProjectMapper.selectOne(projectWrapper);
		vo.setProject(project);

		// 项目排行榜
		LambdaQueryWrapper<DriverStoreProjectRank> projectRankWrapper = Wrappers.<DriverStoreProjectRank>lambdaQuery()
			.eq(DriverStoreProjectRank::getYear, year)
			.eq(DriverStoreProjectRank::getUnit, unit)
			.orderByDesc(DriverStoreProjectRank::getQuantity);
		List<DriverStoreProjectRank> proRanks = driverStoreProjectRankMapper.selectList(projectRankWrapper);
		vo.setProRanks(proRanks);

		// 土地
		LambdaQueryWrapper<DriverStoreTudi> tuDiWrapper = Wrappers.<DriverStoreTudi>lambdaQuery()
			.eq(DriverStoreTudi::getYear, year)
			.eq(DriverStoreTudi::getUnit, unit);
		DriverStoreTudi tudi = driverStoreTudiMapper.selectOne(tuDiWrapper);
		vo.setTudi(tudi);

		return vo;
	}

	/**
	 * 规划分仓
	 */
	public DriverStorePlanningVo loadPlanningStore() {
		DriverStorePlanningVo planningVo = new DriverStorePlanningVo();
		planningVo.setOrganVo(new DriverStorePlanningOrganVo());
		planningVo.setItemVo(new DriverStorePlanningItemVo());
		List<DriverStorePlanningItemAreaVo> areaVos = new ArrayList<>();
		areaVos.add(new DriverStorePlanningItemAreaVo("宁乡市", 277219d));
		areaVos.add(new DriverStorePlanningItemAreaVo("浏阳市", 177283d));
		areaVos.add(new DriverStorePlanningItemAreaVo("长沙县", 132213d));
		planningVo.setAreaVo(areaVos);
		planningVo.setSpecialVo(new DriverStorePlanningSpecialVo());

		List<DriverStorePlanningDetailVo> detailVos = new ArrayList<>();
		DriverStorePlanningDetailVo detailVo = new DriverStorePlanningDetailVo();
		detailVo.setType(1);
		detailVos.add(detailVo);
		detailVo = new DriverStorePlanningDetailVo();
		detailVo.setType(2);
		detailVos.add(detailVo);
		planningVo.setDetailVo(detailVos);

		planningVo.setControleVo(new DriverStorePlanningDetailControleVo());

		List<DriverStorePlanningDetailVillageVo> villageVos = new ArrayList<>();
		villageVos.add(new DriverStorePlanningDetailVillageVo("宁乡市", 277219d));
		villageVos.add(new DriverStorePlanningDetailVillageVo("浏阳市", 177283d));
		villageVos.add(new DriverStorePlanningDetailVillageVo("长沙县", 132213d));

		planningVo.setVillageVo(villageVos);

		return planningVo;
	}

	/**
	 * 土地分仓
	 */
	public DriverStoreLandVo loadLandStore() {
		DriverStoreLandVo landVo = new DriverStoreLandVo();
		landVo.setApproveVo(new DriverStoreLandApproveVo());
		landVo.setSupplyVo(new DriverStoreLandSupplyVo());
		landVo.setStorageVo(new DriverStoreLandStorageVo());
		List<DriverStoreLandRatioVo> ratioVos = new ArrayList<>();
		DriverStoreLandRatioVo ratioVo = new DriverStoreLandRatioVo();
		ratioVo.setYear(2019);
		ratioVos.add(ratioVo);

		ratioVo = new DriverStoreLandRatioVo();
		ratioVo.setYear(2020);
		ratioVos.add(ratioVo);

		ratioVo = new DriverStoreLandRatioVo();
		ratioVo.setYear(2021);
		ratioVos.add(ratioVo);

		ratioVo = new DriverStoreLandRatioVo();
		ratioVo.setYear(2022);
		ratioVos.add(ratioVo);

		landVo.setRatioVos(ratioVos);

		List<DriverStoreLandRepairVo> repairVos = new ArrayList<>();
		DriverStoreLandRepairVo repairVo = new DriverStoreLandRepairVo();
		repairVo.setType(1);
		repairVos.add(repairVo);

		repairVo = new DriverStoreLandRepairVo();
		repairVo.setType(2);
		repairVos.add(repairVo);

		repairVo = new DriverStoreLandRepairVo();
		repairVo.setType(3);
		repairVos.add(repairVo);

		repairVo = new DriverStoreLandRepairVo();
		repairVo.setType(4);
		repairVos.add(repairVo);

		landVo.setRepairVos(repairVos);

		List<DriverStoreLandMonitorVo> monitorVos = new ArrayList<>();
		DriverStoreLandMonitorVo monitorVo = new DriverStoreLandMonitorVo();
		monitorVo.setName("违法用地");
		monitorVo.setArea(100d);
		monitorVo.setLight(1);
		monitorVos.add(monitorVo);

		monitorVo = new DriverStoreLandMonitorVo();
		monitorVo.setName("批而未供土地");
		monitorVo.setArea(100d);
		monitorVo.setLight(2);
		monitorVos.add(monitorVo);

		monitorVo = new DriverStoreLandMonitorVo();
		monitorVo.setName("待撤批地");
		monitorVo.setArea(100d);
		monitorVo.setLight(3);
		monitorVos.add(monitorVo);
		landVo.setMonitorVos(monitorVos);

		List<DriverStoreLandBuildAppriveVo> buildVos = new ArrayList<>();
		DriverStoreLandBuildAppriveVo buildVo = new DriverStoreLandBuildAppriveVo();
		buildVo.setName("长沙市");
		buildVos.add(buildVo);

		buildVo = new DriverStoreLandBuildAppriveVo();
		buildVo.setName("芙蓉区");
		buildVos.add(buildVo);

		buildVo = new DriverStoreLandBuildAppriveVo();
		buildVo.setName("天心区");
		buildVos.add(buildVo);

		buildVo = new DriverStoreLandBuildAppriveVo();
		buildVo.setName("雨花区");
		buildVos.add(buildVo);

		buildVo = new DriverStoreLandBuildAppriveVo();
		buildVo.setName("开福区");
		buildVos.add(buildVo);
		landVo.setBuildVos(buildVos);
		return landVo;
	}

	/**
	 * 同步公文数据
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synDocData() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		List<DriverStoreDoc> docs = driverStoreDataMapper.statisDoc(year);
		if(CollectionUtils.isNotEmpty(docs)) {
			LambdaQueryWrapper<DriverStoreDoc> delWrapper = new LambdaQueryWrapper<>();
			delWrapper.eq(DriverStoreDoc::getYear, year);
			driverStoreDocMapper.delete(delWrapper);
			docs.forEach(doc -> {
				driverStoreDocMapper.insert(doc);
			});
		}
	}

	/**
	 * 同步督办数据
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synSuppervise() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		DriverStoreSupervise supervise = driverStoreDataMapper.statisSuppervise(year);
		if(supervise != null) {
			LambdaQueryWrapper<DriverStoreSupervise> delWrapper = new LambdaQueryWrapper<>();
			delWrapper.eq(DriverStoreSupervise::getYear, year);
			driverStoreSuperviseMapper.delete(delWrapper);
			driverStoreSuperviseMapper.insert(supervise);
		}
	}

	/**
	 * 同步op数据
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synOp() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		DriverStoreOp op = driverStoreDataMapper.statisOp(year );
		if(op != null) {
			LambdaQueryWrapper<DriverStoreOp> delWrapper = new LambdaQueryWrapper<>();
			delWrapper.eq(DriverStoreOp::getYear, year);
			driverStoreOpMapper.delete(delWrapper);
			driverStoreOpMapper.insert(op);
		}
	}

	/**
	 * 同步op排行榜数据
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synOpRank() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		List<DriverStoreOpRank> opRanks = driverStoreDataMapper.statisOpRank(year);
		if(CollectionUtils.isNotEmpty(opRanks)) {
			LambdaQueryWrapper<DriverStoreOpRank> delWrapper = new LambdaQueryWrapper<>();
			delWrapper.eq(DriverStoreOpRank::getYear, year);
			driverStoreOpRankMapper.delete(delWrapper);
			opRanks.forEach(opRank -> {
				driverStoreOpRankMapper.insert(opRank);
			});
		}
	}

	/**
	 * 同步会议数据
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synMeeting() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		DriverStoreMeeting meeting = driverStoreDataMapper.staisMeeting(year);
		if(meeting != null) {
			LambdaQueryWrapper<DriverStoreMeeting> delWrapper = new LambdaQueryWrapper<>();
			delWrapper.eq(DriverStoreMeeting::getYear, year);
			driverStoreMeetingMapper.delete(delWrapper);
			driverStoreMeetingMapper.insert(meeting);
		}
	}

	/**
	 * 同步首次登记确权数量
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synZican() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		DriverStoreZican zican = driverStoreDataMapper.statisZican(year);
		if(zican != null) {
			LambdaQueryWrapper<DriverStoreZican> delWrapper = new LambdaQueryWrapper<>();
			delWrapper
				.eq(DriverStoreZican::getYear, year)
				.eq(DriverStoreZican::getType, zican.getType());
			driverStoreZicanMapper.delete(delWrapper);
			driverStoreZicanMapper.insert(zican);
		}
	}

	/**
	 * 同步执法督查数据
	 */
	@Transactional(rollbackFor = Exception.class)
	public void synZhifa() {
		Calendar date = Calendar.getInstance();
		Integer year = date.get(Calendar.YEAR) - 1;
		List<DriverStoreZhifa> zhifas = driverStoreDataMapper.statisZhifa(year);
		if(zhifas != null) {
			zhifas.forEach(zhifa -> {
				LambdaQueryWrapper<DriverStoreZhifa> delWrapper = new LambdaQueryWrapper<>();
				delWrapper
					.eq(DriverStoreZhifa::getYear, year)
					.eq(DriverStoreZhifa::getType, zhifa.getType())
					.eq(DriverStoreZhifa::getSubType, zhifa.getSubType());
				driverStoreZhifaMapper.delete(delWrapper);
				driverStoreZhifaMapper.insert(zhifa);
			});
		}
	}

	/**
	 * 保存土地数据【土地管理】
	 * @param tudi
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveTudi(DriverStoreTudi tudi) {
		tudi.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreTudi> delWrapper = new LambdaQueryWrapper<>();
		delWrapper.eq(DriverStoreTudi::getYear, tudi.getYear());
		driverStoreTudiMapper.delete(delWrapper);
		driverStoreTudiMapper.insert(tudi);
	}

	/**
	 * 保存地矿数据【地质矿产】
	 * @param dikuang
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveDikuang(DriverStoreDikuang dikuang) {
		dikuang.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreDikuang> delWrapper = new LambdaQueryWrapper<>();
		delWrapper.eq(DriverStoreDikuang::getYear, dikuang.getYear());
		driverStoreDikuangMapper.delete(delWrapper);
		driverStoreDikuangMapper.insert(dikuang);
	}

	/**
	 * 保存项目数据【项目管理】
	 * @param project
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveProject(DriverStoreProject project) {
		project.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreProject> delWrapper = new LambdaQueryWrapper<>();
		delWrapper.eq(DriverStoreProject::getYear, project.getYear());
		driverStoreProjectMapper.delete(delWrapper);
		driverStoreProjectMapper.insert(project);
	}

	/**
	 * 保存项目数据【项目排行榜】
	 * @param ranks
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveProjectRank(List<DriverStoreProjectRank> ranks) {
		if(CollectionUtils.isNotEmpty(ranks)) {
			LambdaQueryWrapper<DriverStoreProjectRank> delWrapper = new LambdaQueryWrapper<>();
			delWrapper.eq(DriverStoreProjectRank::getYear, ranks.get(0).getYear());
			driverStoreProjectRankMapper.delete(delWrapper);
			ranks.forEach(rank -> {
				rank.setCreateTime(new Date());
				driverStoreProjectRankMapper.insert(rank);
			});
		}
	}

	/**
	 * 保存测绘数据【测绘调查】
	 * @param cehui
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveCehui(DriverStoreCehui cehui) {
		cehui.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreCehui> delWrapper = new LambdaQueryWrapper<>();
		delWrapper
			.eq(DriverStoreCehui::getType, cehui.getType())
			.eq(DriverStoreCehui::getYear, cehui.getYear());
		driverStoreCehuiMapper.delete(delWrapper);
		driverStoreCehuiMapper.insert(cehui);
	}

	/**
	 * 保存规划编制项目总览【规划管控】
	 * @param guihuaPrj
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveGuihuaPrj(DriverStoreGuihuaPrj guihuaPrj) {
		guihuaPrj.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreGuihuaPrj> delWrapper = new LambdaQueryWrapper<>();
		delWrapper
			.eq(DriverStoreGuihuaPrj::getYear, guihuaPrj.getYear());
		driverStoreGuihuaPrjMapper.delete(delWrapper);
		driverStoreGuihuaPrjMapper.insert(guihuaPrj);
	}

	/**
	 * 保存规划编制项目状态【规划管控】
	 * @param guihuaGain
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveGuihuaGain(DriverStoreGuihuaGain guihuaGain) {
		guihuaGain.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreGuihuaGain> delWrapper = new LambdaQueryWrapper<>();
		delWrapper
			.eq(DriverStoreGuihuaGain::getYear, guihuaGain.getYear());
		driverStoreGuihuaGainMapper.delete(delWrapper);
		driverStoreGuihuaGainMapper.insert(guihuaGain);
	}

	/**
	 * 保存不动产统一登记/农村房地一体确权登记(资产产权)
	 * @param zican
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveZican(DriverStoreZican zican) {
		zican.setCreateTime(new Date());
		LambdaQueryWrapper<DriverStoreZican> delWrapper = new LambdaQueryWrapper<>();
		delWrapper
			.eq(DriverStoreZican::getYear, zican.getYear())
			.eq(DriverStoreZican::getType, zican.getType());
		driverStoreZicanMapper.delete(delWrapper);
		driverStoreZicanMapper.insert(zican);
	}
}
