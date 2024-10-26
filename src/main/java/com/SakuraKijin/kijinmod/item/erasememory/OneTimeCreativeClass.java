package com.SakuraKijin.kijinmod.item.erasememory;

import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class OneTimeCreativeClass {

    public static int time = 0;
    public static boolean isCasting = false;
    public static PlayerEntity play;
    public static void cretive(PlayerEntity player) {
        if (player != null) {
            time = 0;
            isCasting = true;
            play = player;
        }
    }
}
