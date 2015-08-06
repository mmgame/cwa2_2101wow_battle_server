package com.cwa.simuiationimpl.buff.task;

import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.task.ITask;
/**
 * Buff任务
 * @author yangfeng
 *
 */
public class BuffTask implements ITask{
	private IBuffContext context;
	private SClock clock;// 时钟
	private String taskId;// 任务id
	private int excuteTime;// 任务开始时间（仿真器时间）
	private int overTime;//任务结束时间（仿真器时间）
	private int intervalTime;// 任务间隔时间（毫秒）
	
	public BuffTask(String taskId,IBuffContext context,SClock clock,int overTime,int intervalTime){
		this.taskId = taskId;
		this.context = context;
		this.intervalTime = intervalTime;
		this.clock = clock;
		excuteTime = clock.getCurrentSTime().getMSTime() + intervalTime;
		this.overTime = clock.getCurrentSTime().getMSTime() + overTime;
	}

	@Override
	public String getTaskId() {
		return this.taskId;
	}

	@Override
	public void excute() {
		context.getBuff().update(context);
	}

	@Override
	public boolean canExcute() {
		long currentTime = clock.getCurrentSTime().getMSTime();
		if (currentTime >= excuteTime && intervalTime > 0) {//有多次触发的情况
			excuteTime = excuteTime + intervalTime;
			return true;
		}else if(currentTime >= overTime){
			return true;
		}
		return false;
	}

	@Override
	public int getExcuteTime() {
		return this.excuteTime;
	}
	public long getOverTime(){
		return overTime;
	}
	public SClock getClock(){
		return clock;
	}
}
