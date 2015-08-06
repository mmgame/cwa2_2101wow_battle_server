package com.cwa.simuiationimpl.state.action;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiationimpl.action.MagicActionContext;

/**
 * 施法状态上下文
 * 
 * @author yangfeng
 * 
 */
public class MagicStateContext implements IActionStateContext {
	private ISState state;
	// 动作上下文
	private MagicActionContext actionContext;

	@Override
	public ISState getState() {
		return state;
	}

	@Override
	public void resetContext() {

	}

	@Override
	public IPerformer getPerformer() {
		return actionContext.getPerformer();
	}

	@Override
	public boolean isBlock() {
		return false;
	}

	@Override
	public void subscribe(IPerformer p) {

	}

	@Override
	public IActionContext getActionContext() {
		return actionContext;
	}

	public void setState(ISState state) {
		this.state = state;
	}

	public void setActionContext(IActionContext actionContext) {
		this.actionContext = (MagicActionContext) actionContext;
	}
}
