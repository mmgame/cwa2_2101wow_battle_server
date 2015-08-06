package com.cwa.simuiation.action;

import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 技能上下文
 * 
 * @author mausmars
 * 
 */
public interface IActionContext {
	/**
	 * 获得行为接口
	 * 
	 * @return
	 */
	ISAction getAction();

	/**
	 * 开始时间
	 * 
	 * @return
	 */
	SimuiationTime getStartTime();

	/**
	 * 拥有者
	 */
	IPerformer getPerformer();

	/**
	 * 目标
	 */
	ISObject getTarget();

	/**
	 * 是否结束
	 * 
	 * @return
	 */
	int getOver();

	/**
	 * 结束
	 * 
	 * @param interrupt
	 *            中断类型 3正常退出 4中断
	 */
	void setOver(int interrupt);

	/**
	 * 重置开始时间
	 */
	void resetStartTime();

	/**
	 * 重置上下文
	 */
	void resetContext();

	/**
	 * 每帧重置上下文
	 */
	void resetContextByFrame();

	/**
	 * 获取动作事件
	 */
	ISStateEvent getEvent();

	/**
	 * 是否触发
	 * 
	 * @return
	 */
	boolean isTrigger();
}
