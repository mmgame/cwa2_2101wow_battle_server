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
 * 麻痹
 * @author yangfeng
 *
 */
public class ParalysisBuff implements ISBuff{

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_Paralysis.value();
	}

	@Override
	public void enter(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		//修改伤害倍率
		performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_HitRate.value(), BuffConstant.ParalysisHurtRate);
		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_HitRate.value(), BuffConstant.ParalysisHurtRate);
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
		//修改伤害倍率
		context.getPerformer().getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), AttrTypeEnum.Attr_HitRate.value(), -BuffConstant.ParalysisHurtRate);
		context.getPerformer().getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_HitRate.value(), -BuffConstant.ParalysisHurtRate);
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
