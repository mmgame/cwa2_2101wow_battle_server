package com.cwa.simuiationimpl.manage;

import java.util.ArrayList;
import java.util.List;

import wow.data.entity.HeroGeneralEntity;
import baseice.basedao.IEntity;

import com.cwa.component.prototype.IPrototype;
import com.cwa.data.entity.domain.EquipmentEntity;
import com.cwa.data.entity.domain.HeroEntity;
import com.cwa.prototype.EquipmentGradePrototype;
import com.cwa.prototype.HeroGradePrototype;
import com.cwa.prototype.HeroPrototype;
import com.cwa.prototype.PassiveSkillPrototype;
import com.cwa.prototype.PlugItemPrototype;
import com.cwa.prototype.gameEnum.AttrTypeEnum;
import com.cwa.prototype.gameEnum.BuffBeneficialTypeEnum;
import com.cwa.prototype.gameEnum.BuffTypeEnum;
import com.cwa.prototype.gameEnum.DeBuffResistanceEnum;
import com.cwa.prototype.gameEnum.ElementEnum;
import com.cwa.prototype.gameEnum.HeroClassEnum;
import com.cwa.prototype.gameEnum.ValueTypeEnum;
import com.cwa.simuiation.enums.AttrSourceTypeEnum;
import com.cwa.simuiation.enums.StateSubTypeEnum;
import com.cwa.simuiation.manager.IAttrMgr;
import com.cwa.simuiation.obj.IPerformer;
import com.cwa.simuiation.state.IStateContext;
import com.cwa.simuiationimpl.constant.SimuiationConstant;
import com.cwa.simuiationimpl.obj.Hero;
import com.cwa.simuiationimpl.passiveskill.PassiveSkillManager;
import com.cwa.simuiationimpl.util.FormulaUtil;

/**
 * 英雄属性管理
 * 
 * @author mausmars
 * 
 */
public class HeroAttrMgr implements IAttrMgr {
	protected IPerformer performer;
	// 英雄数据
	private HeroGeneralEntity entity;
	// 英雄等级原型
	private HeroGradePrototype herogradePrototype;
	// 英雄原型
	private HeroPrototype prototype;
	
	private int retinueSkillId;
	// 实际值XXX（XXX/最大）
	private int[] actualAttrs = new int[AttrTypeEnum.values().length];

	// {来源类型，属性列表}
	private int[][] attrs = new int[AttrSourceTypeEnum.values().length][AttrTypeEnum.values().length];
	// {来源类型，元素耐性列表}
	private int[][] elementResistances = new int[AttrSourceTypeEnum.values().length][ElementEnum.values().length];
	// {来源类型，元素专精列表}
	private int[][] elementAttacks = new int[AttrSourceTypeEnum.values().length][ElementEnum.values().length];
	// {来源类型，debuff耐性列表}
	private int[][] debuffResistances = new int[AttrSourceTypeEnum.values().length][DeBuffResistanceEnum.values().length];
	// {来源类型，种族克制列表}
	private int[][] raceResistances = new int[AttrSourceTypeEnum.values().length][HeroClassEnum.values().length];

	@Override
	public IEntity getEntity() {
		return entity;
	}

	@Override
	public IPrototype getPrototype() {
		return prototype;
	}

	// ------------------------------------------
	@Override
	public int getSize() {
		return prototype.getHeroSize();
	}

	@Override
	public int getQuality() {
		return entity.quality - 1;
	}

	@Override
	public int getLevel() {
		return entity.level;
	}

	@Override
	public int getRace() {
		return prototype.getHeroClass();
	}

	@Override
	public int getConsumeValue(int attrType) {
		return actualAttrs[attrType - 1];
	}

	@Override
	public int changeConsumeValue(int attrType, int changeValue) {
		int index = attrType - 1;

		int attr = actualAttrs[index];
		int actualValue = attr + changeValue;
		if (actualValue <= 0) {
			actualAttrs[index] = 0;
			if (attrType == AttrTypeEnum.Attr_Blood.value() && attr > 0) {// 死亡
				// 切换到死亡状态
				IStateContext deadStateContext = performer.getGlobalContext().getStateContextFactory()
						.createGSContext(performer, StateSubTypeEnum.GS_Dead.value());
				performer.getStateManager().transtion(deadStateContext);
			}
		} else {
			int maxValue = getAttrFinalValue(attrType);
			if (actualValue > maxValue) {
				actualAttrs[index] = maxValue;
			} else {
				actualAttrs[index] = actualValue;
			}
		}
		return actualAttrs[index];
	}

	@Override
	public int getAttrFinalValue(int attrType) {
		int finalValue = 0;
		for (AttrSourceTypeEnum ast : AttrSourceTypeEnum.values()) {
			finalValue += attrs[ast.value() - 1][attrType - 1];
		}
		return finalValue;
	}

	@Override
	public int getAttrValue(int sourceType, int attrType) {
		return attrs[sourceType - 1][attrType - 1];
	}

	@Override
	public void setAttrValue(int sourceType, int attrType, int value) {
		attrs[sourceType - 1][attrType - 1] += value;
	}

	@Override
	public int getElementResistanceFinalValue(int type) {
		int finalValue = 0;
		for (AttrSourceTypeEnum ast : AttrSourceTypeEnum.values()) {
			finalValue += elementResistances[ast.value() - 1][type - 1];
		}
		return finalValue;
	}

	@Override
	public int getElementResistanceValue(int sourceType, int type) {
		return elementResistances[sourceType - 1][type - 1];
	}

	@Override
	public void setElementResistanceValue(int sourceType, int type, int value) {
		elementResistances[sourceType - 1][type - 1] += value;
	}

	@Override
	public int getElementAttackFinalValue(int type) {
		int finalValue = 0;
		for (AttrSourceTypeEnum ast : AttrSourceTypeEnum.values()) {
			finalValue += elementAttacks[ast.value() - 1][type - 1];
		}
		return finalValue;
	}

	@Override
	public int getElementAttackValue(int sourceType, int type) {
		return elementAttacks[sourceType - 1][type - 1];
	}

	@Override
	public void setElementAttackValue(int sourceType, int type, int value) {
		elementAttacks[sourceType - 1][type - 1] += value;
	}

	@Override
	public int getDebuffResistanceFinalValue(int type) {
		int finalValue = 0;
		for (AttrSourceTypeEnum ast : AttrSourceTypeEnum.values()) {
			finalValue += debuffResistances[ast.value() - 1][type - 1];
		}
		return finalValue;
	}

	@Override
	public int getDebuffResistanceValue(int sourceType, int type) {
		return debuffResistances[sourceType - 1][type - 1];
	}

	@Override
	public void setDebuffResistanceValue(int sourceType, int type, int value) {
		debuffResistances[sourceType - 1][type - 1] += value;
	}

	@Override
	public int getRaceResistanceFinalValue(int type) {
		int finalValue = 0;
		for (AttrSourceTypeEnum ast : AttrSourceTypeEnum.values()) {
			finalValue += raceResistances[ast.value() - 1][type - 1];
		}
		return finalValue;
	}

	@Override
	public int getRaceResistanceValue(int sourceType, int type) {
		return raceResistances[sourceType - 1][type - 1];
	}

	@Override
	public void setRaceResistanceValue(int sourceType, int type, int value) {
		raceResistances[sourceType - 1][type - 1] += value;
	}

	public HeroGradePrototype getHerogradePrototype() {
		return herogradePrototype;
	}

	// --------------------------------------------------------
	public void setEntity(IEntity entity) {
		this.entity = (HeroGeneralEntity) entity;
	}

	public void setPrototype(IPrototype prototype) {
		this.prototype = (HeroPrototype) prototype;
	}

	private void initAttr(int attrType, int value) {
		actualAttrs[attrType - 1] += value;
	}

	public void setHerogradePrototype(IPrototype prototype) {
		this.herogradePrototype = (HeroGradePrototype) prototype;
		List<Integer> attrList = this.herogradePrototype.getBasicAttributeList();
		List<Integer> growthList = this.herogradePrototype.getAttributesGrowthList();
		for (int i = 1; i <= attrList.size(); i++) {
			int value = attrList.get(i - 1) + (entity.level - 1) * growthList.get(i - 1);
			setAttrValue(AttrSourceTypeEnum.AS_Base.value(), i, value);
			initAttr(i, value);
		}
		setAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Shield.value(), 0);
		initAttr(AttrTypeEnum.Attr_Shield.value(), 0);
		setAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_HitRate.value(), 100);
		initAttr(AttrTypeEnum.Attr_HitRate.value(), 100);
		setAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Thorns.value(), 0);
		initAttr(AttrTypeEnum.Attr_Thorns.value(), 0);
		setAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_SuckBlood.value(), 0);
		initAttr(AttrTypeEnum.Attr_SuckBlood.value(), 0);
		setAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_SuckBlue.value(), 0);
		initAttr(AttrTypeEnum.Attr_SuckBlue.value(), 0);
		setAttrValue(AttrSourceTypeEnum.AS_Base.value(), AttrTypeEnum.Attr_Invincible.value(), 1);
		initAttr(AttrTypeEnum.Attr_Invincible.value(), 1);

		List<Integer> resistanceList = this.herogradePrototype.getResistanceList();
		List<Integer> elementMasterList = this.herogradePrototype.getElementMasterList();
		for (int i = 1; i <= resistanceList.size(); i++) {
			setElementResistanceValue(AttrSourceTypeEnum.AS_Base.value(), i, resistanceList.get(i - 1));
			setElementAttackValue(AttrSourceTypeEnum.AS_Base.value(), i, elementMasterList.get(i - 1));
		}

		List<Integer> debuffList = this.herogradePrototype.getDebuffPatienceList();
		for (int i = 1; i <= debuffList.size(); i++) {
			setDebuffResistanceValue(AttrSourceTypeEnum.AS_Base.value(), i, debuffList.get(i - 1));
		}

		List<Integer> raceResistanceList = this.herogradePrototype.getRaceRestrainList();
		for (int i = 1; i <= raceResistanceList.size(); i++) {
			setRaceResistanceValue(AttrSourceTypeEnum.AS_Base.value(), i, raceResistanceList.get(i - 1));
		}
	}

	void setEquipments(List<EquipmentEntity> equipments){
        addEquipmentsAttr(equipments,AttrSourceTypeEnum.AS_Base.value(),1);
    }
	void addEquipmentsAttr(List<EquipmentEntity> equipments,int type,float coe){
        if (equipments == null || equipments.size() == 0) {
            return;
        }
        for(EquipmentEntity equipment : equipments){
            int level = equipment.equipmentLevel;
            int quality = equipment.equipmentQuality;
            int poison = equipment.positionId;
            int eid = prototype.getEquipmentList().get(poison-1);
            eid += quality;
            EquipmentGradePrototype equipmentGradePrototype = performer.getGlobalContext().getProtpyeMgr().getEquipmentGradePrototype(eid);
            List<Integer> attributeIds = equipmentGradePrototype.getAttributeIdList();
            List<Integer> coefficients = equipmentGradePrototype.getCoefficientList();
            for (int i=0; i<attributeIds.size(); i++) {
                int value = coefficients.get(i) * level;
                value *= coe;
                setAttrValue(type, attributeIds.get(i), value);
                initAttr(attributeIds.get(i), value);
            }
            // TODO
            String[] plugs = equipment.plugs.split(",");
            List<Integer> plugList = new ArrayList<Integer>();
            for(String p : plugs){
            	plugList.add(Integer.parseInt(p));
            }
            
            for(int plug : plugList){
                if (plug == 0) {
                    continue;
                }
                PlugItemPrototype plugPrototype = performer.getGlobalContext().getProtpyeMgr().getPlugItemPrototype(plug);
                List<Integer> raiseAttrs = plugPrototype.getRaiseAttrList();
                List<Integer> raiseValues = plugPrototype.getRaiseValueList();
                for(int i=0;i<raiseAttrs.size();i++){
                    setAttrValue(type, raiseAttrs.get(i), (int)(raiseValues.get(i) * coe));
                    initAttr(raiseAttrs.get(i), (int)(raiseValues.get(i) * coe));
                }
            }
        }
    }
    void setRetinue(IEntity entity,List<EquipmentEntity> equipments){
        if (entity == null) {
            return;
        }
        HeroEntity retinueEntity = (HeroEntity)entity;
        if (retinueEntity.heroId == 0) {
			return;
		}
        HeroPrototype retinueHeroPrototype = performer.getGlobalContext().getProtpyeMgr().getHeroPrototype(retinueEntity.heroId);
        int heroKeyId = FormulaUtil.getHerogradeKeyId(retinueEntity.heroId, retinueEntity.quality, retinueEntity.starLevel);
        HeroGradePrototype retinueHeroGradePrototype = performer.getGlobalContext().getProtpyeMgr().getHerogradePrototype(heroKeyId);
        float coe = 0;//加成比例
        if(retinueHeroPrototype.getHeroClass() == prototype.getHeroClass()){//同种族加成0.2
            coe = 0.2f;
        }else{//不同种族加成0.15
            coe = 0.15f;
        }
        List<Integer> attrList = retinueHeroGradePrototype.getBasicAttributeList();
        List<Integer> growthList = retinueHeroGradePrototype.getAttributesGrowthList();
        for (int i = 1; i <= attrList.size(); i++) {
            if (AttrTypeEnum.Attr_Energy.value() == i || AttrTypeEnum.Attr_Speed.value() == i) {
                continue;
            }
            int value = attrList.get(i - 1) + (retinueEntity.level - 1) * growthList.get(i - 1);
            value *= coe;
            setAttrValue(AttrSourceTypeEnum.AS_Retinue.value(), i, value);
            initAttr(i, value);
        }
        for(int keyId : retinueHeroGradePrototype.getCommonAttackList()){
            if (keyId / SimuiationConstant.SkillIdBase == SimuiationConstant.MaxSkill) {//大技能
                this.retinueSkillId = keyId;
            }
        }
        addEquipmentsAttr(equipments,AttrSourceTypeEnum.AS_Retinue.value(),coe);
    }

	
	public void setPerformer(IPerformer performer) {
		this.performer = performer;
	}

	@Override
	public void initPassiveSkill(PassiveSkillPrototype prototype) {
		List<Integer> subTypeList = prototype.gotEffectionTypeListsList().get(getQuality());
		List<Integer> valueList = prototype.gotDamageAmountListsList().get(getQuality());
		
		if(prototype.getType() == BuffTypeEnum.Buff_SevenAttr.value()){//7属性
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setAttrValue(AttrSourceTypeEnum.AS_Passive.value(), subTypeList.get(i), changeValue);
				changeConsumeValue(subTypeList.get(i), changeValue);
			}
		}else if(prototype.getType() == BuffTypeEnum.Buff_SevenResistance.value()){//7耐性
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(getElementResistanceValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setElementResistanceValue(AttrSourceTypeEnum.AS_Passive.value(), subTypeList.get(i), changeValue);
			}
		}else if(prototype.getType() == BuffTypeEnum.Buff_DeBuffResistance.value()){//debuff耐性
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(getDebuffResistanceValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setDebuffResistanceValue(AttrSourceTypeEnum.AS_Passive.value(), subTypeList.get(i), changeValue);
			}
		}else if(prototype.getType() == BuffTypeEnum.Buff_SevenElement.value()){//7元素专精
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(getElementAttackValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setElementAttackValue(AttrSourceTypeEnum.AS_Passive.value(), subTypeList.get(i), changeValue);
			}
		}else if(prototype.getType() == BuffTypeEnum.Buff_RaceRestrain.value()){//种族相克
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(getRaceResistanceValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setRaceResistanceValue(AttrSourceTypeEnum.AS_Passive.value(), subTypeList.get(i), changeValue);
			}
		}else if(prototype.getType() == BuffTypeEnum.Buff_SuckBlue.value()){//吸蓝
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setAttrValue(AttrSourceTypeEnum.AS_Passive.value(), AttrTypeEnum.Attr_SuckBlue.value(), changeValue);
				changeConsumeValue(AttrTypeEnum.Attr_SuckBlue.value(), changeValue);
			}
		}else if(prototype.getType() == BuffTypeEnum.Buff_SuckBlue.value()){//吸血
			for(int i=0;i<subTypeList.size();i++){
				int changeValue = 0;
				if(prototype.getValueType() == ValueTypeEnum.Value_Num.value()){//数值
					changeValue = valueList.get(i);
				}else{//百分比
					changeValue = (int)(performer.getAttrMgr().getAttrValue(AttrSourceTypeEnum.AS_Base.value(), subTypeList.get(i))
							* valueList.get(i) / 100.0f);
				}
				if(prototype.getBuffOrDebuff() == BuffBeneficialTypeEnum.Beneficial_Bad.value()){//减益
					changeValue = -changeValue;
				}
				setAttrValue(AttrSourceTypeEnum.AS_Passive.value(), AttrTypeEnum.Attr_SuckBlood.value(), changeValue);
				changeConsumeValue(AttrTypeEnum.Attr_SuckBlood.value(), changeValue);
			}
		}else{
			PassiveSkillManager passiveSkillManager = new PassiveSkillManager(performer);
			passiveSkillManager.enter(prototype.getKeyId());
			((Hero)performer).setPassiveSkillManager(passiveSkillManager);
		}
	}

	@Override
	public List<Integer> getEffectTimeList() {
		return prototype.getEffectTimeList();
	}

	@Override
	public List<Integer> getActionTimeList() {
		return prototype.getActionTimeList();
	}

	public int getRetinueSkillId() {
		return retinueSkillId;
	}
}
