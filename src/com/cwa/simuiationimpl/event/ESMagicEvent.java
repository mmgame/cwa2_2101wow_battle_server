package com.cwa.simuiationimpl.event;

import java.util.List;

import com.cwa.simuiation.enums.EventSubTypeEnum;
import com.cwa.simuiation.event.ISStateEvent;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;
import com.cwa.simuiationimpl.action.MagicActionContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.event.bean.MagicResult;

/**
 * 施法状态事件
 * 
 * @author mausmars
 * 
 */
public class ESMagicEvent extends ISStateEvent {
	private int step; // 阶段
	private int mstate;//消息状态1成功0失败
	private int errorType;//施法失败类型
	private int errorValue;

	private MagicActionContext actionContext;

	// 法术结果
	private MagicResult magicResult = new MagicResult();

	public void addNewObjects(IPerformer performer) {
		magicResult.addNewObjects(performer);
	}

	public void insertMagicEffectBean(int eid, int randseed, List<IPerformer> performers) {
		magicResult.insertMagicEffectBean(eid, randseed, performers);
	}

	@Override
	public int getEventType() {
		return EventSubTypeEnum.ES_Magic.value() / SimuiationConstant.Type_Divisors;
	}

	@Override
	public int getEventSubType() {
		return EventSubTypeEnum.ES_Magic.value();
	}

	@Override
	public int getChannel() {
		return channel;
	}

	@Override
	public int getStep() {
		return step;
	}

	@Override
	public List<String> getReceivers() {
		return receivers;
	}

	@Override
	public IPerformer getSource() {
		return actionContext.getPerformer();
	}

	@Override
	public ISObject getTarget() {
		return actionContext.getTarget();
	}

	public MagicActionContext getActionContext() {
		return actionContext;
	}

	public MagicResult getMagicResult() {
		return magicResult;
	}

	// -------------------------
	@Override
	public void setStep(int step) {
		this.step = step;
	}

	public void setActionContext(MagicActionContext actionContext) {
		this.actionContext = actionContext;
	}

	public int getMstate() {
		return mstate;
	}

	public void setMstate(int mstate) {
		this.mstate = mstate;
	}

	public void setMagicResult(MagicResult magicResult) {
		this.magicResult = magicResult;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}

	public int getErrorValue() {
		return errorValue;
	}

	public void setErrorValue(int errorValue) {
		this.errorValue = errorValue;
	}
}
