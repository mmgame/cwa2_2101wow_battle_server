package com.cwa.simuiationimpl.action;

import java.util.Map;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.manager.IActionContextHandler;

/**
 * 处理MessageActionContext
 * 
 * @author mausmars
 * 
 */
public class MessageActionContextHandler implements IActionContextHandler {
	private Map<Integer, IActionContextHandler> actionContextHandlerMap;

	@Override
	public IActionContext handler(IActionContext ac) {
		MessageActionContext mac = (MessageActionContext) ac;

		int type = mac.getType();
		IActionContextHandler handler = actionContextHandlerMap.get(type);
		if (handler == null) {
			return null;
		}
		return handler.handler(mac);
	}

	// ----------------------------------------------------
	public void setActionContextHandlerMap(Map<Integer, IActionContextHandler> actionContextHandlerMap) {
		this.actionContextHandlerMap = actionContextHandlerMap;
	}
}
