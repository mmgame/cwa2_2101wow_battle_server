package com.cwa.simuiation.manager;

import com.cwa.component.prototype.IPrototype;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;

/**
 * 动作上下文工厂
 * 
 * @author mausmars
 * 
 */
public interface IActionContextFactory {
	/**
	 * 创建移动上下文
	 * 
	 * @param actionType
	 * @param p
	 * @param params
	 * @return
	 */
	IActionContext createMoveContext(IPerformer performer, ISObject target);

	/**
	 * 创建施法上下文
	 * 
	 * @param actionType
	 * @param p
	 * @param params
	 * @return
	 */
	IActionContext createMagicContext(IPerformer performer, ISObject target, IPrototype prototype);
	
	/**
	 * 创建静止上下文
	 * 
	 * @param actionType
	 * @param p
	 * @param params
	 * @return
	 */
	IActionContext createIdleContext(IPerformer performer);
}
