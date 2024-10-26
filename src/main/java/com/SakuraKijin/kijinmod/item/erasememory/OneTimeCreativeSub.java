package com.SakuraKijin.kijinmod.item.erasememory;

import net.minecraft.data.BlockStateVariantBuilder;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "kijinmod",bus= Mod.EventBusSubscriber.Bus.FORGE)
public class OneTimeCreativeSub {
    @SubscribeEvent
    public static void onServerTick(final TickEvent.ServerTickEvent event){
        if(event.phase == TickEvent.Phase.END){
            OneTimeCreativeClass.time++;
            if(OneTimeCreativeClass.time==9 &&OneTimeCreativeClass.isCasting){
                OneTimeCreativeClass.play.abilities.invulnerable = true;
            }
            if(OneTimeCreativeClass.time==10 &&OneTimeCreativeClass.isCasting){
                OneTimeCreativeClass.play.abilities.invulnerable=false;
                OneTimeCreativeClass.time=0;
                OneTimeCreativeClass.isCasting =false;
            }
        }
    }
}