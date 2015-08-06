package com.cwa.simuiation.enums;

/**
 * 状态类型
 * 
 * @author mausmars
 * 
 */

public enum StateTypeEnum {
	Global(1), // 全局
	Control(2), // 控制
	Action(3), // 行为
	;

	private int value;

	StateTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
