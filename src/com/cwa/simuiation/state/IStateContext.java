package com.cwa.simuiation.state;

import com.cwa.simuiation.obj.IPerformer;

/**
 * 状态上下文
 * 
 * @author mausmars
 * 
 */
public interface IStateContext {
	/**
	 * 状态接口
	 * 
	 * @return
	 */
	ISState getState();

	/**
	 * 重置上下文
	 */
	void resetContext();

	/**
	 * 拥有者
	 */
	IPerformer getPerformer();

	/**
	 * 是否锁（不知道作用）
	 * 
	 * @return
	 */
	boolean isBlock();

}
