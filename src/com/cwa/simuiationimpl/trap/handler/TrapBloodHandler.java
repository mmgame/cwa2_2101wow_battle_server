package com.cwa.simuiationimpl.trap.handler;

import java.util.List;

import com.cwa.message.BattleMessage.TrapTriggerBean;
import com.cwa.prototype.TrapPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.ValueTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.obj.Trap;

public class TrapBloodHandler extends ATrapHandler {

	@Override
	public TrapTriggerBean handler(IPerformer performer, Trap trap) {

		TrapTriggerBean.Builder b = TrapTriggerBean.newBuilder();
		TrapPrototype trapPrototype = trap.getTrapPrototype();
		int changeValue = 0;
		if (trapPrototype.getValueType() == ValueTypeEnum.Value_Num.value()) {
			// 数值
			changeValue = trapPrototype.getDamageAmountList().get(0);
		} else {
			// 百分比
			changeValue = performer.getAttrMgr().getAttrFinalValue(AttrTypeEnum.Attr_Blood.value()) * trapPrototype.getDamageAmountList().get(0) / SimuiationConstant.Percent;
		}
		if (trapPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()) {
			// 减益
			changeValue = -changeValue;
		}
//		performer.getAttrMgr().changeConsumeValue(AttrTypeEnum.Attr_Blood.value(), changeValue);
		b.setPid(performer.getId());
		b.setValue(changeValue);
		// 判断进入buff
		List<Integer> enterList = enterBuff(performer, trapPrototype, trap.getRandom());
		b.addAllBuffKeyId(enterList);
		return b.build();
	}

}
