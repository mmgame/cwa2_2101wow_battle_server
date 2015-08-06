package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.BuffPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
/**
 * 吸血
 * @author yangfeng
 *
 */
public class SuckBloodBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_SuckBlood.value();
	}

	@Override
	public void enter(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		BuffPrototype buffPrototype = context.getBuffPrototype();
		int value = buffPrototype.getDamageAmountList().get(0);
		//修改伤害倍率
		performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_SuckBlood.value(), value);
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_SuckBlood.value(), value);
	}

	@Override
	public void exit(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		BuffPrototype buffPrototype = context.getBuffPrototype();
		int value = buffPrototype.getDamageAmountList().get(0);
		//修改伤害倍率
		performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_SuckBlood.value(), -value);
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_SuckBlood.value(), -value);
	}

	@Override
	public void update(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		//效果时间到
		performer.getBuffManager().exit(context);
	}

	@Override
	public boolean canTranstion(IStateContext target) {
		return true;
	}
}
