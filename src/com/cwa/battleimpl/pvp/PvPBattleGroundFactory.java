package com.cwa.battleimpl.pvp;

import java.util.List;

import com.cwa.battle.state.IBState;
import com.cwa.battleimpl.ABattleGround;
import com.cwa.battleimpl.ABattleGroundFactory;
import com.cwa.battleimpl.SimuiationListener;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.battleimpl.state.StateManager;
import com.cwa.simuiation.event.IEventHandler;
import com.cwa.simuiationimpl.event.handler.SEventHandlerManager;

/**
 * pvp战场工厂
 * 
 * @author mausmars
 * 
 */
public class PvPBattleGroundFactory extends ABattleGroundFactory {
	private List<IEventHandler> eventHandlers;

	@Override
	public ABattleGround create(String id, int keyId) {

		PvPBattleGround battleGround = new PvPBattleGround(id, keyId);

		StateManager stateManager = new StateManager();
		stateManager.setBattleGround(battleGround);
		stateManager.setDefaultStateMap(defaultStateMap);

		// 设置站前为当前状态
		IBState currentState = defaultStateMap.get(BattleStateTypeEnum.SPrewar.value());
		stateManager.setCurrentState(currentState);

		// 设置状态机
		battleGround.setStateManager(stateManager);

		return battleGround;
	}

	@Override
	public SimuiationListener createSimuiationListener() {
		SimuiationListener simuiationListener = new SimuiationListener();
		SEventHandlerManager eventHandlerManager = new SEventHandlerManager();
		if (eventHandlers != null) {
			for (IEventHandler eventHandler : eventHandlers) {
				eventHandlerManager.insertEventHandler(eventHandler);
			}
		}
		simuiationListener.setEventHandlerManager(eventHandlerManager);
		return simuiationListener;
	}

	// ---------------------------------------------------
	public void setEventHandlers(List<IEventHandler> eventHandlers) {
		this.eventHandlers = eventHandlers;
	}
}
