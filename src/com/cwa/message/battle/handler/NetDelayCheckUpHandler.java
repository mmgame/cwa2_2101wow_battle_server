package com.cwa.message.battle.handler;

import com.cwa.ISession;
import com.cwa.component.netdelaycheck.CheckStepType;
import com.cwa.component.netdelaycheck.DelayInfo;
import com.cwa.message.BattleMessage.NetDelayCheckDown;
import com.cwa.message.BattleMessage.NetDelayCheckUp;
import com.cwa.message.battle.ABattleMessageHandler;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiationimpl.ClientSimuiation;

public class NetDelayCheckUpHandler extends ABattleMessageHandler<NetDelayCheckUp> {

	@Override
	public void loginedHandler(NetDelayCheckUp message, ISession session) {
		ClientSimuiation clientSimuiation = (ClientSimuiation) session.getAttachment(IClientSimuiation.class.getName());
		int step = message.getStep();
		if (step == CheckStepType.CS_RequestServerTime.value()) {
			DelayInfo delayInfo = clientSimuiation.getNetDelayCheckServer().requestServerTime(message.getCTime());
			NetDelayCheckDown.Builder netDelayCheckDown = NetDelayCheckDown.newBuilder();
			netDelayCheckDown.setCTime(delayInfo.getCtime());
			netDelayCheckDown.setVersion(delayInfo.getVersion());
			session.send(netDelayCheckDown.build());
			return;
		} else if (step == CheckStepType.CS_ReplyClientDelay.value()) {
			clientSimuiation.getNetDelayCheckServer().replyClientDelay(message.getVersion(), message.getCTime());
			if (logger.isInfoEnabled()) {
				long delayTime=clientSimuiation.getNetDelayCheckServer().getDelayTime();
				if (delayTime>100) {
					logger.info("id:"+clientSimuiation.getId()+"  DelayTime=="+delayTime);
				}
				
			}
		}
	}
}
