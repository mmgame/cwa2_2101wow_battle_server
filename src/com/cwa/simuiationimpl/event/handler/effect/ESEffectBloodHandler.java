package com.cwa.simuiationimpl.event.handler.effect;

import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.enums.HurtTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;
import com.cwa.simuiationimpl.event.bean.PEffectBean;

/**
 * 吸血效果
 * 
 * @author tzy
 * 
 */
public class ESEffectBloodHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_Suck_Blood.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
		MEffectBean magicEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
		for (IPerformer target : magicEffectBean.getPerformers()) {
			int attackvalue = attack(source, target, affectedBean,event);
			PEffectBean sourcePEffectBean = magicEffectBean.getPerformerEffectBeans().get(source.getId());
			if(sourcePEffectBean == null){
				sourcePEffectBean = new PEffectBean();
				sourcePEffectBean.setPid(source.getId());
				magicEffectBean.addPerformerEffectBeans(sourcePEffectBean);
			}
			int value = (int) (attackvalue * SimuiationConstant.SuckRate);
			if(value > 0){
				sourcePEffectBean.addValue(value);
				sourcePEffectBean.addEffects(HurtTypeEnum.Effect_SuckBlood.value());
//				source.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), value);
			}
			if(attackvalue > 0){
				enterBuff(source, target, affectedBean,event);
			}
		}
	}
}
