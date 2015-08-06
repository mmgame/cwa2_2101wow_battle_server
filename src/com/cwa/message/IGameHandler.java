/**
 * 
 */
package com.cwa.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.ISession;
import com.cwa.handler.IMessageHandler;
import com.cwa.net.ISessionManager;

/**
 * @author tzy
 * 
 */
public abstract class IGameHandler<T> implements IMessageHandler<T> {
	protected static final Logger logger = LoggerFactory.getLogger(IGameHandler.class);

	@Override
	public void handle(T message, ISession session) {
		Long userId = (Long) session.getAttachment(ISessionManager.Target_Key);
		try {
			if (userId == null) {// 用户登陆
				Object obj = unloginHandler(message, session);
				if (obj != null) {
					long targetId = (long) obj;
					if (logger.isInfoEnabled()) {
						logger.info("User register userId=" + targetId);
					}
				}
				return;
			} else {
				loginedHandler(message, session);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
		}
	}

	public Object unloginHandler(T message, ISession session) {
		logger.error("Error user state!  login!" + message);
		return null;
	}

	public void loginedHandler(T message, ISession session) {
		logger.error("Error user state! no login!" + message);
	}
}