package com.cwa.simuiationimpl.manage;

import java.util.HashMap;
import java.util.Map;

import com.cwa.prototype.BuffPrototype;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.manager.IBuffContextFactory;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.buff.BuffContext;

public class BuffContextFactory implements IBuffContextFactory {
	private Map<Integer, ISBuff> buffMap = new HashMap<Integer, ISBuff>();
	@Override
	public IBuffContext createContext(int buffType, IPerformer performer, BuffPrototype buffPrototype, SimuiationTime startTime) {
		BuffContext context = new BuffContext();
		context.setBuff(buffMap.get(buffType));
		context.setBuffPrototype(buffPrototype);
		context.setStartTime(startTime);
		context.setPerformer(performer);
		return context;
	}

	public void setBuffMap(Map<Integer, ISBuff> buffMap) {
		this.buffMap = buffMap;
	}
}
