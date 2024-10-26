package com.SakuraKijin.kijinmod.item.SkeltonTurretItem;

import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.regi.KijinmodBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
@Mod.EventBusSubscriber
public class SkeltonTurretItem extends Item{
    private static final int DELAY_TICKS = 20; //(1秒 = 20ティック)
    private static int tickCounter = 0;
    private static List<BlockPos> pendingBlocks = new ArrayList<>(); // 出現予定の位置リスト
    private static PlayerEntity playerRef; // プレイヤー参照

    public SkeltonTurretItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_skeleton_turret");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide) {
            // プレイヤー参照を設定
            playerRef = player;

            // 出現予定位置のリストを作成
            prepareBlocks(world, player);
        }
        return ActionResult.success(player.getItemInHand(hand));
    }

    private static void prepareBlocks(World world, PlayerEntity player) { // メソッドを静的に
        Vector3d playerPos = player.position();

        for (int i = 0; i < 5; i++) {
            double offsetX = (world.random.nextDouble() - 0.5) * 10;
            double offsetY = 5 + world.random.nextInt(10);
            double offsetZ = -5 - world.random.nextDouble() * 10;

            BlockPos spawnPos = new BlockPos(playerPos.x + offsetX, playerPos.y + offsetY, playerPos.z + offsetZ);
            pendingBlocks.add(spawnPos); // 生成位置をリストに追加
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !pendingBlocks.isEmpty() && playerRef != null) {
            tickCounter++;

            if (tickCounter >= DELAY_TICKS) {
                tickCounter = 0; // カウンターをリセット
                World world = playerRef.level; // プレイヤーのワールドを取得

                if (!pendingBlocks.isEmpty()) {
                    BlockPos spawnPos = pendingBlocks.remove(0); // リストから次の位置を取得して削除
                    world.setBlock(spawnPos, KijinmodBlocks.MAGIC_GLASS.defaultBlockState(), 3);

                    // スケルトンをブロックの上に召喚
                    SkeletonEntity skeleton = new SkeletonEntity(EntityType.SKELETON, world);
                    skeleton.setPos(spawnPos.getX() + 0.5, spawnPos.getY() + 1, spawnPos.getZ() + 0.5); // スケルトンの位置を調整
                    world.addFreshEntity(skeleton);
                }
            }
        }
    }
}
