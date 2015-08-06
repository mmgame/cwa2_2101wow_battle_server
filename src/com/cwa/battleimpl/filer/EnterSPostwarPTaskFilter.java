package com.cwa.battleimpl.filer;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.prototype.BattlePrototype;

/**
 * 进入战后等待
 * 
 * @author mausmars
 * 
 */
public class EnterSPostwarPTaskFilter extends ABattleFilter {
	public EnterSPostwarPTaskFilter() {
		super("EnterSPostwarPTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("EnterSPostwarPTaskFilter doWork!");
		}
		IBattleGround battleGround = (IBattleGround) context;
		// 通知客户端战场结束准备阶段
		sendStateChangeEvent(battleGround, BattleStateTypeEnum.SPostwarP);
//		stateChangeTask(battleGround, BattleStateTypeEnum.SPostwarP, 0);
		setStateChangeTask(battleGround);
		return true;
	}
	
	private void setStateChangeTask(IBattleGround battleGround) {
		// 设置进入战中任务
		BattlePrototype battlePrototype = battleGround.getSimuiation().getProtpyeMgr().getBattlePrototype();
		int waitTime = battlePrototype.getPostWtime();
		stateChangeTask(battleGround, BattleStateTypeEnum.SPostwar, waitTime);
	}
}
