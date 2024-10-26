package com.SakuraKijin.kijinmod.item.wall;

import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.regi.KijinmodBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemWall extends Item {
    private static BlockState originalBlockState;
    public ItemWall() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_wall");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            int radius=40;
            int distanceBehind = 35;

            //プレイヤーの向いている方向の逆方向を計算
            Vector3d lookVec = player.getLookAngle().normalize();
            double centerX = player.getX() - lookVec.x * distanceBehind;
            double centerY = player.getY() - lookVec.y * distanceBehind;
            double centerZ = player.getZ() - lookVec.z * distanceBehind;

            //壁生成時の目標を指定
            double targetX = player.getX() + ( lookVec.x * ( radius - distanceBehind ) );
            double targetY = player.getY() + ( lookVec.y * ( radius - distanceBehind ) ) + 1;
            double targetZ = player.getZ() + ( lookVec.z * ( radius - distanceBehind ) );

            //球の生成
            for(double i=0;i<Math.PI;i+=Math.PI/ 128) {
                for (double j = 0; j < 2 * Math.PI; j += Math.PI / 128) {
                    double xOffset = Math.sin(i) * Math.cos(j) * radius;
                    double yOffset = Math.sin(i) * Math.sin(j) * radius;
                    double zOffset = Math.cos(i) * radius;
                    BlockPos blockPos = new BlockPos(centerX + xOffset, centerY + yOffset, centerZ + zOffset);
                    originalBlockState = world.getBlockState(blockPos);

                    //目標との差
                    double difX = blockPos.getX() - targetX;
                    double difY = blockPos.getY() - targetY;
                    double difZ = blockPos.getZ() - targetZ;

                    if (originalBlockState.getBlock() == Blocks.AIR) {
                        if ( Math.abs(difX) <= 3 && Math.abs(difY) <= 3 && Math.abs(difZ) <= 3) {
                            world.setBlock(blockPos, KijinmodBlocks.WALL_BLOCK.defaultBlockState(), 3);
                        }
                    }
                }
            }
            return ActionResult.success(player.getItemInHand(hand));
        }
        return ActionResult.fail(player.getItemInHand(hand));
    }
}
