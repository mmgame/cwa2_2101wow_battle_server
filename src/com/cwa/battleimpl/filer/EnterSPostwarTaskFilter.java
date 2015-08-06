package com.cwa.battleimpl.filer;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.prototype.BattlePrototype;

/**
 * 进入战后任务
 * 
 * @author mausmars
 * 
 */
public class EnterSPostwarTaskFilter extends ABattleFilter {
	public EnterSPostwarTaskFilter() {
		super("EnterSPostwarTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("EnterSPostwarTaskFilter doWork!");
		}
		IBattleGround battleGround = (IBattleGround) context;

		// 通知客户端战场结束准备阶段
		sendStateChangeEvent(battleGround, BattleStateTypeEnum.SPostwarP);

//		setStateChangeTask(battleGround);
		stateChangeTask(battleGround, BattleStateTypeEnum.SPostwar, 0);
		return true;
	}

	private void setStateChangeTask(IBattleGround battleGround) {
		// 设置进入战中任务
		BattlePrototype battlePrototype = battleGround.getSimuiation().getProtpyeMgr().getBattlePrototype();
		int waitTime = battlePrototype.getPostWtime();
		stateChangeTask(battleGround, BattleStateTypeEnum.SPostwar, waitTime);
	}

}
