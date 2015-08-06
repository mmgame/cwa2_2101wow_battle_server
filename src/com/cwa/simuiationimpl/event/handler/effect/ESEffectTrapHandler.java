package com.cwa.simuiationimpl.event.handler.effect;

import java.util.List;

import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.TrapPrototype;
import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.TrapBean;

/**
 * 陷阱效果
 * 
 * @author tzy
 * 
 */
public class ESEffectTrapHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_Trap.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
		EffectPrototype effectPrototype = affectedBean.getEffectPrototype();
		ISObject target = affectedBean.getActionContext().getTarget();
		List<Integer> buffList = effectPrototype.getIncidentalParamList();
		for (Integer integer : buffList) {
			if (integer == 0) {
				break;
			} else {
				// TODO 设置位置
				Position position = new Position(SimuiationConstant.Dimensions);
				position.setPoint(target.getPosition().getPoint());
				TrapPrototype trapPrototype = source.getGlobalContext().getProtpyeMgr().getTrapPrototype(integer);

				IPerformer trap = source.getGlobalContext().getObjFactory().createPerformerTrap(source, position, trapPrototype, effectPrototype,affectedBean.getRandom());
				TrapBean t = new TrapBean();
				t.setTid(trap.getId());
				t.setPoint(position);
				t.setEid(effectPrototype.getKeyId());
				event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId()).addTrapBean(t);//告诉客户端陷阱的信息
			}
		}
	}
}
