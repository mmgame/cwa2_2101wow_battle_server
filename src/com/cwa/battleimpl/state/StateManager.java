package com.cwa.battleimpl.state;

import java.util.Map;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.state.IBState;
import com.cwa.battle.state.IStateManager;

/**
 * 状态机
 * 
 * @author mausmars
 * 
 */
public class StateManager implements IStateManager {
	private Map<Integer, IBState> defaultStateMap;
	// 战场
	protected IBattleGround battleGround;
	// 战场状态
	protected IBState currentState;

	// 改变状态
	public boolean changeState(int state) {
		IBState nextState = defaultStateMap.get(state);
		if (nextState == null) {
			return false;
		}
		currentState.exit(battleGround);
		currentState = nextState;
		currentState.enter(battleGround);
		return true;
	}

	public IBattleGround getBattleGround() {
		return battleGround;
	}

	public IBState getCurrentState() {
		return currentState;
	}

	// ------------------------------

	public void setBattleGround(IBattleGround battleGround) {
		this.battleGround = battleGround;
	}

	public void setDefaultStateMap(Map<Integer, IBState> defaultStateMap) {
		this.defaultStateMap = defaultStateMap;
	}

	public void setCurrentState(IBState currentState) {
		this.currentState = currentState;
	}
}
