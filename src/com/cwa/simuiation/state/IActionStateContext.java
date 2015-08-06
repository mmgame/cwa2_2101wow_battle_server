package com.cwa.simuiation.state;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 动作状态上下文
 * 
 * @author mausmars
 * 
 */
public interface IActionStateContext extends IStateContext {
	/**
	 * 动作状态订阅
	 * 
	 * @param p
	 */
	void subscribe(IPerformer p);

	/**
	 * 动作状态上下文
	 * 
	 * @param p
	 */
	IActionContext getActionContext();
}
