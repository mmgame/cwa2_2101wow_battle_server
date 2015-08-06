package com.cwa.battle.filter;

import com.cwa.battle.IBattleGround;
import com.cwa.battleimpl.event.BattleStateChangeEvent;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.battleimpl.task.StateChangeTask;
import com.cwa.component.task.ITaskTypeConfig;
import com.cwa.util.TimeUtil;


/**
 * 抽象战场filter（每个战场fitler都继承这个类）
 * 
 * @author mausmars
 * 
 */
public abstract class ABattleFilter extends AbstractFilter {
	public ABattleFilter(String name) {
		super(name);
	}

	protected void stateChangeTask(IBattleGround battleGround, BattleStateTypeEnum battleStateType, int intervalTime) {
		long startTime = TimeUtil.currentSystemTime() + intervalTime;
		ITaskTypeConfig taskType = battleGround.getBattleContext().getScheduleMgr().getTaskTypeConfigFactory()
				.createSimpleTypeConfig(startTime, 0, 0, 0);
		StateChangeTask task = new StateChangeTask(battleGround.getId());
		task.setBattleStateType(battleStateType);
		task.setStateManager(battleGround.getStateManager());
		battleGround.getBattleContext().getScheduleMgr().scheduleTask(task, taskType);
	}

	// 发送战场状态改变
	protected void sendStateChangeEvent(IBattleGround battleGround, BattleStateTypeEnum battleStateType) {
		BattleStateChangeEvent event = BattleStateChangeEvent.getBattleStateChangeEvent(battleStateType.value());
		battleGround.getSimuiation().getClientMgr().sendEvent(event);
	}
}
