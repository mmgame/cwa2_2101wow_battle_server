package com.cwa.message.battle.handler;

import java.util.List;

import com.cwa.ISession;
import com.cwa.battle.IBattleGround;
import com.cwa.battle.constant.BattleConstant;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.message.BattleMessage.AttachBattleDown;
import com.cwa.message.BattleMessage.AttachBattleUp;
import com.cwa.message.BattleMessage.SyncBattleDown;
import com.cwa.message.battle.ABattleMessageHandler;
import com.cwa.net.ISessionManager;
import com.cwa.prototype.BattlePrototype;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.util.TimeUtil;

/**
 * 链接战场
 * 
 * @author mausmars
 * 
 */
public class AttachBattleUpHandler extends ABattleMessageHandler<AttachBattleUp> {
	@Override
	public void loginedHandler(AttachBattleUp message, ISession session) {
		String battleId = message.getBattleId();
		String userId = message.getUserId();

		IBattleGround battleGround = battleService.getBattleGround(battleId);
		AttachBattleDown.Builder attachBattleDown = AttachBattleDown.newBuilder();
		if (battleGround == null) {
			// 要的战场不存在，断开连接
			attachBattleDown.setState(0);
			session.send(attachBattleDown.build());
			// session.close(true);
			return;
		}
		Object obj = session.getAttachment(IClientSimuiation.class.getName());
		if (obj != null) {
			// 已经链接过
			attachBattleDown.setState(0);
			session.send(attachBattleDown.build());
			return;
		}

		IClientSimuiation clientSimuiation = battleGround.getSimuiation().getClientMgr().getClientSimuiation(userId);
		if (clientSimuiation == null) {
			// 用户不是这个战场的
			attachBattleDown.setState(0);
			session.send(attachBattleDown.build());
			session.close(true);
			return;
		}
		long u = (long) session.getAttachment(ISessionManager.Target_Key);
		if (logger.isErrorEnabled()) {
			logger.error("message->"+userId+"   session->"+u+"   sessionId->"+session.getSessionId()+"  clientSimuiation->"+session.getAttachment(IClientSimuiation.class.getName()));
		}
		// 保存客户端仿真到session
		session.setAttachment(IClientSimuiation.class.getName(), clientSimuiation);
		
		
		if (logger.isErrorEnabled()) {
			logger.error(""+session.getAttachment(IClientSimuiation.class.getName()));
		}
		// 绑定session
		clientSimuiation.bindSession(session);

		SyncBattleDown syncBattleDown = getSyncBattleDown(battleGround);
		attachBattleDown.setState(1);// 状态 0失败 1成功
		attachBattleDown.setSyncBattleDown(syncBattleDown);
		BattlePrototype battlePrototype = battleGround.getSimuiation().getProtpyeMgr().getBattlePrototype();
		if(syncBattleDown.getBattleBean().getCurrentState() != BattleStateTypeEnum.SFighting.value()){
			attachBattleDown.setCountdown((int) (battlePrototype.getBattleTime()));
		}else{
			attachBattleDown.setCountdown((int) (battlePrototype.getBattleTime() + battlePrototype.getFightWtime() + BattleConstant.SFightingPTaskTime - (TimeUtil.currentSystemTime() - battleGround
					.getBattleCreatTime())));
		}
		session.send(attachBattleDown.build());
	}
}
