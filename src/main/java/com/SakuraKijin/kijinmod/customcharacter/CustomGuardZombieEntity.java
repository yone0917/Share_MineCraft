package com.SakuraKijin.kijinmod.customcharacter;

import com.SakuraKijin.kijinmod.item.guardzombieitem.ItemGuardZombieItem;
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

public class CustomGuardZombieEntity extends ZombieEntity {
    public CustomGuardZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType,world);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        ItemStack chainhelmet =new ItemStack(Items.CHAINMAIL_HELMET);
        this.setItemSlot(EquipmentSlotType.HEAD,chainhelmet);
        ItemStack chainChestplate = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
        this.setItemSlot(EquipmentSlotType.CHEST,chainChestplate);

        ItemStack chainLeggings = new ItemStack(Items.CHAINMAIL_LEGGINGS);
        this.setItemSlot(EquipmentSlotType.LEGS,chainLeggings);

        ItemStack chainBoots = new ItemStack(Items.CHAINMAIL_BOOTS);
        this.setItemSlot(EquipmentSlotType.FEET,chainBoots);

        ItemStack ironSword = new ItemStack(Items.IRON_SWORD);
        this.setItemSlot(EquipmentSlotType.MAINHAND,ironSword);

        ModifiableAttributeInstance followRange = this.getAttribute(Attributes.FOLLOW_RANGE);
        if (followRange != null){
            followRange.setBaseValue(followRange.getBaseValue() * 2);
        }
    }

    @Override
    public boolean canAttack(LivingEntity entity) {
        LivingEntity targetmob = ItemGuardZombieItem.SUMMONER.getLastHurtByMob();
        if(targetmob != null && entity.getStringUUID().equals(targetmob.getStringUUID())){
            return true;
        }
        else if(entity instanceof PlayerEntity){
            if(entity.getStringUUID().equals(ItemGuardZombieItem.SUMMONERUUID)){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        LivingEntity targetmob = ItemGuardZombieItem.SUMMONER.getLastHurtByMob();
        this.setTarget(targetmob);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity summoner = ItemGuardZombieItem.SUMMONER;
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
