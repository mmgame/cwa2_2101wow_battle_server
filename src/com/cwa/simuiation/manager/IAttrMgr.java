package com.cwa.simuiation.manager;

import java.util.List;

import baseice.basedao.IEntity;

import com.cwa.component.prototype.IPrototype;
import com.cwa.prototype.PassiveSkillPrototype;

/**
 * 各种属性的管理
 * 
 * @author mausmars
 * 
 */
public interface IAttrMgr {
	/**
	 * 实体
	 * 
	 * @return
	 */
	IEntity getEntity();

	/**
	 * 原型
	 * 
	 * @return
	 */
	IPrototype getPrototype();

	// --------------------------
	/**
	 * 得到大小
	 * 
	 * @return
	 */
	int getSize();

	/**
	 * 得品质
	 * 
	 * @return
	 */
	int getQuality();

	/**
	 * 得到等级
	 * 
	 * @return
	 */
	int getLevel();

	/**
	 * 得到种族
	 * 
	 * @return
	 */
	int getRace();

	/**
	 * 得到消耗值
	 * 
	 * @param attrType
	 * @return
	 */
	int getConsumeValue(int attrType);

	/**
	 * 改变消耗值
	 * 
	 * @param attrType
	 * @param value
	 * @return 返回改变后的值
	 */
	int changeConsumeValue(int attrType, int changeValue);

	/**
	 * 得到属性最终值
	 * 
	 * @param attrType
	 * @return
	 */
	int getAttrFinalValue(int attrType);

	/**
	 * 得到属性值
	 * 
	 * @param attrType
	 * @return
	 */
	int getAttrValue(int sourceType, int attrType);

	/**
	 * 设置属性值
	 * 
	 * @param attrType
	 * @return
	 */
	void setAttrValue(int sourceType, int attrType, int value);

	/**
	 * 得到元素耐性最终值
	 * 
	 * @param type
	 * @return
	 */
	int getElementResistanceFinalValue(int type);

	/**
	 * 得到元素耐性值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @return
	 */
	int getElementResistanceValue(int sourceType, int type);

	/**
	 * 设置元素耐性值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @param value
	 * @return
	 */
	void setElementResistanceValue(int sourceType, int type, int value);

	/**
	 * 得到元素专精最终值
	 * 
	 * @param type
	 * @return
	 */
	int getElementAttackFinalValue(int type);

	/**
	 * 得到元素专精值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @return
	 */
	int getElementAttackValue(int sourceType, int type);

	/**
	 * 设置元素专精值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @param value
	 * @return
	 */
	void setElementAttackValue(int sourceType, int type, int value);

	/**
	 * 得到debuff耐性最终值
	 * 
	 * @param type
	 * @return
	 */
	int getDebuffResistanceFinalValue(int type);

	/**
	 * 得到debuff耐性值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @return
	 */
	int getDebuffResistanceValue(int sourceType, int type);

	/**
	 * 设置debuff耐性值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @param value
	 * @return
	 */
	void setDebuffResistanceValue(int sourceType, int type, int value);

	/**
	 * 得到种族克制最终值
	 * 
	 * @param type
	 * @return
	 */
	int getRaceResistanceFinalValue(int type);

	/**
	 * 得到种族克制值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @return
	 */
	int getRaceResistanceValue(int sourceType, int type);

	/**
	 * 设置种族克制值
	 * 
	 * @param sourceType
	 * @param attrType
	 * @param value
	 * @return
	 */
	void setRaceResistanceValue(int sourceType, int type, int value);
	/**
	 * 初始化被动技能
	 */
	void initPassiveSkill(PassiveSkillPrototype prototype);
	/**
	 * 从原型表获取出效果时间
	 * @return
	 */
	List<Integer> getEffectTimeList();
	/**
	 * 从原型表获取技能释放时间
	 * @return
	 */
	List<Integer> getActionTimeList();
}
