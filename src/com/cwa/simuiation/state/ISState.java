package com.cwa.simuiation.state;

/**
 * 仿真状态（执行者的状态机）
 * 
 * @author mausmars
 * 
 */
public interface ISState {
	/**
	 * 状态type
	 * 
	 * @return
	 */
	int getType();

	/**
	 * 状态子type
	 * 
	 * @return
	 */
	int getSubType();

	/**
	 * 进入状态
	 */
	void enter(IStateContext context);

	/**
	 * 持续状态
	 */
	void update(IStateContext context);

	/**
	 * 退出状态
	 */
	void exit(IStateContext context);

	/**
	 * 锁定状态（状态不可变）
	 * 
	 * @param context
	 */
	void block(IStateContext context);

	/**
	 * 解锁状态
	 * 
	 * @param context
	 */
	void unBlock(IStateContext context);

	/**
	 * 是否锁定了状态
	 * 
	 * @param context
	 * @return
	 */
	boolean isBlock(IStateContext context);

	/**
	 * 是否能改变到指定状态
	 * 
	 * @param state
	 *            目标状态
	 * @return
	 */
	boolean canTranstion(IStateContext source, IStateContext target);
}
