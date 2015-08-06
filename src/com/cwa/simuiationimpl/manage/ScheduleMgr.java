package com.cwa.simuiationimpl.manage;

import com.cwa.simuiation.manager.IScheduleMgr;
import com.cwa.simuiation.task.ITask;
import com.cwa.simuiation.task.TaskQueue;

/**
 * 仿真器任务管理
 * 
 * @author tzy
 * 
 */
public class ScheduleMgr implements IScheduleMgr {
	private TaskQueue taskQueue = new TaskQueue(); // 任务队列

	@Override
	public void processTasks() {
		for (;;) {
			ITask task = taskQueue.poll();
			if (task == null) {
				break;
			}
			task.excute();
		}
	}

	@Override
	public void addTask(ITask task) {
		taskQueue.addTask(task);
	}

	@Override
	public void clearAll() {
		taskQueue.clear();
	}
}
