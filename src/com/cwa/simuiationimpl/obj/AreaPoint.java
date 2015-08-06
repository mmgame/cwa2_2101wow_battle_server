package com.cwa.simuiationimpl.obj;

import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IArea;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 区域点
 * 
 * @author mausmars
 * 
 */
public class AreaPoint implements IArea {
	private Position position;

	@Override
	public int getType() {
		return ObjSubTypeEnum.Area_Point.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getSubType() {
		return ObjSubTypeEnum.Area_Point.value();
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public boolean isPassable() {
		return true;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public int getSize() {
		return 0;
	}

	public AreaPoint cloneObj() {
		AreaPoint areaPoint = new AreaPoint();
		areaPoint.position = (Position) position.cloneObj();
		return areaPoint;
	}

	// --------------------------------
	public void setPosition(Position position) {
		this.position = position;
	}

}
