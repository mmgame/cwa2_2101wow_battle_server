package com.cwa.simuiation.event;

import java.util.List;

import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;

/**
 * 状态事件
 * 
 * @author mausmars
 * 
 */
public abstract class ISStateEvent implements ISEvent {
	public int channel;
	public List<String> receivers;
	/**
	 * 源
	 * 
	 * @return
	 */
	public abstract IPerformer getSource();

	/**
	 * 目标
	 * 
	 * @return
	 */
	public abstract ISObject getTarget();

	/**
	 * 阶段,{1:enter, 2:update, 3:exit}
	 * 
	 * @return
	 */
	public abstract int getStep();

	/**
	 * 设置阶段,{1:enter, 2:update, 3:exit}
	 * 
	 * @return
	 */
	public abstract void setStep(int step);

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}
}
