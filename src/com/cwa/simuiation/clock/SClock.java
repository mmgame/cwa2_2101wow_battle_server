package com.cwa.simuiation.clock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.util.TimeUtil;

/**
 * 仿真器时钟
 * 
 * @author mausmars
 * 
 */
public class SClock {
	protected static final Logger logger = LoggerFactory.getLogger(SClock.class);

	// 帧间隔（1000ms 20次 理论值，可能比这个值快或慢）
	private static int frameInterval = 50;

	// 当前帧数（这个就是时间）
	private int currentFrameCount = 0;

	private long updateTime;

	// update耗时
	private long consumeTime = 0;

	public void startup() {
		restart(0);
	}

	public void restart(int frameInterval) {
		if (frameInterval > 0) {
			setFrameInterval(frameInterval);
		}
		updateTime = TimeUtil.currentSystemTime();
		currentFrameCount = 0;
		consumeTime = 0;
	}

	// 更新结束
	public int updateOver() {
		consumeTime = TimeUtil.currentSystemTime() - updateTime;
		int nextInterval = (int) (getFrameInterval() - consumeTime);
		if (nextInterval < 0) {
			nextInterval = 0;
			if (logger.isErrorEnabled()) {
				logger.error("Update consumeTime is exceed! " + consumeTime + " ms");
			}
		}
		// 返回间隔时间
		return nextInterval;
	}

	// 开始更新
	public synchronized void updateStart() {
		currentFrameCount++;
		updateTime = TimeUtil.currentSystemTime();
	}

	public SimuiationTime getCurrentSTime() {
		SimuiationTime s = new SimuiationTime();
		synchronized (this) {
			long currentTime = TimeUtil.currentSystemTime();
			if (updateTime == 0) {
				updateTime = currentTime;
			}
			s.offset = (int) (currentTime - updateTime);
			s.frameCount = currentFrameCount;
		}
		return s;
	}

	/**
	 * 当前时差
	 * 
	 * @param simuiationTime
	 * @return
	 */
	public int differByCurrentTime(SimuiationTime simuiationTime) {
		return getCurrentSTime().getMSTime() - simuiationTime.getMSTime();
	}

	// ----------------------------------
	public int getCurrentFrameCount() {
		return currentFrameCount;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public static int getFrameInterval() {
		return frameInterval;
	}

	public static void setFrameInterval(int frameInterval) {
		SClock.frameInterval = frameInterval;
	}
}
