package com.cwa.simuiation.event;

import com.cwa.prototype.EffectPrototype;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 效果事件
 * 
 * @author mausmars
 * 
 */
public interface ISEffectEvent extends ISEvent {
	/**
	 * 源
	 * 
	 * @return
	 */
	IPerformer getSource();

	/**
	 * 目标
	 * 
	 * @return
	 */
	IPerformer getTarget();

	/**
	 * 效果原型Id
	 * 
	 * @return
	 */
	EffectPrototype getEffectPrototype();

}
