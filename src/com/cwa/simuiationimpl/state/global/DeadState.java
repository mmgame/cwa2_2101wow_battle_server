package com.cwa.simuiationimpl.state.global;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESDeadEvent;

/**
 * 死亡状态
 * 
 * @author mausmars
 * 
 */
public class DeadState implements ISState {

	@Override
	public int getType() {
		return StateSubTypeEnum.GS_Dead.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getSubType() {
		return StateSubTypeEnum.GS_Dead.value();
	}

	@Override
	public void enter(IStateContext context) {
		IPerformer p = context.getPerformer();
		p.getBuffManager().clear();
		if(p.getPassiveSkillManager() != null){
			p.getPassiveSkillManager().clear();
		}

		// 切换死亡状态之前动作先切换成静止状态
		IActionContext adleContext = p.getGlobalContext().getActionContextFactory().createIdleContext(p);
		IStateContext stateContext = p.getGlobalContext().getStateContextFactory().createASContext(adleContext);
		p.getStateManager().transtion(stateContext);
		
		// 向客户端发送死亡事件
		ESDeadEvent event = new ESDeadEvent();
		event.setPerformer(p);
		p.getOwner().sendEvent(SimuiationConstant.ListenerKey_ClientMgr, event);
		p.getOwner().sendEvent(SimuiationConstant.ListenerKey_System, event);
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
		if(context.getState().getType() == StateTypeEnum.Global.value() || context.getState().getSubType() == StateSubTypeEnum.AS_Idle.value()){
			//死亡后允许动作切换成静止态
			return true;
		}
		return false;
	}
}
