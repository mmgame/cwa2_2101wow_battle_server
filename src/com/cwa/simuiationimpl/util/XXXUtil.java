package com.cwa.simuiationimpl.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import serverice.room.TroopTypeEnum;

import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.EffectTargetTypeEnum;
import com.cwa.prototype.gameEnum.SkillReleaseRuleEnum;
import com.cwa.simuiation.context.ISGlobalContext;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.manager.IDistanceMgr;
import com.cwa.simuiation.manager.ISobMgr;
import com.cwa.simuiation.map.Point;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;

public class XXXUtil {
	/**
	 * 得到点到所有Performer的距离
	 * 
	 * @param source
	 * @param sobMgr
	 * @return
	 */
	public static Map<IPerformer, Double> getPoint2PerfDistances(Point source, ISobMgr sobMgr) {
		Map<IPerformer, Double> distanceMap = new HashMap<IPerformer, Double>();

		List<ISObject> performers = sobMgr.getSObjectListByType(ObjTypeEnum.Performer.value());

		for (ISObject performer : performers) {
			double distance = Math2D.getDistance(source, performer.getPosition().getPoint());
			distanceMap.put((IPerformer) performer, distance);
		}
		return distanceMap;
	}

	// ------------------------------
	/**
	 * 选目标
	 */
	public static List<IPerformer> chooseTarget(IPerformer performer, ISObject target, int targetRule, int range) {
		List<ISObject> targets = performer.getGlobalContext().getSobMgr().getSObjectListByType(ObjTypeEnum.Performer.value());
		List<IPerformer> affecteds = new LinkedList<IPerformer>();
		if (targetRule == EffectTargetTypeEnum.Target_MySelf.value()) {
			affecteds.add(performer);
			return affecteds;
		}
		// 过滤类型
		filterObjType(targets, ObjTypeEnum.Performer.value(), new int[] { ObjSubTypeEnum.P_Hero.value(), ObjSubTypeEnum.P_Pet.value() });

		if (targetRule == EffectTargetTypeEnum.Target_League.value()) {
			// 同盟
			filterFaction(targets, SkillReleaseRuleEnum.Release_League.value(), performer);
		} else if (targetRule == EffectTargetTypeEnum.Target_Enemy.value()) {
			// 敌人
			filterFaction(targets, SkillReleaseRuleEnum.Release_Enemy.value(), performer);
		} else if (targetRule == EffectTargetTypeEnum.Target_All.value()) {
		} else if (targetRule == EffectTargetTypeEnum.Target_TheWeakOfUs.value()) {
			// 同盟
			filterFaction(targets, SkillReleaseRuleEnum.Release_League.value(), performer);
			// 血最小
			filterAttr(targets, AttrTypeEnum.Attr_Blood.value(), 0);
		} else if (targetRule == EffectTargetTypeEnum.Target_TheWeakOfEnemy.value()) {
			// 敌人
			filterFaction(targets, SkillReleaseRuleEnum.Release_Enemy.value(), performer);
			// 血最小
			filterAttr(targets, AttrTypeEnum.Attr_Blood.value(), 0);
		} else if (targetRule == EffectTargetTypeEnum.Target_TheWeakHero.value()) {
			// 血最小
			filterAttr(targets, AttrTypeEnum.Attr_Blood.value(), 0);
		} else if (targetRule == EffectTargetTypeEnum.Target_LeagueBadBuff.value()) {
			// 同盟
			filterFaction(targets, SkillReleaseRuleEnum.Release_League.value(), performer);
			// TODO 坏buff
			filterBuff(targets, BuffBeneficialTypeEnum.Beneficial_Bad.value());
		} else if (targetRule == EffectTargetTypeEnum.Target_EnemyWellBuff.value()) {
			// 敌人
			filterFaction(targets, SkillReleaseRuleEnum.Release_Enemy.value(), performer);
			// TODO 好buff
			filterBuff(targets, BuffBeneficialTypeEnum.Beneficial_Good.value());
		}

		// 过滤范围
		filterRange(targets, range, target, performer.getGlobalContext().getDistanceMgr());
		for (ISObject s : targets) {
			if (s instanceof IPerformer) {
				affecteds.add((IPerformer) s);
			}
		}
		return affecteds;
	}

	/**
	 * 过滤拥有增益buff或者减益buff
	 */
	public static void filterBuff(List<ISObject> targets, int type) {
		Iterator<ISObject> it = targets.iterator();
		if (type == BuffBeneficialTypeEnum.Beneficial_Bad.value()) {
			while (it.hasNext()) {
				IPerformer p = (IPerformer) it.next();
				if (!p.getBuffManager().haveDebuff()) {
					it.remove();
				}
			}
		} else if (type == BuffBeneficialTypeEnum.Beneficial_Good.value()) {
			while (it.hasNext()) {
				IPerformer p = (IPerformer) it.next();
				if (!p.getBuffManager().haveBuff()) {
					it.remove();
				}
			}
		}
	}

	/**
	 * 范围内过滤【范围，目标】
	 * 
	 * @param targets
	 * @param range
	 * @param s
	 */
	public static void filterRange(List<ISObject> targets, int range, ISObject s, IDistanceMgr distanceMgr) {
		if (range <= 0) {
			targets.clear();
			targets.add(s);
			return;
		}
		Iterator<ISObject> it = targets.iterator();
		for (; it.hasNext();) {
			ISObject t = it.next();

			// 判断碰撞
			if (!distanceMgr.isCollision(s, t, false, range)) {
				// 超出攻击范围
				it.remove();
			}
		}
	}

	/**
	 * 对象类型过滤【类型，子类型1 子类型2...】
	 * 
	 * @param targets
	 * @param objType
	 * @param objSubTypes
	 */
	public static void filterObjType(List<ISObject> targets, int objType, int[] objSubTypes) {
		Iterator<ISObject> it = targets.iterator();
		for (; it.hasNext();) {
			ISObject t = it.next();
			if (objType == -1 && t.getType() != objType) {
				it.remove();
				continue;
			}
			boolean isRemove = true;
			for (int objSubType : objSubTypes) {
				if (t.getSubType() == objSubType) {
					isRemove = false;
					break;
				}
			}
			if (isRemove) {
				it.remove();
			}
		}
	}

	/**
	 * 随机过滤【个数】
	 * 
	 * @param targets
	 * @param size
	 */
	public static List<ISObject> filterRandom(List<ISObject> targets, int size) {
		if (size <= 0) {
			return new LinkedList<ISObject>();
		}
		if (targets.size() <= size) {
			return new LinkedList<ISObject>();
		}
		// 这里截取前size个
		return targets.subList(0, size - 1);
	}

	/**
	 * 阵营过滤【阵营关系，目标】
	 * 
	 * @param targets
	 *            目标
	 * @param relation
	 *            阵营关系
	 * @param s
	 */
	public static void filterFaction(List<ISObject> targets, int relation, ISObject s) {
		if (!(s instanceof IPerformer)) {
			return;
		}
		if (relation != SkillReleaseRuleEnum.Release_League.value() && relation != SkillReleaseRuleEnum.Release_Enemy.value()) {
			return;
		}
		IPerformer source = (IPerformer) s;
		int controlerTroopType = source.getControler().getTroopType();
		Iterator<ISObject> it = targets.iterator();
		for (; it.hasNext();) {
			ISObject t = it.next();
			if (!(t instanceof IPerformer)) {
				continue;
			}
			IPerformer target = (IPerformer) t;
			int tcty = target.getControler().getTroopType();
			if ((relation == SkillReleaseRuleEnum.Release_League.value() && tcty != controlerTroopType)
					|| (relation == SkillReleaseRuleEnum.Release_Enemy.value() && tcty == controlerTroopType)) {
				it.remove();
				continue;
			}
		}
	}

	/**
	 * 属性过滤【某个属性类型，最低0最高1】
	 * 
	 * @param attrType
	 *            属性类型
	 * @param maxmin
	 *            最高1最低0
	 */
	public static void filterAttr(List<ISObject> targets, int attrType, int maxmin) {
		if (maxmin < 0 || attrType < 0) {
			return;
		}
		int temp = 0;
		int id = 0;
		if (targets.size() > 0) {
			IPerformer p = (IPerformer) targets.get(0);
			temp = p.getAttrMgr().getConsumeValue(attrType);
			temp = p.getAttrMgr().getConsumeValue(attrType);
			id = p.getId();
		}
		for (ISObject object : targets) {
			IPerformer p = (IPerformer) object;
			if (maxmin == 1) {
				if (p.getAttrMgr().getConsumeValue(attrType) > temp) {
					temp = p.getAttrMgr().getConsumeValue(attrType);
					id = p.getId();
				}
			} else if (maxmin == 0) {
				if (p.getAttrMgr().getConsumeValue(attrType) < temp) {
					temp = p.getAttrMgr().getConsumeValue(attrType);
					id = p.getId();
				}
			}
		}

		Iterator<ISObject> it = targets.iterator();
		for (; it.hasNext();) {
			ISObject t = it.next();
			if (t.getId() != id) {
				it.remove();
			}
		}

	}

	/**
	 * 找离自己最近的敌人
	 * 
	 * @param performer
	 * @return
	 */
	public static ISObject selectTarget(IPerformer performer) {
		List<ISObject> targets = performer.getGlobalContext().getSobMgr().getSObjectListByType(ObjTypeEnum.Performer.value());
		filterFaction(targets, SkillReleaseRuleEnum.Release_Enemy.value(), performer);// 所有敌人
		// 过滤类型
		filterObjType(targets, ObjTypeEnum.Performer.value(), new int[] { ObjSubTypeEnum.P_Hero.value(), ObjSubTypeEnum.P_Pet.value() });

		IPerformer retTarget = null;
		for (ISObject target : targets) {
			IPerformer t = (IPerformer) target;
			if (t.getStateManager().getCurrentState(StateTypeEnum.Global.value()).getState().getSubType() != StateSubTypeEnum.GS_Dead
					.value()) {
				// 备选目标不是死亡状态
				if (retTarget == null) {
					retTarget = t;
				} else {
					if (Math2D.getDistance(t.getPosition().getPoint(), performer.getPosition().getPoint()) < Math2D.getDistance(retTarget
							.getPosition().getPoint(), performer.getPosition().getPoint())) {
						retTarget = t;
					}
				}
			}
		}
		return retTarget;
	}

	public static void filterDead(List<ISObject> targets) {
		Iterator<ISObject> it = targets.iterator();
		for (; it.hasNext();) {
			IPerformer t = (IPerformer) it.next();
			if (t.getStateManager().getCurrentState(StateTypeEnum.Global.value()).getState().getSubType() == StateSubTypeEnum.GS_Dead
					.value()) {
				it.remove();
			}
		}
	}

	/**
	 * 找失败方
	 * 
	 * @param simuiation
	 * @return 失败方队伍类型
	 */
	public static int selectFailure(ISGlobalContext globalContext) {
		List<ISObject> targets = globalContext.getSobMgr().getSObjectListByType(ObjTypeEnum.Performer.value());
		// 过滤类型
		filterObjType(targets, ObjTypeEnum.Performer.value(), new int[] { ObjSubTypeEnum.P_Hero.value(), ObjSubTypeEnum.P_Pet.value() });
		filterDead(targets);
		if (targets.size() == 0) {
			return TroopTypeEnum.Attack.value();// 进攻方
		}
		int sourceTroopType = ((IPerformer) targets.get(0)).getOwner().getTroopType();
		Iterator<ISObject> it = targets.iterator();
		int sNum = 0, tNum = 0, targetTroopType = 0;
		if (sourceTroopType == TroopTypeEnum.Attack.value()) {
			targetTroopType = TroopTypeEnum.Defend.value();
		} else {
			targetTroopType = TroopTypeEnum.Attack.value();
		}
		for (; it.hasNext();) {
			ISObject t = it.next();
			IPerformer target = (IPerformer) t;
			int tcty = target.getControler().getTroopType();
			if (tcty == sourceTroopType) {
				sNum++;
			} else {
				tNum++;
			}
		}
		if (sNum <= tNum) {
			return sourceTroopType;
		} else {
			return targetTroopType;
		}
	}

}
