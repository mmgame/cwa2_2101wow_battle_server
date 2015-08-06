package com.cwa.message.battle.handler;

import com.cwa.ISession;
import com.cwa.message.BattleMessage.MoveActionUp;
import com.cwa.message.battle.ABattleMessageHandler;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MessageActionContext;

/**
 * 移动动作
 * 
 * @author mausmars
 * 
 */
public class MoveActionUpHandler extends ABattleMessageHandler<MoveActionUp> {
	@Override
	public void loginedHandler(MoveActionUp message, ISession session) {
		if (logger.isInfoEnabled()) {
			logger.info("MoveActionUpHandler sessionId=" + session.getSessionId());
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
		context.setType(StateSubTypeEnum.AS_Move.value());
		// 动作请求
		clientSimuiation.sendActionRequest(context);
	}
}
