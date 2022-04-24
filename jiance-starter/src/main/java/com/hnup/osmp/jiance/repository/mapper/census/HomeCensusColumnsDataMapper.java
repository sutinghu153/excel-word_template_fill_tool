package com.hnup.osmp.jiance.repository.mapper.census;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnup.osmp.jiance.repository.entity.census.HomeCensusColumnsData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author shuangyuewu
 * @createTime 2021/12/22
 * @Description
 */
@Mapper
public interface HomeCensusColumnsDataMapper extends BaseMapper<HomeCensusColumnsData> {

	Map<String, Object> statisDoc(@Param("cycle") Integer cycle);

	Map<String, Object> statisOp(@Param("cycle") Integer cycle);

	Map<String, Object> statisMeeting(@Param("cycle") Integer cycle);

	Map<String, Object> statisSupervise(@Param("cycle") Integer cycle);

	Map<String, Object> statisJsydsp(@Param("cycle") Integer cycle);

	Map<String, Object> statisGhxkbl(@Param("cycle") Integer cycle, @Param("type") String type);
}
