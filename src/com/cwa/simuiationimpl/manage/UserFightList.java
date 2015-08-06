package com.cwa.simuiationimpl.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import serverice.room.FightList;
import baseice.basedao.IEntity;

import com.cwa.data.entity.domain.EquipmentEntity;
import com.cwa.data.entity.domain.FormationEntity;
import com.cwa.data.entity.domain.HeroEntity;
import com.cwa.data.entity.domain.ItemEntity;

/**
 * 用户列表
 * 
 * @author mausmars
 * 
 */
public class UserFightList {
	private String tid; // 队伍id
	private int troopType; // 队伍类型
	private Map<Integer, ItemEntity> itemMap = new HashMap<Integer, ItemEntity>();
	private Map<Integer, HeroEntity> heroMap = new HashMap<Integer, HeroEntity>();
	private FormationEntity formationEntity;
	private Map<Integer, List<EquipmentEntity>> equipmentEntityMap = new HashMap<Integer, List<EquipmentEntity>>();

	public void init(FightList fightList) {
		this.tid = fightList.tid;
		troopType = fightList.troopType.value();
		formationEntity = (FormationEntity) fightList.formationEntity;
		
		for (IEntity entity : fightList.heroIds) {
			HeroEntity e = (HeroEntity) entity;
			if (e != null) {
				heroMap.put(e.heroId, e);
			}
		}
		for (IEntity entity : fightList.itemIds) {
			ItemEntity e = (ItemEntity) entity;
			if (e != null) {
				itemMap.put(e.itemId, e);
			}
		}
		for(int heroId : fightList.equipmentEntityMap.keySet()){
			for(IEntity entity : fightList.equipmentEntityMap.get(heroId)){
				if(equipmentEntityMap.get(heroId) != null){
					equipmentEntityMap.get(heroId).add((EquipmentEntity)entity);
				}else{
					List<EquipmentEntity> equipmentList = new ArrayList<EquipmentEntity>();
					equipmentList.add((EquipmentEntity)entity);
					equipmentEntityMap.put(heroId, equipmentList);
				}
			}
		}
	}

	public String getTid() {
		return tid;
	}

	public int getTroopType() {
		return troopType;
	}

	public Map<Integer, ItemEntity> getItemMap() {
		return itemMap;
	}

	public Map<Integer, HeroEntity> getHeroMap() {
		return heroMap;
	}

	public FormationEntity getFormationEntity() {
		return formationEntity;
	}

	public Map<Integer, List<EquipmentEntity>> getEquipmentEntityMap() {
		return equipmentEntityMap;
	}
}
