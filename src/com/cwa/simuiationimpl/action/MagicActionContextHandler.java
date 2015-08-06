package com.cwa.simuiationimpl.action;

import java.util.ArrayList;
import java.util.List;

import com.cwa.message.BattleMessage.ActionTargetBean;
import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.MagicActionUp;
import com.cwa.prototype.SkillPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.SkillReleaseRuleEnum;
import com.cwa.simuiation.IClientSimuiation;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.enums.MagicErrorEnum;
import com.cwa.simuiation.manager.IActionContextHandler;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.obj.AreaPoint;

/**
 * 施法动作上下文处理
 * 
 * @author mausmars
 * 
 */
public class MagicActionContextHandler implements IActionContextHandler {
	@Override
	public IActionContext handler(IActionContext ac) {
		MessageActionContext mac = (MessageActionContext) ac;

		MagicActionUp message = (MagicActionUp) mac.getMessage();
		ActionTargetBean actionTargetBean = message.getActionTarget();
		int skillId = message.getSkillId();
		IPerformer source = mac.getPerformer();
		IClientSimuiation clientSimuiation = mac.getClientSimuiation();

		IActionContext context = null;
		ISObject target = null;

		// 判断原型
		SkillPrototype skillPrototype = source.getGlobalContext().getProtpyeMgr().getSkillPrototype(skillId);
		if (source.getControler() != clientSimuiation) {
			// 控制者不是该用户
			sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_Controler.value(),0);
			return null;
		}
		if (skillPrototype == null) {
			sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_SkillId.value(),0);
			return null;
		}

		// 判断cd
		SimuiationTime current = source.getGlobalContext().getClock().getCurrentSTime();
		if (!(source.getSkillManager().isFinishedCd(skillId, skillPrototype.getCdTime(), current))) {
			int value = (int)(source.getSkillManager().getCdTime(skillId) + skillPrototype.getCdTime() - current.getMSTime()); 
			sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_CD.value(),value);
			return null;
		}

		// 判断mp
		int energy = source.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Energy.value());
		if (energy < skillPrototype.getMpList().get(source.getAttrMgr().getQuality())) {
			sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_Energy.value(),energy);
			return null;
		}

		if (skillPrototype.getReleaseRule() == SkillReleaseRuleEnum.Release_League.value()
				|| skillPrototype.getReleaseRule() == SkillReleaseRuleEnum.Release_Enemy.value()) {
			// 人，这里不验证目标的选择相信客户端了。偷个懒
			int targetId = actionTargetBean.getTargetId();
			target = clientSimuiation.getSobMgr().select(targetId);
			if (target == null || !(target instanceof IPerformer)) {
				sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_Target.value(),0);
				return null;
			}
			context = source.getGlobalContext().getActionContextFactory()
					.createMagicContext(source, target, skillPrototype);
		} else if (skillPrototype.getReleaseRule() == SkillReleaseRuleEnum.Release_Area.value()) {
			// 区域
			CoordinateBean coordinate = actionTargetBean.getAreaTarget();
			List<Integer> c = coordinate.getCList();
			if (c.size() != SimuiationConstant.Dimensions) {
				// 维度判断
				sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_Target.value(),0);
				return null;
			}
			// TODO 坐标范围的判断
			target = createAreaPoint(c);
			context = source.getGlobalContext().getActionContextFactory()
					.createMagicContext(source, target, skillPrototype);
		}
		if (context == null) {
			sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_Other.value(),0);
			return null;
		}

		// 判断碰撞，不安目标中心算
		if (!source.getGlobalContext().getDistanceMgr()
				.isCollision(source, context.getTarget(), false, skillPrototype.getShootingDistance())) {
			// 超出攻击范围
			int value = skillPrototype.getShootingDistance() - (int)source.getGlobalContext().getDistanceMgr().getDistance(source, context.getTarget());
			sendMagicError(source,target,skillPrototype,MagicErrorEnum.Error_Distance.value(),value);
			return null;
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
	
	private void sendMagicError(IPerformer source,ISObject target,SkillPrototype skillPrototype,int errorType,int errorValue){ // 通知施法者施法失败
		ESMagicEvent event = new ESMagicEvent();
		event.setMstate(0);
		event.setChannel(EventChannelEnum.Appoint.value());
		List<String> sources = new ArrayList<String>();
		sources.add(source.getControler().getId());
		event.setReceivers(sources);
		event.setErrorType(errorType);
		event.setErrorValue(errorValue);
		
		MagicActionContext magicActionCotext = (MagicActionContext) source.getGlobalContext().getActionContextFactory().createMagicContext(source, target, skillPrototype);
		event.setActionContext(magicActionCotext);
		source.getOwner().sendEvent(source.getControler().getClientMgr().key(), event);
	}
}
