package com.cwa.simuiation.enums;

/**
 * 阶段类型枚举
 * 
 * @author mausmars
 * 
 */
public enum MessageSourceEnum {
	UI_Message(1), // UI
	Net_Message(2), // 网络（服务器这边只用这个值）
	;

	private int value;

	MessageSourceEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
