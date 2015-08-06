package com.cwa.simuiationimpl.obj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.buff.IBuffManager;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.manager.IAttrMgr;
import com.cwa.simuiation.manager.ISkillManager;
import com.cwa.simuiation.passiveskill.IPassiveSkillManager;
import com.cwa.simuiation.state.IActionStateContext;
import com.cwa.simuiation.state.IControlStateContext;
import com.cwa.simuiation.state.ISControlState;
import com.cwa.simuiation.state.IStateManager;

/**
 * 演员
 * 
 * @author mausmars
 * 
 */
public abstract class AActor extends PerformerAdapter {
	protected static final Logger logger = LoggerFactory.getLogger(AActor.class);
	// -------------------------
	// 状态管理
	protected IStateManager stateManager;
	// buff管理
	protected IBuffManager buffManager;
	// 技能管理
	protected ISkillManager skillManager;
	// 属性管理
	protected IAttrMgr attrMgr;
	// 被动技能管理
	protected IPassiveSkillManager passiveSkillManager;

	/**
	 * 处理动作
	 */
	@Override
	public void performActions() {
		IControlStateContext csc = (IControlStateContext) stateManager.getCurrentState(StateTypeEnum.Control.value());
		if (csc == null) {
			return;
		}
		ISControlState sc = (ISControlState) csc.getState();
		if (sc.getSubType() == StateSubTypeEnum.CS_AI.value()) {
			// ai控制
			IActionStateContext asc = sc.performAction(csc);
			if (asc != null) {
				stateManager.transtion(asc);
			}
		} else if (sc.getSubType() == StateSubTypeEnum.CS_User.value()) {
			// 用户控制
			for (;;) {
				// 执行完全部动作
				IActionStateContext asc = sc.performAction(csc);
				if (asc == null) {
					break;
				}
				stateManager.transtion(asc);
			}
		}
	}

	/**
	 * actor更新
	 */
	@Override
	public void update() {
		stateManager.update();
		buffManager.update();
		if(passiveSkillManager != null){
			passiveSkillManager.update();
		}
	}

	/**
	 * 接收动作
	 */
	@Override
	public void requestAction(IActionContext context) {
		IControlStateContext csc = (IControlStateContext) stateManager.getCurrentState(StateTypeEnum.Control.value());
		if (csc == null || csc.getState().getSubType() != StateSubTypeEnum.CS_User.value()) {
			return;
		}
		csc.requestAction(context);
	}

	@Override
	public IStateManager getStateManager() {
		return stateManager;
	}

	@Override
	public ISkillManager getSkillManager() {
		return skillManager;
	}

	@Override
	public IBuffManager getBuffManager() {
		return buffManager;
	}

	@Override
	public IAttrMgr getAttrMgr() {
		return attrMgr;
	}

	@Override
	public String toString() {
		return "IActor [ id=" + id + " position=" + position + "]";
	}

	@Override
	public int getSize() {
		return attrMgr.getSize();
	}

	// -------------------------------------------
	public void setStateManager(IStateManager stateManager) {
		this.stateManager = stateManager;
	}

	public void setBuffManager(IBuffManager buffManager) {
		this.buffManager = buffManager;
	}

	public void setSkillManager(ISkillManager skillManager) {
		this.skillManager = skillManager;
	}

	public void setAttrMgr(IAttrMgr attrMgr) {
		this.attrMgr = attrMgr;
	}

	public IPassiveSkillManager getPassiveSkillManager() {
		return passiveSkillManager;
	}

	public void setPassiveSkillManager(IPassiveSkillManager passiveSkillManager) {
		this.passiveSkillManager = passiveSkillManager;
	}
}
