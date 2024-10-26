package com.SakuraKijin.kijinmod.particle;

import com.SakuraKijin.kijinmod.magiccircle.CreateMagicCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class CreateParticle {
    public static void particleline(Vector3d start, Vector3d end,int steps,IParticleData particle) {
        // 線を分割するステップ数
        double xIncrement = (end.x - start.x) / (double) steps;
        double yIncrement = (end.y - start.y) / (double) steps;
        double zIncrement = (end.z - start.z) / (double) steps;

        for (int i = 0; i <= steps; i++) {
            double x = start.x() + xIncrement * i;
            double y = start.y() + yIncrement * i;
            double z = start.z() + zIncrement * i;
            Minecraft.getInstance().particleEngine.createParticle(particle, x,y,z, 0,0,0);
        }
    }
    public static void circle_partice(Vector3d player, double radius,int points,IParticleData particle){
        for(double i =0; i< points; i += 1){
            double xOffset = Math.cos(2*Math.PI/points*i)*radius;
            double zOffset = Math.sin(2*Math.PI/points*i)*radius;
            Vector3d setpos = new Vector3d(player.x+xOffset,player.y,player.z+zOffset);
            Minecraft.getInstance().particleEngine.createParticle(particle, setpos.x,setpos.y,setpos.z, 0,0,0);
        }
    }



    public static void points_particle(Vector3d player,double radius,int points,IParticleData particle,double offset,int steps){
        Vector3d[] starPoints = new Vector3d[points];
        for(int i=0;i<points;i++){
            double angle = (2*Math.PI*i)/points;
            double x = Math.cos(angle+offset) * radius;
            double z = Math.sin(angle+offset) * radius;
            starPoints[i] = new Vector3d(player.x + x, player.y, player.z + z);
            Minecraft.getInstance().particleEngine.createParticle(particle,starPoints[i].x,starPoints[i].y,starPoints[i].z, 0,0,0);
        }
        for(int i=0;i<points-1;i++){
            for (int j = i+1; j < points; j++) {
                Vector3d startPoint = starPoints[i];
                Vector3d endPoint = starPoints[j];
                particleline(startPoint, endPoint,steps,particle);
            }
        }
    }

    public static void points_particle_circle(Vector3d player, double pos_radius , double circle_radius[], int circle_number,IParticleData particle,int points) {
        Vector3d[] trianglePoints = new Vector3d[circle_number];
        for (int i = 0; i < circle_number; i++) {
            double angle = (2 * Math.PI * i) / circle_number;
            double x = Math.cos(angle) * pos_radius;
            double z = Math.sin(angle) * pos_radius;
            trianglePoints[i] = new Vector3d(player.x + x, player.y, player.z + z);
        }
        for (int i = 0; i < circle_number; i++) {
            circle_partice(trianglePoints[i],circle_radius[i],points,particle);
        }
    }


    public static void make_friends_range(Vector3d player){
        /*for(int i=0;i<100;i++) {
            IParticleData paticle = ParticleTypes.DRAGON_BREATH;
            Minecraft.getInstance().particleEngine.createParticle(paticle, player.x, player.y, player.z, Math.random()-0.5, Math.random()-0.5, Math.random()-0.5);
        }*/
        IParticleData particle = ParticleTypes.DRAGON_BREATH;
        CreateParticle.circle_partice(player,8,1000,particle);
        CreateParticle.circle_partice(player,10,2000,particle);
        CreateParticle.points_particle(player,8,3,particle,0,200);
        CreateParticle.points_particle(player,8,3,particle,Math.PI,200);
    }
    public static void particle_ex(Vector3d player){
        /*for(int i=0;i<100;i++) {
            IParticleData paticle = ParticleTypes.DRAGON_BREATH;
            Minecraft.getInstance().particleEngine.createParticle(paticle, player.x, player.y, player.z, Math.random()-0.5, Math.random()-0.5, Math.random()-0.5);
        }*/
        IParticleData particle = ParticleTypes.DRAGON_BREATH;
        CreateParticle.circle_partice(player,2,50,particle);
        CreateParticle.circle_partice(player,3,100,particle);
        CreateParticle.points_particle(player,2,3,particle,0,20);
        CreateParticle.points_particle(player,2,3,particle,Math.PI,20);
    }
    public static void particle_tele(Vector3d player){
        /*for(int i=0;i<100;i++) {
            IParticleData paticle = ParticleTypes.DRAGON_BREATH;
            Minecraft.getInstance().particleEngine.createParticle(paticle, player.x, player.y, player.z, Math.random()-0.5, Math.random()-0.5, Math.random()-0.5);
        }*/
        IParticleData particle = ParticleTypes.DRAGON_BREATH;
        CreateParticle.circle_partice(player,2,400,particle);
        CreateParticle.circle_partice(player,3,800,particle);
        CreateParticle.points_particle(player,2,3,particle,0,100);
        CreateParticle.points_particle(player,2,3,particle,Math.PI,100);
    }

}
