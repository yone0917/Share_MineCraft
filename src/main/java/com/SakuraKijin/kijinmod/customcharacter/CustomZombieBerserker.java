package com.SakuraKijin.kijinmod.customcharacter;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.world.World;

public class CustomZombieBerserker extends ZombieEntity {

    public CustomZombieBerserker(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        // すべての他のモブをターゲットにする
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, entity -> {
            // 自分自身または他の "zombie_berserker" でない場合のみターゲットにする
            return !(entity instanceof CustomZombieBerserker);
        }));
    }
}
