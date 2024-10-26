package com.SakuraKijin.kijinmod.item.guardzombieitem;

import com.SakuraKijin.kijinmod.customcharacter.CustomGuardZombieEntity;
import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemGuardZombieItem extends Item {
    public ItemGuardZombieItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_guard_zombie_stick");
    }
    public static Vector3d pl;
    public static String SUMMONERUUID;
    public static PlayerEntity SUMMONER;

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            pl =new Vector3d(player.getX(),player.getY(),player.getZ());
            SUMMONER = player;
            SUMMONERUUID = player.getStringUUID();
            zombisum(world,pl);
        }
        return super.use(world, player, hand);
    }

    public static void zombisum(World world,Vector3d pl){
        double radius = 3.0;
        int point = 3;

        for (int i = 0;i < point;i++) {
            double angle = (2 * Math.PI * i) / point;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            BlockState blockst;
            Vector3d summonplace = new Vector3d(pl.x + x,pl.y,pl.z + z);
            double offset =0;
            BlockPos blockPos = new BlockPos(summonplace.x, summonplace.y, summonplace.z);
            blockst = world.getBlockState(blockPos);
            while(blockst.getBlock() != Blocks.AIR){
                offset+=1;
                blockPos = new BlockPos(summonplace.x, summonplace.y+offset, summonplace.z);
                blockst = world.getBlockState(blockPos);
            }
            blockPos = new BlockPos(summonplace.x, summonplace.y+offset-1, summonplace.z);
            blockst = world.getBlockState(blockPos);
            while(blockst.getBlock() ==Blocks.AIR){
                offset -=1;
                blockPos = new BlockPos(summonplace.x, summonplace.y+offset-1, summonplace.z);
                blockst = world.getBlockState(blockPos);
            }
            CustomGuardZombieEntity zombie = new CustomGuardZombieEntity(EntityType.ZOMBIE,world);
            zombie.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(zombie);
        }
    }
}
