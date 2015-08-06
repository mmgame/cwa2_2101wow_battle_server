package com.cwa.simuiationimpl.event.handler.effect;

import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.enums.HurtTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;
import com.cwa.simuiationimpl.event.bean.PEffectBean;
import com.cwa.simuiationimpl.util.FormulaUtil;

/**
 * 恢复效果
 * 
 * @author tzy
 * 
 */
public class ESEffectRecoverHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_Recovery.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
		MEffectBean magicEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
		for (IPerformer target : magicEffectBean.getPerformers()) {
			int changeValue = FormulaUtil.getRecoverMagicValue(source, affectedBean.getEffectPrototype(), affectedBean.getRandom());
			if(changeValue > 0){
				PEffectBean sourcePEffectBean = magicEffectBean.getPerformerEffectBeans().get(target.getId());
				if(sourcePEffectBean == null){
					sourcePEffectBean = new PEffectBean();
					sourcePEffectBean.setPid(target.getId());
					magicEffectBean.addPerformerEffectBeans(sourcePEffectBean);
				}
				sourcePEffectBean.addValue(changeValue);
				sourcePEffectBean.addEffects(HurtTypeEnum.Effect_Recover.value());
//				target.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), changeValue);
			}
			
			int attackEffectId = affectedBean.getActionContext().getIncidentalEffectLists().get(0);
			MEffectBean attackEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(attackEffectId);
			if(attackEffectBean != null && attackEffectBean.getPerformerEffectBeans().get(target.getId()) != null
					&& attackEffectBean.getPerformerEffectBeans().get(target.getId()).getEffects() != null
					&& attackEffectBean.getPerformerEffectBeans().get(target.getId()).getEffects().size() > 0){
				if(attackEffectBean.getPerformerEffectBeans().get(target.getId()).getEffects().get(0) == HurtTypeEnum.Effect_Dodge.value()){
					continue;
				}
			}
			// 判断附带buff
			enterBuff(source, target, affectedBean,event);
		}
	}
}
