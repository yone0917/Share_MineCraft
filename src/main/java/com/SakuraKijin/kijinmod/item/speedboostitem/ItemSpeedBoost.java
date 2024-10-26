package com.SakuraKijin.kijinmod.item.speedboostitem;

import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ItemSpeedBoost extends Item {
    public ItemSpeedBoost() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("speed_boost_item");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.addEffect(new EffectInstance(Effects.REGENERATION,6000,0));//再生能力
        player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED,6000,1));//移動速度上昇
        player.addEffect(new EffectInstance(Effects.JUMP,6000,1));//跳躍力上昇
        player.addEffect(new EffectInstance(Effects.HEALTH_BOOST,6000,0));//体力増強
        player.addEffect(new EffectInstance(Effects.DIG_SPEED,6000,0));//採掘速度上昇


        return ActionResult.success(player.getItemInHand(hand));
    }
}