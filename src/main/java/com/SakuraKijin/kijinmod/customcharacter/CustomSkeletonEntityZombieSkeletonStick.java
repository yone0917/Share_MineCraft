package com.SakuraKijin.kijinmod.customcharacter;

import com.SakuraKijin.kijinmod.item.summonzombieandskeletonitem2.SummonZombieSkeleton;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CustomSkeletonEntityZombieSkeletonStick extends SkeletonEntity {
    public CustomSkeletonEntityZombieSkeletonStick(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ItemStack leatherhelmet = new ItemStack(Items.LEATHER_HELMET);
        this.setItemSlot(EquipmentSlotType.HEAD, leatherhelmet);

        ItemStack bow =new ItemStack(Items.BOW);
        this.setItemSlot(EquipmentSlotType.MAINHAND,bow);

        ModifiableAttributeInstance followRange = this.getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null) {
            followRange.setBaseValue(followRange.getBaseValue() * 2);
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        LivingEntity targetmob = SummonZombieSkeleton.SUMMONER.getLastHurtMob();
        if(entity instanceof PlayerEntity){
            if(entity.getStringUUID().equals(SummonZombieSkeleton.SUMMONERUUID)){
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
        LivingEntity targetmob = SummonZombieSkeleton.SUMMONER.getLastHurtMob();
        this.setTarget(targetmob);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity summoner = SummonZombieSkeleton.SUMMONER;
        if (summoner != null && !this.isAggressive()) {
            double distanceToSummoner = this.distanceToSqr(summoner.getX(), summoner.getY(), summoner.getZ());
            // この距離の値は適切に調整してください
            double followRange = 10.0;
            if (distanceToSummoner > followRange * followRange) {
                this.getNavigation().moveTo(summoner.getX(), summoner.getY(), summoner.getZ(), 1.0);
            }
        }
    }
}