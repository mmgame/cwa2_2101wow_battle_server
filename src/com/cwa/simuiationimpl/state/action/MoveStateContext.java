package com.cwa.simuiationimpl.state.action;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.ISState;

public class MoveStateContext implements IActionStateContext {
	private ISState state;
	// 动作上下文
	private IActionContext actionContext;

	// ----------------------------------------
	@Override
	public ISState getState() {
		return state;
	}

	@Override
	public IPerformer getPerformer() {
		return actionContext.getPerformer();
	}

	@Override
	public void subscribe(IPerformer p) {
	}

	@Override
	public IActionContext getActionContext() {
		return actionContext;
	}

	@Override
	public void resetContext() {
	}

	@Override
	public boolean isBlock() {
		return false;
	}

	// ----------------------------------------
	public void setState(ISState state) {
		this.state = state;
	}

	public void setActionContext(IActionContext actionContext) {
		this.actionContext = actionContext;
	}
}
