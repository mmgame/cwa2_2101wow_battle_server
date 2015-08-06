package com.cwa.simuiationimpl.trap.handler;

import java.util.List;

import com.cwa.message.BattleMessage.TrapTriggerBean;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.obj.Trap;

/**
 * 状态类陷阱（只挂buff）
 * 
 * @author tzy
 * 
 */
public class TrapStateHandler extends ATrapHandler {

	@Override
	public TrapTriggerBean handler(IPerformer performer, Trap trap) {
		TrapTriggerBean.Builder b = TrapTriggerBean.newBuilder();
		// 判断进入buff
		List<Integer> enterList = enterBuff(performer, trap.getTrapPrototype(), trap.getRandom());
		b.setPid(performer.getId());
		b.addAllBuffKeyId(enterList);
		return b.build();
	}
}
