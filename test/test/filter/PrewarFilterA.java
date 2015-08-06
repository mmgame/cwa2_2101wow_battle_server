package test.filter;

import com.cwa.battle.filter.ABattleFilter;

public class PrewarFilterA extends ABattleFilter {
	public PrewarFilterA() {
		super("PrewarFilterA");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		System.out.println("PrewarFilterA");
		return true;
	}

}
