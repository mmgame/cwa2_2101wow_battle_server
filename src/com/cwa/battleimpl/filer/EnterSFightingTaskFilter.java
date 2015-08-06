package com.cwa.battleimpl.filer;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.prototype.BattlePrototype;

/**
 * 进入战中任务（战前等待最后一个链）
 * 
 * @author mausmars
 * 
 */
public class EnterSFightingTaskFilter extends ABattleFilter {
	public EnterSFightingTaskFilter() {
		super("EnterSFightingTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("EnterSFightingTaskFilter doWork!");
		}

		IBattleGround battleGround = (IBattleGround) context;

		// 通知客户端战场开始前等待
		sendStateChangeEvent(battleGround, BattleStateTypeEnum.SFightingP);

		setStateChangeTask(battleGround);
		return true;
	}

	private void setStateChangeTask(IBattleGround battleGround) {
		// 设置进入战中任务
		BattlePrototype battlePrototype = battleGround.getSimuiation().getProtpyeMgr().getBattlePrototype();
		int waitTime = battlePrototype.getFightWtime();
		stateChangeTask(battleGround, BattleStateTypeEnum.SFighting, waitTime);
	}
}
