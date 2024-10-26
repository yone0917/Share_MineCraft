package com.SakuraKijin.kijinmod.block;

import com.SakuraKijin.kijinmod.regi.KijinmodBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockPurpleGlowingBlock extends Block {

    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
    private static BlockPos selfpos;
    public BlockPurpleGlowingBlock() {
        super(Properties.of(Material.HEAVY_METAL)
                .lightLevel((state)-> state.getValue(LIGHT_LEVEL)));
        this.setRegistryName("purple_glowing_block");
    }

    // 状態を変更するメソッドを追加（必要に応じて呼び出す）
    public static void setLightLevel(World world, BlockPos pos, int lightLevel) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() == KijinmodBlocks.PURPLE_GLOWING_BLOCK) {
            world.setBlock(pos, state.setValue(LIGHT_LEVEL, lightLevel), 3); // 新しい明るさに更新
        }
    }

    public static void removeSelf(World world) {
        BlockPos pos = selfpos; // フィールドを直接使用
        if (world.getBlockState(pos).getBlock() == KijinmodBlocks.PURPLE_GLOWING_BLOCK) {
            world.removeBlock(pos, false); // ブロックを消去
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        this.selfpos = context.getClickedPos(); // 設置時に自身の位置を保存
        return this.defaultBlockState().setValue(LIGHT_LEVEL, 0); // 初期値は0
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL); // LIGHT_LEVELをブロックの状態に追加
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() < 0.3F) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();

            world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);  // ポータルの紫パーティクル
        }
    }
}
