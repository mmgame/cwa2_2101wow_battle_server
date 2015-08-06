package com.cwa.simuiation.event;

/**
 * 仿真器事件处理器管理
 * 
 * @author mausmars
 * 
 */
public interface ISEventHandlerManager {
	/**
	 * 处理事件
	 * 
	 * @param event
	 */
	void handler(ISEvent event);

	/**
	 * 添加处理器
	 * 
	 * @param event
	 */
	void insertEventHandler(IEventHandler eventHandler);

	/**
	 * 移除
	 * 
	 * @param event
	 */
	void removeEventHandler(int eventType);
}
