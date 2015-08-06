package com.cwa.simuiation.enums;

public enum ObjTypeEnum {
	Performer(1), // 执行者
	NonPerformer(2), // 非执行者
	Area(3), // 地块
	;
	private int value;

	ObjTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
