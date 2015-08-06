package com.cwa.simuiation.trap;

import com.cwa.message.BattleMessage.TrapTriggerBean;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.obj.Trap;


public interface ISTrapHandler {
	TrapTriggerBean handler(IPerformer performer,Trap trap);
}
