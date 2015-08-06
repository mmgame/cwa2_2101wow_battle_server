package com.cwa.simuiationimpl.action;

import java.util.List;

import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.ActionTargetBean;
import com.cwa.message.BattleMessage.MoveActionUp;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.obj.AreaPoint;

/**
 * 移动动作上下文处理
 * 
 * @author mausmars
 * 
 */
public class MoveActionContextHandler implements IActionContextHandler {
	@Override
	public IActionContext handler(IActionContext ac) {
		MessageActionContext mac = (MessageActionContext) ac;

		MoveActionUp message = (MoveActionUp) mac.getMessage();
		ActionTargetBean actionTargetBean=message.getActionTarget();
		int targetType = actionTargetBean.getTargetType();

		IPerformer source = mac.getPerformer();
		IClientSimuiation clientSimuiation = mac.getClientSimuiation();
		if (source.getControler() != clientSimuiation) {
			// 控制者不是该用户
			return null;
		}

		ISObject target = null;
		if (targetType == ObjTypeEnum.Performer.value()) {
			int targetId = actionTargetBean.getTargetId();
			target = clientSimuiation.getSobMgr().select(targetId);
			if (target == null || !(target instanceof IPerformer)) {
				return null;
			}
		} else if (targetType == ObjTypeEnum.Area.value()) {
			CoordinateBean coordinate = actionTargetBean.getAreaTarget();// 目标点
			List<Integer> c = coordinate.getCList();
			if (c.size() != SimuiationConstant.Dimensions) {
				// 维度判断
				return null;
			}
			// TODO 坐标范围的判断
			target = createAreaPoint(c);
		} else {
			return null;
		}
		IActionContext context = source.getGlobalContext().getActionContextFactory().createMoveContext(source, target);
		if (context == null) {
			return null;
		}
		if(message.hasIsAdjust()){
			((MoveActionContext)context).setIsAdjust(message.getIsAdjust());
		}
		return context;
	}

	private AreaPoint createAreaPoint(List<Integer> c) {
		// 在设置坐标
		AreaPoint sreaPoint = new AreaPoint();
		Position position = new Position(SimuiationConstant.Dimensions);
		int index = 0;
		for (int v : c) {
			position.setCoordinate(index, v);
			index++;
		}
		sreaPoint.setPosition(position);
		return sreaPoint;
	}
}
