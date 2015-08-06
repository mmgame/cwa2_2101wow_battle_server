package com.cwa.battle;

/**
 * 战场工厂
 * 
 * @author mausmars
 * 
 */
public interface IBattleGroundFactory {
	/**
	 * 创建战场
	 * 
	 * @param id
	 * @param keyId
	 * @return
	 */
	IBattleGround createBattle(String id, int keyId);
}
