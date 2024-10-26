package com.SakuraKijin.kijinmod.main;

import com.SakuraKijin.kijinmod.entity.PanEntityTypes;
import com.SakuraKijin.kijinmod.regi.KijinModTab;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("kijinmod")
public class KijinMod {
    public static final ItemGroup KIJIMOD_TAB = new KijinModTab();
    public KijinMod(){
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        PanEntityTypes.REGISTER.register(bus);
    }
}
