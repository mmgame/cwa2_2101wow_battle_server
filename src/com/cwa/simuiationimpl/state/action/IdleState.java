package com.cwa.simuiationimpl.state.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 空闲状态
 * 
 * @author mausmars
 * 
 */
public class IdleState implements ISState {
	protected static final Logger logger = LoggerFactory.getLogger(IdleState.class);

	@Override
	public int getType() {
		return StateSubTypeEnum.AS_Idle.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getSubType() {
		return StateSubTypeEnum.AS_Idle.value();
	}

	@Override
	public void enter(IStateContext context) {
		IActionStateContext asc = (IActionStateContext) context;
		// 发送事件
		IActionContext ac = asc.getActionContext();
		sendEvent(ac, StepTypeEnum.ST_Enter);

		if (logger.isInfoEnabled()) {
			logger.info("进入静止状态 id=" + context.getPerformer().getId() + " " + context.getPerformer().getPosition().toString());
		}
	}

	@Override
	public void update(IStateContext context) {
	}

	@Override
	public void exit(IStateContext context) {
		if (logger.isInfoEnabled()) {
			logger.info("退出静止状态 id=" + context.getPerformer().getId() + " " + context.getPerformer().getPosition().toString());
		}
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

	private void sendEvent(IActionContext ac, int stepType) {
		ISStateEvent event = ac.getEvent();
		event.setStep(stepType);
		ac.getPerformer().getOwner().sendEvent(event);
	}

	private void sendEvent(IActionContext ac, StepTypeEnum stepType) {
		sendEvent(ac, stepType.value());
	}

//	private void sendEvent(Object listenerType, IActionContext ac, int stepType) {
//		ISStateEvent event = ac.getEvent();
//		event.setStep(stepType);
//		ac.getPerformer().getOwner().sendEvent(listenerType, event);
//	}
}
