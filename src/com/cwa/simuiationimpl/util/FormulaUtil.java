package com.cwa.simuiationimpl.util;

import java.util.Random;

import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.map.Point;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.util.RandomUtil;

/**
 * 计算公式
 * 
 * @author tzy
 * 
 */
public class FormulaUtil {

	/**
	 * 计算速度
	 * 
	 * @param arg
	 * @return
	 */
	public static float getSpeed(int arg) {
		return arg / 1000.0f;
	}

	/**
	 * 技能效果值
	 * 
	 * @param arg
	 * @return
	 */
	public static int getSkillEffactValue(int rate) {
		return rate;
	}

	/**
	 * 
	 * 获取英雄等级表KeyId
	 * 
	 * @param arg
	 * @return
	 */
	public static int getHerogradeKeyId(int heroId, int quality, int star) {
		return heroId + quality * 10 + star;
	}

	/**
	 * 基础伤害=((攻击方最终攻击-防守方最终防御+(攻击方最终技力-防守方最终技力)/6)*(攻击方最终攻击+攻击方等级*100)/(防守方最终防御+
	 * 防御方等级*105))
	 * 
	 * @param sourceFinalAttack
	 *            :攻击方最终攻击
	 * @param targetFinalDefense
	 *            :防守方最终防御
	 * @param sourceFinalSkill
	 *            :攻击方最终技力
	 * @param targetFinalSkill防守方最终技力
	 * @param sourceLevel
	 *            ：攻击方等级
	 * @param targetLevel
	 *            ：防守方等级
	 * @return
	 */
	public static int getBaseDamageValue(IPerformer source, IPerformer target) {
		int sourceFinalAttack = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Attack.value());
		int sourceFinalSkill = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int sourceLevel = source.getAttrMgr().getLevel();

		int targetFinalDefense = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Defense.value());
		int targetFinalSkill = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int targetLevel = target.getAttrMgr().getLevel();

		int baseDamageValue = ((sourceFinalAttack - targetFinalDefense + (sourceFinalSkill - targetFinalSkill) / 6)
				* (sourceFinalAttack + sourceLevel * 100) / (targetFinalDefense + targetLevel * 105));
		return baseDamageValue > 1 ? baseDamageValue : 1;
	}

	/**
	 * 普攻非暴击最终伤害=((普攻基础伤害+攻击方最终技力/5)*防守方最终元素耐性/100*攻击方最终种族克制*攻击方最终元素专精*乱数/100*
	 * 招式倍率)*防守方伤害倍率/100*防守方无敌状态
	 * 
	 * @param baseDamageValue
	 *            :普攻基础伤害
	 * @param sourceFinalSkill
	 *            :攻击方最终技力
	 * @param targetFinalResistances
	 *            :防守方最终元素耐性
	 * @param sourceFinalClass
	 *            :攻击方最终种族克制
	 * @param sourceFinalMastery
	 *            :攻击方最终元素专精
	 * @param randSeed乱数
	 *            （90-110）
	 * @param rate招式倍率
	 * @param targetHitRate防守方伤害倍率
	 * @param targetInvincible防守方无敌状态
	 * @return
	 */
	public static int getBaseDamageNoCritValue(IPerformer source, IPerformer target, EffectPrototype ep,
			int baseDamageValue, Random r) {
		int sourceFinalSkill = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int sourceFinalClass = source.getAttrMgr().getRaceResistanceFinalValue(target.getAttrMgr().getRace());
		int sourceFinalMastery = source.getAttrMgr().getElementAttackFinalValue(ep.getElement());

		int targetFinalResistances = target.getAttrMgr().getElementResistanceFinalValue(ep.getElement());
		int targetHitRate = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_HitRate.value());
		int targetInvincible = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Invincible.value());

		int rate = ep.getRateList().get(source.getAttrMgr().getQuality());
		int rv = RandomUtil.regionRandom(90, 110, r);

		int baseDamageNoCritValue = (int) (((baseDamageValue + sourceFinalSkill / 5.0f) * (targetFinalResistances / 100.0f)
				* (sourceFinalClass / 100.0f) * (sourceFinalMastery / 100.0f) * (rv / 100.0f) * (rate / 100.0f))
				* (targetHitRate / 100.0f) * targetInvincible);
		return baseDamageNoCritValue;
	}

	/**
	 * 普攻暴击最终伤害=((普攻基础伤害+攻击方最终技力/5)*防守方最终元素耐性/100*攻击方最终种族克制*攻击方最终元素专精*(1+攻击方暴击^
	 * 1.9/防御方暴击^2)乱数/100*招式倍率/100)*防守方伤害倍率/100*防守方无敌状态
	 * 
	 * @param baseDamageValue
	 *            :普攻基础伤害
	 * @param sourceFinalSkill
	 *            :攻击方最终技力
	 * @param targetFinalResistances
	 *            :防守方最终元素耐性
	 * @param sourceFinalClass
	 *            :攻击方最终种族克制
	 * @param sourceFinalMastery
	 *            :攻击方最终元素专精
	 * @param randSee乱数
	 *            （90-110随机数）
	 * @param rate
	 *            :招式倍率
	 * @param sourceCrit
	 *            ：攻击方暴击
	 * @param targetCrit
	 *            ：防御方暴击
	 * @param targetHitRate防守方伤害倍率
	 * @param targetInvincible防守方无敌状态
	 * @return
	 */
	public static int getBaseDamageCritValue(IPerformer source, IPerformer target, EffectPrototype ep,
			int baseDamageValue, Random r) {
		int sourceFinalSkill = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int sourceFinalClass = source.getAttrMgr().getRaceResistanceFinalValue(target.getAttrMgr().getRace());
		int sourceFinalMastery = source.getAttrMgr().getElementAttackFinalValue(ep.getElement());
		int sourceFinalCrit = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());

		int targetFinalResistances = target.getAttrMgr().getElementResistanceFinalValue(ep.getElement());
		int targetFinalCrit = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());
		int targetHitRate = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_HitRate.value());
		int targetInvincible = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Invincible.value());

		int rate = ep.getRateList().get(source.getAttrMgr().getQuality());
		int rv = RandomUtil.regionRandom(90, 110, r);

		int baseDamageCritValue = (int) (((baseDamageValue + sourceFinalSkill / 5.0) * (targetFinalResistances / 100.0f)
				* (sourceFinalClass / 100.0f) * (sourceFinalMastery / 100.0f)
				* (1 + Math.pow(sourceFinalCrit, 1.9d) / Math.pow(targetFinalCrit, 2d)) * (rv / 100.0f) * rate / 100.0f)
				* (targetHitRate / 100.0f) * targetInvincible);
		return baseDamageCritValue;
	}

	/**
	 * 普攻最终暴击率=((攻击方最终暴击/防守方最终暴击)/2+(攻击方最终敏捷/防守方最终敏捷)/10)^2.5
	 * 
	 * @param sourceFinalCrit
	 *            ：攻击方最终暴击
	 * @param targetFinalCrit
	 *            : 防守方最终暴击
	 * @param sourceFinalQuick
	 *            ：攻击方最终敏捷
	 * @param targetFinalQuick
	 *            ：防守方最终敏捷
	 * @return
	 */
	public static int getBaseFinalCritRate(IPerformer source, IPerformer target) {
		int sourceFinalCrit = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());
		int sourceFinalQuick = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());

		int targetFinalCrit = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());
		int targetFinalQuick = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());

		double baseFinalCritRate = Math.pow(
				((sourceFinalCrit / targetFinalCrit) / 2 + (sourceFinalQuick / targetFinalQuick) / 10), 2.5);
		if (baseFinalCritRate < 0.15) {
			baseFinalCritRate = 0.15;
		} else if (baseFinalCritRate > 0.6) {
			baseFinalCritRate = 0.6;
		}
		baseFinalCritRate *= target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Invincible.value());//无敌不会被暴击
		return (int) (baseFinalCritRate * 100);
	}

	/**
	 * 普攻最终命中率=攻击方敏捷/防守方敏捷*0.8
	 * 
	 * @param sourceFinalQuick
	 *            :攻击方敏捷
	 * @param targetFinalQuick
	 *            :防守方敏捷
	 * @return
	 */
	public static int getBaseFinalHitRate(IPerformer source, IPerformer target) {
		float sourceFinalQuick = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());
		float targetFinalQuick = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());

		double baseFinalHitRate = sourceFinalQuick / targetFinalQuick * 0.8;
		if (baseFinalHitRate < 0.3) {
			baseFinalHitRate = 0.3;
		} else if (baseFinalHitRate > 0.9) {
			baseFinalHitRate = 0.9;
		}
		return (int) (baseFinalHitRate * 100);
	}

	/**
	 * 技能最终暴击率=((攻击方最终暴击/防守方最终暴击)/2+(攻击方最终敏捷/防守方最终敏捷)/10)^2.5
	 * 
	 * @param sourceFinalCrit
	 *            ：攻击方最终暴击
	 * @param targetFinalCrit
	 *            : 防守方最终暴击
	 * @param sourceFinalQuick
	 *            ：攻击方最终敏捷
	 * @param targetFinalQuick
	 *            ：防守方最终敏捷
	 * @param rate
	 *            ：招式倍率
	 * @return
	 */
	public static int getMagicFinalCritRate(IPerformer source, IPerformer target) {
		int sourceFinalCrit = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());
		int sourceFinalQuick = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());
		int targetFinalCrit = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());
		int targetFinalQuick = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());
		// int rate =
		// effectPrototype.getRateList().get(source.getAttrMgr().getQuality() -
		// 1);

		double magicFinalCritRate = Math.pow(
				((sourceFinalCrit / targetFinalCrit) / 2 + (sourceFinalQuick / targetFinalQuick) / 10), 2.5);
		if (magicFinalCritRate < 0.15) {
			magicFinalCritRate = 0.15;
		} else if (magicFinalCritRate > 0.6) {
			magicFinalCritRate = 0.6;
		}
		magicFinalCritRate *= target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Invincible.value());//无敌不会被暴击
		return (int) (magicFinalCritRate * 100);
	}

	/**
	 * 技能最终命中率=攻击方敏捷/防守方敏捷
	 * 
	 * @param sourceFinalQuick
	 *            :攻击方敏捷
	 * @param targetFinalQuick
	 *            :防守方敏捷
	 * @return
	 */
	public static int getMagicFinalHitRate(IPerformer source, IPerformer target) {
		float sourceFinalQuick = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());
		float targetFinalQuick = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Quick.value());

		double baseFinalHitRate = sourceFinalQuick / targetFinalQuick;
		if (baseFinalHitRate < 0.3) {
			baseFinalHitRate = 0.3;
		} else if (baseFinalHitRate > 0.95) {
			baseFinalHitRate = 0.95;
		}
		return (int) (baseFinalHitRate * 100);
	}

	/**
	 * 技能非暴击伤害=((普攻基础伤害*0.4+攻击方最终技力)*防守方最终元素耐性/100*攻击方最终种族克制*攻击方最终元素专精*乱数/100*
	 * 招式倍率/100)*防守方伤害倍率/100*防守方无敌状态
	 * 
	 * @param baseDamageValue
	 *            :普攻基础伤害
	 * @param sourceFinalSkill
	 *            :攻击方最终技力
	 * @param targetFinalResistances
	 *            :防守方最终元素耐性
	 * @param sourceFinalClass
	 *            :攻击方最终种族克制
	 * @param sourceFinalMastery
	 *            :攻击方最终元素专精
	 * @param randSeed乱数
	 *            （90-110）
	 * @param rate招式倍率
	 * @param targetHitRate防守方伤害倍率
	 * @param targetInvincible防守方无敌状态
	 * @return
	 */
	public static int getMagicDamageNoCritValue(IPerformer source, IPerformer target, EffectPrototype ep,
			int baseDamageValue, Random r) {
		int sourceFinalSkill = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int sourceFinalClass = source.getAttrMgr().getRaceResistanceFinalValue(target.getAttrMgr().getRace());
		int sourceFinalMastery = source.getAttrMgr().getElementAttackFinalValue(ep.getElement());

		int targetFinalResistances = target.getAttrMgr().getElementResistanceFinalValue(ep.getElement());
		int targetHitRate = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_HitRate.value());
		int targetInvincible = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Invincible.value());
		int rate = ep.getRateList().get(source.getAttrMgr().getQuality());

		int rv = RandomUtil.regionRandom(90, 110, r);

		int magicDamageNoCritValue = (int) (((baseDamageValue * 0.4 + sourceFinalSkill)
				* (targetFinalResistances / 100.0f) * (sourceFinalClass / 100.0f) * (sourceFinalMastery / 100.0f) * (rv / 100.0f) * (rate / 100.0f))
				* (targetHitRate / 100.0f) * targetInvincible);
		return magicDamageNoCritValue;
	}

	/**
	 * 技能暴击伤害=((普攻基础伤害*0.4+攻击方最终技力)*防守方最终元素耐性/100*攻击方最终种族克制*攻击方最终元素专精*(1+攻击方暴击^
	 * 1.9/防御方暴击^2)*乱数/100*招式倍率/100)*防守方伤害倍率/100*防守方无敌状态
	 * 
	 * @param baseDamageValue
	 *            :普攻基础伤害
	 * @param sourceFinalSkill
	 *            :攻击方最终技力
	 * @param targetFinalResistances
	 *            :防守方最终元素耐性
	 * @param sourceFinalClass
	 *            :攻击方最终种族克制
	 * @param sourceFinalMastery
	 *            :攻击方最终元素专精
	 * @param randSee乱数
	 *            （90-110随机数）
	 * @param rate
	 *            :招式倍率
	 * @param sourceCrit
	 *            ：攻击方暴击
	 * @param targetCrit
	 *            ：防御方暴击
	 * @param targetHitRate防守方伤害倍率
	 * @param targetInvincible防守方无敌状态
	 * @return
	 */
	public static int getMagicDamageCritValue(IPerformer source, IPerformer target, EffectPrototype ep,
			int baseDamageValue, Random r) {
		int sourceFinalSkill = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int sourceFinalClass = source.getAttrMgr().getRaceResistanceFinalValue(target.getAttrMgr().getRace());
		int sourceFinalMastery = source.getAttrMgr().getElementAttackFinalValue(ep.getElement());
		int sourceFinalCrit = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());

		int targetFinalResistances = target.getAttrMgr().getElementResistanceFinalValue(ep.getElement());
		int targetFinalCrit = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Crit.value());
		int targetHitRate = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_HitRate.value());
		int targetInvincible = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Invincible.value());

		int rate = ep.getRateList().get(source.getAttrMgr().getQuality());
		int rv = RandomUtil.regionRandom(90, 110, r);

		int magicDamageCritValue = (int) (((baseDamageValue * 0.4 + sourceFinalSkill) * (targetFinalResistances / 100.0f)
				* (sourceFinalClass /100.0f) * (sourceFinalMastery / 100.0f)
				* (1 + Math.pow(sourceFinalCrit, 1.9d) / Math.pow(targetFinalCrit, 2d)) * (rv / 100f) * (rate / 100.0f))
				* (targetHitRate / 100.0f) * targetInvincible);
		return magicDamageCritValue;
	}

	/**
	 * 恢复类技能恢复量=施法方最终技力*乱数/100*招式倍率/100
	 * 
	 * @param sourceFinalSkill
	 *            :施法方最终技力
	 * @param randSeed
	 *            :乱数
	 * @param rate
	 *            :招式倍率
	 * @return
	 */
	public static int getRecoverMagicValue(IPerformer source, EffectPrototype ep, Random r) {
		int quality = source.getAttrMgr().getQuality();
		int sourceFinalSkill = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Skill.value());
		int rate = ep.getRateList().get(quality);

		int rv = RandomUtil.regionRandom(90, 110, r);

		int recoverMagicValue = (int) (sourceFinalSkill * (rv / 100.0f) * (rate / 100.0f));
		return recoverMagicValue;
	}

	/**
	 * 各DEBUFF命中率=DEBUFF基础命中率*（200-相关DEBUFF耐性）/ 100
	 * 
	 * @param DebuffBaseHitRate
	 *            :DEBUFF基础命中率
	 * @param debuffPatience
	 *            :相关DEBUFF耐性
	 * @return
	 */
	public static double getDebuffHitRate(int DebuffBaseHitRate, int debuffPatience) {
		double debuffHitRate = DebuffBaseHitRate * (200 - debuffPatience) / 100.0f;
		return debuffHitRate;
	}

	/**
	 * 吸血
	 * 
	 * @param source
	 * @param attack
	 * @return
	 */
	public static int getSuckBlood(IPerformer source,IPerformer target, int attack) {
		int suckRate = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_SuckBlood.value());

		int suck = (int)(attack * (suckRate / 100.0f));
		return suck;
	}
	
	/**
	 * 反伤
	 */
	public static int getThornsBlood(IPerformer source,IPerformer target, int attack) {
		int thornsRate = target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Thorns.value());
		return (int)(attack * (thornsRate / 100.0f));
	}

	/**
	 * 吸蓝
	 * 
	 * @param source
	 * @param attack
	 * @return
	 */
	public static int getSuckBlue(IPerformer source, int attack) {
		int suckRate = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_SuckBlue.value());
		return (int) (attack * (suckRate / 100.0f));
	}

	public static Position getRandomPosition(Position targetPosition, int positionRand, Point maxPoint) {
		Position position = new Position(targetPosition.getDimensions());
		int v=0;
		//之前是从1开始。不清楚为什么，暂时改为0
		for (int i = 0; i < targetPosition.getDimensions(); i++) {
			v=(int) (targetPosition.getCoordinate(i) + RandomUtil.generalRrandom(positionRand));
			position.setCoordinate(i, (int)(v>maxPoint.getCoordinate(i)?maxPoint.getCoordinate(i):v));
		}
		return position;
	}

	public static void main(String[] args) {
	}
}
