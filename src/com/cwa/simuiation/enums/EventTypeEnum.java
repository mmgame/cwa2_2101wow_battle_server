package com.cwa.simuiation.enums;

/**
 * 事件类型
 * 
 * @author mausmars
 * 
 */
public enum EventTypeEnum {
	E_System(1), // 系统事件
	E_State(2), // 状态事件
	E_Buff(3), // buff事件
	E_Effect(4), // 效果事件
	E_Trap(5), // 陷阱事件
	;

	private int value;

	EventTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
