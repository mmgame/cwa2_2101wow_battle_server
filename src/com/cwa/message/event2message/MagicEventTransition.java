package com.cwa.message.event2message;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.cwa.message.BattleMessage.ActionTargetBean;
import com.cwa.message.BattleMessage.CoordinateBean;
import com.cwa.message.BattleMessage.MagicActionDown;
import com.cwa.message.BattleMessage.MagicEffectBean;
import com.cwa.message.BattleMessage.MagicPetBean;
import com.cwa.message.BattleMessage.MagicResaultBean;
import com.cwa.message.BattleMessage.MagicTrapBean;
import com.cwa.message.BattleMessage.PerformerEffectBean;
import com.cwa.prototype.gameEnum.SkillReleaseTypeEnum;
import com.cwa.simuiation.enums.MessageSourceEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.message.IEventTransition;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MEffectBean;
import com.cwa.simuiationimpl.event.bean.PEffectBean;
import com.cwa.simuiationimpl.event.bean.PetBean;
import com.cwa.simuiationimpl.event.bean.TrapBean;

/**
 * 施法事件转消息
 * 
 * @author mausmars
 * 
 */
public class MagicEventTransition implements IEventTransition {
	@Override
	public Object transition(ISEvent event) {
		ESMagicEvent e = (ESMagicEvent) event;
		MagicActionContext mac = e.getActionContext();
		MagicActionDown.Builder mBuilder = MagicActionDown.newBuilder();
		
		ISObject target = mac.getTarget();
		if(target != null){
			ActionTargetBean.Builder actionTargetBean = ActionTargetBean.newBuilder();
			int targetType = mac.getTarget().getType();
			actionTargetBean.setTargetType(targetType);
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
			mBuilder.setActionTarget(actionTargetBean);
		}
		mBuilder.setPerformerId(e.getSource().getId());
		mBuilder.setSkillId(mac.getSkillId());
		mBuilder.setMstate(e.getMstate());
		mBuilder.setSource(MessageSourceEnum.Net_Message.value());// 来源 0网络 1UI
		if(e.getMstate() == 0){//技能失败
			mBuilder.setErrorType(e.getErrorType());
			mBuilder.setErrorValue(e.getErrorValue());
			return mBuilder.build();
		}
		mBuilder.setState(e.getStep());
		
		if(e.getStep() == StepTypeEnum.ST_Exit.value() || (e.getStep() == StepTypeEnum.ST_Update.value() && mac.getReleaseType() == SkillReleaseTypeEnum.Release_Singing.value())) {
			MagicResaultBean.Builder magicResaultBean = MagicResaultBean.newBuilder();

			Map<Integer, MEffectBean> magicEffectBeanMap = e.getMagicResult().getMagicEffectBeanMap();
			Iterator<Entry<Integer, MEffectBean>> it = magicEffectBeanMap.entrySet().iterator();
			for (; it.hasNext();) {
				Entry<Integer, MEffectBean> entry = it.next();
				MagicEffectBean.Builder magicEffectBean = MagicEffectBean.newBuilder();
				magicEffectBean.setEid(entry.getValue().getEid());
				for(PEffectBean peb : entry.getValue().getPerformerEffectBeans().values()){
					PerformerEffectBean.Builder performerEffectBean =  PerformerEffectBean.newBuilder();
					performerEffectBean.setPid(peb.getPid());
					performerEffectBean.addAllCreateBuffs(peb.getBuffs());
					performerEffectBean.addAllValues(peb.getValues());
					performerEffectBean.addAllEffects(peb.getEffects());
					performerEffectBean.addAllDeleteBuffs(peb.getDeleteBuffs());
					magicEffectBean.addPerformerEffectBeans(performerEffectBean);
				}
				
				if(!entry.getValue().getPetBeanList().isEmpty()){//有宠物产生
					for(PetBean pet : entry.getValue().getPetBeanList()){
						MagicPetBean.Builder petBean = MagicPetBean.newBuilder();
						petBean.setPid(pet.getPid());
						petBean.setKeyId(pet.getKeyId());
						CoordinateBean.Builder coordinate = CoordinateBean.newBuilder();
						for (int i = 0; i < pet.getPoint().getDimensions(); i++) {
							double v = pet.getPoint().getCoordinate(i);
							coordinate.addC((int) v);
						}
						petBean.setPosition(coordinate);
						magicEffectBean.addPetBean(petBean);
					}
				}
				if(!entry.getValue().getTrapBeanList().isEmpty()){//有陷阱产生
					for(TrapBean trap : entry.getValue().getTrapBeanList()){
						MagicTrapBean.Builder trapBean = MagicTrapBean.newBuilder();
						trapBean.setTid(trap.getTid());
						CoordinateBean.Builder coordinate = CoordinateBean.newBuilder();
						for (int i = 0; i < trap.getPoint().getDimensions(); i++) {
							double v = trap.getPoint().getCoordinate(i);
							coordinate.addC((int) v);
						}
						trapBean.setPosition(coordinate);
						trapBean.setEid(trap.getEid());
						magicEffectBean.addTrapBean(trapBean);
					}
				}
				
				magicResaultBean.addEffectBeans(magicEffectBean);
			}
			mBuilder.setMagicResaultBean(magicResaultBean);
		}
		return mBuilder.build();
	}
}
