package com.cwa.simuiationimpl.state.control;

import java.util.LinkedList;

import com.cwa.simuiation.action.IActionContext;

/**
 * 动作队列
 * 
 * @author mausmars
 */

public class ActionQueue {
	// 动作队列
	private LinkedList<IActionContext> requestQueue = new LinkedList<IActionContext>();

	public synchronized void requestAction(IActionContext context) {
		requestQueue.addLast(context);
	}

	public synchronized IActionContext poll() {
		return requestQueue.poll();
	}

	/**
	 * 清除全部请求队列
	 */
	public synchronized void clear() {
		requestQueue.clear();
	}

	/**
	 * 清除最后的请求
	 */
	public synchronized void clearLast() {
		requestQueue.pollLast();
	}

}
