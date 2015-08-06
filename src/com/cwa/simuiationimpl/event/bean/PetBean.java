package com.cwa.simuiationimpl.event.bean;

import com.cwa.simuiation.map.Position;

public class PetBean {
	private int pid;
	private int keyId;//创建的英雄keyid
	private Position point;//位置
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getKeyId() {
		return keyId;
	}
	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}
	public Position getPoint() {
		return point;
	}
	public void setPoint(Position point) {
		this.point = point;
	}
}
