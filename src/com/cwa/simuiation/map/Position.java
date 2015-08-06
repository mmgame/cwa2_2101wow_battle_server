package com.cwa.simuiation.map;

import java.util.Arrays;

import com.cwa.simuiation.enums.AngleTypeEnum;
import com.cwa.simuiation.enums.AxisTyeEunm;

/**
 * 坐标向量
 * 
 * @author mausmars
 * 
 */
public class Position {
	// 朝向角度（x,y平面角度和仰角-360~360）
	private int[] directions;

	private Point point;

	// --------------------------------------
	private Position() {
	}

	public Position(int dimensions) {
		directions = new int[dimensions - 1];
		point = new Point(dimensions);
	}

	public Position(double[] coordinates, int[] directions) {
		point = new Point(coordinates);
		this.directions = directions;
	}

	// --------------------------------------
	public Position cloneObj() {
		Position position = new Position();
		position.directions = Arrays.copyOf(directions, directions.length);
		position.point = (Point) point.cloneObj();
		return position;
	}

	/**
	 * 设置方向
	 * 
	 * @param angleType
	 * @param value
	 */
	public void setDirections(AngleTypeEnum angleType, int value) {
		directions[angleType.value()] = value;
	}

	/**
	 * 获取方向
	 * 
	 * @param angleType
	 * @return
	 */
	public int getDirections(AngleTypeEnum angleType) {
		return directions[angleType.value()];
	}

	/**
	 * 设置坐标轴位置
	 * 
	 * @param axisTye
	 * @param value
	 */
	public void setCoordinate(AxisTyeEunm axisTye, int value) {
		point.setCoordinate(axisTye, value);
	}

	public void setCoordinate(int axisTye, int value) {
		point.setCoordinate(axisTye, value);
	}

	/**
	 * 得到坐标轴位置
	 * 
	 * @param axisTye
	 * @return
	 */
	public double getCoordinate(AxisTyeEunm axisTye) {
		return point.getCoordinate(axisTye);
	}
	
	public double getCoordinate(int axisTye) {
		return point.getCoordinate(axisTye);
	}

	/**
	 * 得到空间维度
	 * 
	 * @return
	 */
	public int getDimensions() {
		return point.getDimensions();
	}

	/**
	 * 得到位置
	 * 
	 * @return
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * 设置新位置
	 * 
	 * @return
	 */
	public void setPoint(Point point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return point.toString();
	}
}
