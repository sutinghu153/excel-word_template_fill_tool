package com.hnup.osmp.jiance.service.census;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hnup.osmp.jiance.repository.entity.census.HomeCensusColumns;
import com.hnup.osmp.jiance.repository.entity.census.HomeCensusColumnsData;
import com.hnup.osmp.jiance.repository.entity.census.HomeCensusColumnsVo;
import com.hnup.osmp.jiance.repository.mapper.census.HomeCensusColumnsDataMapper;
import com.hnup.osmp.jiance.repository.mapper.census.HomeCensusColumnsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description
 */
@Service
public class HomeCensusService {
	@Autowired
	private HomeCensusColumnsMapper homeCensusColumnsMapper;

	@Autowired
	private HomeCensusColumnsDataMapper homeCensusColumnsDataMapper;

	public List<HomeCensusColumnsVo> loadHomeCensusData(Integer censusCircle) {
		LambdaQueryWrapper<HomeCensusColumns> columnsWrapper = Wrappers.<HomeCensusColumns>lambdaQuery()
			.isNull(HomeCensusColumns::getParentType);
		List<HomeCensusColumns> columns = homeCensusColumnsMapper.selectList(columnsWrapper);
		List<HomeCensusColumnsVo> columnsVos = new ArrayList<>();
		if(columns != null) {
			columns.forEach(column -> {
				HomeCensusColumnsVo columnsVo = new HomeCensusColumnsVo();
				BeanUtils.copyProperties(column, columnsVo);
				LambdaQueryWrapper<HomeCensusColumns> subColumnsWrapper = Wrappers.<HomeCensusColumns>lambdaQuery()
					.eq(HomeCensusColumns::getParentType, column.getType());
				List<HomeCensusColumns> subColumns = homeCensusColumnsMapper.selectList(subColumnsWrapper);
				List<HomeCensusColumnsVo> childVos = new ArrayList<>();
				if(subColumns != null) {
					subColumns.forEach(subColumn -> {
						HomeCensusColumnsVo childVo = new HomeCensusColumnsVo();
						BeanUtils.copyProperties(subColumn, childVo);
						LambdaQueryWrapper<HomeCensusColumnsData> columnDataWrapper = Wrappers.<HomeCensusColumnsData>lambdaQuery()
							.eq(HomeCensusColumnsData::getType, subColumn.getType())
							.eq(HomeCensusColumnsData::getCensusCircle, censusCircle);
						List<HomeCensusColumnsData> datas = homeCensusColumnsDataMapper.selectList(columnDataWrapper);
						childVo.setDatas(datas);
						childVos.add(childVo);
					});
				}
				columnsVo.setChild(childVos);
				columnsVos.add(columnsVo);
			});
		}
		return columnsVos;
	}

	/**
	 * ????????????
	 * @param parentType
	 */
	public void syncData(String parentType) {
		LambdaQueryWrapper<HomeCensusColumns> columnsWrapper = Wrappers.<HomeCensusColumns>lambdaQuery()
			.eq(HomeCensusColumns::getParentType, parentType);
		List<HomeCensusColumns> types = homeCensusColumnsMapper.selectList(columnsWrapper);
		if(CollectionUtils.isNotEmpty(types)) {
			types.forEach(column -> {
				LambdaQueryWrapper<HomeCensusColumnsData> dataWrapper = Wrappers.<HomeCensusColumnsData>lambdaQuery()
					.eq(HomeCensusColumnsData::getType, column.getType());
				List<HomeCensusColumnsData> datas = homeCensusColumnsDataMapper.selectList(dataWrapper);
				if(CollectionUtils.isNotEmpty(types)) {
					datas.forEach(data -> {
						// ??????????????????
						String value = statisData(parentType, data);
						if(StringUtils.isNotEmpty(value)) {
							data.setColumnData(value);
						} else {
							data.setColumnData("0");
						}
						data.setCreateTime(new Date());
						LambdaQueryWrapper<HomeCensusColumnsData> updateWrapper = Wrappers.<HomeCensusColumnsData>lambdaQuery();
						updateWrapper
							.eq(HomeCensusColumnsData::getType, data.getType())
							.eq(HomeCensusColumnsData::getColumnDesc, data.getColumnDesc())
							.eq(HomeCensusColumnsData::getCensusCircle, data.getCensusCircle());
						homeCensusColumnsDataMapper.update(data, updateWrapper);
					});
				}
			});
		}
	}

	/**
	 * ????????????
	 * @param parentType
	 * @param columnsData
	 * @return
	 */
	public String statisData(String parentType, HomeCensusColumnsData columnsData) {
		String value = "";
		if("XZXN".equals(parentType)) {
			//	????????????
			if("GW".equals(columnsData.getType())) {
				// ??????????????????????????????
				Map<String, Object> data = homeCensusColumnsDataMapper.statisDoc(columnsData.getCensusCircle());
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("DB".equals(columnsData.getType())) {
				// ??????????????????????????????
				Map<String, Object> data = homeCensusColumnsDataMapper.statisSupervise(columnsData.getCensusCircle());
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("AJ".equals(columnsData.getType())) {
				// ??????????????????????????????
				Map<String, Object> data = homeCensusColumnsDataMapper.statisOp(columnsData.getCensusCircle());
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("HY".equals(columnsData.getType())) {
				// ??????????????????????????????
				Map<String, Object> data = homeCensusColumnsDataMapper.statisMeeting(columnsData.getCensusCircle());
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			}
		} else if("GDBH".equals(parentType)) {
			// TODO ?????????????????????(?????????)
		} else if("JSYDSP".equals(parentType)) {
			if("XZJSYD1".equals(columnsData.getType())) {
				// ??????????????????1
				Map<String, Object> data = homeCensusColumnsDataMapper.statisJsydsp(columnsData.getCensusCircle());
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("XZJSYD2".equals(columnsData.getType())) {
				// ??????????????????2
				Map<String, Object> data = homeCensusColumnsDataMapper.statisJsydsp(columnsData.getCensusCircle());
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			}
		} else if("TDGY".equals(parentType)) {
			// TODO ????????????????????????gis(?????????)
		} else if("GHXKBL".equals(parentType)) {
			if("YDYSYXZYJS".equals(columnsData.getType())) {
				// ??????????????????????????????
				Map<String, Object> data = homeCensusColumnsDataMapper.statisGhxkbl(columnsData.getCensusCircle(), "????????????????????????????????????");
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("JSGCGHXKZ(SZ)".equals(columnsData.getType())) {
				// ???????????????????????????(??????)
				Map<String, Object> data = homeCensusColumnsDataMapper.statisGhxkbl(columnsData.getCensusCircle(), "???????????????????????????????????????-??????");
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("JSGCGHXKZ".equals(columnsData.getType())) {
				// ???????????????????????????
				Map<String, Object> data = homeCensusColumnsDataMapper.statisGhxkbl(columnsData.getCensusCircle(), "???????????????????????????-??????");
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			} else if("JSGCGHXKZ(JZ)".equals(columnsData.getType())) {
				// ???????????????????????????(??????)
				Map<String, Object> data = homeCensusColumnsDataMapper.statisGhxkbl(columnsData.getCensusCircle(), "???????????????????????????????????????-??????");
				if(data != null) {
					value = data.get(columnsData.getColumnDesc()).toString();
				}
			}
		} else if("PEWGD".equals(parentType)) {
			// TODO ????????????????????????(?????????)??????????????????????????????????????????????????????
		} else if("YQSD".equals(parentType)) {
			// TODO ?????????????????????(?????????)??????????????????????????????????????????????????????
		}
		if(value == null) {
			value = "";
		}
		return value;
	}
}
