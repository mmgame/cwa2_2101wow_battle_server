package com.cwa.simuiationimpl.event.handler.effect;

import java.util.Random;

import com.cwa.prototype.EffectPrototype;
import com.cwa.simuiationimpl.action.MagicActionContext;

//效果bean
public class AffectedBean {
	private MagicActionContext actionContext;

	private EffectPrototype effectPrototype;// 效果id
	private int count;// 连击次数

	private int attackType;// 技能攻击类型（1普通攻击2技能3小技能[小技能计算伤害按普攻算]）

	private int seed;
	private Random random;

	public AffectedBean(int seed) {
		this.seed = seed;
		random = new Random(seed);
	}

	public Random getRandom() {
		return random;
	}

	public EffectPrototype getEffectPrototype() {
		return effectPrototype;
	}

	public int getCount() {
		return count;
	}

	public void increaseCount() {
		count++;
	}

	public int getAttackType() {
		return attackType;
	}

	public void setEffectPrototype(EffectPrototype effectPrototype) {
		this.effectPrototype = effectPrototype;
	}

	public MagicActionContext getActionContext() {
		return actionContext;
	}

	public void setActionContext(MagicActionContext actionContext) {
		this.actionContext = actionContext;
	}

	public void setAttackType(int attackType) {
		this.attackType = attackType;
	}

	public int getSeed() {
		return seed;
	}
}
