package com.SakuraKijin.kijinmod.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;
import java.util.function.ToIntFunction;

public class BlockMagicGlass extends GlassBlock {
    private static final ToIntFunction<BlockState> blockStateToIntFunction = state ->{return 15;};
    public BlockMagicGlass() {
        super(Properties.of(Material.GLASS)
                .sound(SoundType.GLASS)
                .strength(2F,150)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops()
                .noOcclusion()
                .noCollission()
                .lightLevel(blockStateToIntFunction)
                );
        this.setRegistryName("magic_glass");
    }
}
