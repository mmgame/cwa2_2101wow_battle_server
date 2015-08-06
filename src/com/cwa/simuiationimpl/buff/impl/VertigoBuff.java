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
import com.cwa.simuiationimpl.state.action.MagicState;
import com.cwa.simuiationimpl.state.action.MagicStateContext;
import com.cwa.simuiationimpl.state.action.MoveState;
import com.cwa.simuiationimpl.state.action.MoveStateContext;
/**
 * 眩晕
 * @author yangfeng
 *
 */
public class VertigoBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Vertigo.value();
	}

	@Override
	public void enter(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		IStateContext stateContext = performer.getStateManager().getCurrentState(StateTypeEnum.Action.value());
		if(stateContext != null){
			ISState state = stateContext.getState();
			if(state instanceof MoveState || state instanceof MagicState){
				//切换到静止状态
				IStateManager sm = performer.getStateManager();
				sm.transtion(sm.getDefaultContext(StateSubTypeEnum.AS_Idle.value()));
			}
		}
	}

	@Override
	public void exit(IBuffContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		//效果时间到
		performer.getBuffManager().exit(context);
	}

	@Override
	public boolean canTranstion(IStateContext target) {
		if(target instanceof MagicStateContext || target instanceof MoveStateContext){
			return false;
		}
		return true;
	}
}
