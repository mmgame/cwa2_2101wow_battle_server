package com.cwa.simuiationimpl;

import com.cwa.component.event.ILocalEvent;
import com.cwa.simuiation.IPlayer;
import com.cwa.simuiation.event.ISEvent;

public class Player extends ClientSimuiation implements IPlayer {
	private int troopType;

	public Player(long id) {
		super(String.valueOf(id));
	}

	public int getTroopType() {
		return troopType;
	}

	@Override
	public void sendEvent(ISEvent event) {
		simuiation.sendEvent(event);
	}

	@Override
	public void sendEvent(Object listenerType, ILocalEvent event) {
		simuiation.sendEvent(listenerType, event);
	}

	// --------------------------------------
	public void setTroopType(int troopType) {
		this.troopType = troopType;
	}

}
