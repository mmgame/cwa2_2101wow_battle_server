package com.cwa.battleimpl.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.prototype.gameEnum.SkillReleaseRuleEnum;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.event.IEventHandler;
import com.cwa.simuiation.event.ISEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.event.ESDeadEvent;
import com.cwa.simuiationimpl.util.XXXUtil;

/**
 * 死亡事件处理
 * 
 * @author mausmars
 *
 */
public class ESDeadEventHandler implements IEventHandler {
	protected static final Logger logger = LoggerFactory.getLogger(IEventHandler.class);
	private ESTimeOverEventHandler timeOverEventHandler;

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_Dead.value();
	}

	@Override
	public void handler(ISEvent event) {
		ESDeadEvent e = (ESDeadEvent) event;

		IPerformer p = e.getPerformer();
		List<ISObject> targets = p.getGlobalContext().getSobMgr().getSObjectListByType(ObjTypeEnum.Performer.value());
		// 过滤类型
		XXXUtil.filterObjType(targets, ObjTypeEnum.Performer.value(),
				new int[] { ObjSubTypeEnum.P_Hero.value(), ObjSubTypeEnum.P_Pet.value() });
		// 找同盟
		XXXUtil.filterFaction(targets, SkillReleaseRuleEnum.Release_League.value(), p);
		// 过滤死亡的英雄
		XXXUtil.filterDead(targets);

		if (targets.size() <= 0) {
			timeOverEventHandler.handler(null);
		}
	}

	public void setTimeOverEventHandler(ESTimeOverEventHandler timeOverEventHandler) {
		this.timeOverEventHandler = timeOverEventHandler;
	}
}
