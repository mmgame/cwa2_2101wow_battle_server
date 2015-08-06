package com.cwa.simuiation.enums;

/**
 * 状态子类型
 * 
 * @author mausmars
 * 
 */
public enum ObjSubTypeEnum {
	P_Hero(101), // 英雄
	P_Pet(102), // 宠物
	P_Bullet(103), // 子弹
	P_Trap(104),//陷阱

	NP_Item(201), // 道具

	Area_Point(301), // 区域点
	Area_Obstacle(302), // 障碍
	;

	private int value;

	ObjSubTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
