package com.cwa.simuiationimpl.event.handler.effect;

import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.event.ESMagicEvent;

/**
 * 即死效果（先不做）
 * 
 * @author tzy
 * 
 */
public class ESEffectDeadHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_ImmediatelyDie.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {

	}

}
