package com.cwa.simuiation.enums;

/**
 * 过滤类型
 * 
 * @author mausmars
 * 
 */
public enum FilterTypeEnum {
	// 无限制为-1
	Filter_ObjType(1), // 对象类型过滤【类型，子类型1 子类型2...】
	Filter_Faction(2), // 阵营过滤【阵营关系，目标】
	Filter_Range(3), // 范围内过滤【范围，目标】
	Filter_Attr(4), // 属性过滤【某个属性类型，最低0最高1】
	Filter_Buff(5), // buff过滤【0类型1是id，buff类型或buff的id】
	Filter_Random(6), // 随机过滤【个数】
	;

	private int value;

	FilterTypeEnum(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}

}
