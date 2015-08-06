package com.cwa.simuiation;

import com.cwa.simuiation.context.ISGlobalContext;

/**
 * 仿真器接口
 * 
 * @author mausmars
 * 
 */
public interface ISimuiation extends ISGlobalContext {
	/**
	 * id
	 */
	String getId();

	/**
	 * 启动仿真运行
	 */
	void startup();

	/**
	 * 停止仿真运行
	 */
	void shutdown();

	/**
	 * 滴答声（用外部控制仿真器的节奏）
	 * 
	 * @return 这里返回下一次执行的间隔
	 */
	int tick();

	/**
	 * 链接用户（这里可以是观察者，也可以是比赛玩家，战场监听者）
	 */
	void attach(ISimuiation aSimuiation);

	/**
	 * 断开用户
	 */
	void detach(String key);
}