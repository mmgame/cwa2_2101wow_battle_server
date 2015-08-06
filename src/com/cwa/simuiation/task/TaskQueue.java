package com.cwa.simuiation.task;

import java.util.PriorityQueue;
import java.util.Queue;

import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 任务队列
 * 
 * @author tzy
 */

public class TaskQueue {
	private TaskCompare taskCompare=new TaskCompare();
	private Queue<ITask> taskQueue=new PriorityQueue<ITask>(SimuiationConstant.PriorityQueue_Capacity, taskCompare);   //优先级队列

	/**
	 * 添加任务
	 * @param task
	 */
	public void addTask(ITask task) {
		taskQueue.add(task);
	}

	/**
	 * 返回当前可执行任务
	 * @return
	 */
	public ITask poll() {
		if (!taskQueue.isEmpty()&&taskQueue.peek().canExcute()) {
			return taskQueue.poll();
		}
		return null;

	}

	/**
	 * 清除全部任务队列
	 */
	public void clear() {
		taskQueue.clear();
	}


}
