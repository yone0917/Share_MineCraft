package com.SakuraKijin.kijinmod.item.itembow;

import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CustomArrowItem extends ArrowItem {
    public CustomArrowItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("custom_arrow_item");
    }
}
