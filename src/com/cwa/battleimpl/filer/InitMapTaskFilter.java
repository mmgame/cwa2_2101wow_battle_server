package com.cwa.battleimpl.filer;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.prototype.BattlePrototype;
import com.cwa.prototype.BattlemapPrototype;
import com.cwa.simuiation.map.Point;
import com.cwa.simuiationimpl.map.AreaMgr;

/**
 * 初始化地图
 * 
 * @author mausmars
 * 
 */
public class InitMapTaskFilter extends ABattleFilter {
	public InitMapTaskFilter() {
		super("InitMapTaskFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("InitMapTaskFilter doWork!");
		}

		IBattleGround battleGround = (IBattleGround) context;
		BattlePrototype battlePrototype = battleGround.getSimuiation().getProtpyeMgr().getBattlePrototype();
		BattlemapPrototype battlemapPrototype = battleGround.getSimuiation().getProtpyeMgr()
				.getBattlemapPrototype(battlePrototype.getMap());
		Point p = new Point(battlemapPrototype.getMapArea().size());
		int index = 0;
		for (int v : battlemapPrototype.getMapArea()) {
			p.setCoordinate(index, v);
			index++;
		}
		AreaMgr areaMgr = (AreaMgr) battleGround.getSimuiation().getAreaMgr();
		areaMgr.setPoint(p);
		return true;
	}

}
