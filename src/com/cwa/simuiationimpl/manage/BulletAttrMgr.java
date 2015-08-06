package com.cwa.simuiationimpl.manage;

import java.util.List;

import baseice.basedao.IEntity;

import com.cwa.component.prototype.IPrototype;
import com.cwa.prototype.EffectPrototype;
import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.simuiation.manager.IAttrMgr;
import com.cwa.simuiation.obj.IPerformer;

/**
 * 英雄属性管理
 * 
 * @author mausmars
 * 
 */
public class BulletAttrMgr implements IAttrMgr {
	protected IPerformer performer;
	// 所有者
//	protected Object owner;
//	// 控制者
//	protected Object controler;
	// 效果原型
	private EffectPrototype prototype;
	// -------------------------------
	private int[] actualAttrs = new int[AttrTypeEnum.values().length];

	@Override
	public IEntity getEntity() {
		return null;
	}

	@Override
	public IPrototype getPrototype() {
		return prototype;
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public int getQuality() {
		return 0;
	}

	@Override
	public int getConsumeValue(int attrType) {
		return actualAttrs[attrType - 1];
	}

	@Override
	public int changeConsumeValue(int attrType, int changeValue) {
		return 0;

	}

	@Override
	public int getAttrFinalValue(int attrType) {
		return actualAttrs[attrType - 1];
	}

	@Override
	public int getAttrValue(int sourceType, int attrType) {
		return 0;
	}

	@Override
	public void setAttrValue(int sourceType, int attrType, int value) {
	}

	@Override
	public int getElementResistanceFinalValue(int type) {
		return 0;
	}

	@Override
	public int getElementResistanceValue(int sourceType, int type) {
		return 0;
	}

	@Override
	public void setElementResistanceValue(int sourceType, int type, int value) {
	}

	@Override
	public int getElementAttackFinalValue(int type) {
		return 0;
	}

	@Override
	public int getElementAttackValue(int sourceType, int type) {
		return 0;
	}

	@Override
	public void setElementAttackValue(int sourceType, int type, int value) {
	}

	@Override
	public int getDebuffResistanceFinalValue(int type) {
		return 0;
	}

	@Override
	public int getDebuffResistanceValue(int sourceType, int type) {
		return 0;
	}

	@Override
	public void setDebuffResistanceValue(int sourceType, int type, int value) {
	}

	// --------------------------------------------------------
//	public void setOwner(Object owner) {
//		this.owner = owner;
//		this.controler = owner;
//	}

	public void setEntity(IEntity entity) {
	}

	public void setPrototype(IPrototype p) {
		this.prototype = (EffectPrototype) p;
		actualAttrs[AttrTypeEnum.Attr_Speed.value() - 1] = prototype.getFlySpeed();
	}

	@Override
	public int getLevel() {

		return 0;
	}

	@Override
	public int getRaceResistanceFinalValue(int type) {

		return 0;
	}

	@Override
	public int getRaceResistanceValue(int sourceType, int type) {

		return 0;
	}

	@Override
	public void setRaceResistanceValue(int sourceType, int type, int value) {

	}

	@Override
	public int getRace() {

		return 0;
	}

	@Override
	public void initPassiveSkill(PassiveSkillPrototype prototype) {
		
	}

	@Override
	public List<Integer> getEffectTimeList() {
		return null;
	}

	@Override
	public List<Integer> getActionTimeList() {
		return null;
	}
}
