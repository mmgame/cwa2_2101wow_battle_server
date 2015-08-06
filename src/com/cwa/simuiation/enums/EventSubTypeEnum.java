package com.cwa.simuiation.enums;

/**
 * 事件子类型
 * 
 * @author mausmars
 * 
 */
public enum EventSubTypeEnum {
	// 系统
	ES_SOver(101), // 仿真器结束事件
	ES_TimeOver(102), // 仿真器时间到了

	// 状态
	ES_Idle(200), // 空闲状态
	ES_Move(201), // 移动事件
	ES_Magic(202), // 施法事件
	ES_Dead(203), // 死亡事件
	EB_StateChange(250), // 战场事件改变
	// buff
	EB_Buff(301), //

	// 效果effect
	EE_Effect(401), // 攻击效果

	// 陷阱trap
	ET_Trap(501), // 陷阱
	;

	private int value;

	EventSubTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
