package com.cwa.simuiation.state;

/**
 * 控制状态
 * 
 * @author mausmars
 * 
 */
public interface ISControlState extends ISState {
	/**
	 * 封装为行为状态
	 */
	IActionStateContext performAction(IControlStateContext context);
}
