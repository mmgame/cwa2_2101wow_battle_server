package com.cwa.simuiation.event;

/**
 * 事件处理接口
 * 
 * @author mausmars
 * 
 */
public interface IEventHandler {
	/**
	 * 事件类型
	 * 
	 * @return
	 */
	int getEventSubType();

	/**
	 * 事件处理
	 * 
	 * @param event
	 */
	void handler(ISEvent event);
}
