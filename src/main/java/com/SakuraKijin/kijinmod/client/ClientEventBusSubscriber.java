package com.SakuraKijin.kijinmod.client;

import com.SakuraKijin.kijinmod.entity.PanEntityTypes;
import com.SakuraKijin.kijinmod.entity.render.ArrowEntitysRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid ="kijinmod",bus= Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        renderRegister();
    }
    private static void renderRegister(){
        RenderingRegistry.registerEntityRenderingHandler(PanEntityTypes.CUSTOM_ARROW.get(), ArrowEntitysRenderer::new);
    }
}
