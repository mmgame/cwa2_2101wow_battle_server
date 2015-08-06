package com.cwa.simuiationimpl.state.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.ISState;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.state.IStateManager;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.event.ESMagicEvent;

/**
 * 施法状态
 * 
 * @author yangfeng
 * 
 */
public class MagicState implements ISState {
	protected static final Logger logger = LoggerFactory.getLogger(MagicState.class);

	@Override
	public int getType() {
		return StateTypeEnum.Action.value();
	}

	@Override
	public int getSubType() {
		return StateSubTypeEnum.AS_Magic.value();
	}

	@Override
	public void enter(IStateContext context) {
		IActionStateContext asc = (IActionStateContext) context;
		// 执行动作的enter
		MagicActionContext ac = (MagicActionContext) asc.getActionContext();
		IPerformer p = ac.getPerformer();
		
		//重置cd时间
		int skillId = ac.getPrototype().getKeyId();
		SimuiationTime currentSTime = p.getGlobalContext().getClock().getCurrentSTime();
		p.getSkillManager().resetCd(skillId, currentSTime);
		//检测成功，扣除魔法值
	    int quality = p.getAttrMgr().getQuality();
	    p.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Energy.value(), -ac.getPrototype().getMpList().get(quality));
		
	    ac.getAction().enter(ac);
		// 发送事件
		sendEvent(ac.getPerformer().getControler().getClientMgr().key(),ac, StepTypeEnum.ST_Enter.value());

		if (logger.isInfoEnabled()) {
			logger.info("进入施法状态 id=" + context.getPerformer().getId() + " " + context.getPerformer().getPosition().toString());
		}
	}

	@Override
	public void update(IStateContext context) {
		IActionStateContext asc = (IActionStateContext) context;
		// 执行动作的update
		IActionContext ac = asc.getActionContext();
		ac.getAction().update(ac);

		if (ac.getOver() == StepTypeEnum.ST_Null.value()) {//瞬发的二连击
			if (ac.isTrigger()) {
				// 发给施法事件处理器
				sendEvent(ac.getPerformer().getControler().getEventMgr().key(), ac, StepTypeEnum.ST_Exit.value());
				// 每帧重置
				ac.resetContextByFrame();
			}
		} else if (ac.getOver() == StepTypeEnum.ST_Update.value()) {//持续吟唱
			if (ac.isTrigger()) {
				// 发给施法事件处理器
				sendEvent(ac.getPerformer().getControler().getEventMgr().key(), ac, StepTypeEnum.ST_Update.value());
				// 每帧重置
				ac.resetContextByFrame();
			}
		} else if(ac.getOver() == StepTypeEnum.ST_Exit.value()){
//			sendEvent(ac.getPerformer().getControler().getEventMgr().key(), ac, ac.getOver());
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

		// 重置上下文
		context.resetContext();

		if (logger.isInfoEnabled()) {
			logger.info("退出施法状态 id=" + context.getPerformer().getId() + " " + context.getPerformer().getPosition().toString());
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
	public boolean canTranstion(IStateContext source, IStateContext target) {
		// MagicStateContext s = (MagicStateContext) source;
		// MagicActionContext ac = (MagicActionContext) s.getActionContext();
		// if (ac.getReleaseType() ==
		// SkillReleaseTypeEnum.Release_Instant.value()) {
		// // 瞬发不能被打断
		// if (target.getState().getSubType() ==
		// StateSubTypeEnum.AS_Move.value()) {
		// // 如果是移动状态
		// return false;
		// }
		// }
		return true;
	}

	private void sendEvent(IActionContext ac, int stepType) {
		ESMagicEvent event = (ESMagicEvent) ac.getEvent();
		event.setStep(stepType);
		event.setMstate(1);//施法成功
		ac.getPerformer().getOwner().sendEvent(event);
	}

	private void sendEvent(IActionContext ac, StepTypeEnum stepType) {
		sendEvent(ac, stepType.value());
	}

	private void sendEvent(Object listenerType, IActionContext ac, int stepType) {
		ESMagicEvent event = (ESMagicEvent) ac.getEvent();
		event.setStep(stepType);
		event.setMstate(1);//施法成功
		ac.getPerformer().getOwner().sendEvent(listenerType, event);
	}
}
