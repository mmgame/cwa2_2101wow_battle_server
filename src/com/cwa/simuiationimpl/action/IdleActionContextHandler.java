package com.cwa.simuiationimpl.action;

import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 施法动作上下文处理
 * 
 * @author mausmars
 * 
 */
public class IdleActionContextHandler implements IActionContextHandler {
	@Override
	public IActionContext handler(IActionContext ac) {
		MessageActionContext mac = (MessageActionContext) ac;

		IPerformer source = mac.getPerformer();
		IClientSimuiation clientSimuiation = mac.getClientSimuiation();

		if (source.getControler() != clientSimuiation) {
			// 控制者不是该用户
			return null;
		}
		IActionContext context = source.getGlobalContext().getActionContextFactory().createIdleContext(source);
		return context;
	}

}
