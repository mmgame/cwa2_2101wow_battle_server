package com.cwa.simuiationimpl.buff.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import com.cwa.simuiation.task.ITask;
import com.cwa.simuiation.task.TaskCompare;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
/**
 * buff任务队列
 * @author yangfeng
 *
 */
public class BuffTaskQueue {
	private TaskCompare taskCompare=new TaskCompare();
	private Map<String,BuffTask> taskMap = new HashMap<String,BuffTask>();
	private Queue<BuffTask> taskQueue=new PriorityBlockingQueue<BuffTask>(SimuiationConstant.PriorityQueue_Capacity, taskCompare);   //优先级队列
	
	/**
	 * 添加任务
	 * @param task
	 */
	public void addTask(BuffTask task) {
		taskMap.put(task.getTaskId(), task);
		taskQueue.add(task);
	}
	
	/**
	 * 返回当前可执行任务
	 * @return
	 */
	public ITask poll(){
		BuffTask retTask = null;
		if(!taskQueue.isEmpty() && taskQueue.peek().canExcute()){
			retTask = taskQueue.poll();
			if(retTask.getOverTime() <= retTask.getClock().getCurrentSTime().getMSTime()){
				taskMap.remove(retTask.getTaskId());
			}else{
				taskQueue.add(retTask);
			}
		}
		return retTask;
	}
	
	/**
	 * 删除任务
	 * @param taskId
	 */
	public boolean removeTask(String taskId){
		BuffTask task = taskMap.get(taskId);
		boolean bRet = taskQueue.remove(task);
		if(bRet){
			taskMap.remove(taskId);
			return true;
		}
		return false;
	}
	/**
	 * 清除全部任务队列
	 */
	public void clear() {
		taskQueue.clear();
		taskMap.clear();
	}
}
