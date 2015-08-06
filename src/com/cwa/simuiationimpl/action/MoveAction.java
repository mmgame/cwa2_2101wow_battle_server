package com.cwa.simuiationimpl.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.ISAction;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.enums.AxisTyeEunm;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.manager.IDistanceMgr;
import com.cwa.simuiation.map.Point;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.util.Math2D;

/**
 * 移动处理
 * 
 * @author mausmars
 * 
 */
public class MoveAction implements ISAction {
	protected static final Logger logger = LoggerFactory.getLogger(MoveAction.class);

	@Override
	public int getType() {
		return StateSubTypeEnum.AS_Move.value();
	}

	@Override
	public void enter(IActionContext context) {
		MoveActionContext ac = (MoveActionContext) context;
		IPerformer p = context.getPerformer();
		IDistanceMgr distanceMgr = p.getGlobalContext().getDistanceMgr();
		ISObject t = ac.getTarget();// 目标
//		double distance = distanceMgr.getDistance(p, t);// 2个对象距离
		double distance = Math2D.getDistance(p.getPosition().getPoint(), t.getPosition().getPoint());
		double speed = p.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Speed.value()) / SimuiationConstant.Millisecond;
		// 设置延迟
		ac.setDelayTime(p.getOwner().getNetDelayCheckServer().getDelayTime());
		double time = distance / speed - ac.getDelayTime();
		speed = distance / (time < 1 ? 1 : time);
		// 设置速度
		ac.setSpeed(speed);
	}

	@Override
	public void exit(IActionContext context) {
		MoveActionContext ac = (MoveActionContext) context;
		SClock clock = context.getPerformer().getGlobalContext().getClock();
		if (ac.getStartTime().getFrameCount() < clock.getCurrentFrameCount()) {
			update(context);
		}
		if (ac.getOver() == StepTypeEnum.ST_Null.value()) {
			// 中断移动
			context.setOver(StepTypeEnum.ST_Interrupt.value());
		}
	}

	@Override
	public void update(IActionContext context) {
		// 这里出发计算
		MoveActionContext ac = (MoveActionContext) context;

		IPerformer p = context.getPerformer();
		double speed = ac.getSpeed();
		SClock clock = context.getPerformer().getGlobalContext().getClock();
		int differTime = (int) ((clock.getCurrentSTime().getFrameCount() - ac.getStartTime().getFrameCount())
				* (SClock.getFrameInterval() + SimuiationConstant.SystemUpdateTime) + (clock.getCurrentSTime().getOffset() - ac
				.getStartTime().getOffset()));
		IDistanceMgr distanceMgr = p.getGlobalContext().getDistanceMgr();
		ISObject t = ac.getTarget();// 目标

		double moveDistance = speed * differTime;// 移动
//		double distance = distanceMgr.getDistance(p, t);// 2个对象距离
		double distance = Math2D.getDistance(p.getPosition().getPoint(), t.getPosition().getPoint());
		
		boolean isOverlap = isOverlap(p, t);// 是否能重叠

		Point newPoint = distanceMgr.getMovePoint(p.getPosition(), t.getPosition(), distance, moveDistance);

		if (newPoint.getCoordinate(AxisTyeEunm.XAxis) != t.getPosition().getCoordinate(AxisTyeEunm.XAxis)
				|| newPoint.getCoordinate(AxisTyeEunm.YAxis) != t.getPosition().getCoordinate(AxisTyeEunm.YAxis)) {
			distance -= moveDistance;
		}
		if (distanceMgr.isCollision(p.getSize(), t.getSize(), isOverlap, distance, 0)) {
			// 发生碰撞
			context.setOver(StepTypeEnum.ST_Exit.value());
		} else {
			// 设置新位置
			p.getPosition().setPoint(newPoint);
		}
		context.resetStartTime();
	}

	/**
	 * 是否可以重叠
	 * 
	 * @return
	 */
	private boolean isOverlap(IPerformer p, ISObject t) {
		if (t.getType() == ObjTypeEnum.Area.value()) {
			// 如果目标是地点可以重叠
			return true;
		}
		if (p.getSubType() == ObjSubTypeEnum.P_Hero.value() || p.getSubType() == ObjSubTypeEnum.P_Pet.value()) {
			// 同阵营的可以重叠
			return p.getOwner().getTroopType() == ((IPerformer) t).getOwner().getTroopType();
		}
		return false;
	}

	/**
	 * 是否跟随
	 * 
	 * @return
	 */
	private boolean isFollow(IPerformer p, ISObject t) {
		return (p.getSubType() == ObjSubTypeEnum.P_Hero.value() || p.getSubType() == ObjSubTypeEnum.P_Pet.value())
				&& (t.getSubType() == ObjSubTypeEnum.P_Hero.value() || t.getSubType() == ObjSubTypeEnum.P_Pet.value());

	}
}
