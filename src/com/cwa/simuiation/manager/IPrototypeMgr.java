package com.cwa.simuiation.manager;

import com.cwa.prototype.BattlePrototype;
import com.cwa.prototype.BattlemapPrototype;
import com.cwa.prototype.BuffPrototype;
import com.cwa.prototype.CallPrototype;
import com.cwa.prototype.CoordinatePrototype;
import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.EquipmentGradePrototype;
import com.cwa.prototype.HeroGradePrototype;
import com.cwa.prototype.HeroPrototype;
import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.prototype.PlugItemPrototype;
import com.cwa.prototype.SkillPrototype;
import com.cwa.prototype.TrapPrototype;

/**
 * 原型管理
 * 
 * @author mausmars
 * 
 */
public interface IPrototypeMgr {
	/**
	 * 战场原型
	 * 
	 * @param keyId
	 * @return
	 */
	BattlePrototype getBattlePrototype();

	/**
	 * 技能原型
	 * 
	 * @param keyId
	 * @return
	 */
	SkillPrototype getSkillPrototype(int keyId);

	/**
	 * buff原型
	 * 
	 * @param keyId
	 * @return
	 */
	BuffPrototype getBuffPrototype(int keyId);

	/**
	 * 效果原型
	 * 
	 * @param keyId
	 * @return
	 */
	EffectPrototype getEffectPrototype(int keyId);

	/**
	 * 英雄原型
	 * 
	 * @param keyId
	 * @return
	 */
	HeroPrototype getHeroPrototype(int keyId);

	/**
	 * 英雄等级原型
	 * 
	 * @param keyId
	 * @return
	 */
	HeroGradePrototype getHerogradePrototype(int keyId);
	/**
	 * 召唤宠物原型
	 * @param keyId
	 * @return
	 */
	CallPrototype getCallPrototype(int keyId);
	/**
	 * 陷阱原型
	 * @param keyId
	 * @return
	 */
	TrapPrototype getTrapPrototype(int keyId);
	/**
	 * 战场地图原型
	 * @param keyId
	 * @return
	 */
	BattlemapPrototype getBattlemapPrototype(int keyId);
	/**
	 * 坐标原型
	 * 
	 */
	CoordinatePrototype getCoordinatePrototype(int keyId);
	
	/**
	 * 被动技能原型
	 */
	PassiveSkillPrototype getPassiveSkillPrototype(int keyId);
	
	/**
	 * 装备原型
	 * @param keyId
	 * @return
	 */
	EquipmentGradePrototype getEquipmentGradePrototype(int keyId);
	
	/**
	 * 插件原型
	 * @param keyId
	 * @return
	 */
	PlugItemPrototype getPlugItemPrototype(int keyId);
}
