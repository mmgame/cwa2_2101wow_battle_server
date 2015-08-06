package com.cwa.battleimpl.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.service.context.IGloabalContext;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.IEventHandler;
import com.cwa.simuiation.event.ISEvent;

/**
 * 仿真器时间结束事件
 * 
 * @author mausmars
 *
 */
public class ESTimeOverEventHandler implements IEventHandler {
	protected static final Logger logger = LoggerFactory.getLogger(IEventHandler.class);

	private IBattleGround battleGround;
	private ABattleFilter battleFilter;

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_TimeOver.value();
	}

	@Override
	public void handler(ISEvent event) {
		// 关闭仿真器
		battleGround.shutdownSimuiation();

		// 这里创建进入战后等待任务
		if (battleFilter != null) {
			try {
				battleFilter.doWork(battleGround);
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	// ----------------------------------
	public void setBattleGround(IBattleGround battleGround) {
		this.battleGround = battleGround;
	}

	public void setBattleFilter(ABattleFilter battleFilter) {
		this.battleFilter = battleFilter;
	}
}
