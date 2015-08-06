package com.cwa.simuiationimpl.state.global;

import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 非战斗状态
 * 
 * @author mausmars
 * 
 */
public class NoCombatState implements ISState {

	@Override
	public int getType() {

		return StateSubTypeEnum.GS_NoCombat.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getSubType() {

		return StateSubTypeEnum.GS_NoCombat.value();
	}

	@Override
	public void enter(IStateContext context) {

	}

	@Override
	public void update(IStateContext context) {

	}

	@Override
	public void exit(IStateContext context) {

	}

	@Override
	public void block(IStateContext context) {

	}

	@Override
	public void unBlock(IStateContext context) {

	}

	@Override
	public boolean isBlock(IStateContext context) {
		return false;
	}

	@Override
	public boolean canTranstion(IStateContext source, IStateContext context) {

		return true;
	}
}
