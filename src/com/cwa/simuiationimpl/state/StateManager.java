package com.cwa.simuiationimpl.state;

import java.util.HashMap;
import java.util.Map;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.manager.IStateContextFactory;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.state.IStateManager;

/**
 * 状态管理
 * 
 * @author mausmars
 * 
 */
public class StateManager implements IStateManager {
	// 拥有者
	protected IPerformer performer;

	// 默认状态 {状态子类型:IStateContext}
	private Map<Integer, IStateContext> defaultContextMap = new HashMap<Integer, IStateContext>();
	// {状态类型:IStateContext}
	private Map<Integer, IStateContext> currentStateMap = new HashMap<Integer, IStateContext>();

	public StateManager(IPerformer performer) {
		this.performer = performer;
		initCurrentState();
	}

	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	@Override
	public void update() {
		for (StateTypeEnum stateType : StateTypeEnum.values()) {
			IStateContext context = currentStateMap.get(stateType.value());
			if (context == null) {
				continue;
			}
			// 按顺序作更新
			context.getState().update(context);
		}
	}

	@Override
	public boolean canTranstion(IStateContext context) {
		for (StateTypeEnum stateType : StateTypeEnum.values()) {
			IStateContext c = currentStateMap.get(stateType.value());
			if (c == null) {
				continue;
			}
			// 按顺序作更新
			if (!c.getState().canTranstion(c, context)) {
				return false;
			}
		}
		if (!performer.getBuffManager().canTranstion(context)) {
	        return false;
	    }
		return true;
	}

	@Override
	public void transtion(IStateContext target) {
		if(!canTranstion(target)){
	        return;
	    }
		IStateContext sc = currentStateMap.get(target.getState().getType());
		if (sc != null) {
			// 之前的状态退出
			sc.getState().exit(sc);
		}
		currentStateMap.put(target.getState().getType(), target);
		// 新状态进入
		target.getState().enter(target);
	}

	@Override
	public IStateContext getCurrentState(int stateType) {
		return currentStateMap.get(stateType);
	}

	@Override
	public IStateContext getDefaultContext(int subStateType) {
		return defaultContextMap.get(subStateType);
	}

	private void initCurrentState() {
		IStateContextFactory stateContextFactory = performer.getGlobalContext().getStateContextFactory();
		// 全局默认状态
		IStateContext context = stateContextFactory.createGSContext(performer,StateSubTypeEnum.GS_NoCombat.value());
		putState(context);
		// 动作默认状态
		IActionContext actionContext = performer.getGlobalContext().getActionContextFactory().createIdleContext(performer);
		context = stateContextFactory.createASContext(actionContext);
		putState(context);
		// 控制默认状态
		context = stateContextFactory.createCSContext(StateSubTypeEnum.CS_User.value(), performer);
		putState(context);
	}

	private void putState(IStateContext context) {
		currentStateMap.put(context.getState().getType(), context);
		defaultContextMap.put(context.getState().getSubType(), context);
	}
}
