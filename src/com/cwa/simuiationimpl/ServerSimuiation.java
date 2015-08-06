package com.cwa.simuiationimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.component.event.ILocalEvent;
import com.cwa.component.event.ILocalEventListener;
import com.cwa.simuiation.IServerSimuiation;
import com.cwa.simuiation.ISimuiation;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.event.ISEventHandlerManager;
import com.cwa.simuiation.manager.IActionContextFactory;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.manager.IAreaMgr;
import com.cwa.simuiation.manager.IBuffContextFactory;
import com.cwa.simuiation.manager.IClientMgr;
import com.cwa.simuiation.manager.IDistanceMgr;
import com.cwa.simuiation.manager.IEventMgr;
import com.cwa.simuiation.manager.IObjFactory;
import com.cwa.simuiation.manager.IPrototypeMgr;
import com.cwa.simuiation.manager.IScheduleMgr;
import com.cwa.simuiation.manager.ISobMgr;
import com.cwa.simuiation.manager.IStateContextFactory;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiation.task.Task;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESTimeOverEvent;
import com.cwa.simuiationimpl.obj.AActor;

/**
 * server 仿真，相当于战场战中服务
 * 
 * @author mausmars
 * 
 */
public class ServerSimuiation implements IServerSimuiation {
	protected static final Logger logger = LoggerFactory.getLogger(ServerSimuiation.class);
	// ---------------------
	private ISobMgr sobMgr;// 仿真对象管理者
	private IAreaMgr areaMgr;// 地图管理
	private IScheduleMgr scheduleMgr; // 任务管理
	private ISEventHandlerManager eventHandlerManager;// 事件处理管理
	private IStateContextFactory stateContextFactory;// 状态上下工厂
	private IBuffContextFactory buffContextFactory;// buff上下文工厂
	private IObjFactory objFactory;// 对象工厂
	private IPrototypeMgr protpyeMgr;// 原型查询
	private IClientMgr clientMgr;// 客户端管理
	private IActionContextHandler actionContextHandler;// 动作消息处理器
	private IEventMgr eventMgr;// 事件管理
	private IActionContextFactory actionContextFactory;// 动作上下文工厂
	private IEventTransition eventTransition;// 事件转换
	private IDistanceMgr distanceMgr;// 距离管理

	// 仿真器时钟
	private SClock clock;
	// ---------------------
	// 仿真器key（代表战场id）
	private String id;
	// 仿真器结束事件
	// private ESOverEvent overEvent = ESOverEvent.getInstance();

	private Map<Object, ILocalEventListener> eventListenerMap = new HashMap<Object, ILocalEventListener>();

	private boolean isOver;

	public ServerSimuiation(String key) {
		this.id = key;
	}

	@Override
	public void receiveActionRequest(IActionContext context) {
		AActor actor = (AActor) context.getPerformer();
		if (actor != null) {
			// 调用执行者加入动作
			actor.requestAction(context);
		}
	}

	@Override
	public void attach(ISimuiation aSimuiation) {
		clientMgr.join(aSimuiation);
		aSimuiation.attach(this);
	}

	@Override
	public void detach(String controller) {
		clientMgr.leave(controller);
	}

	@Override
	public int tick() {
		// 时钟update开始
		clock.updateStart();

		long startTime = System.currentTimeMillis();
		try {
			// 移除可移除物体
			removeObj();
			// 处理动作
			performActions();
			// 缓存距离
			distanceMgr.cache();
			// 处理事件
			eventMgr.processEvents();
			// 物体update
			updateObj();
			// 处理任务
			scheduleMgr.processTasks();

			// 清空计算过的距离
			distanceMgr.reset();
		} catch (Exception e) {
			logger.error("Simuiation tick is error! key=" + id, e);
		}

		long ctime = System.currentTimeMillis() - startTime;
		if (ctime > 0) {
			logger.info("update 消耗时间 =" + ctime);
		}
		return clock.updateOver();
	}

	private void performActions() {
		performActions(sobMgr.getSObjectListBySubType(ObjSubTypeEnum.P_Hero.value()));
		performActions(sobMgr.getSObjectListBySubType(ObjSubTypeEnum.P_Pet.value()));
	}

	private void performActions(Set<ISObject> sobList) {
		if (sobList != null) {
			// 处理Action
			for (ISObject obj : sobList) {
				IPerformer p = (IPerformer) obj;
				p.performActions();
			}
		}
	}

	private void updateObj() {
		updateObj(sobMgr.getSObjectListBySubType(ObjSubTypeEnum.P_Hero.value()));
		updateObj(sobMgr.getSObjectListBySubType(ObjSubTypeEnum.P_Pet.value()));
		updateObj(sobMgr.getSObjectListBySubType(ObjSubTypeEnum.P_Bullet.value()));
		updateObj(sobMgr.getSObjectListBySubType(ObjSubTypeEnum.P_Trap.value()));
	}

	private void updateObj(Set<ISObject> sobList) {
		if (sobList != null) {
			// 处理Obj
			for (ISObject obj : sobList) {
				IPerformer p = (IPerformer) obj;
				p.update();
			}
		}
	}

	private void removeObj() {
		sobMgr.remove();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public SClock getClock() {
		return clock;
	}

	@Override
	public void startup() {
		if (logger.isInfoEnabled()) {
			logger.info("Simuiation startup! sid=" + id);
		}
		// 启动仿真
		clock.startup();
		// 设置仿真器时间结束事件
		setSTimeOverTask();
	}

	@Override
	public void shutdown() {
		isOver = true;
		// 停止仿真
		if (logger.isInfoEnabled()) {
			logger.info("Simuiation shutdown! sid=" + id);
		}
	}

	@Override
	public ISobMgr getSobMgr() {
		return sobMgr;
	}

	@Override
	public IAreaMgr getAreaMgr() {
		return areaMgr;
	}

	@Override
	public IPrototypeMgr getProtpyeMgr() {
		return protpyeMgr;
	}

	@Override
	public IScheduleMgr getScheduleMgr() {
		return scheduleMgr;
	}

	@Override
	public IClientMgr getClientMgr() {
		return clientMgr;
	}

	@Override
	public IStateContextFactory getStateContextFactory() {
		return stateContextFactory;
	}

	@Override
	public IBuffContextFactory getBuffContextFactory() {
		return buffContextFactory;
	}

	@Override
	public ISEventHandlerManager getSEventHandlerManager() {
		return eventHandlerManager;
	}

	@Override
	public IObjFactory getObjFactory() {
		return objFactory;
	}

	@Override
	public IEventMgr getEventMgr() {
		return eventMgr;
	}

	@Override
	public IActionContextHandler getMessageActionContextHandler() {
		return actionContextHandler;
	}

	@Override
	public IActionContextFactory getActionContextFactory() {
		return actionContextFactory;
	}

	@Override
	public IEventTransition getEventTransition() {
		return eventTransition;
	}

	public boolean isOver() {
		return isOver;
	}

	@Override
	public IDistanceMgr getDistanceMgr() {
		return distanceMgr;
	}

	private void setSTimeOverTask() {
		int battleOverTime = protpyeMgr.getBattlePrototype().getBattleTime();
		Task overTask = new Task(SimuiationConstant.STimeOverTaskKey, this) {
			@Override
			public void excute() {
				if (logger.isErrorEnabled()) {
					logger.error("excute:--->"+clock.getCurrentSTime().getMSTime());
				}
				// 发送结束事件
				ESTimeOverEvent event = new ESTimeOverEvent();
				sendEvent(SimuiationConstant.ListenerKey_System, event);
			}
		};
		if (logger.isErrorEnabled()) {
			logger.error("clock--->"+clock.getCurrentSTime().getMSTime()+"   battleOverTime--->"+battleOverTime+"    over-->"+(clock.getCurrentSTime().getMSTime()+battleOverTime));
			logger.error("SimuiationConstant---->"+SimuiationConstant.SystemUpdateTime);
		}
		overTask.setClock(clock, battleOverTime);
		scheduleMgr.addTask(overTask);
	}

	@Override
	public void register(ILocalEventListener listener) {
		eventListenerMap.put(listener.key(), listener);
	}

	@Override
	public void unregister(Object key) {
		eventListenerMap.remove(key);
	}

	@Override
	public void sendEvent(ILocalEvent event) {
		for (ILocalEventListener listener : eventListenerMap.values()) {
			listener.answer(event);
		}
	}

	@Override
	public void sendEvent(Object listenerType, ILocalEvent event) {
		ILocalEventListener listener = eventListenerMap.get(listenerType);
		if (listener != null) {
			listener.answer(event);
		}
	}

	// ----------------------------
	public void setId(String id) {
		this.id = id;
	}

	public void setClock(SClock clock) {
		this.clock = clock;
	}

	public void setSobMgr(ISobMgr sobMgr) {
		this.sobMgr = sobMgr;
	}

	public void setAreaMgr(IAreaMgr areaMgr) {
		this.areaMgr = areaMgr;
	}

	public void setScheduleMgr(IScheduleMgr scheduleMgr) {
		this.scheduleMgr = scheduleMgr;
	}

	public void setEventHandlerManager(ISEventHandlerManager eventHandlerManager) {
		this.eventHandlerManager = eventHandlerManager;
	}

	public void setStateContextFactory(IStateContextFactory stateContextFactory) {
		this.stateContextFactory = stateContextFactory;
	}

	public void setBuffContextFactory(IBuffContextFactory buffContextFactory) {
		this.buffContextFactory = buffContextFactory;
	}

	public void setPrototypeMgr(IPrototypeMgr protpyeMgr) {
		this.protpyeMgr = protpyeMgr;
	}

	public void setObjFactory(IObjFactory objFactory) {
		this.objFactory = objFactory;
	}

	public void setProtpyeMgr(IPrototypeMgr protpyeMgr) {
		this.protpyeMgr = protpyeMgr;
	}

	public void setClientMgr(IClientMgr clientMgr) {
		this.clientMgr = clientMgr;
	}

	public void setActionContextHandler(IActionContextHandler actionContextHandler) {
		this.actionContextHandler = actionContextHandler;
	}

	public void setEventMgr(IEventMgr eventMgr) {
		this.eventMgr = eventMgr;
	}

	public void setActionContextFactory(IActionContextFactory actionContextFactory) {
		this.actionContextFactory = actionContextFactory;
	}

	public void setEventTransition(IEventTransition eventTransition) {
		this.eventTransition = eventTransition;
	}

	public void setDistanceMgr(IDistanceMgr distanceMgr) {
		this.distanceMgr = distanceMgr;
	}
}
