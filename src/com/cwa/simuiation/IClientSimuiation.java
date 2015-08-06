package com.cwa.simuiation;

import com.cwa.ISession;
import com.cwa.component.netdelaycheck.INetDelayCheckServer;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.event.ISEvent;

/**
 * 客户端仿真器
 * 
 * @author mausmars
 * 
 */
public interface IClientSimuiation extends ISimuiation {
	/**
	 * 发送请求
	 * 
	 * @param action
	 */
	void sendActionRequest(IActionContext context);

	/**
	 * 接收事件
	 * 
	 * @param event
	 */
	void receiveSimuiationEvent(ISEvent event);

	// -------------------
	/**
	 * 开启日志
	 */
	void logOn();

	/**
	 * 关闭日志
	 */
	void logOff();

	// -------------------
	/**
	 * 绑定网络
	 * 
	 * @param session
	 */
	void bindSession(ISession session);

	INetDelayCheckServer getNetDelayCheckServer();
}
