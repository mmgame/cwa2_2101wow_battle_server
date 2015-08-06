package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.state.IStateManager;
import com.cwa.simuiationimpl.state.action.MoveState;
import com.cwa.simuiationimpl.state.action.MoveStateContext;
/**
 * 定身
 * @author yangfeng
 *
 */
public class NoMoveBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_NoMove.value();
	}

	@Override
	public void enter(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		IStateContext stateContext = performer.getStateManager().getCurrentState(StateTypeEnum.Action.value());
		if(stateContext != null){
			ISState state = stateContext.getState();
			if(state instanceof MoveState){
				//切换到静止状态
				IStateManager sm = performer.getStateManager();
				sm.transtion(sm.getDefaultContext(StateSubTypeEnum.AS_Idle.value()));
			}
		}
	}

	@Override
	public void exit(IBuffContext context) {
		
	}

	@Override
	public void update(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		//效果时间到
		performer.getBuffManager().exit(context);
	}

	@Override
	public boolean canTranstion(IStateContext target) {
		if(target instanceof MoveStateContext){
			return false;
		}
		return true;
	}
}
