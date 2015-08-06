package com.cwa.battle.event;

import serverice.proto.ProtoEvent;
import baseice.event.IEvent;

import com.cwa.component.event.IEventHandler;
import com.cwa.component.prototype.IPrototypeClientService;
import com.cwa.service.IService;
import com.cwa.service.constant.ServiceConstant;
import com.cwa.service.context.IGloabalContext;

public class ProtoEventEventHandler implements IEventHandler {
	private IGloabalContext gloabalContext;

	@Override
	public void eventHandler(IEvent event) {
		IService service = gloabalContext.getCurrentService(ServiceConstant.ProtoclientKey);
		if (service != null) {
			IPrototypeClientService s = (IPrototypeClientService) service;
			s.protoInform((ProtoEvent) event);
		}
	}

	// -------------------------------------
	public void setGloabalContext(IGloabalContext gloabalContext) {
		this.gloabalContext = gloabalContext;
	}
}
