package com.cwa.simuiation.state;

import com.cwa.simuiation.obj.IPerformer;

/**
 * 状态管理接口
 * 
 * @author mausmars
 * 
 */
public interface IStateManager {
	/**
	 * 拥有者
	 */
	IPerformer getPerformer();

	/**
	 * 状态更新
	 */
	void update();

	/**
	 * 是否能改变到指定状态
	 * 
	 * @param state
	 *            目标状态
	 * @return
	 */
	boolean canTranstion(IStateContext context);

	/**
	 * 转换状态
	 * 
	 * @param state
	 *            目标状态
	 * @return
	 */
	void transtion(IStateContext context);

	/**
	 * 获得当前状态
	 * 
	 * @param stateType
	 * @return
	 */
	IStateContext getCurrentState(int stateType);

	/**
	 * 得到默认上下文
	 * 
	 * @param subStateType
	 * @return
	 */
	IStateContext getDefaultContext(int subStateType);
}
