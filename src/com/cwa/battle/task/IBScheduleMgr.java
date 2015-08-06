package com.cwa.battle.task;

import com.cwa.component.task.ITask;
import com.cwa.component.task.ITaskTypeConfig;
import com.cwa.component.task.quartz.config.ITaskTypeConfigFactory;

public interface IBScheduleMgr {
	/**
	 * 开启任务调度
	 * 
	 * @param task
	 */
	void startup();

	/**
	 * 关闭任务调度
	 * 
	 * @param task
	 */
	void shutdown();

	/**
	 * 添加任务
	 * 
	 * @param task
	 */
	void scheduleTask(ITask task, ITaskTypeConfig taskType);

	/**
	 * 得到任务
	 * 
	 * @param key
	 * @return
	 */
	ITask getSchedule(String key);

	/**
	 * 取消任务
	 * 
	 * @param key
	 * @return
	 */
	boolean cancelTask(String key);

	ITaskTypeConfigFactory getTaskTypeConfigFactory();
}
