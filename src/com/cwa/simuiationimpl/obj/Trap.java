package com.cwa.simuiationimpl.obj;

import java.util.Random;

import com.cwa.component.prototype.IPrototype;
import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.TrapPrototype;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.manager.ITrapManager;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 陷阱
 * 
 * @author mausmars
 * 
 */
public class Trap extends PerformerAdapter {
	private TrapPrototype trapPrototype;

	private EffectPrototype effectPrototype;

	private IPerformer source;

	private ITrapManager trapManager;

	private SimuiationTime startTime;

	private SimuiationTime creatTime;
	
	private Random random;

//	private Map<Integer, Integer> excuteCountMap = new HashMap<Integer, Integer>();
//	
//	private List<Integer> insideList = new ArrayList<Integer>();
	
	public int getType() {
		return ObjTypeEnum.Performer.value();
	}

	public ITrapManager getTrapManager() {
		return trapManager;
	};

	@Override
	public int getSubType() {
		return ObjSubTypeEnum.P_Trap.value();
	}

	public EffectPrototype getEffectPrototype() {
		return effectPrototype;
	}

	public TrapPrototype getTrapPrototype() {
		return trapPrototype;
	}

	public IPerformer getSource() {
		return source;
	}

	@Override
	public void update() {
		trapManager.update(this);
	}

	@Override
	public int getSize() {
		return 0;
	}

	public SimuiationTime getStartTime() {
		return startTime;
	}

	public void resetStartTime() {
		startTime = getGlobalContext().getClock().getCurrentSTime();
	}

	public SimuiationTime getCreatTime() {
		return creatTime;
	}

	
//	public Map<Integer, Integer> getExcuteCountMap() {
//		return excuteCountMap;
//	}
//
//	public List<Integer> getInsideList() {
//		return insideList;
//	}

	public Random getRandom() {
		return random;
	}

	// ---------------------------------------------------
	public void setTrapPrototype(IPrototype trapPrototype) {
		this.trapPrototype = (TrapPrototype) trapPrototype;
	}

	public void setEffectPrototype(IPrototype effectPrototype) {
		this.effectPrototype = (EffectPrototype) effectPrototype;
	}

	public void setTrapManager(ITrapManager trapManager) {
		this.trapManager = trapManager;
	}

	public void setSource(IPerformer source) {
		this.source = source;
	}

	public void setStartTime(SimuiationTime startTime) {
		this.startTime = startTime;
	}

	public void setCreatTime(SimuiationTime creatTime) {
		this.creatTime = creatTime;
	}

//	public void setExcuteCountMap(Map<Integer, Integer> excuteCountMap) {
//		this.excuteCountMap = excuteCountMap;
//	}
//
//	public void setInsideList(List<Integer> insideList) {
//		this.insideList = insideList;
//	}

	public void setRandom(Random random) {
		this.random = random;
	}

}
