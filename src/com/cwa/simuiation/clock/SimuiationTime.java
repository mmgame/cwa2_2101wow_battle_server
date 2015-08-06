package com.cwa.simuiation.clock;

import com.cwa.simuiationimpl.constant.SimuiationConstant;

/**
 * 仿真器时间
 * 
 * @author mausmars
 * 
 */
public class SimuiationTime {
	// 帧数
	int frameCount;
	// 偏差
	int offset;

	// 得到毫秒时间
	public int getMSTime() {
		return (SClock.getFrameInterval() + SimuiationConstant.SystemUpdateTime) * frameCount + offset;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public long getOffset() {
		return offset;
	}
}
