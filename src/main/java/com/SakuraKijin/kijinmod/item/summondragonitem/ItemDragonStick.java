package com.SakuraKijin.kijinmod.item.summondragonitem;

import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.particle.CreateParticle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Arrays;

public class ItemDragonStick extends Item {
    public ItemDragonStick() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_dragon_stick");
    }
    public static Vector3d pl;
    public static String SUMMONERUUID;
    public static PlayerEntity SUMMONER;
    public static int time =0;
    public static boolean isCasting =false;
    public static Vector3d regplayer;

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            pl=new Vector3d(player.getX(),player.getY(),player.getZ());
            return ActionResult.success(player.getItemInHand(hand));
        }
        return ActionResult.fail(player.getItemInHand(hand));
    }
}

