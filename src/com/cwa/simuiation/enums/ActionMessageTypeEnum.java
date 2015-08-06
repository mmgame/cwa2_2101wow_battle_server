package com.cwa.simuiation.enums;

/**
 * 消息类型
 * 
 * @author mausmars
 * 
 */
public enum ActionMessageTypeEnum {
	Message_Move(101), // 移动消息
	Message_Magic(102), // 施法消息
	;

	private int value;

	ActionMessageTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
