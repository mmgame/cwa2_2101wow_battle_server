package com.cwa.battle.state;

import com.cwa.battle.IBattleGround;

/**
 * 状态管理
 * 
 * @author mausmars
 * 
 */
public interface IStateManager {
	/**
	 * 改变状态
	 * 
	 * @param state
	 * @return
	 */
	boolean changeState(int state);

	/**
	 * 对应战场
	 * 
	 * @return
	 */
	IBattleGround getBattleGround();

	/**
	 * 当前状态
	 * 
	 * @return
	 */
	IBState getCurrentState();
}
