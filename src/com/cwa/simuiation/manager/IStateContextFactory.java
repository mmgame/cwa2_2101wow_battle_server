package com.cwa.simuiation.manager;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;

/**
 * 状态上下文工厂
 * 
 * @author mausmars
 * 
 */
public interface IStateContextFactory {
	// 动作状态
	IStateContext createASContext(IActionContext ac);

	// 全局状态
	IStateContext createGSContext(IPerformer p,int stateSubType);

	// 控制状态
	IStateContext createCSContext(int stateSubType, IPerformer p);
}
