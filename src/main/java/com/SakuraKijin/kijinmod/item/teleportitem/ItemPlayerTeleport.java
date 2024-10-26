package com.SakuraKijin.kijinmod.item.teleportitem;

import com.SakuraKijin.kijinmod.main.KijinMod;
import com.SakuraKijin.kijinmod.particle.CreateParticle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftGame;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.mclanguageprovider.MinecraftModLanguageProvider;

public class ItemPlayerTeleport extends Item {
    public ItemPlayerTeleport() {
        super(new Properties().tab(KijinMod.KIJIMOD_TAB));
        this.setRegistryName("item_player_teleport");
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if(!world.isClientSide){
            Vector3d lookVec = player.getLookAngle();
            BlockState blockst;
            BlockState upperst;
            Vector3d summonplace = new Vector3d(player.getX()+ lookVec.x*10, player.getY(), player.getZ()+ lookVec.z*10);
            double offset =0;
            BlockPos blockPos = new BlockPos(summonplace.x, summonplace.y, summonplace.z);
            blockst = world.getBlockState(blockPos);
            BlockPos upper = new BlockPos(summonplace.x,summonplace.y+1,summonplace.z);
            upperst = world.getBlockState(upper);

            while (blockst.getBlock() != Blocks.AIR || upperst.getBlock() != Blocks.AIR) {
                offset += 1;
                blockPos = new BlockPos(summonplace.x, summonplace.y + offset, summonplace.z);
                blockst = world.getBlockState(blockPos);
                upper =  new BlockPos(summonplace.x, summonplace.y + offset+1, summonplace.z);
                upperst =world.getBlockState(upper);
            }

            blockPos = new BlockPos(summonplace.x, summonplace.y+offset-1, summonplace.z);
            blockst = world.getBlockState(blockPos);
            while(blockst.getBlock() ==Blocks.AIR){
                offset -=1;
                blockPos = new BlockPos(summonplace.x, summonplace.y+offset-1, summonplace.z);
                blockst = world.getBlockState(blockPos);
            }

            double teleportX = player.getX() + lookVec.x*10;
            double teleportY = player.getY() + offset; //+ lookVec.y*5;
            double teleportZ = player.getZ() + lookVec.z*10;
            Vector3d teleportpc =new Vector3d(teleportX,teleportY,teleportZ);
            player.teleportTo(teleportX,teleportY,teleportZ);
            //ここはメッセージ
            //player.sendMessage(new StringTextComponent("テレポート"), Util.NIL_UUID);
            //
            return ActionResult.success(player.getItemInHand(hand));
        }
        return ActionResult.fail(player.getItemInHand(hand));
    }
}
