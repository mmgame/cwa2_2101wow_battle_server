package com.cwa.simuiationimpl.state.control;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IControlStateContext;
import com.cwa.simuiation.state.ISState;

/**
 * 控制状态上下文
 */

public class UserControlStateContext implements IControlStateContext {
	// 状态接口
	private ISState state;
	// 拥有者
	private IPerformer performer;
	// -----------------------------------
	// 动作队列
	private ActionQueue actionQueue;

	public UserControlStateContext(ISState state, IPerformer performer) {
		actionQueue = new ActionQueue();
		this.state = state;
		this.performer = performer;
	}

	@Override
	public ISState getState() {
		return state;
	}

	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	@Override
	public IActionContext performAction() {
		return actionQueue.poll();
	}

	@Override
	public void requestAction(IActionContext context) {
		actionQueue.requestAction(context);
	}

	@Override
	public void resetContext() {
		actionQueue.clear();
	}

	@Override
	public boolean isBlock() {
		return false;
	}
}
