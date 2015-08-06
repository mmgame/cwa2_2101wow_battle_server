package com.cwa.simuiation.manager;

import com.cwa.component.event.ILocalEventListener;
import com.cwa.simuiation.event.ISEvent;

/**
 * 事件管理接口
 * 
 * @author mausmars
 * 
 */
public interface IEventMgr extends ILocalEventListener {
	/**
	 * 接收事件
	 */
	void receiveEvent(ISEvent event);

	/**
	 * 处理事件
	 */
	void processEvents();
}
