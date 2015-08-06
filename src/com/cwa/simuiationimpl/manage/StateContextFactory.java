package com.cwa.simuiationimpl.manage;

import java.util.HashMap;
import java.util.Map;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.manager.IStateContextFactory;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.state.action.IdleStateContext;
import com.cwa.simuiationimpl.state.action.MagicStateContext;
import com.cwa.simuiationimpl.state.action.MoveStateContext;
import com.cwa.simuiationimpl.state.control.UserControlStateContext;
import com.cwa.simuiationimpl.state.global.DeadStateContext;
import com.cwa.simuiationimpl.state.global.NoCombatStateContext;

/**
 * 上下文工厂
 * 
 * @author mausmars
 * 
 */
public class StateContextFactory implements IStateContextFactory {
	private Map<Integer, ISState> stateMap = new HashMap<Integer, ISState>();

	@Override
	public IStateContext createASContext(IActionContext ac) {
		int stateSubType = ac.getAction().getType();
		ISState state = stateMap.get(stateSubType);
		if (state == null) {
			return null;
		}
		if (stateSubType == StateSubTypeEnum.AS_Idle.value()) {
			IdleStateContext context = new IdleStateContext();
			context.setState(state);
			context.setActionContext(ac);
			return context;
		} else if (stateSubType == StateSubTypeEnum.AS_Move.value()) {
			MoveStateContext context = new MoveStateContext();
			context.setState(state);
			context.setActionContext(ac);
			return context;
		} else if (stateSubType == StateSubTypeEnum.AS_Magic.value()) {
			MagicStateContext context = new MagicStateContext();
			context.setState(state);
			context.setActionContext(ac);
			return context;
		}
		return null;
	}

	@Override
	public IStateContext createGSContext(IPerformer p,int stateSubType) {
		ISState state = stateMap.get(stateSubType);
		if (state == null) {
			return null;
		}
		if (stateSubType == StateSubTypeEnum.GS_NoCombat.value()) {
			NoCombatStateContext context = new NoCombatStateContext();
			context.setState(state);
			return context;
		} else if (stateSubType == StateSubTypeEnum.GS_Combat.value()) {

		} else if (stateSubType == StateSubTypeEnum.GS_Dead.value()) {
			DeadStateContext context = new DeadStateContext();
			context.setState(state);
			context.setPerformer(p);
			return context;
		}
		return null;
	}

	@Override
	public IStateContext createCSContext(int stateSubType, IPerformer p) {
		ISState state = stateMap.get(stateSubType);
		if (state == null) {
			return null;
		}
		if (stateSubType == StateSubTypeEnum.CS_User.value()) {
			UserControlStateContext context = new UserControlStateContext(state, p);
			return context;
		} else if (stateSubType == StateSubTypeEnum.CS_AI.value()) {

		}
		return null;
	}

	public void initStateMap(ISState state) {
		stateMap.put(state.getSubType(), state);
	}
}
