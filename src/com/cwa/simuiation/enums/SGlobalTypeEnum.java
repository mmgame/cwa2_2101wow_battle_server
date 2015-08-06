package com.cwa.simuiation.enums;

public enum SGlobalTypeEnum {
	SGT_Obj(1), // 物体
	SGT_Buff(2), // buff
	SGT_Skill(3), // 技能
	SGT_State(4), // 状态
	;
	private int value;

	SGlobalTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
