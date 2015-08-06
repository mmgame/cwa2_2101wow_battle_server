package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.state.IStateContext;
/**
 * 诅咒
 * @author yangfeng
 *
 */
public class CurseBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Curse.value();
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canTranstion(IStateContext target) {
		return true;
	}
}
