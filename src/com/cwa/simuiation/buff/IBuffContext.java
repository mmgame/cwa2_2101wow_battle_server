package com.cwa.simuiation.buff;

import com.cwa.prototype.BuffPrototype;
import com.cwa.simuiation.clock.SimuiationTime;
import com.cwa.simuiation.obj.IPerformer;

/**
 * buff上下文
 * 
 * @author mausmars
 * 
 */
public interface IBuffContext {
	/**
	 * buff接口
	 * 
	 * @return
	 */
	ISBuff getBuff();

	/**
	 * buff的id
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 开始时间
	 * 
	 * @return
	 */
	SimuiationTime getStartTime();

	/**
	 * actor
	 * 
	 * @return
	 */
	IPerformer getPerformer();
	
	/**
	 * 获取原型数据
	 * @return
	 */
	BuffPrototype getBuffPrototype();
}
