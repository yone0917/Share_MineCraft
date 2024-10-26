package com.SakuraKijin.kijinmod.item.summonzombieandskeletonitem2;


import com.SakuraKijin.kijinmod.customcharacter.CustomManyZombieEntity;
import com.SakuraKijin.kijinmod.customcharacter.CustomSkeletonEntity;
import com.SakuraKijin.kijinmod.customcharacter.CustomSkeletonEntityZombieSkeletonStick;
import com.SakuraKijin.kijinmod.customcharacter.CustomZombieEntityZombieSkeletonStick;
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

public class SummonZombieSkeleton extends Item {

    public SummonZombieSkeleton() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_zombie_and_skeleton_stick");
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
            zombisum10_skleton_5(world,pl);

        }
        return super.use(world, player, hand);
    }
    public static void zombisum10_skleton_5(World world,Vector3d pl){
        for (int i=0;i<10;i++) {
            BlockState blockst;
            Vector3d summonplace = new Vector3d(pl.x + Math.random() * 20 - 10, pl.y, pl.z + Math.random() * 20 - 10);
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
            CustomZombieEntityZombieSkeletonStick zombie = new CustomZombieEntityZombieSkeletonStick(EntityType.ZOMBIE, world);
            zombie.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(zombie);
        }
        for (int i=0;i<5;i++) {
            BlockState blockst;
            Vector3d summonplace = new Vector3d(pl.x + Math.random() * 20 - 10, pl.y, pl.z + Math.random() * 20 - 10);
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
            CustomSkeletonEntityZombieSkeletonStick skeleton = new CustomSkeletonEntityZombieSkeletonStick(EntityType.SKELETON, world);
            skeleton.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(skeleton);
        }
    }
}
