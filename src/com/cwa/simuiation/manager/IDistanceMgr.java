package com.cwa.simuiation.manager;

import com.cwa.simuiation.map.Point;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.ISObject;

/**
 * 距离管理
 * 
 * @author mausmars
 * 
 */
public interface IDistanceMgr {
	/**
	 * 重置
	 */
	void reset();

	/**
	 * 缓存距离
	 */
	void cache();

	/**
	 * 得到source到target的距离（中心距离）
	 * 
	 * @param s
	 * @param t
	 * @return
	 */
	double getDistance(ISObject s, ISObject t);

	/**
	 * 碰撞
	 * 
	 * @param s
	 * @param t
	 * @param isOverlap
	 *            是否能重叠
	 * @param off
	 *            偏移
	 * @return
	 */
	boolean isCollision(ISObject s, ISObject t, boolean isOverlap, int off);

	boolean isCollision(int ssize, int tsize, boolean isOverlap, double distance, int off);

	/**
	 * 计算移动坐标
	 * 
	 * @param source
	 * @param target
	 * @param moveDistance
	 *            移动距离
	 * @return
	 */
	Point getMovePoint(ISObject s, ISObject t, double moveDistance);

	Point getMovePoint(Position s, Position t, double distance, double moveDistance);
}
