package com.cwa.simuiationimpl.event.handler.effect;

import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;

/**
 * 攻击效果
 * 
 * @author tzy
 * 
 */
public class ESEffectAttackHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_Attack.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
//		MEffectBean magicEffectBean = affectedBean.getAffectedTargets();
		MEffectBean magicEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
		for (IPerformer target : magicEffectBean.getPerformers()) {
			int value = attack(source, target, affectedBean,event);
			if(value > 0){//伤害为0说明目标闪避或者无敌，不中buff
				enterBuff(source, target, affectedBean,event);
			}
		}
	}
}
