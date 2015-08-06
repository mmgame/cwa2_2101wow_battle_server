package com.cwa.battle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serverice.battle.BattleInfo;
import serverice.room.FightList;

import com.cwa.battle.constant.BattleConstant;
import com.cwa.battle.context.IBattleServiceContext;
import com.cwa.battle.task.IBScheduleMgr;
import com.cwa.service.constant.ServiceConstant;
import com.cwa.service.context.IGloabalContext;
import com.cwa.service.init.services.NettyService;
import com.cwa.util.KeyUtil;

/**
 * 战场服务
 * 
 * @author mausmars
 * 
 */
public class BattleService implements IBattleService, IBattleServiceContext {
	protected static final Logger logger = LoggerFactory.getLogger(BattleService.class);
	// {战场id：战场对象}
	private Map<String, IBattleGround> battleGroundMap = new HashMap<String, IBattleGround>();
	// -------------------------------
	// {战场战场类型：战场工厂}
	private Map<Integer, IBattleGroundFactory> battleGroundFactoryMap;

	private IBScheduleMgr scheduleMgr; // 任务管理

	private IGloabalContext gloabalContext;

	/**
	 * 开始服务
	 */
	@Override
	public void startup(IGloabalContext gloabalContext) {
		this.gloabalContext = gloabalContext;
		if (logger.isInfoEnabled()) {
			logger.info("BattleService startup!");
		}
		scheduleMgr.startup();
	}

	/**
	 * 停止服务
	 */
	@Override
	public void shutdown() {
		if (logger.isInfoEnabled()) {
			logger.info("BattleService shutdown!");
		}
		scheduleMgr.shutdown();
	}

	@Override
	public BattleInfo createBattle(int battleKeyId, List<FightList> fightLists) {
		int battleType = battleKeyId / BattleConstant.Type_Divisors;
		IBattleGroundFactory battleGroundFactory = battleGroundFactoryMap.get(battleType);
		if (battleGroundFactory == null) {
			// 没有该战场工厂
			return null;
		}
		// TODO 创建战场
		String battleId = KeyUtil.getUUID();
		IBattleGround battleGround = battleGroundFactory.createBattle(battleId, battleKeyId);
		if (battleGround == null) {
			// 战场创建失败
			return null;
		}
		battleGroundMap.put(battleGround.getId(), battleGround);

		// 设置战斗列表
		battleGround.insertFightLists(fightLists);
		// 设置上下文
		battleGround.setBattleContext(this);

		if (logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			for (FightList fightList : fightLists) {
				sb.append("[");
				sb.append("troopType=");
				sb.append(fightList.troopType);
				sb.append(" uid=");
				sb.append(fightList.uid);
				sb.append("]");
			}
			logger.info("CreateBattle! battleId=" + battleGround.getId() + " battleKeyId=" + battleKeyId + " " + sb.toString());
		}
		// TODO 启动战场流
		battleGround.getStateManager().getCurrentState().enter(battleGround);

		BattleInfo battleInfo = new BattleInfo();
		battleInfo.battleId = battleGround.getId();
		NettyService nettyService = (NettyService) gloabalContext.getCurrentService(ServiceConstant.NettyServerClientKey);
		battleInfo.port = nettyService.getServer().getPort();
		battleInfo.ip = gloabalContext.getOutsideNetIp();
		return battleInfo;
	}

	public IBattleGround getBattleGround(String id) {
		return battleGroundMap.get(id);
	}

	@Override
	public IBScheduleMgr getScheduleMgr() {
		return scheduleMgr;
	}

	// -----------------------------------------------
	public void setBattleGroundFactoryMap(Map<Integer, IBattleGroundFactory> battleGroundFactoryMap) {
		this.battleGroundFactoryMap = battleGroundFactoryMap;
	}

	public void setScheduleMgr(IBScheduleMgr scheduleMgr) {
		this.scheduleMgr = scheduleMgr;
	}
}
