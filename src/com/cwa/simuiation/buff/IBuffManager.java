package com.cwa.simuiation.buff;

import java.util.List;
import java.util.Map;

import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;

/**
 * buff管理
 * 
 * @author mausmars
 * 
 */
public interface IBuffManager {
	/**
	 * 拥有者
	 */
	IPerformer getPerformer();

	/**
	 * 进入buff
	 */
	boolean enter(Integer id, int value);

	/**
	 * 退出buff
	 */
	void exit(IBuffContext context);

	/**
	 * 状态更新
	 */
	void update();

	/**
	 * 是否能改变到指定状态
	 * 
	 * @param state
	 *            目标状态
	 * @return
	 */
	boolean canTranstion(IStateContext context);

	/**
	 * 驱散Buff
	 * 
	 * @param e
	 * @return  驱散的buffIds
	 */
	List<Integer> dispelBuff(BuffBeneficialTypeEnum e, List<String> buffTypeList);
	/**
	 * 获取全部buff
	 * @return
	 */
	Map<String, IBuffContext> getBuffContextMap();
	/**
	 * 拥有增益buff
	 * @return
	 */
	Boolean haveBuff();
	/**
	 * 拥有减益buff
	 * @return
	 */
	Boolean haveDebuff();
	/**
	 * 清空buff
	 */
	void clear();
}
