package com.cwa.battleimpl.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleChain;
import com.cwa.battle.state.IBState;

/**
 * 通用状态
 * 
 * @author mausmars
 * 
 */
public class GeneralState implements IBState {
	protected static final Logger logger = LoggerFactory.getLogger(GeneralState.class);

	// 战场处理链
	protected ABattleChain battleChain;
	
	protected int type;
	

	
	@Override
	public void enter(IBattleGround battleGround) {
		// 执行责任链
		execute(battleGround);
	}

	@Override
	public void exit(IBattleGround battleGround) {

	}

	@Override
	public void update(IBattleGround battleGround) {

	}
	@Override
	public int getType() {
		return type;
	}

	protected void execute(IBattleGround battleGround) {
		try {
			if (battleChain == null) {
				return;
			}
			battleChain.doFilter(null, battleGround);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	// --------------------------------------
	public void setBattleChain(ABattleChain battleChain) {
		this.battleChain = battleChain;
	}
	public void setType(int type) {
		this.type = type;
	}

}
