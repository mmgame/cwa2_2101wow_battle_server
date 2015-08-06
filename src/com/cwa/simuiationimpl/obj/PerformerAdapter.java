package com.cwa.simuiationimpl.obj;

import com.cwa.simuiation.IPlayer;
import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.buff.IBuffManager;
import com.cwa.simuiation.context.ISGlobalContext;
import com.cwa.simuiation.manager.IAttrMgr;
import com.cwa.simuiation.manager.ISkillManager;
import com.cwa.simuiation.map.Position;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.passiveskill.IPassiveSkillManager;
import com.cwa.simuiation.state.IStateManager;

public abstract class PerformerAdapter implements IPerformer {
	// id
	protected int id;
	// 子类型
	protected int subType;
	// 上下文
	protected ISGlobalContext context;
	// 坐标
	protected Position position;

	// 所有者
	protected IPlayer owner;
	// 控制者
	protected IPlayer controler;
	
	protected boolean isRomove=false;

	@Override
	public IPlayer getOwner() {
		return owner;
	}

	@Override
	public IPlayer getControler() {
		return controler;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public ISGlobalContext getGlobalContext() {
		return context;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public int getType() {

		return 0;
	}

	@Override
	public int getSubType() {

		return 0;
	}

	@Override
	public void performActions() {

	}

	@Override
	public void update() {

	}

	@Override
	public void requestAction(IActionContext context) {

	}

	@Override
	public IStateManager getStateManager() {
		return null;
	}

	@Override
	public IBuffManager getBuffManager() {
		return null;
	}

	@Override
	public IAttrMgr getAttrMgr() {
		return null;
	}

	@Override
	public ISkillManager getSkillManager() {
		return null;
	}
	@Override
	public IPassiveSkillManager getPassiveSkillManager() {
		return null;
	}

	@Override
	public void setControler(IPlayer controler) {
		this.controler = controler;
	}

	public void setOwner(IPlayer owner) {
		this.owner = owner;
		this.controler = owner;
	}
	@Override
	public boolean isRomove(){
		 return isRomove;
	 }
	// ---------------------------------
	public void setId(int id) {
		this.id = id;
	}
	public void setContext(ISGlobalContext context) {
		this.context = context;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setRomove(boolean isRomove) {
		this.isRomove = isRomove;
	}
}
