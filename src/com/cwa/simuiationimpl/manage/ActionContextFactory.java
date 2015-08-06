package com.cwa.simuiationimpl.manage;

import java.util.Map;

import com.cwa.component.prototype.IPrototype;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.ISAction;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.manager.IActionContextFactory;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.IdleActionContext;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.action.MoveActionContext;

public class ActionContextFactory implements IActionContextFactory {
	private Map<Integer, ISAction> actionMap;

	@Override
	public IActionContext createMoveContext(IPerformer performer, ISObject target) {
		ISAction action = actionMap.get(StateSubTypeEnum.AS_Move.value());
		if (action == null) {
			return null;
		}
		MoveActionContext context = new MoveActionContext();
		context.setStartTime(performer.getGlobalContext().getClock().getCurrentSTime());
		context.setAction(action);
		context.setPerformer(performer);
		context.setTarget(target);
		return context;
	}

	@Override
	public IActionContext createMagicContext(IPerformer performer, ISObject target, IPrototype prototype) {
		ISAction action = actionMap.get(StateSubTypeEnum.AS_Magic.value());
		if (action == null) {
			return null;
		}
		MagicActionContext context = new MagicActionContext();
		context.setPrototype(prototype);
		context.setStartTime(performer.getGlobalContext().getClock().getCurrentSTime());
		context.setAction(action);
		context.setPerformer(performer);
		context.setTarget(target);
		return context;
	}
	
	@Override
	public IActionContext createIdleContext(IPerformer performer) {
		ISAction action = actionMap.get(StateSubTypeEnum.AS_Idle.value());
		if (action == null) {
			return null;
		}
		IdleActionContext context = new IdleActionContext();
		context.setAction(action);
		context.setPerformer(performer);
		context.setStartTime(performer.getGlobalContext().getClock().getCurrentSTime());
		return context;
	}

	// --------------------------------------------
	public void setActionMap(Map<Integer, ISAction> actionMap) {
		this.actionMap = actionMap;
	}


}
