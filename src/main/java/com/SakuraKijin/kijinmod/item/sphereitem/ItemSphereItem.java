package com.SakuraKijin.kijinmod.item.sphereitem;

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
import net.minecraft.world.World;

public class ItemSphereItem extends Item {
    private static BlockState originalBlockState;
    public ItemSphereItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("sphere_item");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide){
            int radius=10;
            for(double i=0;i<Math.PI;i+=Math.PI/16) {
                for (double j = 0; j < 2 * Math.PI; j += Math.PI / 16) {
                    double xOffset = Math.sin(i) * Math.cos(j) * radius;
                    double yOffset = Math.sin(i) * Math.sin(j) * radius;
                    double zOffset = Math.cos(i) * radius;
                    BlockPos blockPos = new BlockPos(player.getX() + xOffset, player.getY() + yOffset, player.getZ() + zOffset);
                    originalBlockState = world.getBlockState(blockPos);
                    if (originalBlockState.getBlock() == Blocks.AIR) {
                        world.setBlock(blockPos, KijinmodBlocks.MAGIC_GLASS.defaultBlockState(), 3);
                    }
                }
            }
            return ActionResult.success(player.getItemInHand(hand));
        }
        return ActionResult.fail(player.getItemInHand(hand));
    }
}
