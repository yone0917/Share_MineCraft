package com.SakuraKijin.kijinmod.customcharacter;

import com.SakuraKijin.kijinmod.item.lastsommonitem.ItemZombieStick_Lv2;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class CustomZombieEntity2 extends ZombieEntity {
    public CustomZombieEntity2(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType,world);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ItemStack ironHelmet =new ItemStack(Items.IRON_HELMET);
        this.setItemSlot(EquipmentSlotType.HEAD,ironHelmet);

        ItemStack ironChestplate = new ItemStack(Items.IRON_CHESTPLATE);
        this.setItemSlot(EquipmentSlotType.CHEST,ironChestplate);

        ItemStack ironLeggings = new ItemStack(Items.IRON_LEGGINGS);
        this.setItemSlot(EquipmentSlotType.LEGS,ironLeggings);

        ItemStack ironBoots = new ItemStack(Items.IRON_BOOTS);
        this.setItemSlot(EquipmentSlotType.FEET,ironBoots);

        ItemStack ironSword = new ItemStack(Items.IRON_SWORD);
        this.setItemSlot(EquipmentSlotType.MAINHAND,ironSword);

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
        /*if(targetmob != null && targetmob.isAlive()){
            if(entity.getStringUUID().equals(targetmob.getStringUUID())) {
                return true;
            }
        }
        if(entity instanceof PlayerEntity){
            if(entity.getStringUUID().equals(ItemZombieStick_Lv2.UUID)){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            return false;
        }*/
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
