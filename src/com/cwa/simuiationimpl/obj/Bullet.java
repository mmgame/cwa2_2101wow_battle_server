package com.cwa.simuiationimpl.obj;

import com.cwa.simuiation.action.IActionContext;
import com.cwa.simuiation.action.skill.IEffectTrigger;
import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.enums.StepTypeEnum;
import com.cwa.simuiation.manager.IAttrMgr;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiationimpl.action.MoveActionContext;

/**
 * 子弹
 * 
 * @author mausmars
 * 
 */
public class Bullet extends PerformerAdapter {
	// 移动上下文
	private MoveActionContext ac;

	// 属性管理
	protected IAttrMgr attrMgr;

	// 效果触发
	private IEffectTrigger effectTrigger;

	//源
	private IPerformer source;
	@Override
	public int getType() {
		return ObjTypeEnum.Performer.value();
	}

	@Override
	public int getSubType() {
		return ObjSubTypeEnum.P_Bullet.value();
	}

	@Override
	public void update() {
		// 执行动作的update
		ac.getAction().update(ac);

		if (ac.getOver() == StepTypeEnum.ST_Null.value()) {
			if (ac.isTrigger()) {
				// 每帧重置
				ac.resetContextByFrame();
			}
		} else {
			// 发生碰撞
			effectTrigger.trigger();
			setRomove(true);
//			this.getGlobalContext().getSobMgr().remove(id);
		}
	}

	@Override
	public IAttrMgr getAttrMgr() {
		return attrMgr;
	}

	@Override
	public int getSize() {
		return 0;
	}
	public IPerformer getSource() {
		return source;
	}
	// --------------------------------------
	public void setActionContext(IActionContext ac) {
		this.ac = (MoveActionContext) ac;
	}

	public void setEffectTrigger(IEffectTrigger effectTrigger) {
		this.effectTrigger = effectTrigger;
	}

	public void setAttrMgr(IAttrMgr attrMgr) {
		this.attrMgr = attrMgr;
	}



	public void setSource(IPerformer source) {
		this.source = source;
	}
}