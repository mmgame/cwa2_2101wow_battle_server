package com.cwa.simuiationimpl.manage;

import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.component.event.ILocalEvent;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.event.ISEventHandlerManager;
import com.cwa.simuiation.manager.IEventMgr;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

public class EventMgr implements IEventMgr {
	protected static final Logger logger = LoggerFactory.getLogger(EventMgr.class);
	// 事件处理管理
	private ISEventHandlerManager eventHandlerManager;
	// 事件队列
	private Queue<ISEvent> eventQueue = new LinkedList<ISEvent>();

	@Override
	public void receiveEvent(ISEvent event) {
		eventQueue.add(event);
		// if (logger.isInfoEnabled()) {
		// logger.info("receiveEvent:" + event);
		// }
	}

	@Override
	public void processEvents() {
		for (;;) {
			ISEvent event = eventQueue.poll();
			if (event != null) {
				eventHandlerManager.handler(event);
				// if (logger.isInfoEnabled()) {
				// logger.info("processEvents:" + event);
				// }
			} else {
				break;
			}
		}
	}

	@Override
	public Object key() {
		return SimuiationConstant.ListenerKey_IEventMgr;
	}

	@Override
	public void answer(ILocalEvent event) {
		if (event instanceof ISEvent) {
			receiveEvent((ISEvent) event);
		}
	}

	// ------------------------------------------------
	public void setEventHandlerManager(ISEventHandlerManager eventHandlerManager) {
		this.eventHandlerManager = eventHandlerManager;
	}
}
