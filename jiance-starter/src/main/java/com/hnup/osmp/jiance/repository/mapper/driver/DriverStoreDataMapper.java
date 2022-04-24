package com.hnup.osmp.jiance.repository.mapper.driver;

import com.hnup.osmp.jiance.repository.entity.driver.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuangyuewu
 * @createTime 2022/02/17
 * @Description
 */
@Mapper
public interface DriverStoreDataMapper {

	List<DriverStoreDoc> statisDoc(@Param("year") Integer year);

	DriverStoreSupervise statisSuppervise(@Param("year") Integer year);

	DriverStoreMeeting staisMeeting(@Param("year") Integer year);

	DriverStoreOp statisOp(@Param("year") Integer year);

	List<DriverStoreOpRank> statisOpRank(@Param("year") Integer year);

	DriverStoreZican statisZican(@Param("year") Integer year);

	List<DriverStoreZhifa> statisZhifa(@Param("year") Integer year);
}
