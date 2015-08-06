package com.cwa.simuiationimpl.buff;

import com.cwa.prototype.BuffPrototype;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.obj.IPerformer;
/**
 * buff上下文
 * @author yangfeng
 *
 */
public class BuffContext implements IBuffContext{
	private BuffPrototype buffPrototype;
	private SimuiationTime startTime;
	private IPerformer performer;
	private ISBuff buff;

	@Override
	public int getId() {
		return buffPrototype.getKeyId();
	}

	@Override
	public SimuiationTime getStartTime() {
		return startTime;
	}
	
	@Override
	public ISBuff getBuff() {
		return buff;
	}

	public void setBuffPrototype(BuffPrototype buffPrototype) {
		this.buffPrototype = buffPrototype;
	}

	public void setStartTime(SimuiationTime startTime) {
		this.startTime = startTime;
	}


	public void setBuff(ISBuff buff) {
		this.buff = buff;
	}

	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	public void setPerformer(IPerformer performer) {
		this.performer = performer;
	}

	@Override
	public BuffPrototype getBuffPrototype() {
		return buffPrototype;
	}
}
