package com.cwa.simuiation.state;

import com.cwa.simuiation.action.IActionContext;

/**
 * 控制状态上下文
 * 
 * @author mausmars
 * 
 */
public interface IControlStateContext extends IStateContext {
	/**
	 * 返回action
	 * 
	 * @return
	 */
	IActionContext performAction();

	/**
	 * 请求action
	 */
	void requestAction(IActionContext context);
}
