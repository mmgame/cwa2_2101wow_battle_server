package com.cwa.simuiation.enums;

/**
 * 属性来源类型
 * 
 * @author mausmars
 * 
 */
public enum AttrSourceTypeEnum {
	AS_Base(1), // 基础（原型）
	AS_Passive(2),//被动技能
	AS_Buff(3), // buff
	AS_Trap(4),//陷阱
	AS_Retinue(5),//随从
	;

	private int value;

	AttrSourceTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
