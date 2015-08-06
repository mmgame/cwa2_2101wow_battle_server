package com.cwa.simuiationimpl.util;

import com.cwa.simuiation.enums.AxisTyeEunm;
import com.cwa.simuiation.map.Point;

public class Math2D {
	/**
	 * 计算2点间距离
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static double getDistance(Point source, Point target) {
		double x = Math.abs(source.getCoordinate(AxisTyeEunm.XAxis) - target.getCoordinate(AxisTyeEunm.XAxis));
		double y = Math.abs(source.getCoordinate(AxisTyeEunm.YAxis) - target.getCoordinate(AxisTyeEunm.YAxis));
		return Math.sqrt(x * x + y * y);
	}
	
	public static void main(String[] args) {
		
		double a[]={0,0};
		double b[]={-5,5};
		
		Point point1 = new Point(b);
		Point point2 = new Point(a);
		
		
		double l=Math2D.getDistance(point1, point2);
		System.out.println(l);
		
		
	}
}
