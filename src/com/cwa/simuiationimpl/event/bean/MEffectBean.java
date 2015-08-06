package com.cwa.simuiationimpl.event.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cwa.simuiation.obj.IPerformer;
/**
 * 一个技能效果产生的影响
 * @author yangfeng
 *
 */
public class MEffectBean {
	// 英雄
	private int eid;//效果id
	private List<IPerformer> performers = new LinkedList<IPerformer>();
	//<英雄仿真id,产生的效果>
	private Map<Integer,PEffectBean> performerEffectBeans = new HashMap<Integer,PEffectBean>();
	// 随机数种子
	private int randseed;
	
	private int bid;//子弹id
	private List<PetBean> petBeanList = new ArrayList<PetBean>();//宠物
	private List<TrapBean> trapBeanList = new ArrayList<TrapBean>();//陷阱

	public int getRandseed() {
		return randseed;
	}

	public void setRandseed(int randseed) {
		this.randseed = randseed;
	}

	public List<IPerformer> getPerformers() {
		return performers;
	}

	public void setPerformers(List<IPerformer> performers) {
		this.performers = performers;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public List<PetBean> getPetBeanList() {
		return petBeanList;
	}

	public void addPetBean(PetBean petBean) {
		petBeanList.add(petBean);
	}

	public List<TrapBean> getTrapBeanList() {
		return trapBeanList;
	}

	public void addTrapBean(TrapBean trapBean) {
		trapBeanList.add(trapBean);
	}

	public Map<Integer,PEffectBean> getPerformerEffectBeans() {
		return performerEffectBeans;
	}

	public void addPerformerEffectBeans(PEffectBean performerEffectBean) {
		this.performerEffectBeans.put(performerEffectBean.getPid(), performerEffectBean);
	}

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

}
