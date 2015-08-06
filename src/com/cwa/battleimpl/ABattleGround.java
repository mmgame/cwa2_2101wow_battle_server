package com.cwa.battleimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import serverice.room.FightList;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.context.IBattleServiceContext;
import com.cwa.battle.state.IStateManager;
import com.cwa.battle.task.ITaskCallback;
import com.cwa.battleimpl.task.TickTask;
import com.cwa.component.task.ITaskManager;
import com.cwa.component.task.ITaskTypeConfig;
import com.cwa.component.task.quartz.config.ITaskTypeConfigFactory;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.IServerSimuiation;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.event.ISEventHandlerManager;
import com.cwa.simuiationimpl.manage.UserFightList;
import com.cwa.util.TimeUtil;

/**
 * 抽象工厂类
 * 
 * @author mausmars
 * 
 */
public abstract class ABattleGround implements IBattleGround {
	// 战场id
	protected String id;
	// 战场KeyId
	protected int keyId;
	// 战场创建时间
	protected long creatTime;
	// 战场仿真器
	protected IServerSimuiation simuiation;
	// 战场仿真器监听器
	protected ISEventHandlerManager eventHandlerManager;
	// 战场状态管理
	protected IStateManager stateManager;
	// 战场上下文
	protected IBattleServiceContext battleContext;

	protected volatile boolean isOver = false;
	// -----------------------------
	private ITaskManager taskManager;
	private ITaskTypeConfigFactory taskTypeConfigFactory;

	private Map<Long, UserFightList> userFightListMap = new HashMap<Long, UserFightList>();

	public ABattleGround(String id, int keyId) {
		this.id = id;
		this.keyId = keyId;
		this.creatTime = TimeUtil.currentSystemTime();
	}

	public void attach(IClientSimuiation clientSimuiation) {
		simuiation.attach(clientSimuiation);
	}

	@Override
	public void startupSimuiation() {
		// 初始化任务
		initTickTask();
		// 开始仿真器
		simuiation.startup();
		// 启动仿真器tick任务
		resetTickTask(SClock.getFrameInterval());
	}

	@Override
	public void shutdownSimuiation() {
		simuiation.shutdown();
		// 删除任务
		setOver(true);
		taskManager.deleteTask(task.id());
	}

	private TickTask task;

	private void initTickTask() {
		task = new TickTask(simuiation);
		task.setTaskCallback(new ITaskCallback() {
			@Override
			public void overTask(Object param) {
				// 结束更新任务
				resetTickTask((Integer) param);
			}
		});
	}

	private void resetTickTask(int intervalTime) {
		if (!isOver()) {
			long startTime = TimeUtil.currentSystemTime() + intervalTime;
			ITaskTypeConfig taskType = taskTypeConfigFactory.createSimpleTypeConfig(startTime, 0, 0, 0);
			taskManager.addTask(task, taskType);
		}
	}

	@Override
	public void insertFightLists(List<FightList> fightLists) {
		for (FightList fightList : fightLists) {
			insertFightList(fightList);
		}
	}

	private void insertFightList(FightList fightList) {
		UserFightList userFightList = new UserFightList();
		userFightList.init(fightList);
		userFightListMap.put(fightList.uid, userFightList);
	}

	@Override
	public int getKeyId() {
		return keyId;
	}

	@Override
	public long getBattleCreatTime() {
		return creatTime;
	}

	public void setBattleCreateTime(long createTime){
		this.creatTime = createTime;
	}
	@Override
	public String getId() {
		return id;
	}

	@Override
	public IServerSimuiation getSimuiation() {
		return simuiation;
	}

	@Override
	public IStateManager getStateManager() {
		return stateManager;
	}

	private boolean isOver() {
		return isOver;
	}

	private void setOver(boolean isOver) {
		synchronized (this) {
			this.isOver = isOver;
		}
	}

	@Override
	public IBattleServiceContext getBattleContext() {
		return battleContext;
	}

	@Override
	public ISEventHandlerManager getSEventHandlerManager() {
		return eventHandlerManager;
	}

	@Override
	public Map<Long, UserFightList> getUserFightListMap() {
		return userFightListMap;
	}

	// -----------------------------------------
	public void setSimuiation(IServerSimuiation simuiation) {
		this.simuiation = simuiation;
	}

	public void setStateManager(IStateManager stateManager) {
		this.stateManager = stateManager;
	}

	public void setBattleContext(IBattleServiceContext battleContext) {
		this.battleContext = battleContext;
	}

	public void setTaskManager(ITaskManager taskManager) {
		this.taskManager = taskManager;
	}

	public void setTaskTypeConfigFactory(ITaskTypeConfigFactory taskTypeConfigFactory) {
		this.taskTypeConfigFactory = taskTypeConfigFactory;
	}

	public void setEventHandlerManager(ISEventHandlerManager eventHandlerManager) {
		this.eventHandlerManager = eventHandlerManager;
	}
}
