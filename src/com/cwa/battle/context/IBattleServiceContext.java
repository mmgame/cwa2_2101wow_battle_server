package com.cwa.battle.context;

import com.cwa.battle.task.IBScheduleMgr;

/**
 * 战场服务上下文
 * 
 * @author mausmars
 * 
 */
public interface IBattleServiceContext {
	/**
	 * 调度管理
	 * 
	 * @return
	 */
	IBScheduleMgr getScheduleMgr();
}
