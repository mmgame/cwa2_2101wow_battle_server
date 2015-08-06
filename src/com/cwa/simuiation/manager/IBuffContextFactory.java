package com.cwa.simuiation.manager;

import com.cwa.prototype.BuffPrototype;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.obj.IPerformer;

/**
 * buff上下文工厂
 * 
 * @author mausmars
 * 
 */
public interface IBuffContextFactory {
	/**
	 * 创建buff上下文
	 * @param buffType
	 * @param performer
	 * @param buffPrototype
	 * @param startTime
	 * @return
	 */
	IBuffContext createContext(int buffType, IPerformer performer, BuffPrototype buffPrototype,SimuiationTime startTime);
}
