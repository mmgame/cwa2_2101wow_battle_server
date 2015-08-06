package com.cwa.simuiation.task;

import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.clock.SimuiationTime;

public abstract class Task implements ITask {
	protected SClock clock;// 时钟
	protected String taskId;// 任务id
	protected int excuteTime;// 任务执行时间（仿真器时间）
	protected Object obj;

	public Task(String taskId,Object obj) {
		this.taskId = taskId;
		this.obj = obj;
	}

	@Override
	public String getTaskId() {
		return taskId;
	}
	
	public Object getObj(){
		return obj;
	}
	@Override
	public boolean canExcute() {
		SimuiationTime currentTime = clock.getCurrentSTime();
		if (currentTime.getMSTime() >= excuteTime) {
			return true;
		}
		return false;
	}

	@Override
	public int getExcuteTime() {
		return excuteTime;
	}

	// --------------------------------------------------
	public void setClock(SClock clock,int intervalTime) {
		this.clock = clock;
		excuteTime = (int) (clock.getCurrentSTime().getMSTime() + intervalTime);
	}
}
