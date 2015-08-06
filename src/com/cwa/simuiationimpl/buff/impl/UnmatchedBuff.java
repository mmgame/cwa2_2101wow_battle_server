package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
/**
 * 无敌
 * @author yangfeng
 *
 */
public class UnmatchedBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Unmatched.value();
	}

	@Override
	public void enter(IBuffContext context) {
		//修改伤害倍率
		context.getPerformer().getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Invincible.value(), -1);
	}

	@Override
	public void exit(IBuffContext context) {
		//修改伤害倍率
		context.getPerformer().getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Invincible.value(), 1);
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
