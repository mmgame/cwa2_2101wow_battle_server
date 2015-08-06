package test.filter;

import com.cwa.battle.filter.ABattleFilter;

public class ContinuewarFilterA extends ABattleFilter {
	public ContinuewarFilterA() {
		super("ContinuewarFilterA");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		System.out.println("ContinuewarFilterA");
		return true;
	}

}
