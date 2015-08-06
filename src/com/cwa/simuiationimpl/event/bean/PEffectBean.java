package com.cwa.simuiationimpl.event.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个技能效果对某个执行者产生的影响
 * @author yangfeng
 *
 */
public class PEffectBean {
	private int pid;//
	private List<Integer> values = new ArrayList<Integer>();
	private List<Integer> effects = new ArrayList<Integer>();
	private List<Integer> buffs = new ArrayList<Integer>();//增加的buff
	private List<Integer> deleteBuffs = new ArrayList<Integer>();//被驱散的buff
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public List<Integer> getValues() {
		return values;
	}
	public void addValue(Integer value) {
		this.values.add(value);
	}
	public List<Integer> getEffects() {
		return effects;
	}
	public void addEffects(Integer effect) {
		this.effects.add(effect);
	}
	public List<Integer> getBuffs() {
		return buffs;
	}
	public void addBuffs(Integer buff) {
		this.buffs.add(buff);
	}
	public List<Integer> getDeleteBuffs() {
		return deleteBuffs;
	}
	public void setDeleteBuffs(List<Integer> buffs) {
		this.deleteBuffs = buffs;
	}
}
