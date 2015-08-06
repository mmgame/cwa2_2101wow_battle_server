package com.cwa.message.battle.handler;

import com.cwa.ISession;
import com.cwa.battle.IBattleGround;
import com.cwa.message.battle.ABattleMessageHandler;
import com.cwa.message.BattleMessage.SyncBattleDown;
import com.cwa.message.BattleMessage.SyncBattleUp;

public class SyncBattleUpHandler extends ABattleMessageHandler<SyncBattleUp> {

	@Override
	public void loginedHandler(SyncBattleUp message, ISession session) {
		if (logger.isInfoEnabled()) {
			logger.info("SyncBattleUpHandler sessionId=" + session.getSessionId());
		}
		String battleId = message.getBattleId();
		String userId = message.getUserId();
		IBattleGround battleGround = battleService.getBattleGround(battleId);
		if (battleGround == null) {
			// 要的战场不存在，断开连接
			session.close(true);
			return;
		}
		SyncBattleDown syncBattleDown = getSyncBattleDown(battleGround);
		session.send(syncBattleDown);
	}
}
