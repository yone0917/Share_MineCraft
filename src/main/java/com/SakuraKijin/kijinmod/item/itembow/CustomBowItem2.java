package com.SakuraKijin.kijinmod.item.itembow;

import com.SakuraKijin.kijinmod.entity.ArrowEntitys;
import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.regi.KijinModItems;
import com.ibm.icu.impl.coll.BOCSU;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class CustomBowItem2 extends BowItem {
    public CustomBowItem2() {
        super(new Item.Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("custom_bow_item");
    }

    public void releaseUsing(ItemStack p_77615_1_, World p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
        if (p_77615_3_ instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)p_77615_3_;
            ItemStack itemstack = KijinModItems.ARROW_ITEM.getDefaultInstance();

            int i = this.getUseDuration(p_77615_1_) - p_77615_4_;
            if (i < 0) {
                return;
            }

            float f = getPowerForTime(i);
            if (!((double)f < 0.1)) {
                if (!p_77615_2_.isClientSide) {
                    AbstractArrowEntity abstractarrowentity = new ArrowEntitys(p_77615_2_,playerentity,itemstack);
                    abstractarrowentity.shootFromRotation(playerentity, playerentity.xRot, playerentity.yRot, 0.0F, f * 3.0F, 0.0F);//射角、弾速、たまのぶれ
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

                    p_77615_2_.addFreshEntity(abstractarrowentity);
                }

                p_77615_2_.playSound(null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundCategory.PLAYERS, 1.0F, 0F);

                playerentity.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }

    public static float getPowerForTime(int p_185059_0_) {
        float f = 1.0F;
        return f;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public UseAction getUseAnimation(ItemStack p_77661_1_) {
        return UseAction.BOW;
    }

    public ActionResult<ItemStack> use(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
        ItemStack itemstack = p_77659_2_.getItemInHand(p_77659_3_);
        p_77659_2_.startUsingItem(p_77659_3_);
        return ActionResult.consume(itemstack);
    }
    public int getDefaultProjectileRange() {
        return 15;
    }
}
