package com.cwa.simuiationimpl.buff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwa.prototype.BuffPrototype;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.DeBuffResistanceEnum;
import com.cwa.simuiation.buff.IBuffContext;
import com.cwa.simuiation.buff.IBuffManager;
import com.cwa.simuiation.clock.SClock;
import com.cwa.simuiation.manager.IBuffContextFactory;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiation.task.ITask;
import com.cwa.simuiationimpl.buff.task.BuffTask;
import com.cwa.simuiationimpl.buff.task.BuffTaskQueue;
import com.cwa.simuiationimpl.constant.BuffConstant;
import com.cwa.simuiationimpl.util.FormulaUtil;

public class BuffManager implements IBuffManager {
	// 拥有者
	private IPerformer performer;
	private IBuffContextFactory buffContextFactory;

	private BuffTaskQueue queue = new BuffTaskQueue();

	// {buffTypeKeyId:IBuffContext}
	private Map<String, IBuffContext> buffContextMap = new HashMap<String, IBuffContext>();

	public BuffManager(IPerformer performer, IBuffContextFactory buffContextFactory) {
		this.performer = performer;
		this.buffContextFactory = buffContextFactory;
	}

	@Override
	public boolean enter(Integer id, int value) {
		BuffPrototype buffPrototype = performer.getGlobalContext().getProtpyeMgr().getBuffPrototype(id);
		if (buffPrototype == null) {
			return false;
		}
		int rate = buffPrototype.getRate();
		if(buffPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value() && 
				buffPrototype.getType() <= DeBuffResistanceEnum.values().length){
			rate = (int)FormulaUtil.getDebuffHitRate(rate, performer.getAttrMgr().getDebuffResistanceFinalValue(buffPrototype.getType()));
		}
		if (value >= rate) {
			// 没中buff
			return false;
		}
		IBuffContext context = buffContextFactory.createContext(buffPrototype.getType(), performer, buffPrototype, performer
				.getGlobalContext().getClock().getCurrentSTime());

		String buffTypeKeyId = buffPrototype.getBuffTypeKeyId();
		if (buffContextMap.containsKey(BuffTypeEnum.Buff_RemoveAllBad.value() + buffTypeKeyId.substring(buffTypeKeyId.indexOf("_")))) {
			// 拥有明镜止水Buff
			if (buffPrototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()) {
				// 减益
				return false;
			}
		}
		String taskId = BuffConstant.taskId + performer.getId() + "_" + buffTypeKeyId;
		if (buffContextMap.containsKey(buffTypeKeyId)) {
			// 拥有同类型buff且优先级比较高
			if (buffPrototype.getPriority() < buffContextMap.get(buffTypeKeyId).getBuffPrototype().getPriority()) {
				return false;
			} else {
				// 先删除队列中原有的任务
				queue.removeTask(taskId);
			}
		}
		SClock clock = context.getPerformer().getGlobalContext().getClock();
		BuffTask task = new BuffTask(taskId, context, clock, buffPrototype.getEffectiveTime(), buffPrototype.getIntervalTime());
		queue.addTask(task);
		buffContextMap.put(buffTypeKeyId, context);
		context.getBuff().enter(context);
		return true;
	}

	@Override
	public void exit(IBuffContext context) {
		IBuffContext c = buffContextMap.remove(context.getBuffPrototype().getBuffTypeKeyId());
		if (c != null) {
			c.getBuff().exit(c);
		}
	}

	@Override
	public void update() {
		ITask task = queue.poll();
		while(task != null) {
			task.excute();
			task = queue.poll();
		}
	}

	@Override
	public boolean canTranstion(IStateContext context) {
		for (IBuffContext bc : buffContextMap.values()) {
			if (!bc.getBuff().canTranstion(context)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public List<Integer> dispelBuff(BuffBeneficialTypeEnum e, List<String> buffTypeList) {
		List<Integer> buffs = new ArrayList<Integer>();
		if (buffTypeList != null) {
			for (String buffType : buffTypeList) {
				IBuffContext context = buffContextMap.get(buffType);
				if (context != null) {
					String buffTypeKeyId =  context.getBuffPrototype().getBuffTypeKeyId();
					String taskId = BuffConstant.taskId + performer.getId() + "_"  + buffTypeKeyId;
					queue.removeTask(taskId);
					buffs.add(context.getId());
					exit(context);
				}
			}
		} else {
			List<IBuffContext> removeContextList = new ArrayList<IBuffContext>();
			for (String buffType : buffContextMap.keySet()) {
				IBuffContext context = buffContextMap.get(buffType);
				if (context != null && e.value() == context.getBuffPrototype().getBuffOrDebuff()) {
					String buffTypeKeyId =  context.getBuffPrototype().getBuffTypeKeyId();
					String taskId = BuffConstant.taskId + performer.getId() + "_" + buffTypeKeyId;
					queue.removeTask(taskId);
					removeContextList.add(context);
				}
			}
			for(IBuffContext context : removeContextList){
				buffs.add(context.getId());
				exit(context);
			}
		}
		return buffs;
	}

	/**
	 * 拥有减益buff
	 * @return
	 */
	public Boolean haveDebuff(){
		for (String buffType : buffContextMap.keySet()) {
			IBuffContext context = buffContextMap.get(buffType);
			if (context != null && BuffBeneficialTypeEnum.Beneficial_Bad.value() == context.getBuffPrototype().getBuffOrDebuff()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 拥有增益buff
	 */
	public Boolean haveBuff(){
		for (String buffType : buffContextMap.keySet()) {
			IBuffContext context = buffContextMap.get(buffType);
			if (context != null && BuffBeneficialTypeEnum.Beneficial_Good.value() == context.getBuffPrototype().getBuffOrDebuff()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public IPerformer getPerformer() {
		return performer;
	}

	@Override
	public Map<String, IBuffContext> getBuffContextMap() {
		return buffContextMap;
	}
	@Override
	public void clear() {
		queue.clear();
		buffContextMap.clear();
	}

	public static void main(String args[]) {
		String ddd = "A_1_3_4";
		int index = ddd.indexOf("_");

		ddd = "B" + ddd.substring(index);
		System.out.println(ddd);
	}
}
