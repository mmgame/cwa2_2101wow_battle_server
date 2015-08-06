package com.cwa.battleimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.component.event.ILocalEvent;
import com.cwa.component.event.ILocalEventListener;
import com.cwa.simuiation.event.IEventHandler;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.event.ISEventHandlerManager;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 仿真器监听者
 * 
 * @author mausmars
 * 
 */
public class SimuiationListener implements ILocalEventListener, ISEventHandlerManager {
	protected static final Logger logger = LoggerFactory.getLogger(SimuiationListener.class);

	// 事件处理管理
	private ISEventHandlerManager eventHandlerManager;

	@Override
	public void handler(ISEvent event) {
		eventHandlerManager.handler(event);
	}

	@Override
	public void insertEventHandler(IEventHandler eventHandler) {
		eventHandlerManager.insertEventHandler(eventHandler);
	}

	@Override
	public void removeEventHandler(int eventType) {
		eventHandlerManager.removeEventHandler(eventType);
	}

	@Override
	public Object key() {
		return SimuiationConstant.ListenerKey_System;
	}

	@Override
	public void answer(ILocalEvent event) {
		if (event instanceof ISEvent) {
			eventHandlerManager.handler((ISEvent) event);
		}
	}

	// --------------------------------------------------
	public void setEventHandlerManager(ISEventHandlerManager eventHandlerManager) {
		this.eventHandlerManager = eventHandlerManager;
	}
}
