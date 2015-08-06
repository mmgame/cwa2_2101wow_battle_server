package com.cwa.simuiationimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.component.netdelaycheck.INetDelayCheckServer;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.IServerSimuiation;
import com.cwa.simuiation.ISimuiation;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.event.ISEventHandlerManager;
import com.cwa.simuiation.manager.IActionContextFactory;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.manager.IAreaMgr;
import com.cwa.simuiation.manager.IBuffContextFactory;
import com.cwa.simuiation.manager.IClientMgr;
import com.cwa.simuiation.manager.IDistanceMgr;
import com.cwa.simuiation.manager.IEventMgr;
import com.cwa.simuiation.manager.IObjFactory;
import com.cwa.simuiation.manager.IPrototypeMgr;
import com.cwa.simuiation.manager.IScheduleMgr;
import com.cwa.simuiation.manager.ISobMgr;
import com.cwa.simuiation.manager.IStateContextFactory;
import com.cwa.simuiation.message.IEventTransition;

/**
 * 客户端仿真，相当于每个玩家的链接
 * 
 * @author mausmars
 * 
 */
public class ClientSimuiation implements IClientSimuiation {
	protected static final Logger logger = LoggerFactory.getLogger(ClientSimuiation.class);

	// 仿真器key（代表用户链接）
	protected String id;
	// session
	protected ISession session;
	// ---------------------------------
	// 服务器端仿真
	protected IServerSimuiation simuiation;

	// 服务端
	protected INetDelayCheckServer netDelayCheckServer;

	// ---------------------------------
	public ClientSimuiation(String id) {
		this.id = id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void bindSession(ISession session) {
		this.session = session;
	}

	@Override
	public void sendActionRequest(IActionContext context) {
		simuiation.receiveActionRequest(context);
	}

	@Override
	public void receiveSimuiationEvent(ISEvent event) {
//		if (simuiation.isOver() && event.getEventSubType() != EventSubTypeEnum.ES_SOver.value()) {
//			return;
//		}
		// 发送事件
		Object msg = simuiation.getEventTransition().transition(event);
		if (msg != null && session != null) {
			session.send(msg);
		}
	}

	@Override
	public void attach(ISimuiation aSimuiation) {
		// 客户端和某个服务仿真器链接
		if (!(aSimuiation instanceof IServerSimuiation)) {
			if (logger.isWarnEnabled()) {
				logger.warn("ASimuiation isn't IServerSimuiation! key=" + aSimuiation.getId());
			}
			return;
		}
		simuiation = (IServerSimuiation) aSimuiation;
	}

	@Override
	public void detach(String key) {
		// 断开服务仿真
		simuiation.detach(this.id);
		simuiation = null;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ISobMgr getSobMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getSobMgr();
	}

	@Override
	public IAreaMgr getAreaMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getAreaMgr();
	}

	@Override
	public IPrototypeMgr getProtpyeMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getProtpyeMgr();
	}

	@Override
	public IScheduleMgr getScheduleMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getScheduleMgr();
	}

	@Override
	public IStateContextFactory getStateContextFactory() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getStateContextFactory();
	}

	@Override
	public IBuffContextFactory getBuffContextFactory() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getBuffContextFactory();
	}

	@Override
	public ISEventHandlerManager getSEventHandlerManager() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getSEventHandlerManager();
	}

	@Override
	public IObjFactory getObjFactory() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getObjFactory();
	}

	@Override
	public IClientMgr getClientMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getClientMgr();
	}

	@Override
	public IEventMgr getEventMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getEventMgr();
	}

	@Override
	public IActionContextFactory getActionContextFactory() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getActionContextFactory();
	}

	@Override
	public IActionContextHandler getMessageActionContextHandler() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getMessageActionContextHandler();
	}

	@Override
	public IEventTransition getEventTransition() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getEventTransition();
	}

	@Override
	public SClock getClock() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getClock();
	}

	@Override
	public IDistanceMgr getDistanceMgr() {
		if (simuiation == null) {
			return null;
		}
		return simuiation.getDistanceMgr();
	}

	@Override
	public void logOn() {
	}

	@Override
	public void logOff() {

	}

	@Override
	public void startup() {

	}

	@Override
	public void shutdown() {

	}

	@Override
	public int tick() {
		return 0;
	}

	@Override
	public INetDelayCheckServer getNetDelayCheckServer() {
		return netDelayCheckServer;
	}

	public void setNetDelayCheckServer(INetDelayCheckServer netDelayCheckServer) {
		this.netDelayCheckServer = netDelayCheckServer;
	}
}
