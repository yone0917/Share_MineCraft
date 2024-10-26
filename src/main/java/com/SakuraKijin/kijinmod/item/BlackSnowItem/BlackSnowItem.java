package com.SakuraKijin.kijinmod.item.BlackSnowItem;

import com.SakuraKijin.kijinmod.block.BlockPurpleGlowingBlock;
import com.SakuraKijin.kijinmod.customcharacter.CustomManyZombieEntity;
import com.SakuraKijin.kijinmod.customcharacter.CustomZombieBerserker;
import com.SakuraKijin.kijinmod.item.lastsommonitem.ItemZombieStick_Lv2;
import com.SakuraKijin.kijinmod.item.manyzombieitem.ItemManyZombieStick;
import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.regi.KijinmodBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.particles.ParticleTypes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "kijinmod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlackSnowItem extends Item {

    public static boolean isSnowing = false;  // 降雪状態を保持
    private static PlayerEntity snowingPlayer = null; // 雪を降らせているプレイヤー
    private static int tickCounter = 0; // 40ティックごとの処理カウンター

    private static int zombieCounter = 0; //ゾンビ召喚のための処理カウンター
    private static int diskCounter = 0; //円を広げるための処理カウンター

    private static BlockPos centerPos;

    private static Vector3d cp;
    private static BlockState originalBlockState;

    public BlackSnowItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_blacksnow");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        //雪を降らせる＆止ませる
        if (!world.isClientSide) {
            // 降雪状態をトグル
            isSnowing = !isSnowing;

            // 降雪が開始した場合、プレイヤーを記録
            if (isSnowing) {
                snowingPlayer = player;
                centerPos = snowingPlayer.blockPosition();
                cp = new Vector3d(snowingPlayer.getX(),snowingPlayer.getY(),snowingPlayer.getZ());
            } else {
                // 停止する場合は、プレイヤーの追跡をリセット
                snowingPlayer = null;
                diskCounter = 0;
                BlockPurpleGlowingBlock.removeSelf(world);
            }
        }

        return ActionResult.success(player.getItemInHand(hand));
    }

    // 雪を降らせるロジックをティックごとに処理するイベントハンドラ
    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && isSnowing && snowingPlayer != null) {
            tickCounter++; // 毎ティックカウントを増やす
            zombieCounter++;
            diskCounter++;
            ServerWorld world = (ServerWorld) snowingPlayer.level;
            removePlants(world,cp,15);

            if (diskCounter >= 1) {
                create_disk(world,cp,0,5,10);
            }

            if (diskCounter >= 20) {
                create_disk(world,cp,6,10,5);
            }

            if (diskCounter >= 40) {
                create_disk(world,cp,11,15,3);
            }

            if (tickCounter >= 40) { // 40ティックごとに処理
                spawnBlackSnow(world, centerPos, 15, 15); // 雪を降らせる範囲
                tickCounter = 0; // カウンターリセット
            }

            if (zombieCounter >= 100) { //100ティックごとの処理
                zombisum(world, cp, 5, 21);
                zombieCounter = 0; //カウンターリセット
            }
        }
    }

    // プレイヤーが死亡した場合に降雪を止める
    @SubscribeEvent
    public static void onPlayerDeath(TickEvent.PlayerTickEvent event) {
        if (!event.player.level.isClientSide && event.player == snowingPlayer && event.player.isDeadOrDying()) {
            ServerWorld world = (ServerWorld) snowingPlayer.level;
            BlockPurpleGlowingBlock.removeSelf(world);
            isSnowing = false;
            snowingPlayer = null;// プレイヤーが死亡したら追跡をリセット
            diskCounter = 0;
        }
    }

    // 黒い雪を降らせるメソッド
    private static void spawnBlackSnow(ServerWorld world, BlockPos centerPos, int radius, int height) {

        for (int i = 0; i < 200; i++) { //雪を降らせるマスの数
            // 円の中でランダムな位置を生成
            double angle = world.random.nextDouble() * 2 * Math.PI; // 0〜2πのランダムな角度
            double r = world.random.nextDouble() * radius; // 0〜radiusのランダムな半径

            double posX = centerPos.getX() + Math.cos(angle) * r; // 円のX座標
            double posZ = centerPos.getZ() + Math.sin(angle) * r; // 円のZ座標
            double posY = centerPos.getY() + world.random.nextDouble() * height; // 高さの範囲内

            // 黒いパーティクル（灰）をスポーン
            world.sendParticles(ParticleTypes.ASH, posX, posY, posZ, 5, 0.0, 0.1, 0.0, 0.01);
        }
    }

    //ゾンビを召喚するメソッド
    public static void zombisum(World world,Vector3d pl, int n, int diameret){
        for (int i=0;i<n;i++) {
            BlockState blockst;
            Vector3d summonplace = new Vector3d(pl.x + Math.random() * diameret - diameret / 2, pl.y, pl.z + Math.random() * diameret - diameret / 2);
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

            CustomZombieBerserker zombie = new CustomZombieBerserker(EntityType.ZOMBIE, world);
            zombie.setPos(summonplace.x,summonplace.y+offset,summonplace.z);
            world.addFreshEntity(zombie);
        }
    }

    //地面に円形の領域を造るメソッド
    public static void create_disk(World world, Vector3d player, double minradius, double maxradius, int light){
        for (double n = minradius ; n <= maxradius ; n += 1) {
            for (double i = 0; i < Math.PI * 2; i += Math.PI / 40) {
                double xOffset = Math.cos(i) * n;
                double yOffset = 0;
                double zOffset = Math.sin(i) * n;
                BlockPos blockPos = new BlockPos(player.x + xOffset, player.y + yOffset, player.z + zOffset);
                originalBlockState = world.getBlockState(blockPos);
                ///ここからへんこう
                while(originalBlockState.getBlock() != Blocks.AIR){
                    yOffset+=1;
                    blockPos = new BlockPos(player.x+xOffset, player.y+yOffset, player.z+zOffset);
                    originalBlockState = world.getBlockState(blockPos);
                }
                blockPos = new BlockPos(player.x+xOffset, player.y+yOffset-1, player.z+zOffset);
                originalBlockState = world.getBlockState(blockPos);

                while(originalBlockState.getBlock() ==Blocks.AIR){
                    yOffset -=1;
                    blockPos = new BlockPos(player.x+xOffset, player.y+yOffset, player.z+zOffset);
                    originalBlockState = world.getBlockState(blockPos);
                }

                // 空気ブロック以外の場所にブロックを設置
                world.setBlock(blockPos, KijinmodBlocks.PURPLE_GLOWING_BLOCK.defaultBlockState(), 3);

                BlockPurpleGlowingBlock.setLightLevel(world, blockPos, light);
            }
        }
    }
    public static void removePlants(World world, Vector3d player, int radius) {
        BlockPos centerPos = new BlockPos(player.x, player.y, player.z);
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {  // 高さ方向の範囲も指定
                    BlockPos targetPos = centerPos.offset(x, y, z);  // 対象ブロックの位置を計算
                    BlockState blockState = world.getBlockState(targetPos);

                    // 植物ブロックを識別
                    if (isPlantBlock(blockState)) {
                        world.destroyBlock(targetPos, false);  // 植物ブロックを削除（ドロップしない）
                    }
                }
            }
        }
    }

    private static boolean isPlantBlock(BlockState blockState){
        Material material = blockState.getMaterial();
        return material == Material.PLANT
                || material == Material.REPLACEABLE_PLANT
                || material==Material.TOP_SNOW;

    }
}