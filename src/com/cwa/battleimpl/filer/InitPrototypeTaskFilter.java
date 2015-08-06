package com.cwa.battleimpl.filer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.component.prototype.IPrototypeClientService;
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
import com.cwa.service.IService;
import com.cwa.service.constant.ServiceConstant;
import com.cwa.service.context.IGloabalContext;
import com.cwa.simuiationimpl.manage.ProtpyeMgr;

/**
 * 初始化原型filter
 * 
 * @author mausmars
 * 
 */
public class InitPrototypeTaskFilter extends ABattleFilter {
	private IGloabalContext gloabalContext;

	public InitPrototypeTaskFilter() {
		super("InitPrototypeTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("InitPrototypeTaskFilter doWork!");
		}

		IBattleGround battleGround = (IBattleGround) context;
		initPrototype(battleGround);
		return true;
	}

	private void initPrototype(IBattleGround battleGround) {
		IService service = gloabalContext.getCurrentService(ServiceConstant.ProtoclientKey);
		if (service == null) {
			throw new RuntimeException("Battle Prototype init error!");
		}
		IPrototypeClientService prototypeMgr = (IPrototypeClientService) service;

		ProtpyeMgr pm = (ProtpyeMgr) battleGround.getSimuiation().getProtpyeMgr();

		// 战场原型
		BattlePrototype battlePrototype = prototypeMgr.getPrototype(BattlePrototype.class, battleGround.getKeyId());
		// 英雄原型
		List<HeroPrototype> heroPrototypeList = prototypeMgr.getAllPrototype(HeroPrototype.class);
		Map<Integer, HeroPrototype> heroPrototypeMap = new HashMap<Integer, HeroPrototype>();
		for (HeroPrototype heroPrototype : heroPrototypeList) {
			heroPrototypeMap.put(heroPrototype.getKeyId(), heroPrototype);
		}
		// 英雄等级原型
		List<HeroGradePrototype> herogradePrototypeList = prototypeMgr.getAllPrototype(HeroGradePrototype.class);
		Map<Integer, HeroGradePrototype> herogradePrototypeMap = new HashMap<Integer, HeroGradePrototype>();
		for (HeroGradePrototype herogradePrototype : herogradePrototypeList) {
			herogradePrototypeMap.put(herogradePrototype.getKeyId(), herogradePrototype);
		}
		// 技能原型
		List<SkillPrototype> skillPrototypeList = prototypeMgr.getAllPrototype(SkillPrototype.class);
		Map<Integer, SkillPrototype> skillPrototypeMap = new HashMap<Integer, SkillPrototype>();
		for (SkillPrototype skillPrototype : skillPrototypeList) {
			skillPrototypeMap.put(skillPrototype.getKeyId(), skillPrototype);
		}
		
		// 被动技能原型
		List<PassiveSkillPrototype> passiveSkillPrototypeList = prototypeMgr.getAllPrototype(PassiveSkillPrototype.class);
		Map<Integer, PassiveSkillPrototype> passiveSkillPrototypeMap = new HashMap<Integer, PassiveSkillPrototype>();
		for (PassiveSkillPrototype passiveSkillPrototype : passiveSkillPrototypeList) {
			passiveSkillPrototypeMap.put(passiveSkillPrototype.getKeyId(), passiveSkillPrototype);
		}

		// buff原型
		List<BuffPrototype> buffPrototypeList = prototypeMgr.getAllPrototype(BuffPrototype.class);
		Map<Integer, BuffPrototype> buffPrototypeMap = new HashMap<Integer, BuffPrototype>();
		for (BuffPrototype buffPrototype : buffPrototypeList) {
			buffPrototypeMap.put(buffPrototype.getKeyId(), buffPrototype);
		}

		// 技能效果原型
		List<EffectPrototype> effectPrototypeList = prototypeMgr.getAllPrototype(EffectPrototype.class);
		Map<Integer, EffectPrototype> effectPrototypeMap = new HashMap<Integer, EffectPrototype>();
		for (EffectPrototype effectPrototype : effectPrototypeList) {
			effectPrototypeMap.put(effectPrototype.getKeyId(), effectPrototype);
		}

		// 召唤宠物原型
		List<CallPrototype> callPrototypeList = prototypeMgr.getAllPrototype(CallPrototype.class);
		Map<Integer, CallPrototype> callPrototypeMap = new HashMap<Integer, CallPrototype>();
		for (CallPrototype callPrototype : callPrototypeList) {
			callPrototypeMap.put(callPrototype.getKeyId(), callPrototype);
		}

		// 召唤陷阱原型
		List<TrapPrototype> trapPrototypeList = prototypeMgr.getAllPrototype(TrapPrototype.class);
		Map<Integer, TrapPrototype> trapPrototypeMap = new HashMap<Integer, TrapPrototype>();
		for (TrapPrototype trapPrototype : trapPrototypeList) {
			trapPrototypeMap.put(trapPrototype.getKeyId(), trapPrototype);
		}

		// 战场地图原型
		List<BattlemapPrototype> battlemapPrototypeList = prototypeMgr.getAllPrototype(BattlemapPrototype.class);
		Map<Integer, BattlemapPrototype> battlemapPrototypeMap = new HashMap<Integer, BattlemapPrototype>();
		for (BattlemapPrototype battlemapPrototype : battlemapPrototypeList) {
			battlemapPrototypeMap.put(battlemapPrototype.getKeyId(), battlemapPrototype);
		}
		// 战场坐标原型
		List<CoordinatePrototype> coordinatePrototypeList = prototypeMgr.getAllPrototype(CoordinatePrototype.class);
		Map<Integer, CoordinatePrototype> coordinatePrototypeMap = new HashMap<Integer, CoordinatePrototype>();
		for (CoordinatePrototype coordinatePrototype : coordinatePrototypeList) {
			coordinatePrototypeMap.put(coordinatePrototype.getKeyId(), coordinatePrototype);
		}
		// 装备原型
		List<EquipmentGradePrototype> equipmentGradePrototypeList = prototypeMgr.getAllPrototype(EquipmentGradePrototype.class);
		Map<Integer, EquipmentGradePrototype> equipmentGradePrototypeMap = new HashMap<Integer, EquipmentGradePrototype>();
		for (EquipmentGradePrototype equipmentGradePrototype : equipmentGradePrototypeList) {
			equipmentGradePrototypeMap.put(equipmentGradePrototype.getKeyId(), equipmentGradePrototype);
		}
		// 插件原型
		List<PlugItemPrototype> plugItemPrototypeList = prototypeMgr.getAllPrototype(PlugItemPrototype.class);
		Map<Integer, PlugItemPrototype> plugItemPrototypeMap = new HashMap<Integer, PlugItemPrototype>();
		for (PlugItemPrototype plugItemPrototype : plugItemPrototypeList) {
			plugItemPrototypeMap.put(plugItemPrototype.getKeyId(), plugItemPrototype);
		}

		pm.setBattlePrototype(battlePrototype);
		pm.setHeroPrototypeMap(heroPrototypeMap);
		pm.setSkillPrototypeMap(skillPrototypeMap);
		pm.setBuffPrototypeMap(buffPrototypeMap);
		pm.setEffectPrototypeMap(effectPrototypeMap);
		pm.setHerogradePrototypeMap(herogradePrototypeMap);
		pm.setCallPrototypeMap(callPrototypeMap);
		pm.setTrapPrototypeMap(trapPrototypeMap);
		pm.setBattlemapPrototypeMap(battlemapPrototypeMap);
		pm.setCoordinatePrototypeMap(coordinatePrototypeMap);
		pm.setPassiveSkillPrototypeMap(passiveSkillPrototypeMap);
		pm.setEquipmentGradePrototypeMap(equipmentGradePrototypeMap);
		pm.setPlugItemPrototypeMap(plugItemPrototypeMap);
	}

	// -----------------------------------------------
	public void setGloabalContext(IGloabalContext gloabalContext) {
		this.gloabalContext = gloabalContext;
	}
}
