package com.cwa.simuiationimpl.obj;

import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 宠物
 * 
 * @author mausmars
 * 
 */
public class Pet extends AActor {
	private IPerformer source;
	@Override
	public int getType() {
		return ObjTypeEnum.Performer.value();
	}

	@Override
	public int getSubType() {
		return ObjSubTypeEnum.P_Pet.value();
	}

	public IPerformer getSource() {
		return source;
	}

	public void setSource(IPerformer source) {
		this.source = source;
	}
}
