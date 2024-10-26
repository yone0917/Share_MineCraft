package com.SakuraKijin.kijinmod.item.manyzombieitem;

import com.SakuraKijin.kijinmod.customcharacter.CustomManyZombieEntity;
import com.SakuraKijin.kijinmod.magiccircle.CreateMagicCircle;
import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ItemManyZombieStick extends Item {
    public ItemManyZombieStick() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_many_zombie_stick");
    }
    public static Vector3d pl;
    public static String SUMMONERUUID;
    public static PlayerEntity SUMMONER;
    public static int timer=0;
    public static boolean isCasting=false;
    public static World world2;

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide){
            if(!isCasting) {
                pl = new Vector3d(player.getX(), player.getY(), player.getZ());
                SUMMONER = player;
                SUMMONERUUID = player.getStringUUID();
                world2 = world;
                zombisum(world, pl);
                lightning(world, pl);
                CreateMagicCircle.create_random_block(world, pl);
                isCasting = true;
                timer = 0;
            }
        }
        return super.use(world, player, hand);
    }
    public static void zombisum(World world,Vector3d pl){
        for (int i=0;i<50;i++) {
            BlockState blockst;
            Vector3d summonplace = new Vector3d(pl.x + Math.random() * 30 - 15, pl.y, pl.z + Math.random() * 30 - 15);
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
            CustomManyZombieEntity zombie = new CustomManyZombieEntity(EntityType.ZOMBIE, world);
            zombie.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(zombie);
        }
    }
    public static void lightning(World world,Vector3d player){
        int p=5;
        int radius=15;
        for(int i=0;i<p;i++){
            double angle = (2*Math.PI*i)/p;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            LightningBoltEntity lightningBolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,world);
            lightningBolt.setPos(player.x + x, player.y, player.z +z);
            world.addFreshEntity(lightningBolt);
        }
    }

}
