package com.cwa.simuiation.buff;

import com.cwa.simuiation.state.IStateContext;

public interface ISBuff {
	/**
	 * 得到buff类型
	 * 
	 * @return
	 */
	int getType();

	/**
	 * 进入buff
	 * 
	 * @param performer
	 */
	void enter(IBuffContext context);

	/**
	 * 退出buff
	 * 
	 * @param performer
	 */
	void exit(IBuffContext context);

	/**
	 * buff持续
	 * 
	 * @param performer
	 */
	void update(IBuffContext context);

	/**
	 * 是否能改变到指定状态
	 * 
	 * @param state
	 *            目标状态
	 * @return
	 */
	boolean canTranstion(IStateContext target);
}
