package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.BuffPrototype;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.ValueTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
/**
 * 护盾
 * @author yangfeng
 *
 */
public class ShieldBuff implements ISBuff {
	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Shield.value();
	}

	@Override
	public void enter(IBuffContext context) {
		int shield = 0;
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		if(buffPrototype.getValueType() == ValueTypeEnum.Value_Num.value()){
			//数值
			shield = buffPrototype.getDamageAmountList().get(0);
		}else{
			//百分比
			shield = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Blood.value())
					* buffPrototype.getDamageAmountList().get(0) / 100.0f);
		}
		performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_Shield.value(), shield);
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Shield.value(), shield);
	}

	@Override
	public void exit(IBuffContext context) {
		int shield = 0;
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		if(buffPrototype.getValueType() == ValueTypeEnum.Value_Num.value()){
			//数值
			shield = buffPrototype.getDamageAmountList().get(0);
		}else{
			//百分比
			shield = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Blood.value())
					* buffPrototype.getDamageAmountList().get(0) / 100.0f);
		}
		performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_Shield.value(), -shield);
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Shield.value(), -shield);
	}

	@Override
	public void update(IBuffContext context) {
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		SClock clock = performer.getGlobalContext().getClock();
		long differTime = clock.differByCurrentTime(context.getStartTime());
		if(differTime >= buffPrototype.getEffectiveTime()){
			//效果时间到
			performer.getBuffManager().exit(context);
		}
	}

	@Override
	public boolean canTranstion(IStateContext target) {
		return true;
	}
}
