package com.cwa.simuiationimpl.buff.impl;

import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.state.IStateManager;
import com.cwa.simuiationimpl.constant.BuffConstant;
import com.cwa.simuiationimpl.state.action.MagicState;
import com.cwa.simuiationimpl.state.action.MagicStateContext;
import com.cwa.simuiationimpl.state.action.MoveState;
import com.cwa.simuiationimpl.state.action.MoveStateContext;
/**
 * 石化
 * @author yangfeng
 *
 */
public class StoneBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Stone.value();
	}

	@Override
	public void enter(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_HitRate.value(), BuffConstant.StoneHurtRate);
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_HitRate.value(), BuffConstant.StoneHurtRate);
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
		context.getPerformer().getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_HitRate.value(), -BuffConstant.StoneHurtRate);
		context.getPerformer().getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_HitRate.value(), -BuffConstant.StoneHurtRate);
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
