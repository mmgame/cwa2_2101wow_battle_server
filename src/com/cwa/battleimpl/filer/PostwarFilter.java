package com.cwa.battleimpl.filer;

import serverice.battle.BattleOverEvent;
import baseice.event.IEvent;
import baseice.event.IEventListenerPrx;
import baseice.service.FunctionTypeEnum;

import com.cwa.battle.IBattleGround;
import com.cwa.battle.filter.ABattleFilter;
import com.cwa.battleimpl.state.BattleStateTypeEnum;
import com.cwa.component.functionmanage.IFunctionCluster;
import com.cwa.component.functionmanage.IFunctionService;
import com.cwa.service.context.IGloabalContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.ESOverEvent;
import com.cwa.simuiationimpl.util.XXXUtil;

/**
 * 战后
 * 
 * @author mausmars
 *
 */
public class PostwarFilter extends ABattleFilter {
	private IGloabalContext gloabalContext;

	public PostwarFilter() {
		super("PostwarFilter");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		if (logger.isInfoEnabled()) {
			logger.info("PostwarFilter doWork!");
		}
		final IBattleGround battleGround = (IBattleGround) context;
		// 通知客户端战场结束准备阶段
		sendStateChangeEvent(battleGround, BattleStateTypeEnum.SPostwar);
		// performer一方失败
		ESOverEvent oe = new ESOverEvent();
		oe.setFailureTroopType(XXXUtil.selectFailure(battleGround.getSimuiation()));
		battleGround.getSimuiation().sendEvent(SimuiationConstant.ListenerKey_ClientMgr, oe);

		if (logger.isErrorEnabled()) {
			logger.error("sendEventToRoom！！！！！！！！！！！！！！！！！！！！！"+battleGround.getId());
		}
		// TODO 通知房间服战场结束
		BattleOverEvent battleOverEvent = new BattleOverEvent();
		battleOverEvent.battleId = battleGround.getId();
		sendEventToRoom(battleOverEvent);
		return true;
	}

	private void sendEventToRoom(IEvent event) {
		IFunctionService functionService = gloabalContext.getCurrentFunctionService();
		IFunctionCluster functionCluster = functionService.getFunctionCluster(gloabalContext.getGid(), FunctionTypeEnum.Room);
		IEventListenerPrx prx = functionCluster.getMasterService(IEventListenerPrx.class);
		prx.answer(event);
	}

	// -----------------------------------------------------
	public void setGloabalContext(IGloabalContext gloabalContext) {
		this.gloabalContext = gloabalContext;
	}
}
