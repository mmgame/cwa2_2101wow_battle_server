package com.cwa.simuiation;

import com.cwa.component.event.ILocalEvent;
import com.cwa.component.event.ILocalSubject;
import com.cwa.simuiation.action.IActionContext;

/**
 * 服务端仿真器
 * 
 * @author mausmars
 * 
 */
public interface IServerSimuiation extends ISimuiation, ILocalSubject {
	/**
	 * 接收请求
	 * 
	 * @param action
	 */
	void receiveActionRequest(IActionContext context);

	/**
	 * 发送事件
	 */
	void sendEvent(ILocalEvent event);

	/**
	 * 发送事件
	 */
	void sendEvent(Object listenerType, ILocalEvent event);
	/**
	 * 是否结束
	 * @return
	 */
	boolean isOver();
}
