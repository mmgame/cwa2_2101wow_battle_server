package com.cwa.simuiationimpl.event.handler.effect;

import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESMagicEvent;

public interface IESEffectHandler {
	/**
	 * 事件类型
	 * 
	 * @return
	 */
	int getEffectType();

	/**
	 * 事件处理
	 * 
	 * @param event
	 */
	void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event);
}
