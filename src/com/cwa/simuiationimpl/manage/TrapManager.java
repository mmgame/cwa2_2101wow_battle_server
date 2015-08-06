package com.cwa.simuiationimpl.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cwa.message.BattleMessage.TrapTriggerBean;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.TrapTypeEnum;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.manager.ITrapManager;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.trap.ISTrapHandler;
import com.cwa.simuiationimpl.event.ESTrapEvent;
import com.cwa.simuiationimpl.obj.Trap;
import com.cwa.simuiationimpl.util.XXXUtil;

/**
 * 陷阱管理类
 * 
 * @author tzy
 * 
 */
public class TrapManager implements ITrapManager {
	private Map<Integer, ISTrapHandler> trapHanderMap;

	@Override
	public void update(Trap trap) {
		// 刚开始就执行一次
		if (trap.getStartTime() == trap.getCreatTime()) {
			excuteTrap(trap);
			return;
		}

		SClock clock = trap.getGlobalContext().getClock();
		long differTime = clock.differByCurrentTime(trap.getCreatTime());
		if (differTime >= trap.getTrapPrototype().getEffectiveTime()) {
			// 陷阱到期，直接退出
			trap.setRomove(true);
			return;
		}

		differTime = clock.differByCurrentTime(trap.getStartTime());
		int intervalTime = trap.getTrapPrototype().getIntervalTime();
		if (intervalTime == 0) {
			return;
		}
		if (differTime < intervalTime) {
			return;
		}
		// 间隔时间到，执行陷阱
		excuteTrap(trap);
	}

	// 按规则筛选并执行相应陷阱处理
	private void excuteTrap(Trap trap) {
//		List<Integer> insideList = new ArrayList<Integer>();
//		Map<Integer, Integer> excuteCountMap = trap.getExcuteCountMap();
		int targetRule = trap.getEffectPrototype().getTargetRule();
		List<IPerformer> performerList = XXXUtil.chooseTarget(trap.getSource(), trap, targetRule, trap.getEffectPrototype().getRange());
		int trapType = trap.getTrapPrototype().getType();
		ISTrapHandler handler = trapHanderMap.get(trapType);
		if (handler == null) {
			return;
		}
		// 执行本次陷阱内handler
		List<TrapTriggerBean> trapTriggerBeanList=new ArrayList<TrapTriggerBean>();
		for (IPerformer iPerformer : performerList) {
			TrapTriggerBean bean=handler.handler(iPerformer, trap);
			trapTriggerBeanList.add(bean);
//			int keyId = iPerformer.getId();
//			Integer count = excuteCountMap.get(keyId);
//			if (count != null) {
//				excuteCountMap.put(keyId, ++count);
//			} else {
//				excuteCountMap.put(keyId, 1);
//			}
//			insideList.add(keyId);
		}
		sendEvent(trap.getControler().getClientMgr().key(), trap, trapTriggerBeanList);
		for(TrapTriggerBean bean : trapTriggerBeanList){
			if(trap.getTrapPrototype().getType() == TrapTypeEnum.Trap_Blood.value()){
				IPerformer performer = (IPerformer)trap.getGlobalContext().getSobMgr().select(bean.getPid());
				performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), bean.getValue());
			}
		}
		trap.resetStartTime();
	}

//	private void removeTrap(Trap trap, Map<Integer, Integer> excuteCountMap, List<Integer> insideList) {
//		int trapType = trap.getTrapPrototype().getType();
//		ISTrapHandler handler = trapHanderMap.get(trapType);
//		if (handler == null) {
//			return;
//		}
//		List<Integer> lastList = trap.getInsideList();
//		// 获得上次在陷阱内，本次不在的list，并执行移除陷阱效果
//		lastList.removeAll(insideList);
//		for (Integer i : lastList) {
//			handler.removeHandler(i, excuteCountMap.get(i), trap.getTrapPrototype());
//			excuteCountMap.put(i, 0);
//		}
//		// 记录本次陷阱内idlist
//		trap.setExcuteCountMap(excuteCountMap);
//		trap.setInsideList(insideList);
//	}

	private void sendEvent(Object listenerType,Trap trap,List<TrapTriggerBean> trapTriggerBeanList) {
		ESTrapEvent event=new ESTrapEvent();
		event.setTrap(trap);
		event.setTrapTriggerBeanList(trapTriggerBeanList);
		trap.getOwner().sendEvent(listenerType, event);
	}
	// -----------------------------------------
	public void setTrapHanderMap(Map<Integer, ISTrapHandler> trapHanderMap) {
		this.trapHanderMap = trapHanderMap;
	}
}