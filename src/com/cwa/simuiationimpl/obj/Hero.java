package com.cwa.simuiationimpl.obj;

import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;

/**
 * 英雄
 * 
 * @author mausmars
 * 
 */
public class Hero extends AActor {
	@Override
	public int getType() {
		return ObjTypeEnum.Performer.value();
	}

	@Override
	public int getSubType() {
		return ObjSubTypeEnum.P_Hero.value();
	}
}
