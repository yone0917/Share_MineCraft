package com.SakuraKijin.kijinmod.item.DamageTransferItem;

import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class DamageTransferItem extends Item {
    public DamageTransferItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_damagetransfer");
    }

    // ダメージ肩代わりのロジックをイベントとしてフックする
    @Mod.EventBusSubscriber(modid = "kijinmod")
    public static class EventHandlers {

        @SubscribeEvent
        public static void onPlayerDamage(LivingDamageEvent event) {
            // ダメージを受けたエンティティがプレイヤーであるか確認
            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();

                // プレイヤーが特定のアイテムを持っているかチェック
                ItemStack offhandItem = player.getOffhandItem(); // オフハンドに持っている場合
                if (offhandItem.getItem() instanceof DamageTransferItem) {
                    // プレイヤーの近くのゾンビを探す
                    World world = player.level;
                    List<ZombieEntity> nearbyZombies = world.getEntitiesOfClass(
                            ZombieEntity.class,
                            new AxisAlignedBB(player.getX() - 30, player.getY() - 30, player.getZ() - 30,
                                    player.getX() + 30, player.getY() + 30, player.getZ() + 30)
                    );

                    if (!nearbyZombies.isEmpty()) {
                        // 一番近いゾンビを選ぶ
                        ZombieEntity nearestZombie = null;
                        double nearestDistance = Double.MAX_VALUE;

                        for (ZombieEntity zombie : nearbyZombies) {
                            double distance = player.distanceTo(zombie);
                            if (distance < nearestDistance) {
                                nearestDistance = distance;
                                nearestZombie = zombie;
                            }
                        }

                        if (nearestZombie != null) {
                            // ゾンビにダメージを肩代わりさせる
                            float damage = event.getAmount();
                            nearestZombie.hurt(event.getSource(), damage);

                            // プレイヤーのダメージをゼロにする
                            event.setAmount(0);
                        }
                    }
                }
            }
        }

        // ノックバックを無効化する処理
        @SubscribeEvent
        public static void onPlayerKnockBack(LivingKnockBackEvent event) {
            // ノックバックを受けたエンティティがプレイヤーであるか確認
            if (event.getEntityLiving() instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) event.getEntityLiving();

                // プレイヤーが特定のアイテムを持っているかチェック
                ItemStack offhandItem = player.getOffhandItem();
                if (offhandItem.getItem() instanceof DamageTransferItem) {
                    // プレイヤーのノックバックを無効化する
                    event.setCanceled(true);
                }
            }
        }
    }
}
