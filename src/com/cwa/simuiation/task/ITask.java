package com.cwa.simuiation.task;

/**
 * 任务接口
 * @author tzy
 *
 */
public interface ITask {

	/**
	 * 获取任务Id
	 * @return
	 */
	String getTaskId();
	/**
	 * 任务执行
	 */
	void excute();
	
	/**
	 * 判断能否现在执行
	 * @return
	 */
	boolean canExcute();

	/**
	 * 获取任务执行时间（仿真器时间）
	 * @return
	 */
	int getExcuteTime();
	
}
