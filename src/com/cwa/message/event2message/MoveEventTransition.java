package com.cwa.message.event2message;

import com.cwa.message.BattleMessage.ActionTargetBean;
import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.MoveActionDown;
import com.cwa.simuiation.enums.MessageSourceEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MoveActionContext;
import com.cwa.simuiationimpl.event.ESMoveEvent;

/**
 * 移动事件转消息
 * 
 * @author mausmars
 * 
 */
public class MoveEventTransition implements IEventTransition {
	@Override
	public Object transition(ISEvent event) {
		ESMoveEvent e = (ESMoveEvent) event;
		MoveActionContext mac = e.getActionContext();

		MoveActionDown.Builder mBuilder = MoveActionDown.newBuilder();
		ActionTargetBean.Builder actionTargetBean = ActionTargetBean.newBuilder();
		if (e.getStep() == StepTypeEnum.ST_Enter.value()) {
			int targetType = mac.getTarget().getType();
			actionTargetBean.setTargetType(targetType);
			ISObject target = mac.getTarget();
			if (targetType == ObjTypeEnum.Performer.value()) {
				actionTargetBean.setTargetId(target.getId());
			} else if (targetType == ObjTypeEnum.Area.value()) {
				CoordinateBean.Builder coordinate = CoordinateBean.newBuilder();
				for (int i = 0; i < target.getPosition().getDimensions(); i++) {
					double v = target.getPosition().getCoordinate(i);
					coordinate.addC((int) v);
				}
				actionTargetBean.setAreaTarget(coordinate.build());
			}
			actionTargetBean.setTargetType(targetType);
		} else if (e.getStep() == StepTypeEnum.ST_Exit.value() || e.getStep() == StepTypeEnum.ST_Interrupt.value()) {
			CoordinateBean.Builder coordinate = CoordinateBean.newBuilder();
			for (int i = 0; i < e.getSource().getPosition().getDimensions(); i++) {
				double v = e.getSource().getPosition().getCoordinate(i);
				coordinate.addC((int) v);
			}
			actionTargetBean.setAreaTarget(coordinate.build());
			actionTargetBean.setTargetType(ObjTypeEnum.Area.value());
		}
		mBuilder.setPerformerId(e.getSource().getId());
		mBuilder.setState(e.getStep());
		mBuilder.setActionTarget(actionTargetBean);
		mBuilder.setSource(MessageSourceEnum.Net_Message.value());
		mBuilder.setDelayTime(mac.getDelayTime());
		mBuilder.setIsAdjust(mac.getIsAdjust());
		return mBuilder.build();
	}
}
