package com.hnup.osmp.jiance.operation.computes;

import com.hnup.osmp.jiance.operation.enums.ComputeEnum;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 *  四则运算-基本方法
 * @data2021/11/10,15:13
 * @authorsutinghu
 */
public class ComputeBase {

	public static String selectByCode(String code,String a,String b){
		BigDecimal decimalA = new BigDecimal(a);
		BigDecimal decimalB = new BigDecimal(b);
		String result = null;
		if (Objects.equals(code, ComputeEnum.ADD.getDesc())) {
			result = String.valueOf(formatToNumber(ComputeBase.addCompute(decimalA,decimalB)));
		}
		if (Objects.equals(code, ComputeEnum.REDUCE.getDesc())) {
			result = String.valueOf(formatToNumber(ComputeBase.reduceCompute(decimalA,decimalB)));
		}
		if (Objects.equals(code, ComputeEnum.RIDE.getDesc())) {
			result = String.valueOf(formatToNumber(ComputeBase.rideCompute(decimalA,decimalB)));
		}
		if (Objects.equals(code, ComputeEnum.DIVIDE.getDesc())) {
			result = String.valueOf(formatToNumber(ComputeBase.divideCompute(decimalA,decimalB)));
		}
		return result;
	}

	/**
	 * 功能描述: 将结果输出为小数点后两位
	 * @author sutinghu
	 * @date
	 * @param bg 参数
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal decimal(BigDecimal bg){
		return bg.setScale(4, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 功能描述: 求和
	 * @author sutinghu
	 * @date
	 * @param a
	 * @param b 参数
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal addCompute(BigDecimal a,BigDecimal b){

		if (b == null) {
			b = new BigDecimal("0");
		}

		return ComputeBase.decimal(a.add(b));
	}

	/**
	 * 功能描述:做差
	 * @author sutinghu
	 * @date
	 * @param a
	 * @param b 参数
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal reduceCompute(BigDecimal a,BigDecimal b){
		return ComputeBase.decimal(a.subtract(b));
	}

	/**
	 * 功能描述:乘法
	 * @author sutinghu
	 * @date
	 * @param a
	 * @param b 参数
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal rideCompute(BigDecimal a,BigDecimal b){
		return ComputeBase.decimal(a.multiply(b));
	}

	/**
	 * 功能描述:除法
	 * @author sutinghu
	 * @date
	 * @param a
	 * @param b 参数
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal divideCompute(BigDecimal a,BigDecimal b){
		return ComputeBase.decimal(a.divide(b,4,BigDecimal.ROUND_HALF_UP));
	}

	/**
	 * 功能描述:百分比
	 * @author sutinghu
	 * @date
	 * @param a
	 * @param b 参数
	 * @return java.math.BigDecimal
	 */
	public static BigDecimal percentageCompute(Integer a,Integer b){
		BigDecimal c=new BigDecimal(a);
		BigDecimal d=new BigDecimal(b);
		BigDecimal e=divideCompute(c,d);
		return new BigDecimal(formatToNumber(e.multiply(new BigDecimal(100))));
	}

	/**
	 * 功能描述:  1.0~1之间的BigDecimal小数，格式化后失去前面的0,则前面直接加上0。
	 * 	        2.传入的参数等于0，则直接返回字符串"0.00"
	 * 	 	    3.大于1的小数，直接格式化返回字符串
	 * @author sutinghu
	 * @date
	 * @param obj 参数
	 * @return java.lang.String
	 */
	public static String formatToNumber(BigDecimal obj) {
		DecimalFormat df = new DecimalFormat("#.00");
		if(obj.compareTo(BigDecimal.ZERO)==0) {
			return "0.00";
		}else if(obj.compareTo(BigDecimal.ZERO)>0&&obj.compareTo(new BigDecimal(1))<0){
			return "0"+df.format(obj).toString();
		}else if(obj.compareTo(BigDecimal.ZERO)<0&&obj.compareTo(new BigDecimal(-1))>0){
			df = new DecimalFormat("0.00");
			return df.format(obj);
		}else {
			return df.format(obj).toString();
		}
	}
}
