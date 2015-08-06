package com.cwa.simuiation.enums;

/**
 * 阶段类型枚举
 * 
 * @author mausmars
 * 
 */
public enum StepTypeEnum {
	ST_Null(0), // 空
	ST_Enter(1), // 进入阶段
	ST_Update(2), // 更新阶段
	ST_Exit(3), // 退出阶段
	ST_Interrupt(4), // 中断
	;

	private int value;

	StepTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
