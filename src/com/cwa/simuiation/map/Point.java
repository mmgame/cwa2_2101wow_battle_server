package com.cwa.simuiation.map;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.cwa.simuiation.enums.AxisTyeEunm;

public class Point {
	// 空间维度
	private int dimensions;

	// 坐标[x,y,z]
	private double[] coordinates;

	private Point() {
	}

	public Point(int dimensions) {
		this.dimensions=dimensions;
		coordinates = new double[dimensions];
	}

	public Point(double[] coordinates) {
		dimensions = coordinates.length;
		this.coordinates = coordinates;
	}

	public Point cloneObj() {
		Point point = new Point();
		point.dimensions = dimensions;
		point.coordinates = Arrays.copyOf(coordinates, coordinates.length);
		return point;
	}

	/**
	 * 设置坐标轴位置
	 * 
	 * @param axisTye
	 * @param value
	 */
	public void setCoordinate(AxisTyeEunm axisTye, double value) {
		coordinates[axisTye.value()] = value;
	}

	public void setCoordinate(int axisTye, int value) {
		coordinates[axisTye] = value;
	}

	/**
	 * 得到坐标轴位置
	 * 
	 * @param axisTye
	 * @return
	 */
	public double getCoordinate(AxisTyeEunm axisTye) {
		return coordinates[axisTye.value()];
	}
	
	public double getCoordinate(int axisTye) {
		return coordinates[axisTye];
	}

	/**
	 * 得到空间维度
	 * 
	 * @return
	 */
	public int getDimensions() {
		return dimensions;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < coordinates.length; i++) {
			sb.append(coordinates[i]);
			if (i < coordinates.length - 1) {
				sb.append(",");
			}
		}
		return "坐标[" + sb.toString() + "]";
	}

	// 按格式四舍五入
	private double getRoundValue(double value) {
		DecimalFormat formater = new DecimalFormat("#0.##");
		return Double.parseDouble(formater.format(value));
	}

	// 四舍五入取整
	private double getRoundIntValue(double value) {
		return Math.rint(value);
	}

	public static void main(String[] args) {
		double a = 1.4656564644;
		// System.out.println(a);
		System.out.println(Math.rint(a));
		// // System.out.println(Math.round(a));
		// a=((int)(a*100))/100.0;
		// System.out.println(a);
		// DecimalFormat formater = new DecimalFormat("#0.##");
		// System.out.println(formater.format(123456.7897456));
	}
}
