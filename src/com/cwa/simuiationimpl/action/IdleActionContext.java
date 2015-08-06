package com.cwa.simuiationimpl.action;

import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiationimpl.event.ESIdleEvent;

/**
 * 移动动作上下文
 * 
 * @author mausmars
 * 
 */
public class IdleActionContext extends AActionContext {
	@Override
	public ISStateEvent getEvent() {
		ESIdleEvent event = new ESIdleEvent();
		event.setActionContext(this);
		return event;
	}

	@Override
	public void resetContext() {

	}

	@Override
	public boolean isTrigger() {
		return true;
	}

}
