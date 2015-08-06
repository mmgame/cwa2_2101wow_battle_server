package com.cwa.simuiation.enums;

public enum HurtTypeEnum {
	Effect_Normal(0),//正常伤害
	Effect_Dodge(1),//闪避
	Effect_Crit(2),//暴击
	Effect_Parry(3),//格挡
	Effect_SuckBlood(4),//吸血
	Effect_SuckMagic(5),//吸魔
	Effect_Recover(6),//回血
    Effect_Thorns(7),//反伤
	;
		    		
	private int value;

	HurtTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
