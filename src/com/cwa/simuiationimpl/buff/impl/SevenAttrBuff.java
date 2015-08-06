package com.cwa.simuiationimpl.buff.impl;

import java.util.List;

import com.cwa.prototype.BuffPrototype;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.ValueTypeEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.ISBuff;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
/**
 * 7属性
 * @author yangfeng
 *
 */
public class SevenAttrBuff implements ISBuff {

	@Override
	public int getType() {
		return BuffTypeEnum.Buff_SevenAttr.value();
	}

	@Override
	public void enter(IBuffContext context) {
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		int changeValue = 0;
		List<Integer> list = buffPrototype.getEffectionTypeList();
		for(int i=0;i<list.size();i++){
			if(buffPrototype.getValueType() == ValueTypeEnum.Value_Num.value()){
				//数值
				changeValue = buffPrototype.getDamageAmountList().get(i);
			}else{
				//百分比
				changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), list.get(i))
						* buffPrototype.getDamageAmountList().get(i) / 100.0f);
			}
			if(buffPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){
				//减益
				changeValue = -changeValue;
			}
			performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), list.get(i), changeValue);
			performer.getAttrMgr().changeConsumeValue(list.get(i), changeValue);
		}
	}

	@Override
	public void exit(IBuffContext context) {
		BuffPrototype buffPrototype = context.getBuffPrototype();
		IPerformer performer = context.getPerformer();
		int changeValue = 0;
		List<Integer> list = buffPrototype.getEffectionTypeList();
		for(int i=0;i<list.size();i++){
			if(buffPrototype.getValueType() == ValueTypeEnum.Value_Num.value()){
				//数值
				changeValue = buffPrototype.getDamageAmountList().get(i);
			}else{
				//百分比
				changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), list.get(i))
						* buffPrototype.getDamageAmountList().get(i) / 100.0f);
			}
			if(buffPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Good.value()){
				//增益
				changeValue = -changeValue;
			}
			performer.getAttrMgr().setAttrValue(AttrSourceTypeEnum.AS_Buff.value(), list.get(i), changeValue);
			performer.getAttrMgr().changeConsumeValue(list.get(i), changeValue);
		}
	}

	@Override
	public void update(IBuffContext context) {
		IPerformer performer = context.getPerformer();
		//效果时间到
		performer.getBuffManager().exit(context);
	}

	@Override
	public boolean canTranstion(IStateContext target) {
		return true;
	}
}
