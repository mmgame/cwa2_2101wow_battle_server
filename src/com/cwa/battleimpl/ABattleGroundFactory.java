package com.cwa.battleimpl;

import java.util.Map;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.IBattleGroundFactory;
import com.cwa.battle.state.IBState;
import com.cwa.component.task.ITaskManager;
import com.cwa.component.task.quartz.config.ITaskTypeConfigFactory;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.manager.IActionContextFactory;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.manager.IBuffContextFactory;
import com.cwa.simuiation.manager.ITrapManager;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiationimpl.ServerSimuiation;
import com.cwa.simuiationimpl.event.handler.SEventHandlerManager;
import com.cwa.simuiationimpl.manage.ClientMgr;
import com.cwa.simuiationimpl.manage.DistanceMgr2D;
import com.cwa.simuiationimpl.manage.EventMgr;
import com.cwa.simuiationimpl.manage.ObjFactory;
import com.cwa.simuiationimpl.manage.ProtpyeMgr;
import com.cwa.simuiationimpl.manage.ScheduleMgr;
import com.cwa.simuiationimpl.manage.SobMgr;
import com.cwa.simuiationimpl.manage.StateContextFactory;
import com.cwa.simuiationimpl.map.AreaMgr;
import com.cwa.simuiationimpl.state.action.IdleState;
import com.cwa.simuiationimpl.state.action.MagicState;
import com.cwa.simuiationimpl.state.action.MoveState;
import com.cwa.simuiationimpl.state.control.UserControlState;
import com.cwa.simuiationimpl.state.global.DeadState;
import com.cwa.simuiationimpl.state.global.NoCombatState;

/**
 * 战场抽象工厂
 * 
 * @author mausmars
 * 
 */
public abstract class ABattleGroundFactory implements IBattleGroundFactory {
	/**
	 * 战场默认状态机
	 */
	protected Map<Integer, IBState> defaultStateMap;
	/**
	 * 任务
	 */
	protected ITaskManager taskManager;
	protected ITaskTypeConfigFactory taskTypeConfigFactory;

	protected SEventHandlerManager eventHandlerManager;
	protected IActionContextFactory actionContextFactory;
	protected IActionContextHandler actionContextHandler;
	protected IEventTransition eventTransition;
	protected ITrapManager trapManager;
	protected IBuffContextFactory buffContextFactory;

	/**
	 * 创建战场
	 */
	public IBattleGround createBattle(String id, int keyId) {
		SimuiationListener simuiationListener=createSimuiationListener();
		// 创建仿真器
		ServerSimuiation simuiation = new ServerSimuiation(id);
		ABattleGround battleGround = create(id, keyId);
		SClock clock = new SClock();
		SobMgr sobMgr = new SobMgr();
		AreaMgr areaMgr = new AreaMgr();
		ScheduleMgr scheduleMgr = new ScheduleMgr();
		ClientMgr clientMgr = new ClientMgr();
		ProtpyeMgr protpyeMgr = new ProtpyeMgr();
		
		// 事件管理
		EventMgr eventMgr = new EventMgr();
		eventMgr.setEventHandlerManager(eventHandlerManager);

		StateContextFactory stateContextFactory = initStateContextFactory();
		ObjFactory objFactory = new ObjFactory();
		objFactory.setContext(simuiation);
		objFactory.setBuffContextFactory(buffContextFactory);
		objFactory.setTrapManager(trapManager);
		DistanceMgr2D distanceMgr = new DistanceMgr2D();
		// 设置创建仿真器
		simuiation.setPrototypeMgr(protpyeMgr);
		simuiation.setClock(clock);
		simuiation.setAreaMgr(areaMgr);
		simuiation.setSobMgr(sobMgr);
		simuiation.setScheduleMgr(scheduleMgr);
		simuiation.setBuffContextFactory(buffContextFactory);
		simuiation.setEventHandlerManager(eventHandlerManager);
		simuiation.setStateContextFactory(stateContextFactory);
		simuiation.setObjFactory(objFactory);
		simuiation.setClientMgr(clientMgr);
		simuiation.setActionContextHandler(actionContextHandler);
		simuiation.setActionContextFactory(actionContextFactory);
		simuiation.setEventMgr(eventMgr);
		simuiation.setEventTransition(eventTransition);
		simuiation.setDistanceMgr(distanceMgr);

		simuiation.register(eventMgr);
		simuiation.register(clientMgr);
		simuiation.register(simuiationListener);
		
		battleGround.setTaskTypeConfigFactory(taskTypeConfigFactory);
		battleGround.setTaskManager(taskManager);
		battleGround.setEventHandlerManager(simuiationListener);
		// 注入仿真器
		battleGround.setSimuiation(simuiation);
		// 链接监听
		return battleGround;
	}

	
	private StateContextFactory initStateContextFactory() {
		NoCombatState noCombatState = new NoCombatState();
		UserControlState userControlState = new UserControlState();
		IdleState idleState = new IdleState();
		MoveState moveState = new MoveState();
		MagicState magicState = new MagicState();
		DeadState deadState = new DeadState();

		StateContextFactory stateContextFactory = new StateContextFactory();
		stateContextFactory.initStateMap(moveState);
		stateContextFactory.initStateMap(noCombatState);
		stateContextFactory.initStateMap(userControlState);
		stateContextFactory.initStateMap(idleState);
		stateContextFactory.initStateMap(magicState);
		stateContextFactory.initStateMap(deadState);
		return stateContextFactory;
	}

	public abstract SimuiationListener createSimuiationListener();
	// ------------------------------------
	public abstract ABattleGround create(String id, int keyId);

	// ------------------------------------
	public void setDefaultStateMap(Map<Integer, IBState> defaultStateMap) {
		this.defaultStateMap = defaultStateMap;
	}

	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public void setTaskTypeConfigFactory(ITaskTypeConfigFactory taskTypeConfigFactory) {
		this.taskTypeConfigFactory = taskTypeConfigFactory;
	}

	public void setEventHandlerManager(SEventHandlerManager eventHandlerManager) {
		this.eventHandlerManager = eventHandlerManager;
	}

	public void setActionContextFactory(IActionContextFactory actionContextFactory) {
		this.actionContextFactory = actionContextFactory;
	}

	public void setActionContextHandler(IActionContextHandler actionContextHandler) {
		this.actionContextHandler = actionContextHandler;
	}

	public void setEventTransition(IEventTransition eventTransition) {
		this.eventTransition = eventTransition;
	}


	public void setTrapManager(ITrapManager trapManager) {
		this.trapManager = trapManager;
	}


	public void setBuffContextFactory(IBuffContextFactory buffContextFactory) {
		this.buffContextFactory = buffContextFactory;
	}
}
