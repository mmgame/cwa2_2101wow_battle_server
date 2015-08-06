package com.cwa.simuiationimpl.event.handler.effect;

import java.util.List;

import com.cwa.prototype.gameEnum.SkillAttackTypeEnum;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.HurtTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;
import com.cwa.simuiationimpl.event.bean.PEffectBean;
import com.cwa.simuiationimpl.util.FormulaUtil;

public abstract class AEffectHandler implements IESEffectHandler {
	protected int attack(IPerformer source, IPerformer target, AffectedBean affectedBean,ESMagicEvent magicEvent) {
		MEffectBean meffectBean = magicEvent.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
		PEffectBean sourcePEffectBean = meffectBean.getPerformerEffectBeans().get(source.getId());
		PEffectBean targetPEffectBean = meffectBean.getPerformerEffectBeans().get(target.getId());
		if(sourcePEffectBean == null){
			sourcePEffectBean = new PEffectBean();
			sourcePEffectBean.setPid(source.getId());
			meffectBean.addPerformerEffectBeans(sourcePEffectBean);
		}
		if(targetPEffectBean == null){
			targetPEffectBean = new PEffectBean();
			targetPEffectBean.setPid(target.getId());
			meffectBean.addPerformerEffectBeans(targetPEffectBean);
		}
		int attackType = affectedBean.getActionContext().getAttackType();

		// 计算命中
		int attackRate = 0;// 命中率
		if (attackType == SkillAttackTypeEnum.Type_Normal.value()) {// 普攻
			attackRate = FormulaUtil.getBaseFinalHitRate(source, target);
		} else {
			attackRate = FormulaUtil.getMagicFinalHitRate(source, target);
		}
		if (!isSuccess(affectedBean, attackRate)) {
			// 没有命中
			targetPEffectBean.addValue(0);
			targetPEffectBean.addEffects(HurtTypeEnum.Effect_Dodge.value());
			return 0;
		}
		// 计算暴击
		int critRate = 0;// 暴击率
		if (attackType == SkillAttackTypeEnum.Type_Normal.value()) {//  普攻
			critRate = FormulaUtil.getBaseFinalCritRate(source, target);
		} else {
			critRate = FormulaUtil.getMagicFinalCritRate(source, target);
		}
		boolean isCrit = isSuccess(affectedBean, critRate);// 是否暴击
		// 计算伤害
		int attackValue = 0;// 最终伤害
		int baseDamageValue = FormulaUtil.getBaseDamageValue(source, target);
		if (attackType == SkillAttackTypeEnum.Type_Normal.value()) {//  普攻
			if (isCrit) {
				attackValue = FormulaUtil.getBaseDamageCritValue(source, target, affectedBean.getEffectPrototype(), baseDamageValue,
						affectedBean.getRandom());
				targetPEffectBean.addEffects(HurtTypeEnum.Effect_Crit.value());
				changeToIdleState(target);//暴击就打断当前状态
			} else {
				attackValue = FormulaUtil.getBaseDamageNoCritValue(source, target, affectedBean.getEffectPrototype(), baseDamageValue,
						affectedBean.getRandom());
				targetPEffectBean.addEffects(HurtTypeEnum.Effect_Normal.value());
			}
		} else {
			if (isCrit) {
				attackValue = FormulaUtil.getMagicDamageCritValue(source, target, affectedBean.getEffectPrototype(), baseDamageValue,
						affectedBean.getRandom());
				targetPEffectBean.addEffects(HurtTypeEnum.Effect_Crit.value());
				changeToIdleState(target);//暴击就打断当前状态
			} else {
				attackValue = FormulaUtil.getMagicDamageNoCritValue(source, target, affectedBean.getEffectPrototype(), baseDamageValue,
						affectedBean.getRandom());
				targetPEffectBean.addEffects(HurtTypeEnum.Effect_Normal.value());
			}
		}
		targetPEffectBean.addValue(attackValue);
//		// 减护盾
//		if (target.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Shield.value()) != 0) {
//			target.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Shield.value(), -attackValue);
//		} else {
//			target.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), -attackValue);
//		}
		// 攻击方吸血
		int sourceValue = FormulaUtil.getSuckBlood(source,target, attackValue);
		if(sourceValue > 0){
			sourcePEffectBean.addValue(sourceValue);
			sourcePEffectBean.addEffects(HurtTypeEnum.Effect_SuckBlood.value());
//			source.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), sourceValue);
		}
		
		//攻击方反伤
		sourceValue = FormulaUtil.getThornsBlood(source,target, attackValue);
		if(sourceValue > 0){
			sourcePEffectBean.addValue(sourceValue);
			sourcePEffectBean.addEffects(HurtTypeEnum.Effect_Thorns.value());
//			source.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), -sourceValue);
		}
		// 攻击方吸蓝
		sourceValue = FormulaUtil.getSuckBlue(source, attackValue);
		if(sourceValue > 0){
			sourcePEffectBean.addValue(sourceValue);
			sourcePEffectBean.addEffects(HurtTypeEnum.Effect_SuckMagic.value());
//			source.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Energy.value(), sourceValue);
		}

		return attackValue;
	}

	/**
	 * 挂buff
	 * 
	 * @param effectPrototype
	 * @param target
	 * @param ac
	 */
	protected void enterBuff(IPerformer source, IPerformer target, AffectedBean affectedBean,ESMagicEvent magicEvent) {
		// 判断附带buff
		List<Integer> buffList = affectedBean.getEffectPrototype().getIncidentalParamList();
		for (Integer integer : buffList) {
			if (integer == 0) {
				break;
			} else {
				// 加buff
				MEffectBean meffectBean = magicEvent.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
				PEffectBean targetPEffectBean = meffectBean.getPerformerEffectBeans().get(target.getId());
				if(targetPEffectBean == null){
					targetPEffectBean = new PEffectBean();
					targetPEffectBean.setPid(target.getId());
					meffectBean.addPerformerEffectBeans(targetPEffectBean);
				}
				int value = affectedBean.getRandom().nextInt(SimuiationConstant.Percent);
				if(target.getBuffManager().enter(integer, value)){
					targetPEffectBean.addBuffs(integer);
				}
			}
		}
	}

	private boolean isSuccess(AffectedBean affectedBean, int v) {
		int value = affectedBean.getRandom().nextInt(SimuiationConstant.Percent);
		return value < v;
	}
	
	private void changeToIdleState(IPerformer target){
		IActionContext idleActionContext = target.getGlobalContext().getActionContextFactory().createIdleContext(target);
		IStateContext idleStateContext = target.getGlobalContext().getStateContextFactory().createASContext(idleActionContext);
		target.getStateManager().transtion(idleStateContext);
	}
}
