package com.cwa.simuiation.enums;

/**
 * 状态子类型
 * 
 * @author mausmars
 * 
 */
public enum StateSubTypeEnum {
	// 全局状态
	GS_NoCombat(101), // 非战斗
	GS_Combat(102), // 战斗
	GS_Dead(103), // 死亡

	// 控制状态
	CS_AI(201), // AI状态
	CS_User(202), // 用户状态

	// 行为状态
	AS_Idle(301), // 空闲
	AS_Move(302), // 移动
	AS_Magic(303), // 施法
	;

	private int value;

	StateSubTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
