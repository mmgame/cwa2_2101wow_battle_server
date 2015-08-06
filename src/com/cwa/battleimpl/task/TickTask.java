package com.cwa.battleimpl.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.battle.task.ITaskCallback;
import com.cwa.component.task.ITask;
import com.cwa.component.task.ITaskContext;
import com.cwa.simuiation.ISimuiation;

/**
 * 仿真的update任务
 * 
 * @author mausmars
 * 
 */
public class TickTask implements ITask {
	protected static final Logger logger = LoggerFactory.getLogger(TickTask.class);

	private ISimuiation simuiation;
	// 任务回调
	protected ITaskCallback taskCallback;

	public TickTask(ISimuiation simuiation) {
		this.simuiation = simuiation;
	}

	@Override
	public String id() {
		return simuiation.getId() + "_" + simuiation.getClock().getCurrentFrameCount();
	}

	@Override
	public void execute(ITaskContext context) {
		Object result = null;
		try {
//			if (logger.isInfoEnabled()) {
//				logger.info("TickTask doWork!");
//			}
			result = simuiation.tick();
		} catch (Exception e) {
			logger.error("", e);
		}
		if (taskCallback != null) {
			taskCallback.overTask(result);
		}
	}

	// --------------------------------

	public void setTaskCallback(ITaskCallback taskCallback) {
		this.taskCallback = taskCallback;
	}
}
