package com.cwa.simuiation.enums;

/**
 * 频道类型{发给Source,发给target,发给全体}
 * 
 * @author mausmars
 * 
 */
public enum EventChannelEnum {
	Whole(0), // 全体
	Appoint(1), // 指定人员
	;

	private int value;

	EventChannelEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
