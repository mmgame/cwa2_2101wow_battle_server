package com.cwa.battle;

import java.util.List;

import serverice.battle.BattleInfo;
import serverice.room.FightList;

import com.cwa.service.IModuleServer;

public interface IBattleService extends IModuleServer {
	BattleInfo createBattle(int battleKeyId, List<FightList> fightLists);

	IBattleGround getBattleGround(String id);
}
