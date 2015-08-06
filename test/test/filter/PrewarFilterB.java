package test.filter;

import com.cwa.battle.filter.ABattleFilter;

public class PrewarFilterB extends ABattleFilter {
	public PrewarFilterB() {
		super("PrewarFilterB");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		System.out.println("PrewarFilterB");
		return true;
	}

}
