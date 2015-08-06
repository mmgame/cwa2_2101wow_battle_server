package com.cwa.simuiationimpl.state.global;

import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;

public class DeadStateContext implements IStateContext {
	private ISState state;
	private IPerformer performer;
	@Override
	public ISState getState() {
		return state;
	}

	@Override
	public void resetContext() {

	}

	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	public void setPerformer(IPerformer performer){
		this.performer = performer;
	}
	@Override
	public boolean isBlock() {

		return false;
	}

	public void setState(ISState state){
		this.state = state;
	}
}
