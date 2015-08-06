package com.cwa.simuiationimpl.map;

import com.cwa.simuiation.manager.IAreaMgr;
import com.cwa.simuiation.map.Point;

/**
 * 区域管理
 * 
 * @author mausmars
 * 
 */
public class AreaMgr implements IAreaMgr {
	private Point point;
	@Override
	public Point getSize() {
		return point;
	}
	//---------------------------------------
	public void setPoint(Point point) {
		this.point = point;
	}
	

}
