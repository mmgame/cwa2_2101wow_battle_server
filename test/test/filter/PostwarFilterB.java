package test.filter;

import com.cwa.battle.filter.ABattleFilter;

public class PostwarFilterB extends ABattleFilter {
	public PostwarFilterB() {
		super("PostwarFilterB");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		System.out.println("PostwarFilterB");
		return true;
	}

}