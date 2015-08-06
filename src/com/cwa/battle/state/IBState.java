package com.cwa.battle.state;

import com.cwa.battle.IBattleGround;

/**
 * 战场状态
 * 
 * @author mausmars
 * 
 */
public interface IBState {
	/**
	 * 进入状态
	 */
	void enter(IBattleGround battleGround);

	/**
	 * 退出状态
	 */
	void exit(IBattleGround battleGround);

	/**
	 * 持续状态
	 */
	void update(IBattleGround battleGround);
	
	/**
	 * 类型
	 */
	int getType();
}
