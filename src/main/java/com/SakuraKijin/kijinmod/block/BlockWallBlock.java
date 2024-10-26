package com.SakuraKijin.kijinmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class BlockWallBlock extends Block {
    public BlockWallBlock() {
        super(Properties.of(Material.METAL));
        this.setRegistryName("wall_block");
    }

    //ブロックが設置された際に呼び出されるメソッド

    @Override
    public void onPlace(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);

        //10秒後にブロックを消す
        world.getBlockTicks().scheduleTick(pos, this, 200);
    }

    //ブロックのtickイベント(タイマーが完了した時に呼ばれる)

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isClientSide){
            //ブロックを削除する
            world.removeBlock(pos,false);
        }
        super.tick(state, world, pos, random);
    }
}
