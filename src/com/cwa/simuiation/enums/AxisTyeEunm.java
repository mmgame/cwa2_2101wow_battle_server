package com.cwa.simuiation.enums;

/**
 * 坐标轴类型
 * 
 * @author mausmars
 * 
 */
public enum AxisTyeEunm {
	XAxis(0), // x轴
	YAxis(1), // y轴
	ZAxis(2), // z轴
	;

	private int value;

	AxisTyeEunm(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}