package com.cwa.message.battle.handler;

import com.cwa.ISession;
import com.cwa.message.BattleMessage.MagicActionUp;
import com.cwa.message.battle.ABattleMessageHandler;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MessageActionContext;

/**
 * 施法动作
 * 
 * @author mausmars
 * 
 */
public class MagicActionUpHandler extends ABattleMessageHandler<MagicActionUp> {
	@Override
	public void loginedHandler(MagicActionUp message, ISession session) {
		if (logger.isInfoEnabled()) {
			logger.info("MagicActionUpHandler sessionId=" + session.getSessionId());
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
		context.setType(StateSubTypeEnum.AS_Magic.value());

		// 动作请求
		clientSimuiation.sendActionRequest(context);
	}
}
