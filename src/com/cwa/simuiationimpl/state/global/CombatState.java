package com.cwa.simuiationimpl.state.global;

import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;

/**
 * 战斗状态
 * 
 * @author mausmars
 * 
 */
public class CombatState implements ISState {

	@Override
	public int getType() {

		return 0;
	}

	@Override
	public int getSubType() {

		return 0;
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
