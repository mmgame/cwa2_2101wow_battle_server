package com.cwa.simuiation.event;

import java.util.List;

import com.cwa.component.event.ILocalEvent;

/**
 * 仿真器事件
 * 
 * @author mausmars
 * 
 */
public interface ISEvent extends ILocalEvent {
	/**
	 * 事件类型
	 * 
	 * @return
	 */
	int getEventType();

	/**
	 * 事件子类型
	 * 
	 * @return
	 */
	int getEventSubType();

	/**
	 * 频道
	 * 
	 * @return
	 */
	int getChannel();
	void setChannel(int channel);

	/**
	 * 接收人
	 * 
	 * @return
	 */
	List<String> getReceivers();
	void setReceivers(List<String> receivers);
}
