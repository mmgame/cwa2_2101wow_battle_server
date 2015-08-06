package com.cwa.simuiationimpl.action;

import java.util.List;

import com.cwa.component.prototype.IPrototype;
import com.cwa.prototype.SkillPrototype;
import com.cwa.prototype.gameEnum.SkillAttackTypeEnum;
import com.cwa.prototype.gameEnum.SkillReleaseTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiationimpl.event.ESMagicEvent;

/**
 * 施法动作
 * 
 * @author mausmars
 * 
 */
public class MagicActionContext extends AActionContext {
	private SkillPrototype prototype;// 技能
	private boolean isTrigger = false;// 是否触发了
	private int count;// 执行第n次数
	private ESMagicEvent event;// 事件
	private int delayTime;// 网络延迟时间
	private int continueTime;
	private int actionTime;//整个攻击动作时间
	
	@Override
	public ISStateEvent getEvent() {
		return event;
	}

	public void trigger() {
		// 创建事件
		createEvent();

		// 执行次数加1
		count++;
		// 重置时间
		resetStartTime();
		isTrigger = true;
	}

	public void createEvent() {
		event = new ESMagicEvent();
		event.setActionContext(this);
	}

	@Override
	public void resetContext() {
	}

	@Override
	public void resetContextByFrame() {
//		event = null;
		isTrigger = false;
	}

	public boolean isTrigger() {
		return isTrigger;
	}

	public SkillPrototype getPrototype() {
		return prototype;
	}

	public int getSkillId() {
		return prototype.getKeyId();
	}

	public int getReleaseType() {
		return prototype.getReleaseType();
	}

	public int getAttackType() {
		return prototype.getCommonAttack();
	}

	public List<Integer> getIncidentalEffectLists() {
		return prototype.gotIncidentalEffectListsList().get(performer.getAttrMgr().getQuality());
	}

	public int getCountable() {
		return prototype.getCount();
	}

	public int getCount() {
		return count;
	}

	public int getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
		List<Integer> effectTimes = performer.getAttrMgr().getEffectTimeList();
		List<Integer> actionTimes = performer.getAttrMgr().getActionTimeList();
		
		int effectTime = 0;
		if(prototype.getCommonAttack() == SkillAttackTypeEnum.Type_Normal.value()){
			effectTime = effectTimes.get(0);
			this.actionTime = actionTimes.get(0);
		}else{
			effectTime = effectTimes.get(prototype.getType());
			this.actionTime = actionTimes.get(prototype.getType());
		}
		
		if(prototype.getReleaseType() == SkillReleaseTypeEnum.Release_Instant.value()){
			this.continueTime = effectTime;
			if(prototype.getPlaySequence() != 1){//二连击
				this.actionTime = actionTime * getIncidentalEffectLists().size();
			}
		}else if(prototype.getReleaseType() == SkillReleaseTypeEnum.Release_Reading.value()){
			this.continueTime = prototype.getActionTime() + effectTime;
			if(prototype.getPlaySequence() != 1){//二连击
				this.actionTime = actionTime * getIncidentalEffectLists().size() + prototype.getActionTime();
			}else{
				this.actionTime = actionTime + prototype.getActionTime();
			}
		}else if(prototype.getReleaseType() == SkillReleaseTypeEnum.Release_Singing.value()){
			this.continueTime = prototype.getActionTime();
			this.actionTime = prototype.getActionTime() * prototype.getCount();
		}
	}
	public void resetContinueTime(){
		if(prototype.getReleaseType() == SkillReleaseTypeEnum.Release_Instant.value()){//瞬发技能才有多连击
			this.continueTime = actionTime / getIncidentalEffectLists().size();
		}else if(prototype.getReleaseType() == SkillReleaseTypeEnum.Release_Reading.value()){
			List<Integer> actionTimes = performer.getAttrMgr().getActionTimeList();
			if(prototype.getCommonAttack() == SkillAttackTypeEnum.Type_Normal.value()){
				this.continueTime = actionTimes.get(0);
			}else{
				this.continueTime = actionTimes.get(prototype.getType());
			}
		}
	}
	public int getContinueTime() {
		return continueTime;
	}
	public int getActionTime() {
		return actionTime;
	}

	// ------------------------------
	public void setPrototype(IPrototype prototype) {
		this.prototype = (SkillPrototype) prototype;
	}
}
