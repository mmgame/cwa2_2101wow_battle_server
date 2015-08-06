package com.cwa.battleimpl.task;

import com.cwa.battle.task.IBScheduleMgr;
import com.cwa.component.task.ITask;
import com.cwa.component.task.ITaskManager;
import com.cwa.component.task.ITaskTypeConfig;
import com.cwa.component.task.quartz.config.ITaskTypeConfigFactory;

/**
 * 战场调度器
 * 
 * @author mausmars
 * 
 */
public class ScheduleMgr implements IBScheduleMgr {
	private ITaskManager taskManager;
	private ITaskTypeConfigFactory taskTypeConfigFactory;

	@Override
	public void startup() {
		taskManager.startup();
	}

	@Override
	public void shutdown() {
		// 停止驱动器
		taskManager.shutdown();
	}

	@Override
	public ITask getSchedule(String key) {
		return null;
	}

	@Override
	public boolean cancelTask(String key) {
		return false;
	}

	@Override
	public ITaskTypeConfigFactory getTaskTypeConfigFactory() {
		return taskTypeConfigFactory;
	}

	@Override
	public void scheduleTask(ITask task, ITaskTypeConfig taskType) {
		taskManager.addTask(task, taskType);
	}

	// -------------------------------------
	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public void setTaskTypeConfigFactory(ITaskTypeConfigFactory taskTypeConfigFactory) {
		this.taskTypeConfigFactory = taskTypeConfigFactory;
	}
}
