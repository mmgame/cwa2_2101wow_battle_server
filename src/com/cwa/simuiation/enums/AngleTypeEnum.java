package com.cwa.simuiation.enums;

/**
 * 角类型
 * 
 * @author mausmars
 * 
 */
public enum AngleTypeEnum {
	XYAngle(0), // xy平面角度
	Elevation(1), // 仰角
	;

	private int value;

	AngleTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}