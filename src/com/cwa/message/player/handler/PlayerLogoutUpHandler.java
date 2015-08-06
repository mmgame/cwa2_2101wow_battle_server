package com.cwa.message.player.handler;

import com.cwa.ISession;
import com.cwa.message.PlayerMessage.PlayerLogoutUp;
import com.cwa.message.player.APlayerMessageHandler;

public class PlayerLogoutUpHandler extends APlayerMessageHandler<PlayerLogoutUp> {
	@Override
	public void loginedHandler(PlayerLogoutUp message, ISession session) {
		if (logger.isInfoEnabled()) {
			logger.info("关闭session，用户登出！！！");
		}
		session.close(true);
	}
}