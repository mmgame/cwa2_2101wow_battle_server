package com.cwa.battleimpl.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.simuiation.enums.EventChannelEnum;
import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.enums.EventTypeEnum;
import com.cwa.simuiation.event.ISEvent;

/**
 * 战场状态事件
 * 
 * @author mausmars
 *
 */
public class BattleStateChangeEvent implements ISEvent {
	public static Map<Integer, BattleStateChangeEvent> battleStateChangeEventMap = new HashMap<Integer, BattleStateChangeEvent>();

	static {
		for (BattleStateTypeEnum battleStateType : BattleStateTypeEnum.values()) {
			battleStateChangeEventMap.put(battleStateType.value(), new BattleStateChangeEvent(battleStateType.value()));
		}
	}

	public static BattleStateChangeEvent getBattleStateChangeEvent(int battleStateType) {
		return battleStateChangeEventMap.get(battleStateType);
	}

	// 战场状态 BattleStateTypeEnum
	private int step; // 阶段

	public BattleStateChangeEvent(int step) {
		this.step = step;
	}

	@Override
	public int getEventType() {
		return EventTypeEnum.E_State.value();
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.EB_StateChange.value();
	}

	@Override
	public int getChannel() {
		return EventChannelEnum.Whole.value();
	}

	@Override
	public List<String> getReceivers() {
		return null;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public void setChannel(int channel) {
		
	}

	@Override
	public void setReceivers(List<String> receivers) {
		
	}
}
