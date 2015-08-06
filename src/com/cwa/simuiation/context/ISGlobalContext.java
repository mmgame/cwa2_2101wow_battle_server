package com.cwa.simuiation.context;

import com.cwa.simuiation.clock.SClock;
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

/**
 * 仿真器全局上下文
 * 
 * @author mausmars
 * 
 */
public interface ISGlobalContext {
	/**
	 * 获取滴答声值
	 * 
	 * @return
	 */
	SClock getClock();

	// ----------------------
	/**
	 * 对象管理
	 * 
	 * @return
	 */
	ISobMgr getSobMgr();

	/**
	 * 区域管理
	 * 
	 * @return
	 */
	IAreaMgr getAreaMgr();

	/**
	 * 原型管理
	 * 
	 * @return
	 */
	IPrototypeMgr getProtpyeMgr();

	/**
	 * 任务管理
	 * 
	 * @return
	 */
	IScheduleMgr getScheduleMgr();

	/**
	 * 事件处理器管理
	 * 
	 * @return
	 */
	ISEventHandlerManager getSEventHandlerManager();

	/**
	 * 状态上下文工厂
	 * 
	 * @return
	 */
	IStateContextFactory getStateContextFactory();

	/**
	 * buff上下文工厂
	 * 
	 * @return
	 */
	IBuffContextFactory getBuffContextFactory();

	/**
	 * 动作上下文工厂
	 * 
	 * @return
	 */
	IActionContextFactory getActionContextFactory();

	/**
	 * 对象工厂
	 * 
	 * @return
	 */
	IObjFactory getObjFactory();

	/**
	 * 客户端管理
	 * 
	 * @return
	 */
	IClientMgr getClientMgr();

	/**
	 * MessageActionContext处理器
	 * 
	 * @return
	 */
	IActionContextHandler getMessageActionContextHandler();

	/**
	 * 事件管理
	 * 
	 * @return
	 */
	IEventMgr getEventMgr();

	/**
	 * 消息转换
	 * 
	 * @return
	 */
	IEventTransition getEventTransition();

	/**
	 * 距离管理接口
	 * 
	 * @return
	 */
	IDistanceMgr getDistanceMgr();
}
