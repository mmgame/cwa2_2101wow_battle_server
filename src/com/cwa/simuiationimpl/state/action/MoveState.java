package com.cwa.simuiationimpl.state.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.state.IStateManager;

/**
 * 移动状态
 * 
 * @author mausmars
 * 
 */
public class MoveState implements ISState {
	protected static final Logger logger = LoggerFactory.getLogger(MoveState.class);

	@Override
	public int getType() {
		return StateTypeEnum.Action.value();
	}

	@Override
	public int getSubType() {
		return StateSubTypeEnum.AS_Move.value();
	}

	@Override
	public void enter(IStateContext context) {
		IActionStateContext asc = (IActionStateContext) context;
		// 执行动作的enter
		IActionContext ac = asc.getActionContext();
		ac.getAction().enter(ac);
		// 发送事件
		sendEvent(ac, StepTypeEnum.ST_Enter);

		if (logger.isInfoEnabled()) {
			logger.info("进入移动状态 id=" + context.getPerformer().getId() + " " + context.getPerformer().getPosition().toString());
		}
	}

	@Override
	public void update(IStateContext context) {
		IActionStateContext asc = (IActionStateContext) context;
		// 执行动作的update
		IActionContext ac = asc.getActionContext();
		ac.getAction().update(ac);

		if (ac.getOver() == StepTypeEnum.ST_Null.value()) {
			if (ac.isTrigger()) {
				// 发送事件
				// sendEvent(ac, StepTypeEnum.ST_Update);
				// 每帧重置
				ac.resetContextByFrame();
			}
		} else {
			// 已经结束了动作
			IPerformer p = context.getPerformer();
			// 转换到什么都不干
			IStateManager sm = p.getStateManager();
			// 这里会调用当前状态的exit方法直接发送了事件
			sm.transtion(sm.getDefaultContext(StateSubTypeEnum.AS_Idle.value()));
		}
	}

	@Override
	public void exit(IStateContext context) {
		IActionStateContext asc = (IActionStateContext) context;
		// 执行动作的exit
		IActionContext ac = asc.getActionContext();
		ac.getAction().exit(ac);

		// 这里用ac.getOver()获取结束类型
		// sendEvent(ac, ac.getOver());

		// 重置上下文
		context.resetContext();
		if (logger.isInfoEnabled()) {
			logger.info("退出移动状态 id=" + context.getPerformer().getId() + " " + context.getPerformer().getPosition().toString());
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
}
