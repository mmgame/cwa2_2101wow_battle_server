package com.cwa.message.player.handler;

import com.cwa.ISession;
import com.cwa.message.PlayerMessage.PlayerLoginDown;
import com.cwa.message.PlayerMessage.PlayerLoginUp;
import com.cwa.message.player.APlayerMessageHandler;
import com.cwa.net.ISessionManager;

public class PlayerLoginUpHandler extends APlayerMessageHandler<PlayerLoginUp> {

	@Override
	public Object unloginHandler(PlayerLoginUp message, ISession session) {

		// ContinuewarFilterA continuewarFilterA = new ContinuewarFilterA();
		// ContinuewarFilterB continuewarFilterB = new ContinuewarFilterB();
		// PostwarFilterA postwarFilterA = new PostwarFilterA();
		// PostwarFilterB postwarFilterB = new PostwarFilterB();
		// PrewarFilterA prewarFilterA = new PrewarFilterA();
		// PrewarFilterB prewarFilterB = new PrewarFilterB();
		//
		// List<ABattleFilter> prewarList = new ArrayList<ABattleFilter>();
		// prewarList.add(prewarFilterA);
		// prewarList.add(prewarFilterB);
		// List<ABattleFilter> fightingList = new ArrayList<ABattleFilter>();
		// fightingList.add(continuewarFilterA);
		// fightingList.add(continuewarFilterB);
		// List<ABattleFilter> postwarList = new ArrayList<ABattleFilter>();
		// postwarList.add(postwarFilterA);
		// postwarList.add(postwarFilterB);
		//
		// ABattleChain prewarABattleChain = new ABattleChain("prewar",
		// prewarList);// 战前链
		// ABattleChain fightingPrepareABattleChain = new
		// ABattleChain("fightingPrepare", new ArrayList<ABattleFilter>());//
		// 战中准备链
		// ABattleChain fightingABattleChain = new ABattleChain("fighting",
		// fightingList);// 战中链
		// ABattleChain postwarPrepareABattleChain = new
		// ABattleChain("postwarPrepare", new ArrayList<ABattleFilter>());//
		// 战后准备链
		// ABattleChain postwarABattleChain = new ABattleChain("postwar",
		// postwarList);// 战后链
		//
		// PrewarState<IBattleGround> prewarState = new
		// PrewarState<IBattleGround>();
		// FightingPrepareState<IBattleGround> fightingPrepareState = new
		// FightingPrepareState<IBattleGround>();
		// FightingState<IBattleGround> fightingState = new
		// FightingState<IBattleGround>();
		// PostwarPrepareState<IBattleGround> postwarPrepareState = new
		// PostwarPrepareState<IBattleGround>();
		// PostwarState<IBattleGround> postwarState = new
		// PostwarState<IBattleGround>();
		//
		// prewarState.setBattleChain(prewarABattleChain);
		// prewarState.setWaitTime(100);
		// fightingPrepareState.setBattleChain(fightingPrepareABattleChain);
		// fightingPrepareState.setWaitTime(100);
		// fightingState.setBattleChain(fightingABattleChain);
		// postwarPrepareState.setBattleChain(postwarPrepareABattleChain);
		// postwarPrepareState.setWaitTime(100);
		// postwarState.setBattleChain(postwarABattleChain);
		//
		// StateMachine<IBattleGround> stateMachine = new
		// StateMachine<IBattleGround>();
		//
		// Map<Integer, IBState<IBattleGround>> stateMap = new HashMap<Integer,
		// IBState<IBattleGround>>();
		// stateMap.put(BattleStateTypeEnum.SPrewar.value(), prewarState);
		// stateMap.put(BattleStateTypeEnum.SFightingPrepare.value(),
		// fightingPrepareState);
		// stateMap.put(BattleStateTypeEnum.SFighting.value(), fightingState);
		// stateMap.put(BattleStateTypeEnum.SPostwarPrepare.value(),
		// postwarPrepareState);
		// stateMap.put(BattleStateTypeEnum.SPostwar.value(), postwarState);
		// stateMachine.setStateMap(stateMap);
		// stateMachine.setbState(prewarState);
		//
		// BattleService b = new BattleService();
		//
		// ABattleGroundFactory b2 = new ABattleGroundFactory() {
		//
		// @Override
		// public IBattleGround create(String id) {
		// IBattleGround b3 = new IBattleGround(id) {
		// };
		// return b3;
		// }
		// };
		// Map<Integer, ABattleGroundFactory> battleGroundFactoryMap = new
		// HashMap<Integer, ABattleGroundFactory>();
		// battleGroundFactoryMap.put(1, b2);
		// // 仿真器引擎
		// QuartzTaskManager taskManager = new QuartzTaskManager();
		// TaskTypeConfigFactory factory = new TaskTypeConfigFactory();
		// ScheduleMgr s = new ScheduleMgr();
		//
		// s.setTaskManager(taskManager);
		// s.setTaskTypeConfigFactory(factory);
		// b.setBattleGroundFactoryMap(battleGroundFactoryMap);
		// b.setScheduleMgr(s);
		// List<String> userList = new ArrayList<String>();
		// userList.add("10001");
		// userList.add("10002");
		// userList.add("10003");
		// userList.add("10004");
		// String id = b.createBattle(1, userList);
		// IBattleGround battleGround = b.getBattleGround(id);
		// stateMachine.setBattleGround(battleGround);
		// battleGround.setStateMachine(stateMachine);
		// battleGround.setTaskManager(taskManager);
		// battleGround.setTaskTypeConfigFactory(factory);
		// b.startup();
		//
		// stateMachine.getbState().enter(battleGround);

		logger.info("---------------------");

		long userId = message.getUserId();
		if (logger.isInfoEnabled()) {
			logger.info("", message);
		}
		PlayerLoginDown.Builder bb = PlayerLoginDown.newBuilder();
		bb.setLoginState(1);
		bb.setLogoutTime(System.currentTimeMillis());
		// session绑定userId
		session.setAttachment(ISessionManager.Target_Key, userId);
		session.send(bb.build());
		if (logger.isErrorEnabled()) {
			logger.error("User Login success!!!", message.getUserId());
		}
		return userId;
	}

}