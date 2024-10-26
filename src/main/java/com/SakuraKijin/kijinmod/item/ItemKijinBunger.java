package com.SakuraKijin.kijinmod.item;

import com.SakuraKijin.kijinmod.main.KijinMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemKijinBunger extends Item {
    public ItemKijinBunger() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB).food(new Food.Builder()
                .nutrition(8)
                .saturationMod(0.8F)
                .meat()
                .alwaysEat()
                .effect(new EffectInstance(Effects.DAMAGE_BOOST,6000,4),1F)//ポーション効果
                .build()));
        this.setRegistryName("kijin_burger");
    }
    @Override
    public void appendHoverText(ItemStack p_77624_1_, World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        p_77624_3_.add(new TranslationTextComponent(this.getDescriptionId() + ".desc").withStyle(TextFormatting.GRAY));
    }
}
