package com.SakuraKijin.kijinmod.regi;

import com.SakuraKijin.kijinmod.block.BlockSakuraBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class KijinModTab extends ItemGroup {
    public KijinModTab() {
        super("kijinmod");
    }

    @Override
    public ItemStack makeIcon() {
        ItemStack itemStack =new ItemStack(KijinModItems.ITEM_ZOMBIE_STICK_LV_2);
        return itemStack;
    }
}
