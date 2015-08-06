package com.cwa.simuiationimpl.state.control;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.manager.IStateContextFactory;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.IControlStateContext;
import com.cwa.simuiation.state.ISControlState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 用户控制状态
 * 
 * @author mausmars
 * 
 */
public class UserControlState implements ISControlState {
	// 状态类型
	private StateSubTypeEnum stateSubType = StateSubTypeEnum.CS_User;

	@Override
	public IActionStateContext performAction(IControlStateContext context) {
		IActionContext ac = context.performAction();
		if (ac == null) {
			return null;
		}
		IActionContextHandler handler = context.getPerformer().getGlobalContext().getMessageActionContextHandler();
		ac = handler.handler(ac);
		if (ac == null) {
			return null;
		}
		IStateContextFactory factory = context.getPerformer().getGlobalContext().getStateContextFactory();
		IActionStateContext asc = (IActionStateContext) factory.createASContext(ac);
		return asc;
	}

	@Override
	public void enter(IStateContext context) {

	}

	@Override
	public void update(IStateContext context) {

	}

	@Override
	public void exit(IStateContext context) {

	}

	@Override
	public void block(IStateContext context) {

	}

	@Override
	public void unBlock(IStateContext context) {

	}

	@Override
	public boolean isBlock(IStateContext context) {
		return false;
	}

	@Override
	public boolean canTranstion(IStateContext source, IStateContext context) {

		return true;
	}

	@Override
	public int getType() {
		return stateSubType.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getSubType() {
		return stateSubType.value();
	}
}
