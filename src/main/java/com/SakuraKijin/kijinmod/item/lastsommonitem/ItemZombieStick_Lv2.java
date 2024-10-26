package com.SakuraKijin.kijinmod.item.lastsommonitem;

import com.SakuraKijin.kijinmod.customcharacter.CustomIronSkeleton;
import com.SakuraKijin.kijinmod.customcharacter.CustomZombieEntity2;
import com.SakuraKijin.kijinmod.magiccircle.CreateMagicCircle;
import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.particle.CreateParticle;
import com.SakuraKijin.kijinmod.regi.KijinmodBlocks;
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
import net.minecraft.world.server.ServerWorld;

public class ItemZombieStick_Lv2 extends Item {
    public ItemZombieStick_Lv2() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_zombie_stick_lv2");
    }
    private static void setnighttime(ServerWorld world) {
        // 夜の開始時間（18000）にセット
        world.setDayTime(18000);
    }
    public static void setdaytime(ServerWorld world) {
        // 夜の開始時間（1000）にセット
        world.setDayTime(1000);
    }
    private static final double height =10;
    public static int timer =0;
    public static boolean isCasting =false;
    public static World world2;
    public static Vector3d pl;
    public static boolean summon_num=true;
    private static BlockState originalBlockState;
    public static String UUID;
    public static PlayerEntity SUMMONER;

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide){
            if(!isCasting) {
                world2 =world;
                pl =new Vector3d(player.getX(),player.getY(),player.getZ());
                SUMMONER = player;
                UUID= player.getStringUUID();
                if (world instanceof ServerWorld && player != null) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    setnighttime(serverWorld);
                }
                isCasting = true;
                summon_num = true;
                timer = 0;
                return ActionResult.success(player.getItemInHand(hand));
            }
        }
        return ActionResult.fail(player.getItemInHand(hand));
    }

    public static void zombisummon(World world,Vector3d player){
        for (int i=0;i<40;i++) {
            BlockState blockst;
            double temp =Math.random();
            double radius = Math.random();
            double x= player.x+(radius*15+15)*Math.cos(temp*2*Math.PI);
            double z= player.z+(radius*15+15)*Math.sin(temp*2*Math.PI);
            Vector3d summonplace = new Vector3d(x, player.y, z);
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
            CustomZombieEntity2 zombie = new CustomZombieEntity2(EntityType.ZOMBIE, world);
            //ここがparticle
            //Vector3d part = new Vector3d(summonplace.x,summonplace.y+offset,summonplace.z);
            //CreateParticle.particle_ex(part);
            //
            zombie.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(zombie);
        }

        for (int i=0;i<20;i++) {
            BlockState blockst;
            Vector3d summonplace = new Vector3d(player.x + Math.random()*30-15, player.y, player.z + Math.random()*30-15);
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
            CustomIronSkeleton skeleton = new CustomIronSkeleton(EntityType.SKELETON, world);
            //ここがparticle
            //Vector3d part = new Vector3d(summonplace.x,summonplace.y+offset,summonplace.z);
            //CreateParticle.particle_ex(part);
            //
            skeleton.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(skeleton);
        }

        summon_num=false;
    }

    public static void lightning(World world,Vector3d player){
        int p=5;
        int radius=20;
        for(int i=0;i<p;i++){
            double angle = (2*Math.PI*i)/p;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            LightningBoltEntity lightningBolt = new LightningBoltEntity(EntityType.LIGHTNING_BOLT,world);
            lightningBolt.setPos(player.x + x, player.y+height, player.z +z);
            world.addFreshEntity(lightningBolt);
        }
    }

    public static void create_circle(World world,Vector3d player){
        CreateMagicCircle.create_circle(world,player,12,height);//12の円

        double radius3 = 14.0;
        for(double i=Math.PI*(Math.floor((timer-1)/2)/20); i< Math.PI*Math.floor(timer/2)/20; i += Math.PI/10){
            double xOffset = Math.cos(i)*radius3;
            double zOffset = Math.sin(i)*radius3;
            BlockPos blockPos = new BlockPos(player.x+xOffset,player.y+height,player.z+zOffset);
            originalBlockState=world.getBlockState(blockPos);
            if(originalBlockState.getBlock() == Blocks.AIR) {
                world.setBlock(blockPos,KijinmodBlocks.MAGIC_CIRCLE_BLOCK.defaultBlockState(), 3);
            }
        }//14の形跡
        CreateMagicCircle.create_circle(world,player,16,height);// 16の円
        CreateMagicCircle.create_circle(world,player,20,height+2);// 20の円
        CreateMagicCircle.create_circle(world,player,23,height+2);// 23の円
        double[] point_circle ={8,8,8,8,8,8};
        CreateMagicCircle.points_circle(world,player,18,point_circle,6,height+4,1);
        double[] point_circle2 ={6,6,6,6};
        CreateMagicCircle.points_circle(world,player,18,point_circle2,4,height+6,1);
        double[] point_circle3 ={4,4};
        CreateMagicCircle.points_circle(world,player,18,point_circle3,2,height+8,1);

    }
    public static void removemagicCircle(World world,Vector3d player){
    //三秒後に後のプログラムを実行したい。
    CreateMagicCircle.remove_circle(world,player,12,height);
    double radius =12.0;
    int points =5;
    BlockPos[] starPoints = new BlockPos[points];
    for(int i=0;i<points;i++){
        double angle = (2*Math.PI*i)/points;
        double x = Math.cos(angle) * radius;
        double z = Math.sin(angle) * radius;
        starPoints[i] = new BlockPos(player.x + x, player.y+height, player.z + z);
        originalBlockState=world.getBlockState(starPoints[i]);
        if(originalBlockState.getBlock() == KijinmodBlocks.MAGIC_CIRCLE_BLOCK) {
            world.setBlock(starPoints[i], Blocks.AIR.defaultBlockState(), 3);  // 3は通知フラグ
        }
    }
    for (int i = 0; i < points - 2; i++) {
        BlockPos startPoint = starPoints[i];
        BlockPos endPoint = starPoints[i + 2];
        CreateMagicCircle.deleteLine(world, startPoint, endPoint);
    }
    CreateMagicCircle.deleteLine(world, starPoints[3], starPoints[0]);
    CreateMagicCircle.deleteLine(world, starPoints[4], starPoints[1]);//ここまで星形

    double radius3 = 14.0;
    for(double i=0; i< Math.PI*2; i += Math.PI/60){
        double xOffset = Math.cos(i)*radius3;
        double zOffset = Math.sin(i)*radius3;
        BlockPos blockPos = new BlockPos(player.x+xOffset,player.y+height,player.z+zOffset);
        originalBlockState=world.getBlockState(blockPos);
        if(originalBlockState.getBlock() == KijinmodBlocks.MAGIC_CIRCLE_BLOCK) {
            world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
        }
    }//14の形跡
        CreateMagicCircle.remove_circle(world,player,16,height);
        CreateMagicCircle.remove_circle(world,player,20,height+2);// 20の円
        CreateMagicCircle.remove_circle(world,player,23,height+2);// 23の円
        double[] point_circle ={8,8,8,8,8,8};
        CreateMagicCircle.points_circle(world,player,18,point_circle,6,height+4,0);
        double[] point_circle2 ={6,6,6,6};
        CreateMagicCircle.points_circle(world,player,18,point_circle2,4,height+6,0);
        double[] point_circle3 ={4,4};
        CreateMagicCircle.points_circle(world,player,18,point_circle3,2,height+8,0);
    }

}
