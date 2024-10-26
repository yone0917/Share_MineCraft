package com.SakuraKijin.kijinmod.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

public class PanEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTER =DeferredRegister.create(ForgeRegistries.ENTITIES,"kijinmod");

    public static final RegistryObject<EntityType<ArrowEntitys>> CUSTOM_ARROW = register("custom_arrow",ArrowEntitys::new);
    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, BiFunction<EntityType<T>, World,T> function){
        EntityType<T> type = EntityType.Builder.of(function::apply,EntityClassification.MISC).sized(0.5f,0.5f).clientTrackingRange(4).updateInterval(20).build(id);
        return REGISTER.register(id,()->type);
    }
}
