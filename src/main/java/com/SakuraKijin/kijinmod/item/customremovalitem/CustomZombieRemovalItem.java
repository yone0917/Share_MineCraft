package com.SakuraKijin.kijinmod.item.customremovalitem;

import com.SakuraKijin.kijinmod.customcharacter.*;
import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class CustomZombieRemovalItem extends Item {
    public CustomZombieRemovalItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("removal_stick");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
       if(!world.isClientSide){
           double range =50;
           AxisAlignedBB boundingBox = player.getBoundingBox().inflate(range);
           //ここがparticle
           /*world.getEntitiesOfClass(CustomZombieEntity2.class,boundingBox).forEach(chara -> {
               for (int i = 0; i < 40; i++) {
                   IParticleData paticle = ParticleTypes.EFFECT;
                   Minecraft.getInstance().particleEngine.createParticle(paticle, chara.getX(),chara.getY(),chara.getZ(), Math.random(), Math.random()+2, Math.random());
               }
           });
           world.getEntitiesOfClass(CustomZombieEntity.class,boundingBox).forEach(chara -> {
               for (int i = 0; i < 40; i++) {
                   IParticleData paticle = ParticleTypes.EFFECT;
                   Minecraft.getInstance().particleEngine.createParticle(paticle, chara.getX(),chara.getY(),chara.getZ(), Math.random(), Math.random()+2, Math.random());
               }
           });
           world.getEntitiesOfClass(CustomSkeletonEntity.class,boundingBox).forEach(chara -> {
               for (int i = 0; i < 40; i++) {
                   IParticleData paticle = ParticleTypes.EFFECT;
                   Minecraft.getInstance().particleEngine.createParticle(paticle, chara.getX(),chara.getY(),chara.getZ(), Math.random(), Math.random()+2, Math.random());
               }
           });
           world.getEntitiesOfClass(CustomGuardZombieEntity.class,boundingBox).forEach(chara -> {
               for (int i = 0; i < 40; i++) {
                   IParticleData paticle = ParticleTypes.EFFECT;
                   Minecraft.getInstance().particleEngine.createParticle(paticle, chara.getX(),chara.getY(),chara.getZ(), Math.random(), Math.random()+2, Math.random());
               }
           });
           world.getEntitiesOfClass(CustomManyZombieEntity.class,boundingBox).forEach(chara -> {
               for (int i = 0; i < 40; i++) {
                   IParticleData paticle = ParticleTypes.EFFECT;
                   Minecraft.getInstance().particleEngine.createParticle(paticle, chara.getX(),chara.getY(),chara.getZ(), Math.random(), Math.random()+2, Math.random());
               }
           });
           world.getEntitiesOfClass(CustomIronSkeleton.class,boundingBox).forEach(chara -> {
               for (int i = 0; i < 40; i++) {
                   IParticleData paticle = ParticleTypes.EFFECT;
                   Minecraft.getInstance().particleEngine.createParticle(paticle, chara.getX(),chara.getY(),chara.getZ(), Math.random(), Math.random()+2, Math.random());
               }
           });*/
           //

           world.getEntitiesOfClass(CustomZombieEntity2.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomZombieEntity.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomSkeletonEntity.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomGuardZombieEntity.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomManyZombieEntity.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomIronSkeleton.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomZombieEntityZombieSkeletonStick.class,boundingBox).forEach(Entity::remove);
           world.getEntitiesOfClass(CustomSkeletonEntityZombieSkeletonStick.class,boundingBox).forEach(Entity::remove);
           return ActionResult.success(player.getItemInHand(hand));
       }
        return ActionResult.fail(player.getItemInHand(hand));
    }
}
