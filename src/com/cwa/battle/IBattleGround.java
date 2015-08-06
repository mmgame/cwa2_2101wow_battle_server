package com.cwa.battle;

import java.util.List;
import java.util.Map;

import serverice.room.FightList;

import com.cwa.battle.context.IBattleServiceContext;
import com.cwa.battle.state.IStateManager;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.IServerSimuiation;
import com.cwa.simuiation.event.ISEventHandlerManager;
import com.cwa.simuiationimpl.manage.UserFightList;

/**
 * 战场抽象类
 * 
 * @author mausmars
 * 
 */
public interface IBattleGround {
	/**
	 * 获得战场id
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 获得战场KeyId
	 * 
	 * @return
	 */
	int getKeyId();

	/**
	 * 启动战场仿真
	 * 
	 * @param userIds
	 */
	void startupSimuiation();

	/**
	 * 停止战场仿真
	 * 
	 * @param userIds
	 */
	void shutdownSimuiation();

	/**
	 * 链接战场
	 * 
	 * @return
	 */
	void attach(IClientSimuiation clientSimuiation);

	/**
	 * 设置战场上下文
	 * 
	 * @param battleContext
	 */
	void setBattleContext(IBattleServiceContext battleContext);

	/**
	 * 设置服务仿真
	 * 
	 * @param battleContext
	 */
	void setSimuiation(IServerSimuiation simuiation);

	/**
	 * 战场状态管理
	 * 
	 * @return
	 */
	IStateManager getStateManager();

	/**
	 * 仿真器
	 * 
	 * @return
	 */
	IServerSimuiation getSimuiation();

	/**
	 * 战场上下文
	 * 
	 * @return
	 */
	IBattleServiceContext getBattleContext();

	/**
	 * 战场仿真器事件管理
	 * 
	 * @return
	 */
	ISEventHandlerManager getSEventHandlerManager();

	/**
	 * 批量插入战斗列表
	 * 
	 * @param fightList
	 */
	void insertFightLists(List<FightList> fightLists);

	/**
	 * 
	 * 得到全部战斗列表
	 * 
	 * @return
	 */
	Map<Long, UserFightList> getUserFightListMap();
	/**
	 * 获得战场创建时间（系统时间）
	 * 
	 * @return
	 */
	long getBattleCreatTime();
	void setBattleCreateTime(long createTime);
}
