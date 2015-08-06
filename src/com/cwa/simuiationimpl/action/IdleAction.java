package com.cwa.simuiationimpl.action;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.ISAction;
import com.cwa.simuiation.enums.StateSubTypeEnum;

/**
 * 施法动作
 * 
 * @author mausmars
 * 
 */
public class IdleAction implements ISAction {
	@Override
	public int getType() {
		return StateSubTypeEnum.AS_Idle.value();
	}

	@Override
	public void enter(IActionContext context) {
		
	}

	@Override
	public void exit(IActionContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(IActionContext context) {
		// TODO Auto-generated method stub
		
	}
}
