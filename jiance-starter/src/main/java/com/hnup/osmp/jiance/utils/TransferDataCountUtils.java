package com.hnup.osmp.jiance.utils;

import com.hnup.osmp.jiance.enums.DirectionEnum;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *  土地利用流转计算类
 * @data2021/11/2,11:48
 * @authorsutinghu
 */
public class TransferDataCountUtils {


	/**
	 * 功能描述: 根据流向求和
	 * @author sutinghu
	 * @date
	 * @param source 来源数据
	 * @param result 结果集
	 * @param transferType 流向
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal subTransfer(Integer transferType , BigDecimal source , BigDecimal result){

		Assert.notNull(source,"输入集不能为空");
		Assert.notNull(result,"结果集不能为空");

		source = source.abs();
		result = result.abs();

		result = result.add(source);

		// 流入时 source 为正
		if (Objects.equals(DirectionEnum.INFLOW.getIntValue(),transferType)) {
			 result.setScale(2, BigDecimal.ROUND_HALF_UP);
			// 流出时 source 为负
		}else if (Objects.equals(DirectionEnum.OUTFLOW.getIntValue(),transferType)) {
			 result.setScale(2, BigDecimal.ROUND_HALF_UP).negate();
		}
		return result;
	}

}
