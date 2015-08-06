package com.cwa.simuiationimpl.manage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import baseice.basedao.IEntity;

import com.cwa.component.prototype.IPrototype;
import com.cwa.data.entity.domain.EquipmentEntity;
import com.cwa.prototype.CallPrototype;
import com.cwa.prototype.HeroGradePrototype;
import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.simuiation.IPlayer;
import com.cwa.simuiation.action.skill.IEffectTrigger;
import com.cwa.simuiation.context.ISGlobalContext;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.manager.IBuffContextFactory;
import com.cwa.simuiation.manager.IObjFactory;
import com.cwa.simuiation.manager.ITrapManager;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IArea;
import com.cwa.simuiation.obj.INonPerformer;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.buff.BuffManager;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.obj.Bullet;
import com.cwa.simuiationimpl.obj.Hero;
import com.cwa.simuiationimpl.obj.Pet;
import com.cwa.simuiationimpl.obj.Trap;
import com.cwa.simuiationimpl.state.StateManager;

/**
 * obj工厂
 * 
 * @author mausmars
 * 
 */
public class ObjFactory implements IObjFactory {
	private ISGlobalContext context;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private IBuffContextFactory buffContextFactory;
	private ITrapManager trapManager;
	

	public IPerformer createPerformerHero(IPlayer player, Position position, IEntity entity, IPrototype heroPrototype,
			IPrototype herogradePrototype, List<EquipmentEntity> equipments,
			IEntity retinueEntity, List<EquipmentEntity> retinueEquipments) {
		// 创建英雄
		Hero actor = new Hero();
		actor.setId(createId(ObjSubTypeEnum.P_Hero.value()));
		actor.setContext(context);
		actor.setOwner(player);
		// buff管理
		BuffManager buffManager = new BuffManager(actor, buffContextFactory);
		actor.setBuffManager(buffManager);
		HeroAttrMgr attrMgr = new HeroAttrMgr();
		attrMgr.setPerformer(actor);
		attrMgr.setEntity(entity);
		attrMgr.setHerogradePrototype(herogradePrototype);
		attrMgr.setPrototype(heroPrototype);
		attrMgr.setEquipments(equipments);
		attrMgr.setRetinue(retinueEntity, retinueEquipments);
		actor.setAttrMgr(attrMgr);
		List<Integer> passiveSkillKeyIds = ((HeroGradePrototype)herogradePrototype).getInitAssiveSkillList();
		for(int passiveSkillKeyId : passiveSkillKeyIds){
			if(passiveSkillKeyId != 0){
				PassiveSkillPrototype passiveSkillPrototype = actor.getGlobalContext().getProtpyeMgr().getPassiveSkillPrototype(passiveSkillKeyId);
				attrMgr.initPassiveSkill(passiveSkillPrototype);
			}
		}
		// 技能管理
		SkillManager skillManager = new SkillManager();
		actor.setSkillManager(skillManager);
		// 状态管理
		StateManager stateManager = new StateManager(actor);
		actor.setStateManager(stateManager);
		// 设置位置
		actor.setPosition(position);

		// 插入缓存
		context.getSobMgr().insert(actor);
		
		return actor;
	}

	public IPerformer createPerformerPet(IPerformer source, Position position, IPrototype petPrototype) {
		Pet actor = new Pet();
		actor.setId(createId(ObjSubTypeEnum.P_Pet.value()));
		actor.setContext(context);
		actor.setOwner(source.getControler());
		actor.setSource(source);
		// buff管理
		BuffManager buffManager = new BuffManager(actor, buffContextFactory);
		actor.setBuffManager(buffManager);
		PetAttrMgr attrMgr = new PetAttrMgr();
		attrMgr.setPrototype(petPrototype);
		attrMgr.setLevel(source.getAttrMgr().getLevel());
		attrMgr.setPerformer(actor);
		actor.setAttrMgr(attrMgr);
		List<Integer> passiveSkillKeyIds = ((CallPrototype)petPrototype).getInitAssiveSkillList();
		for(int passiveSkillKeyId : passiveSkillKeyIds){
			if(passiveSkillKeyId != 0){
				PassiveSkillPrototype passiveSkillPrototype = actor.getGlobalContext().getProtpyeMgr().getPassiveSkillPrototype(passiveSkillKeyId);
				attrMgr.initPassiveSkill(passiveSkillPrototype);
			}
		}
		// 技能管理
		SkillManager skillManager = new SkillManager();
		actor.setSkillManager(skillManager);
		// 状态管理
		StateManager stateManager = new StateManager(actor);
		actor.setStateManager(stateManager);
		// 设置位置
		actor.setPosition(position);
		// 插入缓存
		context.getSobMgr().insert(actor);
		return actor;
	}

	public IPerformer createPerformerTrap(IPerformer source, Position position, IPrototype trapPrototype, IPrototype effectPrototype,Random r) {
		Trap actor = new Trap();
		actor.setId(createId(ObjSubTypeEnum.P_Trap.value()));
		actor.setContext(context);
		actor.setOwner(source.getControler());
		actor.setSource(source);
		actor.setCreatTime(context.getClock().getCurrentSTime());
		actor.setStartTime(context.getClock().getCurrentSTime());
		actor.setTrapManager(trapManager);
		actor.setTrapPrototype(trapPrototype);
		actor.setEffectPrototype(effectPrototype);
		actor.setRandom(r);
		// 设置位置
		actor.setPosition(position);
		// 插入缓存
		context.getSobMgr().insert(actor);
		return actor;
	}

	public IPerformer createPerformerBullet(IPerformer source, Position position, IEffectTrigger effectTrigger, IPrototype effectPrototype) {
		Bullet actor = new Bullet();
		actor.setId(createId(ObjSubTypeEnum.P_Bullet.value()));
		actor.setEffectTrigger(effectTrigger);
		actor.setContext(context);
		actor.setOwner(source.getControler());
		actor.setSource(source);
		
		BulletAttrMgr attrMgr = new BulletAttrMgr();
		attrMgr.setPrototype(effectPrototype);
		actor.setAttrMgr(attrMgr);
		// 设置位置
		actor.setPosition(position);
		context.getSobMgr().insert(actor);
		return actor;
	}

	@Override
	public INonPerformer createNonPerformer() {
		return null;
	}

	@Override
	public IArea createArea() {
		return null;
	}

	/**
	 * 获取唯一id：int型后四位为对象子类型，高位为自增id
	 * 
	 * @param subType
	 * @return
	 */
	private int createId(int subType) {
		return atomicInteger.incrementAndGet() * SimuiationConstant.SubType + subType;
	}

	// --------------------------------------------
	public void setContext(ISGlobalContext context) {
		this.context = context;
	}

	public void setBuffContextFactory(IBuffContextFactory buffContextFactory) {
		this.buffContextFactory = buffContextFactory;
	}

	public void setTrapManager(ITrapManager trapManager) {
		this.trapManager = trapManager;
	}
}
