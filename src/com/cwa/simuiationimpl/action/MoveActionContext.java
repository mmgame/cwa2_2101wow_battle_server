package com.cwa.simuiationimpl.action;

import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiationimpl.event.ESMoveEvent;

/**
 * 移动动作上下文
 * 
 * @author mausmars
 * 
 */
public class MoveActionContext extends AActionContext {
	
	private double speed;
	private int delayTime;
	private Boolean isAdjust;
	@Override
	public void resetContext() {
	}

	@Override
	public ISStateEvent getEvent() {
		ESMoveEvent event = new ESMoveEvent();
		event.setActionContext(this);
		return event;
	}

	@Override
	public boolean isTrigger() {
		return true;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public int getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}
	public Boolean getIsAdjust() {
		return isAdjust;
	}
	public void setIsAdjust(Boolean isAdjust) {
		this.isAdjust = isAdjust;
	}
}
