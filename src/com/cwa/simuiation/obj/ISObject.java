package com.cwa.simuiation.obj;

import com.cwa.simuiation.map.Position;

/**
 * 仿真对象（被描述为当前环境的任何物体）
 * 
 * @author mausmars
 * 
 */
public interface ISObject {
	/**
	 * 获取唯一id
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 获取对象类型
	 * 
	 * @return
	 */
	int getType();

	/**
	 * 获取对象子类型
	 * 
	 * @return
	 */
	int getSubType();

	/**
	 * 坐标位置
	 * 
	 * @return
	 */
	Position getPosition();

	/**
	 * 得到大小
	 * 
	 * @return
	 */
	int getSize();
}
