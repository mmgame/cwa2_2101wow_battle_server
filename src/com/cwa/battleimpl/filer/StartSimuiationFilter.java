package com.cwa.battleimpl.filer;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.battleimpl.event.ESDeadEventHandler;
import com.cwa.battleimpl.event.ESTimeOverEventHandler;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.simuiation.event.ISEventHandlerManager;

public class StartSimuiationFilter extends ABattleFilter {
	// 战后等待filter
	private static EnterSPostwarPTaskFilter battleFilter = new EnterSPostwarPTaskFilter();

	public StartSimuiationFilter() {
		super("StartSimuiationFilter");
	}

	@Override
	public boolean doWork(final Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("StartSimuiationFilter doWork!");
		}
		final IBattleGround battleGround = (IBattleGround) context;

		// 通知客户端战场开始
		sendStateChangeEvent(battleGround, BattleStateTypeEnum.SFighting);

		// 开始仿真器
		battleGround.startupSimuiation();
		// 设置仿真器结束回调
		setEventHandler(battleGround);
		return true;
	}

	private void setEventHandler(final IBattleGround battleGround) {
		ISEventHandlerManager eventHandlerManager = battleGround.getSEventHandlerManager();

		ESTimeOverEventHandler timeOverEventHandler = new ESTimeOverEventHandler();
		timeOverEventHandler.setBattleFilter(battleFilter);
		timeOverEventHandler.setBattleGround(battleGround);
		eventHandlerManager.insertEventHandler(timeOverEventHandler);

		ESDeadEventHandler deadEventHandler = new ESDeadEventHandler();
		deadEventHandler.setTimeOverEventHandler(timeOverEventHandler);
		eventHandlerManager.insertEventHandler(deadEventHandler);
	}
}
