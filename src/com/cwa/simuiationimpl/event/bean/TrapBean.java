package com.cwa.simuiationimpl.event.bean;

import com.cwa.simuiation.map.Position;

public class TrapBean {
	private int tid;
	private Position point;//位置
	private int eid;
	public int getTid() {
		return tid;
	}
	public void setTid(int pid) {
		this.tid = pid;
	}
	public Position getPoint() {
		return point;
	}
	public void setPoint(Position point) {
		this.point = point;
	}
	public int getEid() {
		return eid;
	}
	public void setEid(int eid) {
		this.eid = eid;
	}
}
