package com.SakuraKijin.kijinmod.regi;

import com.SakuraKijin.kijinmod.block.*;
import com.SakuraKijin.kijinmod.main.KijinMod;
import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder("kijinmod")
public class KijinmodBlocks {
    public static final BlockSakuraBlock SAKURA_BLOCK = null;
    public static final BlockMagicGlass MAGIC_GLASS =null;
    public static final BlockMagicCircleBlock MAGIC_CIRCLE_BLOCK =null;
    public static final BlockWallBlock WALL_BLOCK = null;
    public static final BlockPurpleGlowingBlock PURPLE_GLOWING_BLOCK = null;
    @Mod.EventBusSubscriber(modid = "kijinmod",bus = Bus.MOD)
    public static class Register{

        @SubscribeEvent
        public static void registerBlock(final RegistryEvent.Register<Block> event){
            final Block[] blocks={
                    new BlockSakuraBlock(),
                    new BlockMagicGlass(),
                    new BlockMagicCircleBlock(),
                    new BlockWallBlock(),
                    new BlockPurpleGlowingBlock()
            };
            event.getRegistry().registerAll(blocks);

           /* for(Block block : blocks){
                if(block instanceof BlockMagicGlass){
                    RenderTypeLookup.setRenderLayer(block, RenderType.translucent());
                }
            }*/
        }
        @SubscribeEvent
        public static void registerBlockItem(final RegistryEvent.Register<Item> event){
            final BlockItem[] items ={
                    new BlockItem(SAKURA_BLOCK,new Item.Properties().tab(KijinMod.KIJIMOD_TAB)),
                    new BlockItem(MAGIC_GLASS,new Item.Properties().tab(KijinMod.KIJIMOD_TAB)),
                    new BlockItem(MAGIC_CIRCLE_BLOCK,new Item.Properties().tab(KijinMod.KIJIMOD_TAB)),
                    new BlockItem(WALL_BLOCK,new Item.Properties().tab(KijinMod.KIJIMOD_TAB)),
                    new BlockItem(PURPLE_GLOWING_BLOCK,new Item.Properties().tab(KijinMod.KIJIMOD_TAB))
            };
            for(BlockItem item :items){
                final Block block = item.getBlock();
                final ResourceLocation location = Preconditions.checkNotNull(block.getRegistryName());
                event.getRegistry().register(item.setRegistryName(location));
            }
        }
        /*@SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            RenderTypeLookup.setRenderLayer(MAGIC_GLASS, RenderType.translucent());
        }*/
    }

}
