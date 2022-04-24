package com.hnup.osmp.jiance.operation.computes;

import com.hnup.osmp.jiance.operation.constant.ConstantOperation;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Stack;

/**
 *  后缀表达式基础-构建逆波兰表达式
 * @data2021/11/10,16:51
 * @authorsutinghu
 */
public class SuffixBase {

	/**
	 *  运算符栈
	 */
	private Stack<String> operator = new Stack<>();

	/**
	 *  中缀表达式栈
	 */
	private  String[] suffix ;

	/**
	 *  后缀表达式栈
	 */
	private Stack<String> infix = new Stack<>();


	/**
	 *  空格
	 */
	private final String cut = " ";

	/**
	 *  字符串式子
	 */
	private String posture = null;

	public SuffixBase(String posture){
		this.posture = posture;
	}

	public static SuffixBase getInstance(String posture){
		return new SuffixBase(posture);
	}

	public Stack<String> buildPosture(){
		// 解析
		this.resolvPosture();
		//
		this.buildSuffix();
		//
		this.buildInfix();
		return this.infix;
	}

	/**
	 * 功能描述: 构造后缀表达式栈
	 * @author sutinghu
	 * @date
	 * @return void
	 */
	public void buildInfix(){
		int length = this.suffix.length;
		for (int i = 0;i<length;i++) {
			// 如果不包括 则表示为数字进数字栈
			String value = this.suffix[i];
			if (!ConstantOperation.OPERATOR.contains(value)
				&& !Objects.equals(ConstantOperation.BRACKET_LEFT,value)
				&& !Objects.equals(ConstantOperation.BRACKET_RIGHT,value)) {
				this.infix.push(value);
			}else {
				if (operator.empty()) {
					operator.push(value);
				}else {
					// 右括号情况
					// 如果符号栈顶是右括号
					if (Objects.equals(ConstantOperation.BRACKET_RIGHT,value)) {
						String check=operator.peek();
						while(!Objects.equals(check,ConstantOperation.BRACKET_LEFT) && !operator.isEmpty()) {
							this.infix.push(operator.pop());
							check = operator.peek();
							if (Objects.equals(check,ConstantOperation.BRACKET_LEFT)) {
								operator.pop();
							}
						}
					}else {
						this.checkBacketLeft(value);
					}
				}
			}
		}

		this.checkPeek();
	}


	public void checkBacketLeft(String value){
		// 根据符号优先级判断
		// 判断栈顶符号是否为左括号
		if (Objects.equals(ConstantOperation.BRACKET_LEFT,value)) {
			// 入栈
			operator.push(value);
		}else {
			// 判断栈顶元素的优先级
			if (operator.empty()) {
				operator.push(value);
			}else {
				if (Objects.equals(ConstantOperation.BRACKET_LEFT,operator.peek())) {
					operator.push(value);
				}else {
					String code = operator.peek();
					Integer a = ConstantOperation.OPERATOR_PRIORITY.get(code) ;
					Integer b = ConstantOperation.OPERATOR_PRIORITY.get(value);
					if (a>b) {
						// 符号栈依次入后缀栈
						String check=operator.peek();
						while(!Objects.equals(check,ConstantOperation.BRACKET_LEFT) && !operator.isEmpty()) {
							this.infix.push(operator.pop());
							if (!operator.empty()) {
								check = operator.peek();
							}
						}
					}
					operator.push(value);
				}
			}
		}
	}

	public void checkPeek(){
		String check=operator.peek();
		while(!Objects.equals(check,ConstantOperation.BRACKET_LEFT) && !operator.isEmpty()) {
			this.infix.push(operator.pop());
			if (!operator.empty()) {
				check = operator.peek();
				if (Objects.equals(check,ConstantOperation.BRACKET_LEFT)) {
					operator.pop();
				}
			}
		}

	}

	/**
	 * 功能描述: 根据运算字符串构造中缀表达式栈
	 * @author sutinghu
	 * @date
	 * @return void
	 */
	public void buildSuffix() {
		this.suffix = this.resolvPosture();
	}

	/**
	 * 功能描述: 将式子根据空格分割为字符数组
	 * @author sutinghu
	 * @date
	 * @return java.lang.String[]
	 */
	public String[] resolvPosture(){
		if (StringUtils.isBlank(this.posture)) {
			throw new IllegalStateException(" 运算式不能为空 ");
		}
		return this.posture.split(cut);
	}

}
