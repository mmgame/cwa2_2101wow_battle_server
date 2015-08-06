package com.cwa.battleimpl.filer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import serverice.room.TroopTypeEnum;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.component.netdelaycheck.NetDelayCheckServer;
import com.cwa.data.entity.domain.EquipmentEntity;
import com.cwa.data.entity.domain.FormationEntity;
import com.cwa.data.entity.domain.HeroEntity;
import com.cwa.prototype.BattlePrototype;
import com.cwa.prototype.BattlemapPrototype;
import com.cwa.prototype.CoordinatePrototype;
import com.cwa.prototype.HeroGradePrototype;
import com.cwa.prototype.HeroPrototype;
import com.cwa.simuiation.ISimuiation;
import com.cwa.simuiation.manager.IPrototypeMgr;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiationimpl.Player;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.manage.UserFightList;
import com.cwa.simuiationimpl.util.FormulaUtil;

/**
 * 初始化队伍
 * 
 * @author mausmars
 * 
 */
public class InitTroopTaskFilter extends ABattleFilter {
	public InitTroopTaskFilter() {
		super("InitTroopTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("InitTroopTaskFilter doWork!");
		}
		IBattleGround battleGround = (IBattleGround) context;
		initHero(battleGround);

		return true;
	}

	private void initHero(IBattleGround battleGround) {
		Map<Long, UserFightList> fightListMap = battleGround.getUserFightListMap();
		ISimuiation simuiation = battleGround.getSimuiation();

		IPrototypeMgr prototypeMgr = battleGround.getSimuiation().getProtpyeMgr();

		BattlePrototype battlePrototype = prototypeMgr.getBattlePrototype();
		BattlemapPrototype battlemapPrototype = prototypeMgr.getBattlemapPrototype(battlePrototype.getMap());

//		int attIndex = 0;
//		int defIndex = 0;
		for (Entry<Long, UserFightList> entry : fightListMap.entrySet()) {
			// 初始化Player
			Player player = new Player(entry.getKey());
			player.setTroopType(entry.getValue().getTroopType());
			player.setNetDelayCheckServer(new NetDelayCheckServer());
			// 连接战场仿真
			battleGround.attach(player);

			// 英雄初始化Player
			UserFightList fightList = entry.getValue();
			Map<Integer, HeroEntity> heroMap = fightList.getHeroMap();
			Map<Integer, List<EquipmentEntity>> equipmentMap = fightList.getEquipmentEntityMap();
			FormationEntity formation = fightList.getFormationEntity();
			int index = 0;
			// TODO
			String[] heroIds = formation.heros.split(",");
			String[] retinueIds = formation.retinues.split(",");
			List<Integer> heroIdList = new ArrayList<Integer>();
			List<Integer> retinueIdList = new ArrayList<Integer>();
			for(String h : heroIds){
				heroIdList.add(Integer.parseInt(h));
			}
			for(String r : retinueIds){
				retinueIdList.add(Integer.parseInt(r));
			}
			for(int hid : heroIdList){
				int indexKey = 0;
				if(fightList.getTroopType() == TroopTypeEnum.Attack.value()){
					indexKey = battlemapPrototype.getAttackPostionList().get(index);
				}else if(fightList.getTroopType() == TroopTypeEnum.Defend.value()){
					indexKey = battlemapPrototype.getDefensePostionList().get(index);
				}
				if (hid != 0) {
					HeroEntity heroEntity = heroMap.get(hid);
                    HeroPrototype heroPrototype = prototypeMgr.getHeroPrototype(heroEntity.heroId);
                    int herograde = FormulaUtil.getHerogradeKeyId(heroEntity.heroId, heroEntity.quality, heroEntity.starLevel);
                    HeroGradePrototype herogradePrototype = prototypeMgr.getHerogradePrototype(herograde);
                    CoordinatePrototype coordinate = prototypeMgr.getCoordinatePrototype(indexKey);
                    Position position = new Position(SimuiationConstant.Dimensions);
                    position.setCoordinate(0, coordinate.getPostionList().get(0));
                    position.setCoordinate(1, coordinate.getPostionList().get(1));
                    
                    List<EquipmentEntity> equipments = equipmentMap.get(hid);
                    
                    int retinueId = retinueIdList.get(index);
                    HeroEntity retinueEntity = new HeroEntity();
                    List<EquipmentEntity> retinueEquipments = new ArrayList<EquipmentEntity>();
                    if(retinueId != 0){
                        retinueEntity = heroMap.get(retinueId);
                        retinueEquipments = equipmentMap.get(retinueId);
                    }
                    simuiation.getObjFactory().createPerformerHero(player,position,heroEntity,heroPrototype, herogradePrototype,equipments,retinueEntity,retinueEquipments);
				}
				index++;
			}
		}
	}
}
