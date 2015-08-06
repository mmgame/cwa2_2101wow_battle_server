package com.cwa.simuiationimpl.action;

import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.ISAction;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.google.protobuf.GeneratedMessage;

/**
 * 消息动作（封装请求，避免多线程处理消息）
 * 
 * @author mausmars
 * 
 */
public class MessageActionContext implements IActionContext {
	private IClientSimuiation clientSimuiation;
	private GeneratedMessage message;
	private IPerformer performer;
	private int type;

	@Override
	public ISAction getAction() {
		return null;
	}

	@Override
	public SimuiationTime getStartTime() {
		return null;
	}

	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	@Override
	public ISObject getTarget() {
		return null;
	}

	@Override
	public void setOver(int interrupt) {
	}

	@Override
	public int getOver() {
		return 0;
	}

	@Override
	public void resetStartTime() {
	}

	@Override
	public void resetContextByFrame() {
	}

	@Override
	public void resetContext() {
	}

	@Override
	public ISStateEvent getEvent() {
		return null;
	}

	public GeneratedMessage getMessage() {
		return message;
	}

	public void setMessage(GeneratedMessage message) {
		this.message = message;
	}

	public void setPerformer(IPerformer performer) {
		this.performer = performer;
	}

	public IClientSimuiation getClientSimuiation() {
		return clientSimuiation;
	}

	public void setClientSimuiation(IClientSimuiation clientSimuiation) {
		this.clientSimuiation = clientSimuiation;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public boolean isTrigger() {
		return false;
	}
}
