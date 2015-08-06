package com.cwa.battle.service;

import java.util.List;

import serverice.battle.BattleInfo;
import serverice.battle._IBattleServiceDisp;
import serverice.room.FightList;
import Ice.Current;

import com.cwa.battle.IBattleService;

/**
 * 战场服务（ICE远程调用接口）
 * 
 * @author mausmars
 * 
 */
public class BattleServiceI extends _IBattleServiceDisp {
	private static final long serialVersionUID = 1L;

	private IBattleService battleServiceI;

	@Override
	public BattleInfo createBattle(int battleKeyId, List<FightList> fightLists, Current __current) {
		return battleServiceI.createBattle(battleKeyId, fightLists);
	}

	// ------------------------------------
	public void setBattleServiceI(IBattleService battleServiceI) {
		this.battleServiceI = battleServiceI;
	}
}
