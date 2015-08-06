package com.cwa.simuiation.enums;
/**
 * 施法错误枚举
 * @author yangfeng
 *
 */
public enum MagicErrorEnum {
	Error_CD(1), // cd时间
	Error_Energy(2), // 能量不足
	Error_SkillId(3), // 技能id错误
	Error_Controler(4), // 控制者不是该用户
	Error_Target(5), // 目标错误
	Error_Distance(6), // 攻击距离
	Error_Other(7),//其他错误
	;

	private int value;

	MagicErrorEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}
