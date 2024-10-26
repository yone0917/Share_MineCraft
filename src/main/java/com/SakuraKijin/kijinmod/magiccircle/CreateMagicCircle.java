package com.SakuraKijin.kijinmod.magiccircle;

import com.SakuraKijin.kijinmod.item.lastsommonitem.ItemZombieStick_Lv2;
import com.SakuraKijin.kijinmod.regi.KijinmodBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CreateMagicCircle {
    private static BlockState originalBlockState;
    private static double height =10;
    public static void create_circle(World world, Vector3d player, double radius, double height){
        for(double i = Math.PI*(ItemZombieStick_Lv2.timer-1)/40; i< Math.PI* ItemZombieStick_Lv2.timer/40; i += Math.PI/60){
            double xOffset = Math.cos(i)*radius;
            double zOffset = Math.sin(i)*radius;
            BlockPos blockPos = new BlockPos(player.x+xOffset,player.y+height,player.z+zOffset);
            originalBlockState=world.getBlockState(blockPos);
            if(originalBlockState.getBlock() == Blocks.AIR) {
                world.setBlock(blockPos, KijinmodBlocks.MAGIC_CIRCLE_BLOCK.defaultBlockState(), 3);
            }
        }
    }
    public static void remove_circle(World world, Vector3d player,double radius,double height) {
        for (double i = 0; i < Math.PI * 2; i += Math.PI / 120) {
            double xOffset = Math.cos(i) * radius;
            double zOffset = Math.sin(i) * radius;
            BlockPos blockPos = new BlockPos(player.x + xOffset, player.y + height, player.z + zOffset);
            originalBlockState = world.getBlockState(blockPos);
            if (originalBlockState.getBlock() == KijinmodBlocks.MAGIC_CIRCLE_BLOCK) {
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
    private static BlockPos[] random_block_pos = new BlockPos[75];
    public static void create_random_block(World world,Vector3d player){
        double radius =15;
        for (int i = 0; i < 75; i += 1) {
                double angle = (2 * Math.PI * i) / 75;
                double xOffset = radius * Math.cos(angle) + 2 * Math.random();
                double yoffset = 8 + 2 * Math.random();
                double zOffset = radius * Math.sin(angle) + 2 * Math.random();
                random_block_pos[i] = new BlockPos(player.x + xOffset, player.y + yoffset, player.z + zOffset);
                originalBlockState = world.getBlockState(random_block_pos[i]);
                if (originalBlockState.getBlock() == Blocks.AIR) {
                    if(i>0 && i<10){
                        continue;
                    }
                    else if(i>24 && i<34){
                        continue;
                    } else if (i>48 && i<58) {
                        continue;
                    } else {
                        world.setBlock(random_block_pos[i], KijinmodBlocks.MAGIC_CIRCLE_BLOCK.defaultBlockState(), 3);
                    }
                }
        }

    }
    public static void delete_random_block(World world,Vector3d player){
        for(int i=0;i<75;i++) {
            world.setBlock(random_block_pos[i], Blocks.AIR.defaultBlockState(), 3);
        }
    }

    public static void create_star(World world,Vector3d player){
        double radius =12.0;
        int points =5;

        BlockPos[] starPoints = new BlockPos[points];
        for(int i=0;i<points;i++){
            double angle = (2*Math.PI*i)/points;
            double x = Math.cos(angle) * radius;
            double z = Math.sin(angle) * radius;
            starPoints[i] = new BlockPos(player.x + x, player.y+height, player.z + z);
            originalBlockState=world.getBlockState(starPoints[i]);
            if(originalBlockState.getBlock() == Blocks.AIR) {
                world.setBlock(starPoints[i],KijinmodBlocks.MAGIC_CIRCLE_BLOCK.defaultBlockState(), 3);  // 3は通知フラグ
            }
        }
        for (int i = 0; i < points - 2; i++) {
            BlockPos startPoint = starPoints[i];
            BlockPos endPoint = starPoints[i + 2];
            CreateMagicCircle.drawLine(world, startPoint, endPoint);
        }
        CreateMagicCircle.drawLine(world, starPoints[3], starPoints[0]);
        CreateMagicCircle.drawLine(world, starPoints[4], starPoints[1]);//ここまで星形
    }

    public static void points_circle(World world,Vector3d player,double pos_radius ,double circle_radius[],int points, double height,int type) {
        Vector3d[] trianglePoints = new Vector3d[points];
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI * i) / points;
            double x = Math.cos(angle) * pos_radius;
            double z = Math.sin(angle) * pos_radius;
            trianglePoints[i] = new Vector3d(player.x + x, player.y, player.z + z);
        }
        if(type==1) {
            for (int i = 0; i < points; i++) {
                if(circle_radius[i] !=0) {
                    CreateMagicCircle.create_circle(world, trianglePoints[i], circle_radius[i], height);
                }
            }
        }
        else{
            for (int i = 0; i < points; i++) {
                if(circle_radius[i] !=0) {
                    CreateMagicCircle.remove_circle(world, trianglePoints[i], circle_radius[i], height);
                }
            }
        }
    }

    public static void drawLine(World world, BlockPos start, BlockPos end) {
        int steps = 25;  // 線を分割するステップ数

        double xIncrement = (end.getX() - start.getX()) / (double) steps;
        double yIncrement = (end.getY() - start.getY()) / (double) steps;
        double zIncrement = (end.getZ() - start.getZ()) / (double) steps;

        for (int i = 0; i <= steps; i++) {
            double x = start.getX() + xIncrement * i;
            double y = start.getY() + yIncrement * i;
            double z = start.getZ() + zIncrement * i;

            BlockPos pos = new BlockPos(x, y, z);

            // ここでブロックを生成します
            originalBlockState=world.getBlockState(pos);
            if(originalBlockState.getBlock() == Blocks.AIR) {
                world.setBlock(pos,KijinmodBlocks.MAGIC_CIRCLE_BLOCK.defaultBlockState(), 3);  // 3は通知フラグ
            }
        }
    }

    public static void deleteLine(World world, BlockPos start, BlockPos end) {
        int steps = 25;  // 線を分割するステップ数

        double xIncrement = (end.getX() - start.getX()) / (double) steps;
        double yIncrement = (end.getY() - start.getY()) / (double) steps;
        double zIncrement = (end.getZ() - start.getZ()) / (double) steps;

        for (int i = 0; i <= steps; i++) {
            double x = start.getX() + xIncrement * i;
            double y = start.getY() + yIncrement * i;
            double z = start.getZ() + zIncrement * i;

            BlockPos pos = new BlockPos(x, y, z);

            // ここでブロックを生成します
            originalBlockState=world.getBlockState(pos);
            if(originalBlockState.getBlock() == KijinmodBlocks.MAGIC_CIRCLE_BLOCK) {
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);  // 3は通知フラグ
            }
        }
    }
}
