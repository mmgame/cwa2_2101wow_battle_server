package com.cwa.simuiationimpl.event.bean;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cwa.simuiation.obj.IPerformer;

public class MagicResult {
	// {效果id:打到的人}
	private Map<Integer, MEffectBean> magicEffectBeanMap = new HashMap<Integer, MEffectBean>();

	// 产生的新物体
	private List<IPerformer> newObjects = new LinkedList<IPerformer>();

	public List<IPerformer> getNewObjects() {
		return newObjects;
	}

	public void addNewObjects(IPerformer performer) {
		newObjects.add(performer);
	}

	public void insertMagicEffectBean(int eid, int randseed, List<IPerformer> performers) {
		MEffectBean magicEffectBean = new MEffectBean();
		magicEffectBean.setPerformers(performers);
		magicEffectBean.setRandseed(randseed);
		magicEffectBeanMap.put(eid, magicEffectBean);
	}

	public MEffectBean getMagicEffectBeanListByEffectId(int effectid) {
		return magicEffectBeanMap.get(effectid);
	}

	public Map<Integer, MEffectBean> getMagicEffectBeanMap() {
		return magicEffectBeanMap;
	}

}
