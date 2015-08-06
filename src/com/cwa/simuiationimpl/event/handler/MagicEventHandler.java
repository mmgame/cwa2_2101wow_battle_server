package com.cwa.simuiationimpl.event.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.skill.IEffectTrigger;
import com.cwa.simuiation.context.ISGlobalContext;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.IEventHandler;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.MagicResult;
import com.cwa.simuiationimpl.event.bean.PEffectBean;
import com.cwa.simuiationimpl.event.handler.effect.AffectedBean;
import com.cwa.simuiationimpl.event.handler.effect.IESEffectHandler;
import com.cwa.simuiationimpl.obj.Bullet;
import com.cwa.simuiationimpl.util.XXXUtil;
import com.cwa.util.RandomUtil;

/**
 * 施法事件处理器
 * 
 * @author tzy
 * 
 */
public class MagicEventHandler implements IEventHandler {
	private Map<Integer, IESEffectHandler> handlerMap;

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_Magic.value();
	}

	@Override
	public void handler(ISEvent event) {
		ESMagicEvent e = (ESMagicEvent) event;
		MagicActionContext ac = e.getActionContext();

		IPerformer performer = ac.getPerformer();
		ISObject target = ac.getTarget();
		ISGlobalContext gc = performer.getGlobalContext();
		if(ac.getPrototype().getPlaySequence() == 1){//一起释放
			// 技能效果keyId
			List<Integer> keyIds = ac.getIncidentalEffectLists();
			List<AffectedBean> affectedList = new ArrayList<AffectedBean>();
			for(int i=0;i<keyIds.size();i++){
				// 效果原型
				EffectPrototype effectPrototype = gc.getProtpyeMgr().getEffectPrototype(keyIds.get(i));
				// 随机一个数
				AffectedBean affectedBean = new AffectedBean(RandomUtil.generalRrandom());
				affectedBean.setActionContext(ac);
				affectedBean.setEffectPrototype(effectPrototype);
				affectedBean.increaseCount();
				affectedList.add(i, affectedBean);
			}
			EffectPrototype effectPrototype = affectedList.get(0).getEffectPrototype();
			if (effectPrototype.getFlySpeed() > 0) {
				final List<AffectedBean> list = affectedList;
				final ESMagicEvent magicEvent = e;
				// 处理子弹
				IEffectTrigger trigger = new IEffectTrigger() {
					@Override
					public void trigger() {
						affectedHandler(list,magicEvent);
					}
				};
				Position position = (Position) performer.getPosition().cloneObj();
				Bullet bullet = (Bullet) gc.getObjFactory().createPerformerBullet(performer, position, trigger, effectPrototype);

				IActionContext context = gc.getActionContextFactory().createMoveContext(bullet, target);
				bullet.setActionContext(context);
				context.getAction().enter(context);
				e.addNewObjects(bullet);// 设置子弹id
			} else {
				affectedHandler(affectedList,e);
			}
		}else{//顺序释放（例如二连击）
			int keyId = ac.getIncidentalEffectLists().get(ac.getCount()-1);//效果id
			EffectPrototype effectPrototype = gc.getProtpyeMgr().getEffectPrototype(keyId);
			// 随机一个数
			List<AffectedBean> affectedBeanList = new ArrayList<AffectedBean>();
			AffectedBean affectedBean = new AffectedBean(RandomUtil.generalRrandom());
			affectedBean.setActionContext(ac);
			affectedBean.setEffectPrototype(effectPrototype);
			affectedBean.increaseCount();
			affectedBeanList.add(affectedBean);
			if(effectPrototype.getFlySpeed() > 0){//有子弹
				final List<AffectedBean> beanList = affectedBeanList;
				final ESMagicEvent magicEvent = e;
				// 处理子弹
				IEffectTrigger trigger = new IEffectTrigger() {
					@Override
					public void trigger() {
						affectedHandler(beanList,magicEvent);
					}
				};
				Position position = (Position) performer.getPosition().cloneObj();
				Bullet bullet = (Bullet) gc.getObjFactory().createPerformerBullet(performer, position, trigger, effectPrototype);

				IActionContext context = gc.getActionContextFactory().createMoveContext(bullet, target);
				bullet.setActionContext(context);
				context.getAction().enter(context);
				e.addNewObjects(bullet);// 设置子弹id
			}else{
				affectedHandler(affectedBeanList,e);
			}
		}
	}

	private void affectedHandler(List<AffectedBean> affectedBeanList,ESMagicEvent magicEvent) {
		IPerformer performer = null;
		magicEvent.setMagicResult(new MagicResult());
		List<Integer> affectIdList = new ArrayList<Integer>();
		for(AffectedBean affectedBean : affectedBeanList){
			performer = affectedBean.getActionContext().getPerformer();
			ISObject target = affectedBean.getActionContext().getTarget();
			EffectPrototype effectPrototype = affectedBean.getEffectPrototype();
			int effectid = effectPrototype.getKeyId();
			int targetRule = effectPrototype.getTargetRule();
			affectIdList.add(effectid);
			List<IPerformer> targets = XXXUtil.chooseTarget(performer, target, targetRule, effectPrototype.getRange());
			magicEvent.insertMagicEffectBean(effectid, affectedBean.getSeed(), targets);//将效果结果放在Magic事件中

			IESEffectHandler eSEffectHandler = handlerMap.get(affectedBean.getEffectPrototype().getType());
			for (int i = 0; i < affectedBean.getCount(); i++) {
				IPerformer source = affectedBean.getActionContext().getPerformer();
				eSEffectHandler.handler(source, affectedBean,magicEvent);
			}
			magicEvent.getMagicResult().getMagicEffectBeanListByEffectId(effectid).setEid(effectid);
		}
		magicEvent.setMstate(1);//施法成功
		performer.getOwner().sendEvent(performer.getControler().getClientMgr().key(), magicEvent);
		for(int eid : affectIdList){
			Map<Integer, PEffectBean> effectBeans = magicEvent.getMagicResult().getMagicEffectBeanListByEffectId(eid).getPerformerEffectBeans();
			for(Integer pid : effectBeans.keySet()){
				IPerformer t = (IPerformer)performer.getGlobalContext().getSobMgr().select(pid);
				PEffectBean bean = effectBeans.get(pid);
				for(int i=0;i<bean.getValues().size();i++){
					int value = bean.getValues().get(i);
					int type = bean.getEffects().get(i);
					switch(type){
					case 0:
					case 1:
					case 2:
					case 3:
					case 7:
						// 减护盾
						if (t.getAttrMgr().getConsumeValue(AttrTypeEnum.Attr_Shield.value()) != 0) {
							t.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Shield.value(), -value);
						} else {
							t.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), -value);
						}
						break;
					case 4:
					case 6:
						t.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), value);
						break;
					case 5:
						t.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Energy.value(), value);
						break;
					default :
						break;
					}
				}
			}
		}
	}

	// --------------------------------
	public void setHandlerMap(Map<Integer, IESEffectHandler> handlerMap) {
		this.handlerMap = handlerMap;
	}
}
