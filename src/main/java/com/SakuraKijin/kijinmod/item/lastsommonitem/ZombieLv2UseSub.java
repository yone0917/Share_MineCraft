package com.SakuraKijin.kijinmod.item.lastsommonitem;

import com.SakuraKijin.kijinmod.magiccircle.CreateMagicCircle;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "kijinmod",bus= Mod.EventBusSubscriber.Bus.FORGE)
public class ZombieLv2UseSub {
    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            ItemZombieStick_Lv2.timer++;
            if (ItemZombieStick_Lv2.timer<=80 && ItemZombieStick_Lv2.isCasting){
                ItemZombieStick_Lv2.create_circle(ItemZombieStick_Lv2.world2, ItemZombieStick_Lv2.pl);
            }
            else if (ItemZombieStick_Lv2.timer==100 && ItemZombieStick_Lv2.isCasting){
                CreateMagicCircle.create_star(ItemZombieStick_Lv2.world2, ItemZombieStick_Lv2.pl);
                ItemZombieStick_Lv2.zombisummon(ItemZombieStick_Lv2.world2, ItemZombieStick_Lv2.pl);
                ItemZombieStick_Lv2.lightning(ItemZombieStick_Lv2.world2, ItemZombieStick_Lv2.pl);
            }
            else if(ItemZombieStick_Lv2.timer>=120 && ItemZombieStick_Lv2.isCasting){
                ItemZombieStick_Lv2.removemagicCircle(ItemZombieStick_Lv2.world2, ItemZombieStick_Lv2.pl);
                //if (ItemZombieStick_Lv2.world2 instanceof ServerWorld) {
                    //ServerWorld serverWorld = (ServerWorld) ItemZombieStick_Lv2.world2;
                    //ItemZombieStick_Lv2.setdaytime(serverWorld);
                //}
                ItemZombieStick_Lv2.isCasting =false;
                ItemZombieStick_Lv2.timer=0;
            }
        }
    }
}