package com.SakuraKijin.kijinmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockSakuraBlock extends Block {

    public BlockSakuraBlock() {
        super(Properties.of(Material.METAL)
                .strength(2F,150F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .noCollission()
                .lightLevel((state)->15));
        this.setRegistryName("sakura_block");
    }
}
