package com.cwa.battleimpl.task;

import com.cwa.battle.state.IStateManager;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.component.task.ITask;
import com.cwa.component.task.ITaskContext;

/**
 * 状态改变任务
 * 
 * @author mausmars
 * 
 */
public class StateChangeTask implements ITask {
	// 战场id
	private String battleId;

	// 战场状态改变
	private IStateManager stateManager;
	// 改变的状态
	private BattleStateTypeEnum battleStateType;

	public StateChangeTask(String battleId) {
		this.battleId = battleId;
	}

	@Override
	public String id() {
		return battleId + battleStateType.value();
	}

	@Override
	public void execute(ITaskContext context) {
		// 状态改变
		stateManager.changeState(battleStateType.value());
	}

	// ----------------------------------

	public void setBattleStateType(BattleStateTypeEnum battleStateType) {
		this.battleStateType = battleStateType;
	}

	public void setStateManager(IStateManager stateManager) {
		this.stateManager = stateManager;
	}
}
