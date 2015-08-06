package test.filter;

import com.cwa.battle.filter.ABattleFilter;

public class PostwarFilterA extends ABattleFilter {
	public PostwarFilterA() {
		super("PostwarFilterA");
	}

	@Override
	public boolean doWork(Object context) throws Exception {
		System.out.println("PostwarFilterA");
		return true;
	}

}