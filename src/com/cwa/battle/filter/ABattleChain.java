package com.cwa.battle.filter;

import java.util.List;

import com.cwa.util.linklist.AbstractNodeChain;

/**
 * 战场Chain接口（管理战场filter）
 * 
 * @author mausmars
 * 
 */
public class ABattleChain extends AbstractNodeChain<ABattleFilter> implements IFilterChain {
	public ABattleChain(String name) {
		super(name);
	}
	public ABattleChain(String key, List<ABattleFilter> nodes){
		super(key,nodes);
	}

	@Override
	public void doFilter(IFilter next, Object context) throws Exception {
		IFilter filter = selectFirst();
		if (filter != null) {
			filter.doFilter((IFilter) filter.next(), context);
		}
	}
}
