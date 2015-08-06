package com.cwa.simuiationimpl.event.handler.effect;

import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.enums.HurtTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;

/**
 * 状态效果(就是挂BUFF)
 * 
 * @author tzy
 * 
 */
public class ESEffectStateHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_State.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
		MEffectBean magicEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
		for (IPerformer target : magicEffectBean.getPerformers()) {
			int attackEffectId = affectedBean.getActionContext().getIncidentalEffectLists().get(0);
			MEffectBean attackEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(attackEffectId);
			if(attackEffectBean != null && attackEffectBean.getPerformerEffectBeans().get(target.getId()) != null
					&& attackEffectBean.getPerformerEffectBeans().get(target.getId()).getEffects() != null
					&& attackEffectBean.getPerformerEffectBeans().get(target.getId()).getEffects().size() > 0){
				if(attackEffectBean.getPerformerEffectBeans().get(target.getId()).getEffects().get(0) == HurtTypeEnum.Effect_Dodge.value()){
					continue;
				}
			}
			
			enterBuff(source, target, affectedBean,event);
		}
	}
}
