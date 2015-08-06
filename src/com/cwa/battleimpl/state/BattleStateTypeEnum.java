package com.cwa.battleimpl.state;

public enum BattleStateTypeEnum {
	SPrewar(1), // 战前（ 战场创建）
	SFightingP(2), // 战中准备（战斗开始准备）
	SFighting(3), // 战中
	SPostwarP(4), // 战后准备
	SPostwar(5), // 战结束
	;
	private int value;

	BattleStateTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
