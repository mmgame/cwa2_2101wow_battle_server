package com.cwa.simuiation.action;

/**
 * 动作 （该接口可以用作脚本）
 * 
 * @author mausmars
 * 
 */
public interface ISAction {
	/**
	 * 获取动作类型
	 * 
	 * @return
	 */
	int getType();

	/**
	 * 动作开始
	 * 
	 * @param performer
	 *            执行者
	 * @param target
	 *            目标
	 */
	void enter(IActionContext context);

	/**
	 * 动作结束
	 * 
	 * @param performer
	 */
	void exit(IActionContext context);

	/**
	 * 动作持续
	 */
	void update(IActionContext context);
}
