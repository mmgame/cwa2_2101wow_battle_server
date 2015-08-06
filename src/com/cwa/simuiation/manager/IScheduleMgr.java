package com.cwa.simuiation.manager;

import com.cwa.simuiation.task.ITask;

/**
 * 任务管理
 * @author tzy
 *
 */
public interface IScheduleMgr {

	/**
	 * 处理任务
	 */
	void processTasks();

	/**
	 * 添加任务
	 * 
	 * @param task
	 */
	void addTask(ITask task);
	
	/**
	 * 清除所有任务
	 */
	void clearAll();


}
