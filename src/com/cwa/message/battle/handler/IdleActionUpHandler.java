
package com.cwa.message.battle.handler;

import com.cwa.ISession;
import com.cwa.message.battle.ABattleMessageHandler;
import com.cwa.message.BattleMessage.IdleActionUp;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MessageActionContext;

public class IdleActionUpHandler extends ABattleMessageHandler<IdleActionUp>{
	
	@Override
	public void loginedHandler(IdleActionUp message,ISession session) {
		if (logger.isInfoEnabled()) {
			logger.info("IdleActionUpHandler sessionId=" + session.getSessionId());
		}
		
		IClientSimuiation clientSimuiation = (IClientSimuiation) session.getAttachment(IClientSimuiation.class.getName());
		if (clientSimuiation == null) {
			return;
		}
		// 执行者id
		int performerId = message.getPerformerId();

		ISObject p = clientSimuiation.getSobMgr().select(performerId);
		if (!(p instanceof IPerformer)) {
			return;
		}
		
		MessageActionContext context = new MessageActionContext();
		context.setMessage(message);
		context.setPerformer((IPerformer) p);
		context.setClientSimuiation(clientSimuiation);
		context.setType(StateSubTypeEnum.AS_Idle.value());
		// 动作请求
		clientSimuiation.sendActionRequest(context);
	}
}

