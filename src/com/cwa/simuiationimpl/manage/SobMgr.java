package com.cwa.simuiationimpl.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.simuiation.enums.ObjSubTypeEnum;
import com.cwa.simuiation.enums.ObjTypeEnum;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.enums.StateTypeEnum;
import com.cwa.simuiation.manager.ISobMgr;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.obj.ISObject;

/**
 * 仿真对象管理（ 管理全部的对象）
 * 
 * @author mausmars
 * 
 */
public class SobMgr implements ISobMgr {
	protected static final Logger logger = LoggerFactory.getLogger(SobMgr.class);

	/**
	 * (key：仿真对象)
	 */
	private Map<Integer, ISObject> sobMap = new HashMap<Integer, ISObject>();
	/**
	 * (子类型：仿真对象)
	 */
	private Map<Integer, Set<ISObject>> sobSubMap = new HashMap<Integer, Set<ISObject>>();

	@Override
	public void insert(ISObject sob) {
		sobMap.put(sob.getId(), sob);

		Set<ISObject> sobSubs = sobSubMap.get(sob.getSubType());
		if (sobSubs == null) {
			sobSubs = new HashSet<ISObject>();
			sobSubMap.put(sob.getSubType(), sobSubs);
		}
		sobSubs.add(sob);
	}

	@Override
	public List<ISObject> getSObjectListByType(int objType) {
		List<ISObject> list = new ArrayList<ISObject>();
		Iterator<Entry<Integer, ISObject>> it = sobMap.entrySet().iterator();
		for (; it.hasNext();) {
			Entry<Integer, ISObject> entry = it.next();
			if (entry.getValue().getType() == objType) {
				IPerformer obj = (IPerformer) entry.getValue();
				if (obj.getSubType() == ObjSubTypeEnum.P_Hero.value() || obj.getSubType() == ObjSubTypeEnum.P_Pet.value()) {
					// Performer为英雄或者宠物
					if (obj.getStateManager().getCurrentState(StateTypeEnum.Global.value()).getState().getSubType() != StateSubTypeEnum.GS_Dead.value()) {
						list.add(entry.getValue());
					}
				} else {
					list.add(entry.getValue());
				}
			}
		}
		return list;
	}

	@Override
	public Set<ISObject> getSObjectListBySubType(int objSubType) {
		return sobSubMap.get(objSubType);
	}

	@Override
	public ISObject select(int id) {
		return sobMap.get(id);
	}

	@Override
	public ISObject remove(int id) {
		ISObject obj = sobMap.remove(id);
		if (obj != null) {
			Set<ISObject> sobSubs = sobSubMap.get(obj.getSubType());
			if (sobSubs != null) {
				sobSubs.remove(obj);
			}
		}
		return obj;
	}

	@Override
	public void restore() {
	}

	@Override
	public void store() {
	}

	@Override
	public void remove() {
		Iterator<Entry<Integer, ISObject>> it = sobMap.entrySet().iterator();
		for (; it.hasNext();) {
			Entry<Integer, ISObject> entry = it.next();
			int type = entry.getValue().getType();
			if (type != ObjTypeEnum.Performer.value()) {
				continue;
			}
			IPerformer p = (IPerformer) entry.getValue();
			if (p.isRomove()) {
				it.remove();
				Set<ISObject> sobSubs = sobSubMap.get(p.getSubType());
				if (sobSubs != null) {
					sobSubs.remove(p);
				}
			}

		}
	}

}
