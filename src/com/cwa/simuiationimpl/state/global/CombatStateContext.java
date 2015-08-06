package com.cwa.simuiationimpl.state.global;

import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;

public class CombatStateContext implements IStateContext {

	@Override
	public ISState getState() {

		return null;
	}

	@Override
	public void resetContext() {

	}

	@Override
	public IPerformer getPerformer() {

		return null;
	}

	@Override
	public boolean isBlock() {

		return false;
	}

}
