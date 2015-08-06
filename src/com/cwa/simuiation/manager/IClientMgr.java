package com.cwa.simuiation.manager;

import com.cwa.component.event.ILocalEventListener;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.ISimuiation;
import com.cwa.simuiation.event.ISEvent;

/**
 * 客户端管理
 * 
 * @author mausmars
 * 
 */
public interface IClientMgr extends ILocalEventListener {
	/**
	 * 移除仿真
	 * 
	 * @param aSimuiation
	 */
	void leave(String controller);

	/**
	 * 加入客户端链接
	 * 
	 * @param aSimuiation
	 * @param actorIds
	 *            为null为游客
	 */
	void join(ISimuiation simuiation);

	/**
	 * 发送事件
	 * 
	 * @param event
	 */
	void sendEvent(ISEvent event);

	/**
	 * 获取客户端仿真
	 * 
	 * @param uid
	 * @return
	 */
	IClientSimuiation getClientSimuiation(String id);

}
