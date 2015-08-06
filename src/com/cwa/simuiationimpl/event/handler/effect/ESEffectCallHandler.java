package com.cwa.simuiationimpl.event.handler.effect;

import java.util.List;

import com.cwa.prototype.CallPrototype;
import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.gameEnum.EffectTypeEnum;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.event.ESMagicEvent;
import com.cwa.simuiationimpl.event.bean.PetBean;
import com.cwa.util.RandomUtil;

/**
 * 召唤效果
 * 
 * @author tzy
 * 
 */
public class ESEffectCallHandler extends AEffectHandler {

	@Override
	public int getEffectType() {
		return EffectTypeEnum.Type_Call.value();
	}

	@Override
	public void handler(IPerformer source, AffectedBean affectedBean,ESMagicEvent event) {
		EffectPrototype effectPrototype = affectedBean.getEffectPrototype();
		MagicActionContext ac = affectedBean.getActionContext();
		List<Integer> heroList = effectPrototype.getSummonHeroList();
		List<Integer> probabilityList = effectPrototype.getSummonProbabilityList();
		// 顺序随机
		int index = RandomUtil.getOrderRandom(probabilityList, probabilityList.get(probabilityList.size() - 1), affectedBean.getRandom());
		if (index <= -1) {
			return;
		}
		int heroRateKeyId = heroList.get(index);
		CallPrototype callPrototype = source.getGlobalContext().getProtpyeMgr().getCallPrototype(heroRateKeyId);

		// TODO 设置位置
//		Position position = FormulaUtil.getRandomPosition(ac.getTarget().getPosition(), SimuiationConstant.PositionRandom, source
//				.getGlobalContext().getAreaMgr().getSize());
		Position position = ac.getTarget().getPosition();
		
		IPerformer pet = source.getGlobalContext().getObjFactory().createPerformerPet(source, position, callPrototype);
		PetBean p = new PetBean();
		p.setPid(pet.getId());
		p.setKeyId(heroRateKeyId);
		p.setPoint(position);
		event.getMagicResult().getMagicEffectBeanListByEffectId(affectedBean.getEffectPrototype().getKeyId()).addPetBean(p);//告诉客户端宠物的信息
	}
}
