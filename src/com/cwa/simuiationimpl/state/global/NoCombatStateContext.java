package com.cwa.simuiationimpl.state.global;

import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;

public class NoCombatStateContext implements IStateContext {
	private ISState state;
	
	@Override
	public ISState getState() {

		return state;
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

	public void setState(ISState state) {
		this.state = state;
	}

}
