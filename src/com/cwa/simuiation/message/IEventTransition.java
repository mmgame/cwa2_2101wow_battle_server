package com.cwa.simuiation.message;

import com.cwa.simuiation.event.ISEvent;

/**
 * event转换（转换到msg）
 * 
 * @author mausmars
 * 
 */
public interface IEventTransition {
	/**
	 * 转换
	 * 
	 * @param event
	 * @return
	 */
	Object transition(ISEvent event);
}
