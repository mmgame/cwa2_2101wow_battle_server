package com.cwa.simuiationimpl.manage;

import java.util.HashMap;
import java.util.Map;

import com.cwa.simuiation.enums.AxisTyeEunm;
import com.cwa.simuiation.manager.IDistanceMgr;
import com.cwa.simuiation.map.Point;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.util.Math2D;

public class DistanceMgr2D implements IDistanceMgr {
	private Map<String, Double> distanceMap = new HashMap<String, Double>();
	private boolean isCache = false;

	@Override
	public void cache() {
		isCache = true;
	}

	@Override
	public double getDistance(ISObject s, ISObject t) {
		if (s.getId() == t.getId()) {
			return 0;
		}
		Double distance = null;
		if (isCache) {
			String key = s.getId() > t.getId() ? t.getId() + "_" + s.getId() : s.getId() + "_" + t.getId();

			distance = distanceMap.get(key);
			if (distance == null) {
//				distance = Math2D.getDistance(s.getPosition().getPoint(), t.getPosition().getPoint());
				distance = distance(s.getPosition().getPoint(), t.getPosition().getPoint(), SimuiationConstant.Ellipse_Ratio);
				distanceMap.put(key, distance);
			}
		} else {
//			distance = Math2D.getDistance(s.getPosition().getPoint(), t.getPosition().getPoint());
			distance = distance(s.getPosition().getPoint(), t.getPosition().getPoint(), SimuiationConstant.Ellipse_Ratio);
		}
		return distance;
	}
	
	double distance(Point s,Point t,float coe){
		double distance = Math2D.getDistance(s, t);
		double x = t.getCoordinate(AxisTyeEunm.XAxis) - s.getCoordinate(AxisTyeEunm.XAxis);
		double y = t.getCoordinate(AxisTyeEunm.YAxis) - s.getCoordinate(AxisTyeEunm.YAxis);
		double v = Math.acos(x / Math.sqrt(x*x + y*y));
		if(v > Math.PI /2){
			v = Math.PI - v;
		}
		if(v <= Math.PI/2 && v >= Math.PI/3){
			distance *= coe;
		}else if(v < Math.PI/3 && v >= Math.PI/6){//30~60
			distance = distance * (coe + 1) / 2;
		}else if(v < Math.PI/6 && v >= Math.PI/12){//15~30
			distance = distance * (coe + 2) / 3;
		}else{//夹角小于15度
			
		}
		return distance;
	}

	@Override
	public boolean isCollision(ISObject s, ISObject t, boolean isOverlap, int off) {
		// 中心到中心距离
		double distance = getDistance(s, t);
		return isCollision(s.getSize(), t.getSize(), isOverlap, distance, off);
	}

	@Override
	public boolean isCollision(int ssize, int tsize, boolean isOverlap, double distance, int off) {
		if (!isOverlap) {
			// 不能重叠
			distance -= ssize;
			distance -= tsize;
		}
		return distance <= off;
	}

	@Override
	public Point getMovePoint(ISObject s, ISObject t, double moveDistance) {
		double targetDistance = getDistance(s, t);
		return getMovePoint(s.getPosition(), t.getPosition(), targetDistance, moveDistance);
	}

	@Override
	public Point getMovePoint(Position s, Position t, double distance, double moveDistance) {
		if (moveDistance >= distance) {
			// 到达终点
			return t.getPoint().cloneObj();
		}
		double ratio = moveDistance / (distance < 1 ? 1 : distance);
		double x = s.getCoordinate(AxisTyeEunm.XAxis) - (s.getCoordinate(AxisTyeEunm.XAxis) - t.getCoordinate(AxisTyeEunm.XAxis)) * ratio;
		double y = s.getCoordinate(AxisTyeEunm.YAxis) - (s.getCoordinate(AxisTyeEunm.YAxis) - t.getCoordinate(AxisTyeEunm.YAxis)) * ratio;

		Point point = new Point(SimuiationConstant.Dimensions);
		point.setCoordinate(AxisTyeEunm.XAxis, x);
		point.setCoordinate(AxisTyeEunm.YAxis, y);
		return point;
	}

	@Override
	public void reset() {
		isCache = false;
		distanceMap.clear();
	}
}
