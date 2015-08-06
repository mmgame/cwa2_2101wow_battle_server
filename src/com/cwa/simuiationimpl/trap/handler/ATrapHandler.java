package com.cwa.simuiationimpl.trap.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cwa.prototype.TrapPrototype;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.trap.ISTrapHandler;
import com.cwa.simuiationimpl.constant.SimuiationConstant;

public abstract class ATrapHandler implements ISTrapHandler {
	/**
	 * 挂buff
	 * 
	 * @param effectPrototype
	 * @param target
	 * @param ac
	 */
	protected List<Integer> enterBuff(IPerformer performer, TrapPrototype trapPrototype, Random random) {
		// 判断附带buff
		List<Integer> enterList=new ArrayList<Integer>();
		List<Integer> buffList = trapPrototype.getIncidentalBuffList();
		for (Integer integer : buffList) {
			if (integer == 0) {
				break;
			} else {
				// TODO 加buff
				int value = random.nextInt(SimuiationConstant.Percent);
				if (performer.getBuffManager().enter(integer, value)) {
					enterList.add(integer);
				}
			}
		}
		return enterList;
	}
}
