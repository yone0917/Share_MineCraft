package com.SakuraKijin.kijinmod.regi;

import com.SakuraKijin.kijinmod.item.BlackSnowItem.BlackSnowItem;
import com.SakuraKijin.kijinmod.item.DamageTransferItem.DamageTransferItem;
import com.SakuraKijin.kijinmod.item.SkeltonTurretItem.SkeltonTurretItem;
import com.SakuraKijin.kijinmod.item.guardzombieitem.ItemGuardZombieItem;
import com.SakuraKijin.kijinmod.item.ItemKijinBunger;
import com.SakuraKijin.kijinmod.item.ItemSakuraIngod;
import com.SakuraKijin.kijinmod.item.itembow.CustomArrowItem;
import com.SakuraKijin.kijinmod.item.itembow.CustomBowItem;
import com.SakuraKijin.kijinmod.item.itembow.CustomBowItem2;
import com.SakuraKijin.kijinmod.item.manyzombieitem.ItemManyZombieStick;
import com.SakuraKijin.kijinmod.item.speedboostitem.ItemSpeedBoost;
import com.SakuraKijin.kijinmod.item.sphereitem.ItemSphereItem;
import com.SakuraKijin.kijinmod.item.makefriendsitem.makefriendsitem;
import com.SakuraKijin.kijinmod.item.summondragonitem.ItemDragonStick;
import com.SakuraKijin.kijinmod.item.summonskeletonitem.ItemSkeletonStick_Lv1;
import com.SakuraKijin.kijinmod.item.summonzombieandskeletonitem2.SummonZombieSkeleton;
import com.SakuraKijin.kijinmod.item.summonzombieitem.ItemZombieStick_Lv1;
import com.SakuraKijin.kijinmod.item.customremovalitem.CustomZombieRemovalItem;
import com.SakuraKijin.kijinmod.item.teleportitem.ItemPlayerTeleport;
import com.SakuraKijin.kijinmod.item.lastsommonitem.ItemZombieStick_Lv2;
import com.SakuraKijin.kijinmod.item.wall.ItemWall;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;
@ObjectHolder("kijinmod")//アイテムとる六クラスの宣言?
public class KijinModItems {
    public static final ItemKijinBunger KIJIN_BURGER = new ItemKijinBunger();
    public static final ItemZombieStick_Lv2 ITEM_ZOMBIE_STICK_LV_2 = new ItemZombieStick_Lv2();
    public static final CustomArrowItem ARROW_ITEM = new CustomArrowItem();
    public static final CustomBowItem2 CUSTOM_BOW_ITEM_2 = new CustomBowItem2();
    @Mod.EventBusSubscriber(modid = "kijinmod",bus= Bus.MOD)
    public static class Register{
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event){
            final Item[] item = {
                    new ItemSakuraIngod(),
                    KIJIN_BURGER,
                    new ItemZombieStick_Lv1(),
                    ITEM_ZOMBIE_STICK_LV_2,
                    new ItemSphereItem(),
                    new ItemSkeletonStick_Lv1(),
                    new ItemDragonStick(),
                    new CustomZombieRemovalItem(),
                    new ItemManyZombieStick(),
                    new ItemGuardZombieItem(),
                    new makefriendsitem(),
                    new ItemSpeedBoost(),
                    new ItemPlayerTeleport(),
                    new CustomBowItem(),
                    new ItemWall(),
                    new DamageTransferItem(),
                    new BlackSnowItem(),
                    new SummonZombieSkeleton(),
                    new SkeltonTurretItem(),
                    ARROW_ITEM,
                    CUSTOM_BOW_ITEM_2
            };

            event.getRegistry().registerAll(item);
        }
    }

}
