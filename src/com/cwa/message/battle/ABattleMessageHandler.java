package com.cwa.message.battle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.IBattleService;
import com.cwa.data.entity.domain.HeroEntity;
import com.cwa.message.BattleMessage.ActionStateContextBean;
import com.cwa.message.BattleMessage.BattleBean;
import com.cwa.message.BattleMessage.BuffContextBean;
import com.cwa.message.BattleMessage.BulletBean;
import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.HeroBean;
import com.cwa.message.BattleMessage.HeroEntityBean;
import com.cwa.message.BattleMessage.MagicCDBean;
import com.cwa.message.BattleMessage.PetBean;
import com.cwa.message.BattleMessage.PlayerBean;
import com.cwa.message.BattleMessage.SyncBattleDown;
import com.cwa.message.BattleMessage.TrapBean;
import com.cwa.message.BattleMessage.UserEntityBean;
import com.cwa.message.IGameHandler;
import com.cwa.prototype.CallPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.enums.AxisTyeEunm;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiationimpl.manage.HeroAttrMgr;
import com.cwa.simuiationimpl.manage.PetAttrMgr;
import com.cwa.simuiationimpl.manage.UserFightList;
import com.cwa.simuiationimpl.obj.Bullet;
import com.cwa.simuiationimpl.obj.Hero;
import com.cwa.simuiationimpl.obj.Pet;
import com.cwa.simuiationimpl.obj.Trap;

/**
 * 抽象消息类
 * 
 * @author tzy
 * 
 * @param <T>
 */
public abstract class ABattleMessageHandler<T> extends IGameHandler<T> {
	// 战场服务接口
	protected IBattleService battleService;

	public SyncBattleDown getSyncBattleDown(IBattleGround battleGround) {
		SyncBattleDown.Builder syncBattleDown = SyncBattleDown.newBuilder();
		BattleBean.Builder battleBeanb = BattleBean.newBuilder();

		// 战场信息
		battleBeanb.setBattleKeyId(battleGround.getKeyId());
		battleBeanb.setCurrentTime(battleGround.getSimuiation().getClock().getCurrentSTime().getMSTime());
		battleBeanb.setCurrentState(battleGround.getStateManager().getCurrentState().getType());
		// 英雄信息
		Set<ISObject> heroSet = battleGround.getSimuiation().getSobMgr().getSObjectListBySubType(ObjSubTypeEnum.P_Hero.value());
		List<HeroBean> heroBeans = getHeroBeanList(heroSet);
		// 宠物信息
		Set<ISObject> petSet = battleGround.getSimuiation().getSobMgr().getSObjectListBySubType(ObjSubTypeEnum.P_Pet.value());
		List<PetBean> petBeans = getPetBeanList(petSet);
		// 子弹信息
		Set<ISObject> bulletSet = battleGround.getSimuiation().getSobMgr().getSObjectListBySubType(ObjSubTypeEnum.P_Bullet.value());
		List<BulletBean> bulletBeans = getBulletBeanList(bulletSet);
		// 陷阱信息
		Set<ISObject> trapSet = battleGround.getSimuiation().getSobMgr().getSObjectListBySubType(ObjSubTypeEnum.P_Trap.value());
		List<TrapBean> trapBeans = getTrapBeanList(trapSet);

		List<PlayerBean> playerBeans = new ArrayList<PlayerBean>();

		Map<Long, UserFightList> UserFightMap = battleGround.getUserFightListMap();
		Iterator<Entry<Long, UserFightList>> it = UserFightMap.entrySet().iterator();
		for (; it.hasNext();) {
			Entry<Long, UserFightList> entry = it.next();
			PlayerBean.Builder pb = PlayerBean.newBuilder();
			UserEntityBean.Builder ub = UserEntityBean.newBuilder();
			ub.setUid(entry.getKey());
			// TODO 临时用户信息
			ub.setLevel(1);
			pb.setTroopType(entry.getValue().getTroopType());
			pb.setUserEntityBean(ub);
			playerBeans.add(pb.build());
		}
		syncBattleDown.setBattleBean(battleBeanb.build());
		syncBattleDown.addAllHeroBeans(heroBeans);
		syncBattleDown.addAllPetBeans(petBeans);
		syncBattleDown.addAllBulletBeans(bulletBeans);
		syncBattleDown.addAllTrapBeans(trapBeans);
		syncBattleDown.addAllPlayerBeans(playerBeans);
		return syncBattleDown.build();
	}

	private List<HeroBean> getHeroBeanList(Set<ISObject> heroSet) {
		List<HeroBean> heroBeans = new ArrayList<HeroBean>();
		if (heroSet != null) {
			for (ISObject obj : heroSet) {
				Hero hero = (Hero) obj;
				HeroEntity heroEntity = (HeroEntity) hero.getAttrMgr().getEntity();
				HeroEntityBean.Builder heroEntityBean = HeroEntityBean.newBuilder();
				heroEntityBean.setId(heroEntity.heroId);
				heroEntityBean.setLevel(heroEntity.level);
				heroEntityBean.setQuality(heroEntity.quality);
				heroEntityBean.setStar(heroEntity.starLevel);
				heroEntityBean.setUid(heroEntity.userId);

				List<Integer> attrList = new ArrayList<Integer>();
				for (AttrTypeEnum attr : AttrTypeEnum.values()) {
					attrList.add(hero.getAttrMgr().getConsumeValue(attr.value()));
				}
				List<BuffContextBean> buffContextBeanList = new ArrayList<BuffContextBean>();
				Map<String, IBuffContext> buffContextMap = hero.getBuffManager().getBuffContextMap();
				for (IBuffContext context : buffContextMap.values()) {
					BuffContextBean.Builder buffContextBean = BuffContextBean.newBuilder();
					buffContextBean.setPerformerId(hero.getId());
					buffContextBean.setBuffKeyId(context.getBuffPrototype().getKeyId());
					buffContextBean.setTime(hero.getGlobalContext().getClock().getCurrentSTime().getMSTime()
							- context.getStartTime().getMSTime());
					buffContextBeanList.add(buffContextBean.build());
				}
				List<MagicCDBean> magicCDBeans = new ArrayList<MagicCDBean>();
				for (int skillId : ((HeroAttrMgr) hero.getAttrMgr()).getHerogradePrototype().getCommonAttackList()) {
					MagicCDBean.Builder magicCDBean = MagicCDBean.newBuilder();
					magicCDBean.setSkillId(skillId);
					long cdTime = hero.getSkillManager().getCdTime(skillId);
					// if (cdTime != 0) {
					cdTime = cdTime - hero.getGlobalContext().getClock().getCurrentSTime().getMSTime();
					// }
					magicCDBean.setTime((int) cdTime);
					magicCDBeans.add(magicCDBean.build());
				}

				List<ActionStateContextBean> actionStateContextBeanList = new ArrayList<ActionStateContextBean>();
				IActionStateContext stateContext = (IActionStateContext) hero.getStateManager().getCurrentState(
						StateTypeEnum.Action.value());
				if (stateContext != null) {
					ActionStateContextBean.Builder actionStateContextBean = ActionStateContextBean.newBuilder();
					int subType = stateContext.getState().getSubType();
					actionStateContextBean.setStateSubType(subType);
					actionStateContextBean.setStartTime(stateContext.getActionContext().getStartTime().getMSTime());
					actionStateContextBeanList.add(actionStateContextBean.build());
				}

				HeroBean.Builder heroBean = HeroBean.newBuilder();
				heroBean.setPerformerId(hero.getId());
				heroBean.setUid(Long.parseLong(hero.getControler().getId()));
				heroBean.setPosition(getPostion(hero));
				heroBean.setHeroEntityBean(heroEntityBean);
				heroBean.addAllCurrentAttrs(attrList);
				heroBean.addAllBuffContextBeans(buffContextBeanList);
				heroBean.addAllActionStateContextBeans(actionStateContextBeanList);
				heroBean.addAllMagicCDBeans(magicCDBeans);
				heroBean.setRetinueSkillId(((HeroAttrMgr)hero.getAttrMgr()).getRetinueSkillId());
				heroBeans.add(heroBean.build());
			}
		}
		return heroBeans;
	}

	private List<PetBean> getPetBeanList(Set<ISObject> petSet) {
		List<PetBean> petBeans = new ArrayList<PetBean>();
		if (petSet != null) {
			for (ISObject obj : petSet) {
				Pet pet = (Pet) obj;
				List<Integer> attrList = new ArrayList<Integer>();
				for (AttrTypeEnum attr : AttrTypeEnum.values()) {
					attrList.add(pet.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), attr.value()));
				}
				List<BuffContextBean> buffContextBeanList = new ArrayList<BuffContextBean>();
				Map<String, IBuffContext> buffContextMap = pet.getBuffManager().getBuffContextMap();
				for (IBuffContext context : buffContextMap.values()) {
					BuffContextBean.Builder buffContextBean = BuffContextBean.newBuilder();
					buffContextBean.setPerformerId(pet.getId());
					buffContextBean.setBuffKeyId(context.getBuffPrototype().getKeyId());
					buffContextBean.setTime(pet.getGlobalContext().getClock().getCurrentSTime().getMSTime()
							- context.getStartTime().getMSTime());
					buffContextBeanList.add(buffContextBean.build());
				}
				List<MagicCDBean> magicCDBeans = new ArrayList<MagicCDBean>();
				for (int skillId : ((CallPrototype) ((PetAttrMgr) pet.getAttrMgr()).getPrototype()).getCommonAttackList()) {
					MagicCDBean.Builder magicCDBean = MagicCDBean.newBuilder();
					magicCDBean.setSkillId(skillId);
					long cdTime = pet.getSkillManager().getCdTime(skillId);
					// if (cdTime != 0) {
					cdTime = cdTime - pet.getGlobalContext().getClock().getCurrentSTime().getMSTime();
					// }
					magicCDBean.setTime((int) cdTime);
					magicCDBeans.add(magicCDBean.build());
				}
				List<ActionStateContextBean> actionStateContextBeanList = new ArrayList<ActionStateContextBean>();
				IActionStateContext stateContext = (IActionStateContext) pet.getStateManager()
						.getCurrentState(StateTypeEnum.Action.value());
				if (stateContext != null) {
					ActionStateContextBean.Builder actionStateContextBean = ActionStateContextBean.newBuilder();
					actionStateContextBean.setStateSubType(stateContext.getState().getSubType());
					actionStateContextBean.setStartTime(stateContext.getActionContext().getStartTime().getMSTime());
					actionStateContextBeanList.add(actionStateContextBean.build());
				}
				PetBean.Builder petBean = PetBean.newBuilder();
				petBean.setId(pet.getAttrMgr().getPrototype().getKeyId());
				petBean.setLevel(pet.getAttrMgr().getLevel());
				petBean.setPerformerId(pet.getId());
				petBean.setSourceId(pet.getSource().getId());
				petBean.setUid(Long.parseLong(pet.getOwner().getId()));
				petBean.setPosition(getPostion(pet));
				petBean.addAllCurrentAttrs(attrList);
				petBean.addAllBuffContextBeans(buffContextBeanList);
				petBean.addAllActionStateContextBeans(actionStateContextBeanList);
				petBean.addAllMagicCDBeans(magicCDBeans);
				petBeans.add(petBean.build());
			}
		}
		return petBeans;
	}

	private List<BulletBean> getBulletBeanList(Set<ISObject> bulletSet) {
		List<BulletBean> bulletBeans = new ArrayList<BulletBean>();
		if (bulletSet != null) {
			for (ISObject obj : bulletSet) {
				Bullet bullet = (Bullet) obj;
				List<Integer> attrList = new ArrayList<Integer>();
				for (AttrTypeEnum attr : AttrTypeEnum.values()) {
					attrList.add(bullet.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), attr.value()));
				}
				List<ActionStateContextBean> actionStateContextBeanList = new ArrayList<ActionStateContextBean>();
				IActionStateContext stateContext = (IActionStateContext) bullet.getStateManager().getCurrentState(
						StateTypeEnum.Action.value());
				if (stateContext != null) {
					ActionStateContextBean.Builder actionStateContextBean = ActionStateContextBean.newBuilder();
					actionStateContextBean.setStateSubType(stateContext.getState().getSubType());
					actionStateContextBean.setStartTime(stateContext.getActionContext().getStartTime().getMSTime());
					actionStateContextBeanList.add(actionStateContextBean.build());
				}
				BulletBean.Builder bulletBean = BulletBean.newBuilder();
				bulletBean.setEffectId(bullet.getAttrMgr().getPrototype().getKeyId());
				bulletBean.setPerformerId(bullet.getId());
				bulletBean.setSourceId(bullet.getSource().getId());
				bulletBean.setUid(Long.parseLong(bullet.getOwner().getId()));
				bulletBean.setPosition(getPostion(bullet));
				bulletBean.addAllActionStateContextBeans(actionStateContextBeanList);
				bulletBeans.add(bulletBean.build());
			}
		}
		return bulletBeans;
	}

	private List<TrapBean> getTrapBeanList(Set<ISObject> trapSet) {
		List<TrapBean> trapBeans = new ArrayList<TrapBean>();
		if (trapSet != null) {
			for (ISObject obj : trapSet) {
				Trap trap = (Trap) obj;
				List<Integer> attrList = new ArrayList<Integer>();
				for (AttrTypeEnum attr : AttrTypeEnum.values()) {
					attrList.add(trap.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), attr.value()));
				}

				TrapBean.Builder trapBean = TrapBean.newBuilder();
				trapBean.setId(trap.getTrapPrototype().getKeyId());
				trapBean.setEffectId(trap.getEffectPrototype().getKeyId());
				trapBean.setPerformerId(trap.getId());
				trapBean.setSourceId(trap.getSource().getId());
				trapBean.setUid(trap.getOwner().getId());
				trapBean.setPosition(getPostion(trap));
				trapBean.setStartTime(trap.getStartTime().getMSTime());
				trapBeans.add(trapBean.build());
			}
		}
		return trapBeans;
	}

	// 获取位置
	private CoordinateBean getPostion(ISObject obj) {
		CoordinateBean.Builder coordinateBean = CoordinateBean.newBuilder();
		coordinateBean.addC((int) obj.getPosition().getCoordinate(AxisTyeEunm.XAxis));
		coordinateBean.addC((int) obj.getPosition().getCoordinate(AxisTyeEunm.YAxis));
		return coordinateBean.build();
	}

	// --------------------------------------------
	public void setBattleService(IBattleService battleService) {
		this.battleService = battleService;
	}
}
