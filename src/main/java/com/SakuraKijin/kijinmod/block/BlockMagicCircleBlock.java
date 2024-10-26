package com.SakuraKijin.kijinmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockMagicCircleBlock extends Block {
    public BlockMagicCircleBlock() {
        super(Properties.of(Material.GLASS)
                .strength(2F,150F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops()
                .noCollission()
                .lightLevel((state)->15));
        this.setRegistryName("magic_circle_block");
    }
}
