package com.SakuraKijin.kijinmod.item.manyzombieitem;

import com.SakuraKijin.kijinmod.item.lastsommonitem.ItemZombieStick_Lv2;
import com.SakuraKijin.kijinmod.magiccircle.CreateMagicCircle;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "kijinmod",bus= Mod.EventBusSubscriber.Bus.FORGE)
public class ManyZombieUseSub {
    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            ItemManyZombieStick.timer++;
            if(ItemManyZombieStick.timer==60 && ItemManyZombieStick.isCasting){
                CreateMagicCircle.delete_random_block(ItemManyZombieStick.world2,ItemManyZombieStick.pl);
                ItemManyZombieStick.isCasting=false;
                ItemManyZombieStick.timer=0;
            }
        }
    }
}
