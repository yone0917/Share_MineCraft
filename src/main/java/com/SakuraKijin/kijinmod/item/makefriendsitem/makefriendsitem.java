package com.SakuraKijin.kijinmod.item.makefriendsitem;

import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.particle.CreateParticle;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class makefriendsitem extends Item {
    public makefriendsitem() {
            super(new Properties().tab(KijinMod.KIJIMOD_TAB));
            this.setRegistryName("make_friends_item");
    }


    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player != null) {
            double range =10;
            Vector3d place =new Vector3d(player.getX(),player.getY(),player.getZ());
            CreateParticle.make_friends_range(place);
            AxisAlignedBB hanni = player.getBoundingBox().inflate(range);
            //world.getEntitiesOfClass(CustomZombieEntity.class,hanni).forEach(zombie -> zombie.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED,600,1)));
            world.getEntitiesOfClass(MobEntity.class,hanni).forEach(chara -> {
                chara.setTarget(player.getLastHurtMob());
                if(player.getLastHurtMob() != null) {
                    chara.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, player.getLastHurtMob().getUUID(), 600);
                }
                //chara.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                chara.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_TARGET,player.getLastHurtMob(),600);
            });
            //player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED,600,1));
            // エフェクトの持続時間や強さは適宜調整
            return ActionResult.success(player.getItemInHand(hand));

        }
        return ActionResult.fail(player.getItemInHand(hand));
    }
}
