package com.hnup.osmp.jiance.operation.computes;

import com.hnup.osmp.jiance.operation.constant.ConstantOperation;

import java.util.Stack;

/**
 *  逆波兰运算
 * @data2021/11/11,9:39
 * @author sutinghu
 */
public class ComputeCur {

	private Stack<String> operatorCur = new Stack<>();

	private Stack<String> operatorBase = new Stack<>();

	private String result = null;

	public ComputeCur(String opreator){
		Stack<String> operators = SuffixBase.getInstance(opreator).buildPosture();
		while (!operators.empty()) {
			operatorBase.push(operators.pop());
		}
	}

	public static ComputeCur getInstance(String opreator){
		return new ComputeCur(opreator);
	}

	public String getResult(){

		while (!operatorBase.empty()) {
			String check = operatorBase.peek();
			if (!ConstantOperation.OPERATOR.contains(check)) {
				// 入栈
				operatorCur.push(operatorBase.pop());
			}else {
				String code = operatorBase.pop();
				String a = operatorCur.pop();
				String b = operatorCur.pop();
				String c = ComputeBase.selectByCode(code,b,a);
				operatorCur.push(c);
			}
		}
		result = operatorCur.peek();
		return result;
	}

}
