package com.cwa.simuiationimpl.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.component.event.ILocalEvent;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.IPlayer;
import com.cwa.simuiation.ISimuiation;
import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.manager.IClientMgr;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 客户端链接管理
 * 
 * @author mausmars
 * 
 */
public class ClientMgr implements IClientMgr {
	protected static final Logger logger = LoggerFactory.getLogger(ClientMgr.class);

	// 客户端仿真{客户端仿真key：客户端仿真}
	private Map<String, IPlayer> clientSimuiationMap = new HashMap<String, IPlayer>();

	@Override
	public void join(ISimuiation simuiation) {
		if (!(simuiation instanceof IPlayer)) {
			if (logger.isWarnEnabled()) {
				logger.warn("ASimuiation isn't ClientSimuiation! key=" + simuiation.getId());
			}
			return;
		}
		IPlayer cs = (IPlayer) simuiation;
		clientSimuiationMap.put(cs.getId(), cs);
	}

	@Override
	public void leave(String controller) {
		clientSimuiationMap.remove(controller);
	}

	@Override
	public void sendEvent(ISEvent event) {
		if (event.getChannel() == EventChannelEnum.Appoint.value()) {
			// 发送给指定人
			List<String> receivers = event.getReceivers();
			for (String receiver : receivers) {
				IClientSimuiation cs = clientSimuiationMap.get(receiver);
				if (cs != null) {
					cs.receiveSimuiationEvent(event);
				}
			}
		} else if (event.getChannel() == EventChannelEnum.Whole.value()) {
			// 发给全体
			for (IClientSimuiation cs : clientSimuiationMap.values()) {
				cs.receiveSimuiationEvent(event);
			}
		}
	}

	@Override
	public IClientSimuiation getClientSimuiation(String id) {
		return clientSimuiationMap.get(id);
	}

	@Override
	public Object key() {
		return SimuiationConstant.ListenerKey_ClientMgr;
	}

	@Override
	public void answer(ILocalEvent event) {
		if (event instanceof ISEvent) {
			sendEvent((ISEvent) event);
		}
	}
}
