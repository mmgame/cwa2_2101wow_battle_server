package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.SkillAttackTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.state.IStateManager;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.state.action.MagicState;
import com.cwa.simuiationimpl.state.action.MagicStateContext;
/**
 * 沉默
 * @author yangfeng
 *
 */
public class NoSpeekBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_NoSpeek.value();
	}

	@Override
	public void enter(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		IStateContext stateContext = performer.getStateManager().getCurrentState(StateTypeEnum.Action.value());
		if(stateContext != null){
			ISState state = stateContext.getState();
			if(state instanceof MagicState){
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
		if(target instanceof MagicStateContext){
			MagicActionContext mac = (MagicActionContext) ((MagicStateContext) target).getActionContext();
			if(mac.getPrototype().getCommonAttack() == SkillAttackTypeEnum.Type_Normal.value()){
				return true;
			}
			return false;
		}
		return true;
	}
}
