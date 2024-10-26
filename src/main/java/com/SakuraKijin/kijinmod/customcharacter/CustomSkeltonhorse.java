package com.SakuraKijin.kijinmod.customcharacter;

import com.SakuraKijin.kijinmod.item.lastsommonitem.ItemZombieStick_Lv2;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CustomSkeltonhorse extends SkeletonHorseEntity {

    public CustomSkeltonhorse(EntityType<? extends SkeletonHorseEntity> entityType, World world) {
        super(entityType,world);
    }
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ModifiableAttributeInstance followRange = this.getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null){
            followRange.setBaseValue(followRange.getBaseValue() * 2);
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        LivingEntity targetmob = ItemZombieStick_Lv2.SUMMONER.getLastHurtMob();
        if(entity instanceof PlayerEntity){
            if(entity.getStringUUID().equals(ItemZombieStick_Lv2.UUID)){
                return false;
            }
            else{
                return true;
            }
        }
        if (targetmob != null && entity.getStringUUID().equals(targetmob.getStringUUID())) {
            return true;
        }
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        LivingEntity targetmob = ItemZombieStick_Lv2.SUMMONER.getLastHurtMob();
        this.setTarget(targetmob);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity summoner = ItemZombieStick_Lv2.SUMMONER;
        if(summoner !=null && !this.isAggressive()){
            double distanceToSummoner = this.distanceToSqr(summoner.getX(), summoner.getY(), summoner.getZ());
            // この距離の値は適切に調整してください
            double followRange = 10.0;
            if(distanceToSummoner > followRange * followRange) {
                this.getNavigation().moveTo(summoner.getX(), summoner.getY(), summoner.getZ(), 1.0);
            }
        }
    }
}
