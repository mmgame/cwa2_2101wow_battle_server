package com.cwa.simuiationimpl.state.control;

import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.IControlStateContext;
import com.cwa.simuiation.state.ISControlState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * ai控制状态 （负责确定用于执行的合适动作）
 * 
 * @author mausmars
 * 
 */
public class AIControlState implements ISControlState {
	// 状态类型
	private StateSubTypeEnum stateSubType = StateSubTypeEnum.CS_AI;

	@Override
	public IActionStateContext performAction(IControlStateContext context) {

		return null;
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
