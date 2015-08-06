package com.cwa.simuiationimpl.action;

import com.cwa.prototype.gameEnum.SkillReleaseTypeEnum;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.ISAction;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 施法动作
 * 
 * @author mausmars
 * 
 */
public class MagicAction implements ISAction {
	@Override
	public int getType() {
		return StateSubTypeEnum.AS_Magic.value();
	}

	@Override
	public void enter(IActionContext context) {
		MagicActionContext ac = (MagicActionContext) context;
		ac.createEvent();

		IPerformer p = context.getPerformer();
		
	    // 设置延迟
		ac.setDelayTime(p.getOwner().getNetDelayCheckServer().getDelayTime());

		if (ac.getReleaseType() == SkillReleaseTypeEnum.Release_Instant.value()) {
			// 瞬发，正常退出
		} else if (ac.getReleaseType() == SkillReleaseTypeEnum.Release_Reading.value()) {
			// 读条
		} else if (ac.getReleaseType() == SkillReleaseTypeEnum.Release_Singing.value()) {
			// 吟唱
		}
	}

	@Override
	public void exit(IActionContext context) {
		MagicActionContext ac = (MagicActionContext) context;
		SClock clock = context.getPerformer().getGlobalContext().getClock();
		if (ac.getStartTime().getFrameCount() < clock.getCurrentFrameCount()) {
			update(context);
		}
		if (ac.getOver() == StepTypeEnum.ST_Null.value()) {
			// 中断施法
			ac.createEvent();
			context.setOver(StepTypeEnum.ST_Interrupt.value());
		}
	}

	@Override
	public void update(IActionContext context) {
		MagicActionContext ac = (MagicActionContext) context;

		SClock clock = ac.getPerformer().getGlobalContext().getClock();
		long differTime = clock.differByCurrentTime(ac.getStartTime());

		if (ac.getReleaseType() == SkillReleaseTypeEnum.Release_Instant.value()) {// 瞬发
			if(differTime < ac.getActionTime()){
				if(ac.getPrototype().getPlaySequence() == 1){
					if (differTime < ac.getContinueTime() || ac.getCount() > 0){
						return;
					}
				}else{//二连击
					if (differTime < ac.getContinueTime() || ac.getCount() >= ac.getIncidentalEffectLists().size()) {
						return;
					}
					ac.resetContinueTime();//重置触发时间
				}
				ac.trigger();// 触发
			}else{
				context.setOver(StepTypeEnum.ST_Exit.value());// 正常退出
			}
		} else if (ac.getReleaseType() == SkillReleaseTypeEnum.Release_Reading.value()) {// 读条
			if(differTime < ac.getActionTime()){
				if(ac.getPrototype().getPlaySequence() == 1){
					if (differTime < ac.getContinueTime() || ac.getCount() > 0){
						return;
					}
				}else{
					if (differTime < ac.getContinueTime() || ac.getCount() >= ac.getIncidentalEffectLists().size()) {
						return;
					}
					ac.resetContinueTime();//重置触发时间
				}
				ac.trigger();// 触发
			}else{
				context.setOver(StepTypeEnum.ST_Exit.value());// 正常退出
			}
		} else if (ac.getReleaseType() == SkillReleaseTypeEnum.Release_Singing.value()) { // 吟唱
			if (differTime < ac.getContinueTime()) {
				return;
			}
			// 触发
			ac.trigger();
			if (ac.getCount() > ac.getCountable()) {
				// 正常退出
				context.setOver(StepTypeEnum.ST_Exit.value());
			}else{
				context.setOver(StepTypeEnum.ST_Update.value());
			}
		}
	}
}
