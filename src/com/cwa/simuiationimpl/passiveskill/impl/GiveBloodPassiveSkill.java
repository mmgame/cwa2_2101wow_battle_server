package com.cwa.simuiationimpl.passiveskill.impl;

import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.ValueTypeEnum;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.passiveskill.IPassiveSkill;
import com.cwa.simuiation.passiveskill.IPassiveSkillContext;
/**
 * 血量影响
 * @author yangfeng
 *
 */
public class GiveBloodPassiveSkill implements IPassiveSkill {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Blood.value();
	}

	@Override
	public void update(IPassiveSkillContext context) {
		long differTime = context.getPerformer().getGlobalContext().getClock().differByCurrentTime(context.getStartTime());
		if(differTime > 1000){//1秒钟执行一次
			context.resetContext();
			affect(context);
		}
	}
	private void affect(IPassiveSkillContext context){
		PassiveSkillPrototype passiveSkillPrototype = context.getPassiveSkillPrototype();
		IPerformer performer = context.getPerformer();
		//间隔时间到
		int changeValue = 0;
		int quality = performer.getAttrMgr().getQuality();
		if(passiveSkillPrototype.getValueType() == ValueTypeEnum.Value_Num.value()){
			//数值
			changeValue = passiveSkillPrototype.gotDamageAmountListsList().get(quality).get(0);
		}else{
			//百分比
			changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Blood.value())
					* passiveSkillPrototype.gotDamageAmountListsList().get(quality).get(0) / 100.0f);
		}
		if(passiveSkillPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){
			//减益
			changeValue = -changeValue;
		}
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), changeValue);
	}
}
