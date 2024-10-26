package com.SakuraKijin.kijinmod.item.itembow;

import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//@Mod.EventBusSubscriber(modid = "your_mod_id")
public class CustomBowItem extends BowItem {

    public CustomBowItem() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("custom_bow_item_init");
    }


    //@SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        PlayerEntity player = event.getPlayer();
        World world = player.level;
        ItemStack heldItem = player.getItemInHand(event.getHand());

        if (heldItem.getItem() instanceof CustomBowItem) {
            if (!world.isClientSide) {
                ArrowEntity arrow = new ArrowEntity(world, player);
                arrow.shootFromRotation(player,getYawFromVector(player),getPitchFromVector(player),0.0f,60.0f,0.0f);
                world.addFreshEntity(arrow);
            }
            // イベントをキャンセルし、元のアクションが実行されないようにする
            //event.setCanceled(true);
            //event.setCancellationResult(ActionResultType.SUCCESS);
        }
    }
    public static float getYawFromVector(PlayerEntity player) {
        Vector3d lookVec = player.getLookAngle();
        return (float) Math.toDegrees(Math.atan2(lookVec.z, lookVec.x)) - 90.0F;
    }

    public static float getPitchFromVector(PlayerEntity player) {
        Vector3d lookVec = player.getLookAngle();
        return (float) Math.toDegrees(Math.atan2(Math.sqrt(lookVec.x * lookVec.x + lookVec.z * lookVec.z), lookVec.y)) - 90.0F;
    }

    public static float getPowerForTime(int p_185059_0_) {
        float f = 1.0f;
        return f;
    }

    public void releaseUsing(ItemStack p_77615_1_, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
        if (p_77615_3_ instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)p_77615_3_;
            boolean flag = playerentity.abilities.instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_77615_1_) > 0;
            ItemStack itemstack = playerentity.getProjectile(p_77615_1_);
            int i = this.getUseDuration(p_77615_1_) - p_77615_4_;
            i = ForgeEventFactory.onArrowLoose(p_77615_1_, p_77615_2_, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) {
                return;
            }

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1)) {
                    boolean flag1 = playerentity.abilities.instabuild || itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_77615_1_, playerentity);
                    if (!p_77615_2_.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)((ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW));
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(p_77615_2_, itemstack, playerentity);
                        abstractarrowentity = this.customArrow(abstractarrowentity);
                        abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setCritArrow(true);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_77615_1_);
                        if (j > 0) {
                            abstractarrowentity.setBaseDamage(abstractarrowentity.getBaseDamage() + (double)j * 0.5 + 0.5);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_77615_1_);
                        if (k > 0) {
                            abstractarrowentity.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_77615_1_) > 0) {
                            abstractarrowentity.setSecondsOnFire(100);
                        }

                        p_77615_1_.hurtAndBreak(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.broadcastBreakEvent(playerentity.getUsedItemHand());
                        });
                        if (flag1 || playerentity.abilities.instabuild && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickup = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        p_77615_2_.addFreshEntity(abstractarrowentity);
                    }

                    p_77615_2_.playSound((PlayerEntity)null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundCategory.PLAYERS, 1.0F, 0);
                    if (!flag1 && !playerentity.abilities.instabuild) {
                        itemstack.shrink(0);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.removeItem(itemstack);
                        }
                    }

                    playerentity.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }

    }
}
