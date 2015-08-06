package com.cwa.simuiation.obj;

import com.cwa.simuiation.IPlayer;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.buff.IBuffManager;
import com.cwa.simuiation.context.ISGlobalContext;
import com.cwa.simuiation.manager.IAttrMgr;
import com.cwa.simuiation.manager.ISkillManager;
import com.cwa.simuiation.passiveskill.IPassiveSkillManager;
import com.cwa.simuiation.state.IStateManager;

/**
 * 执行者
 * 
 * @author mausmars
 * 
 */
public interface IPerformer extends ISObject {
	/**
	 * 处理动作请求
	 * 
	 * @param action
	 */
	void performActions();

	/**
	 * 更新
	 */
	void update();

	/**
	 * 请求action
	 * 
	 * @param action
	 */
	void requestAction(IActionContext context);

	// -----------------------
	/**
	 * 所有者
	 * 
	 * @return
	 */
	IPlayer getOwner();

	/**
	 * 控制者
	 * 
	 * @return
	 */
	IPlayer getControler();

	/**
	 * 设置控制者
	 * 
	 * @return
	 */
	void setControler(IPlayer controler);

	// -----------------------
	/**
	 * 得到上下文
	 */
	ISGlobalContext getGlobalContext();

	/**
	 * 状态管理
	 * 
	 * @return
	 */
	IStateManager getStateManager();

	/**
	 * buff管理
	 * 
	 * @return
	 */
	IBuffManager getBuffManager();
	/**
	 * 被动技能
	 * @return
	 */
	IPassiveSkillManager getPassiveSkillManager();

	/**
	 * 属性管理
	 * 
	 * @return
	 */
	IAttrMgr getAttrMgr();

	/**
	 * 技能管理
	 * 
	 * @return
	 */
	ISkillManager getSkillManager();
	
	/**
	 * 物体是否可移除
	 * @return
	 */
    boolean isRomove();
}
