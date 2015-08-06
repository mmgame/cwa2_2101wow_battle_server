package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.BuffPrototype;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.ValueTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
/**
 * 魔力影响
 * @author yangfeng
 *
 */
public class GiveBlueBuff implements ISBuff {
	@Override
	public int getType() {
		return BuffTypeEnum.Buff_GiveBlue.value();
	}

	@Override
	public void enter(IBuffContext context) {
	}

	@Override
	public void exit(IBuffContext context) {
	}

	@Override
	public void update(IBuffContext context) {
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		affect(context);
		
		long differTime = context.getPerformer().getGlobalContext().getClock().differByCurrentTime(context.getStartTime());
		if(differTime >= buffPrototype.getEffectiveTime()){
			//效果时间到
			performer.getBuffManager().exit(context);
		}
	}

	@Override
	public boolean canTranstion(IStateContext target) {
		return true;
	}
	
	public void affect(IBuffContext context){
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		//间隔时间到,进行掉血处理
		int changeValue = 0;
		if(buffPrototype.getValueType() == ValueTypeEnum.Value_Num.value()){
			//数值
			changeValue = buffPrototype.getDamageAmountList().get(0);
		}else{
			//百分比
			changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Energy.value())
					* buffPrototype.getDamageAmountList().get(0) / 100.0f);
		}
		if(buffPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){
			//减益
			changeValue = -changeValue;
		}
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Energy.value(), changeValue);
	}
}
