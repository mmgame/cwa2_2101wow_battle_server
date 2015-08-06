package com.cwa.test.battle_ice;

import java.util.ArrayList;
import java.util.List;

import serverice.battle.IBattleServicePrx;
import serverice.battle.IBattleServicePrxHelper;
import serverice.room.FightList;
import serverice.room.TroopTypeEnum;
import baseice.basedao.IEntity;

import com.cwa.data.entity.domain.HeroEntity;

public class StartBattleTestIceServer {

	public static void main(String[] args) {
		int status = 0;
		Ice.Communicator ic = null;
		String url = String.format("%s -h %s -p %d", "tcp", "192.168.1.251", 30701);
		try {
			ic = Ice.Util.initialize(args);
			Ice.ObjectPrx base = ic.stringToProxy("IBattleService:" + url);
			IBattleServicePrx battle = IBattleServicePrxHelper.checkedCast(base);
			if (battle == null) {
				throw new Error("battle is null");
			}
			List<FightList> troops = new ArrayList<FightList>();
			List<IEntity> heroid1 = new ArrayList<IEntity>();
			HeroEntity h11 = new HeroEntity();
			h11.heroId = 600500;
			h11.quality = 6;
			h11.starLevel = 1;

			HeroEntity h12 = new HeroEntity();
			h12.heroId = 602300;
			h12.quality = 6;
			h12.starLevel = 1;

			HeroEntity h13 = new HeroEntity();
			h13.heroId = 600200;
			h13.quality = 6;
			h13.starLevel = 1;

			HeroEntity h14 = new HeroEntity();
			h14.heroId = 601000;
			h14.quality = 6;
			h14.starLevel = 1;

			heroid1.add(h11);
			heroid1.add(h12);
			heroid1.add(h13);
			heroid1.add(h14);
			long u1 = 10001;
			FightList t1 = new FightList();
			t1.troopType = TroopTypeEnum.Attack;
			t1.tid = "t1";
			t1.uid = u1;
			t1.heroIds = heroid1;

			List<IEntity> heroid2 = new ArrayList<IEntity>();
			HeroEntity h21 = new HeroEntity();
			h21.heroId = 600500;
			h21.quality = 6;
			h21.starLevel = 1;

			HeroEntity h22 = new HeroEntity();
			h22.heroId = 602300;
			h22.quality = 6;
			h22.starLevel = 1;

			HeroEntity h23 = new HeroEntity();
			h23.heroId = 600200;
			h23.quality = 6;
			h23.starLevel = 1;

			HeroEntity h24 = new HeroEntity();
			h24.heroId = 601000;
			h24.quality = 6;
			h24.starLevel = 1;

			heroid2.add(h21);
			heroid2.add(h22);
			heroid2.add(h23);
			heroid2.add(h24);
			long u2 = 10002;
			FightList t2 = new FightList();
			t2.troopType = TroopTypeEnum.Defend;
			t2.tid = "t2";
			t2.uid = u2;
			t2.heroIds = heroid2;
			troops.add(t1);
			troops.add(t2);
			battle.createBattle(1001, troops);
		} catch (Ice.LocalException e) {
			e.printStackTrace();
			status = 1;

		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;

		} finally {
			if (ic != null) {
				ic.destroy();
			}
		}
		System.exit(status);
	}

}
