package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
/**
 * 仇恨
 * @author yangfeng
 *
 */
public class HatredBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Hatred.value();
	}

	@Override
	public void enter(IBuffContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(IBuffContext context) {
		// TODO Auto-generated method stub
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
