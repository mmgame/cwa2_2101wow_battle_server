package com.cwa.simuiationimpl.manage;

import java.util.Map;

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
import com.cwa.simuiation.manager.IPrototypeMgr;

/**
 * 原型管理
 * 
 * @author mausmars
 * 
 */
public class ProtpyeMgr implements IPrototypeMgr {
	private BattlePrototype battlePrototype;
	private Map<Integer, SkillPrototype> skillPrototypeMap;
	private Map<Integer, BuffPrototype> buffPrototypeMap;
	private Map<Integer, EffectPrototype> effectPrototypeMap;
	private Map<Integer, HeroPrototype> heroPrototypeMap;
	private Map<Integer, CallPrototype> callPrototypeMap;
	private Map<Integer, HeroGradePrototype> herogradePrototypeMap;
	private Map<Integer, TrapPrototype> trapPrototypeMap;
	private Map<Integer, BattlemapPrototype> battlemapPrototypeMap;
	private Map<Integer, CoordinatePrototype> coordinatePrototypeMap;
	private Map<Integer, PassiveSkillPrototype> passiveSkillPrototypeMap;
	private Map<Integer, EquipmentGradePrototype> equipmentGradePrototypeMap;
	private Map<Integer, PlugItemPrototype> plugItemPrototypeMap;
	@Override
	public BattlePrototype getBattlePrototype() {
		return battlePrototype;
	}
	@Override
	public SkillPrototype getSkillPrototype(int keyId) {
		return skillPrototypeMap.get(keyId);
	}

	@Override
	public BuffPrototype getBuffPrototype(int keyId) {
		return buffPrototypeMap.get(keyId);
	}

	@Override
	public EffectPrototype getEffectPrototype(int keyId) {
		return effectPrototypeMap.get(keyId);
	}

	@Override
	public HeroPrototype getHeroPrototype(int keyId) {
		return heroPrototypeMap.get(keyId);
	}
	
	@Override
	public HeroGradePrototype getHerogradePrototype(int keyId) {
		return herogradePrototypeMap.get(keyId);
	}
	public CallPrototype getCallPrototype(int keyId) {
		return callPrototypeMap.get(keyId);
	}
	@Override
	public TrapPrototype getTrapPrototype(int keyId) {
		return trapPrototypeMap.get(keyId);
	}
	@Override
	public BattlemapPrototype getBattlemapPrototype(int keyId) {
		return battlemapPrototypeMap.get(keyId);
	}
	@Override
	public CoordinatePrototype getCoordinatePrototype(int keyId) {
		return coordinatePrototypeMap.get(keyId);
	}
	@Override
	public PassiveSkillPrototype getPassiveSkillPrototype(int keyId) {
		return passiveSkillPrototypeMap.get(keyId);
	}
	@Override
	public EquipmentGradePrototype getEquipmentGradePrototype(int keyId) {
		return equipmentGradePrototypeMap.get(keyId);
	}
	@Override
	public PlugItemPrototype getPlugItemPrototype(int keyId) {
		return plugItemPrototypeMap.get(keyId);
	}

	// -------------------------------------------
	public void setSkillPrototypeMap(Map<Integer, SkillPrototype> skillPrototypeMap) {
		this.skillPrototypeMap = skillPrototypeMap;
	}

	public void setBuffPrototypeMap(Map<Integer, BuffPrototype> buffPrototypeMap) {
		this.buffPrototypeMap = buffPrototypeMap;
	}

	public void setEffectPrototypeMap(Map<Integer, EffectPrototype> effectPrototypeMap) {
		this.effectPrototypeMap = effectPrototypeMap;
	}

	public void setHeroPrototypeMap(Map<Integer, HeroPrototype> heroPrototypeMap) {
		this.heroPrototypeMap = heroPrototypeMap;
	}

	public void setHerogradePrototypeMap(Map<Integer, HeroGradePrototype> herogradePrototypeMap) {
		this.herogradePrototypeMap = herogradePrototypeMap;
	}

	public void setCallPrototypeMap(Map<Integer, CallPrototype> callPrototypeMap) {
		this.callPrototypeMap = callPrototypeMap;
	}

	public void setBattlePrototype(BattlePrototype battlePrototype) {
		this.battlePrototype = battlePrototype;
	}

	public void setTrapPrototypeMap(Map<Integer, TrapPrototype> trapPrototypeMap) {
		this.trapPrototypeMap = trapPrototypeMap;
	}
	public void setBattlemapPrototypeMap(Map<Integer, BattlemapPrototype> battlemapPrototypeMap) {
		this.battlemapPrototypeMap = battlemapPrototypeMap;
	}
	public void setCoordinatePrototypeMap(Map<Integer, CoordinatePrototype> coordinatePrototypeMap) {
		this.coordinatePrototypeMap = coordinatePrototypeMap;
	}
	public void setPassiveSkillPrototypeMap(Map<Integer, PassiveSkillPrototype> passiveSkillPrototypeMap) {
		this.passiveSkillPrototypeMap = passiveSkillPrototypeMap;
	}
	public void setEquipmentGradePrototypeMap(Map<Integer, EquipmentGradePrototype> equipmentGradePrototypeMap) {
		this.equipmentGradePrototypeMap = equipmentGradePrototypeMap;
	}
	public void setPlugItemPrototypeMap(Map<Integer, PlugItemPrototype> plugItemPrototypeMap) {
		this.plugItemPrototypeMap = plugItemPrototypeMap;
	}
}
