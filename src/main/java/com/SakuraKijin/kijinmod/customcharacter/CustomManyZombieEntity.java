package com.SakuraKijin.kijinmod.customcharacter;

import com.SakuraKijin.kijinmod.item.manyzombieitem.ItemManyZombieStick;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CustomManyZombieEntity extends ZombieEntity {
    public CustomManyZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
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
        LivingEntity targetmob = ItemManyZombieStick.SUMMONER.getLastHurtMob();
        if(entity instanceof PlayerEntity){
            if(entity.getStringUUID().equals(ItemManyZombieStick.SUMMONERUUID)){
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
        /*if(targetmob != null && entity.getStringUUID().equals(targetmob.getStringUUID())){
            return true;
        }
        else if(entity instanceof PlayerEntity){
            if(entity.getStringUUID().equals(ItemManyZombieStick.SUMMONERUUID)){
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
        LivingEntity targetmob = ItemManyZombieStick.SUMMONER.getLastHurtMob();
        this.setTarget(targetmob);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        LivingEntity summoner = ItemManyZombieStick.SUMMONER;
        if(summoner !=null && !this.isAggressive()){
            double distanceToSummoner = this.distanceToSqr(summoner.getX(), summoner.getY(), summoner.getZ());
            // この距離の値は適切に調整してください
            double followRange = 10.0;
            if(distanceToSummoner > followRange * followRange) {
                this.getNavigation().moveTo(summoner.getX(), summoner.getY(), summoner.getZ(), 1.0);
            }
        }
    }
    /*@Override
    public void aiStep() {
        super.aiStep();
        // ゾンビの攻撃対象を取得
        LivingEntity target = this.getTarget();

        // 攻撃対象がプレイヤーであるかを確認
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;

            // プレイヤーが鉄インゴットを持っているかを確認
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() == Items.IRON_INGOT) {
                /*Vector3d randomTarget = new Vector3d(this.getRandom().nextGaussian(),this.getRandom().nextGaussian(),this.getRandom().nextGaussian());
                this.getNavigation().moveTo(randomTarget.x,randomTarget.y,randomTarget.z,this.getSpeed());
                this.yRot = this.getRandom().nextFloat()*360.0F;*//*
                this.setNoAi(true);
                return;
            }
        }
        this.setNoAi(false);
    }*/





    /*@Override
    public void aiStep() {
        // ゾンビの攻撃対象を取得
        LivingEntity target = this.getTarget();

        // 攻撃対象がプレイヤーであるかを確認
        if (target instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) target;

            // プレイヤーが鉄インゴットを持っているかを確認
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() == Items.IRON_INGOT) {
                /*Vector3d randomTarget = new Vector3d(this.getRandom().nextGaussian(),this.getRandom().nextGaussian(),this.getRandom().nextGaussian());
                this.getNavigation().moveTo(randomTarget.x,randomTarget.y,randomTarget.z,this.getSpeed());
                this.yRot = this.getRandom().nextFloat()*360.0F;*//*
                return;
            }
        }
        super.aiStep();
    }*/

    /*@Override
    public void tick() {
        LivingEntity target = this.getTarget();
        if(target instanceof PlayerEntity){
            PlayerEntity player =(PlayerEntity) target;
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem()==Items.IRON_INGOT){
                return;
            }
        }
        super.tick();
    }*///時間停止(ダメージすら食らわない)

    /*@Override
    public boolean doHurtTarget(Entity target) {
        if(target instanceof PlayerEntity){
            PlayerEntity player =(PlayerEntity) target;
            ItemStack heldItem = player.getMainHandItem();
            if(heldItem.getItem() == Items.IRON_INGOT){
                return false;
            }
        }
        return super.doHurtTarget(target);
    }*///よってくるけど攻撃しない。
}
