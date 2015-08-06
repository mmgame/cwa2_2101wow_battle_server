package test.filter;

import com.cwa.battle.filter.ABattleFilter;

public class ContinuewarFilterB extends ABattleFilter {
	public ContinuewarFilterB() {
		super("ContinuewarFilterB");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		System.out.println("ContinuewarFilterB");
		return true;
	}

}