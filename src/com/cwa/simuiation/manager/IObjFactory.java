package com.cwa.simuiation.manager;

import java.util.List;
import java.util.Random;

import baseice.basedao.IEntity;

import com.cwa.component.prototype.IPrototype;
import com.cwa.data.entity.domain.EquipmentEntity;
import com.cwa.simuiation.IPlayer;
import com.cwa.simuiation.action.skill.IEffectTrigger;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IArea;
import com.cwa.simuiation.obj.INonPerformer;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 对象工厂
 * 
 * @author mausmars
 * 
 */
public interface IObjFactory {
	/**
	 * 创建英雄
	 * 
	 * @param subType
	 * @param param
	 * @return
	 */
	IPerformer createPerformerHero(IPlayer player, Position position, IEntity entity, IPrototype heroPrototype,
			IPrototype herogradePrototype,List<EquipmentEntity> equipments,IEntity retinueEntity,List<EquipmentEntity> retinueEquipments);

	/**
	 * 创建宠物
	 * 
	 * @param subType
	 * @param param
	 * @return
	 */
	IPerformer createPerformerPet(IPerformer source, Position position, IPrototype petPrototype);

	/**
	 * 创建子弹
	 * 
	 * @param subType
	 * @param param
	 * @return
	 */
	IPerformer createPerformerBullet(IPerformer source, Position position, IEffectTrigger effectTrigger, IPrototype effectPrototype);

	/**
	 * 创建陷阱
	 * 
	 * @param subType
	 * @param param
	 * @return
	 */
	IPerformer createPerformerTrap(IPerformer source, Position position, IPrototype trapPrototype, IPrototype effectPrototype,Random r);

	/**
	 * 创建非可执行者
	 * 
	 * @param subType
	 * @param param
	 * @return
	 */
	INonPerformer createNonPerformer();

	/**
	 * 创建区域
	 * 
	 * @param subType
	 * @param param
	 * @return
	 */
	IArea createArea();
}
