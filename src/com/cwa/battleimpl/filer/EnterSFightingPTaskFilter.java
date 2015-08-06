package com.cwa.battleimpl.filer;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.constant.BattleConstant;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.battleimpl.state.BattleStateTypeEnum;

/**
 * 进入战中等待任务（战前最后一个链）
 * 
 * @author mausmars
 * 
 */
public class EnterSFightingPTaskFilter extends ABattleFilter {
	public EnterSFightingPTaskFilter() {
		super("EnterSFightingPTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("EnterSFightingPTaskFilter doWork!");
		}

		IBattleGround battleGround = (IBattleGround) context;

		// 通知客户端战场开始
		sendStateChangeEvent(battleGround, BattleStateTypeEnum.SPrewar);
		stateChangeTask(battleGround, BattleStateTypeEnum.SFightingP, BattleConstant.SFightingPTaskTime);
		return true;
	}
}
