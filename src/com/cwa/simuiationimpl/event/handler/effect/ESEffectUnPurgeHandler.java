package com.cwa.simuiationimpl.event.handler.effect;

import java.util.List;

import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.enums.HurtTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;
import com.cwa.simuiationimpl.event.bean.PEffectBean;
import com.cwa.simuiationimpl.util.FormulaUtil;

/**
 * 驱散效果
 * 
 * @author tzy
 * 
 */
public class ESEffectUnPurgeHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_UnPurge.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
		MEffectBean magicEffectBean = event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId());
		for (IPerformer target : magicEffectBean.getPerformers()) {
			List<Integer> buffs = target.getBuffManager().dispelBuff(BuffBeneficialTypeEnum.Beneficial_Good, null);
			int changeValue = FormulaUtil.getRecoverMagicValue(source, affectedBean.getEffectPrototype(), affectedBean.getRandom());
			if(changeValue > 0){
				PEffectBean targetPEffectBean = magicEffectBean.getPerformerEffectBeans().get(target.getId());
				if(targetPEffectBean == null){
					targetPEffectBean = new PEffectBean();
					targetPEffectBean.setPid(target.getId());
					magicEffectBean.addPerformerEffectBeans(targetPEffectBean);
				}
				targetPEffectBean.addValue(changeValue);
				targetPEffectBean.addEffects(HurtTypeEnum.Effect_Normal.value());
				targetPEffectBean.setDeleteBuffs(buffs);
//				target.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), -changeValue);
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
