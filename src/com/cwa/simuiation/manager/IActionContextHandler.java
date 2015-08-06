package com.cwa.simuiation.manager;

import com.cwa.simuiation.action.IActionContext;

/**
 * 动作上下文处理（把消息封装成上下文，为了不破坏流程，避免并发带来的问题）
 * 
 * @author mausmars
 * 
 */
public interface IActionContextHandler {
	IActionContext handler(IActionContext ac);
}
