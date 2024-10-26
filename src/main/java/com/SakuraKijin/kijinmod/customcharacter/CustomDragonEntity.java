package com.SakuraKijin.kijinmod.customcharacter;

import com.SakuraKijin.kijinmod.item.summondragonitem.ItemDragonStick;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.world.World;

public class CustomDragonEntity extends EnderDragonEntity {
    public CustomDragonEntity(EntityType<? extends EnderDragonEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ModifiableAttributeInstance followRange = this.getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null) {
            followRange.setBaseValue(followRange.getBaseValue() * 2);
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        LivingEntity targetmob = ItemDragonStick.SUMMONER.getLastHurtMob();
        if (targetmob != null && entity.getStringUUID().equals(targetmob.getStringUUID())) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void aiStep() {
        super.aiStep();
        LivingEntity targetmob = ItemDragonStick.SUMMONER.getLastHurtMob();
        this.setTarget(targetmob);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity summoner = ItemDragonStick.SUMMONER;
        if (summoner != null && !this.isAggressive()) {
            double distanceToSummoner = this.distanceToSqr(summoner.getX(), summoner.getY()+10, summoner.getZ());
            // この距離の値は適切に調整してください!!
            double followRange = 10.0;
            if (distanceToSummoner > followRange * followRange) {
                this.getNavigation().moveTo(summoner.getX(), summoner.getY()+10, summoner.getZ(), 1.0);
            }
        }
    }
}