package com.SakuraKijin.kijinmod.entity;

import com.SakuraKijin.kijinmod.item.erasememory.OneTimeCreativeClass;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Iterator;
import java.util.List;

public class ArrowEntitys extends AbstractArrowEntity {
    private double baseDamage =0.1D;//ふつうは2.0D
    private SoundEvent soundEvent= SoundEvents.ENDER_DRAGON_SHOOT;
    private ItemStack shootstack = new ItemStack(Items.AIR);
    private int life;
    private List<Entity> piercedAndKilledEntities;
    private int a=0;
    private Entity Own;
    public ArrowEntitys(EntityType<? extends ArrowEntitys> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
    }

    public ArrowEntitys(World world, LivingEntity entity,ItemStack stack){
        super(PanEntityTypes.CUSTOM_ARROW.get(),entity,world);
        shootstack = stack;
    }



    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        float f = (float)this.getDeltaMovement().length();
        int i = MathHelper.ceil(MathHelper.clamp((double)f * this.baseDamage, 0.0, 2.147483647E9));

        if   (this.isCritArrow()) {
            long j = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(j + (long)i, 2147483647L);
        }

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        boolean flag = entity.getType() == EntityType.ENDERMAN;
        int k = entity.getRemainingFireTicks();
        if (this.isOnFire() && !flag) {
            entity.setSecondsOnFire(5);
        }

        if (entity.hurt(damagesource, (float)i)) {
            if (flag) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity1).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.piercedAndKilledEntities != null) {
                    this.piercedAndKilledEntities.add(livingentity);
                }
            }

            this.playSound(this.soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setRemainingFireTicks(k);
            //this.setDeltaMovement(this.getDeltaMovement().scale(-0.1));
            this.yRot += 180.0F;
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
                if (this.pickup == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.remove();
            }
        }
        a=1;
        Own = entity1;
    }


    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
        BlockState state = this.level.getBlockState(p_230299_1_.getBlockPos());
        state.onProjectileHit(this.level, state, p_230299_1_, this);
        Vector3d vector3d = p_230299_1_.getLocation().subtract(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale(0.05000000074505806);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.playSound(soundEvent, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.shakeTime = 2;
        this.setCritArrow(false);
        this.setPierceLevel((byte)0);
    }

    public void setBaseDamage(double p_70239_1_) {
        this.baseDamage = p_70239_1_;
    }

    public double getBaseDamage() {
        return this.baseDamage;
    }

    public void tickDespawn() {
        ++this.life;
        if (this.life >= 120 ) {
            this.remove();
        }
    }

    public void tick() {
        boolean flag = this.isNoPhysics();
        Vector3d vector3d = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            this.yRot = (float)(MathHelper.atan2(vector3d.x, vector3d.z) * 57.2957763671875);
            this.xRot = (float)(MathHelper.atan2(vector3d.y, (double)f) * 57.2957763671875);
            this.yRotO = this.yRot;
            this.xRotO = this.xRot;
        }

        BlockPos blockpos = this.blockPosition();
        BlockState blockstate = this.level.getBlockState(blockpos);
        Vector3d vector3d3;
        if (!blockstate.isAir(this.level, blockpos) && !flag) {
            VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
            if (!voxelshape.isEmpty()) {
                vector3d3 = this.position();
                Iterator var7 = voxelshape.toAabbs().iterator();

                while(var7.hasNext()) {
                    AxisAlignedBB axisalignedbb = (AxisAlignedBB)var7.next();
                    if (axisalignedbb.move(blockpos).contains(vector3d3)) {
                        this.inGround = true;
                        break;
                    }
                }
            }
        }

        if (this.shakeTime > 0) {
            --this.shakeTime;
        }

        if (this.isInWaterOrRain()) {
            this.clearFire();
        }

        if (this.inGround && !flag) {
            this.remove();
            ++this.inGroundTime;
        } else {
            this.inGroundTime = 0;
            Vector3d vector3d2 = this.position();
            vector3d3 = vector3d2.add(vector3d);
            RayTraceResult raytraceresult = this.level.clip(new RayTraceContext(vector3d2, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
            if (((RayTraceResult)raytraceresult).getType() != RayTraceResult.Type.MISS) {
                vector3d3 = ((RayTraceResult)raytraceresult).getLocation();
            }
            tickDespawn();
            while(!this.removed) {
                EntityRayTraceResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
                if (entityraytraceresult != null) {
                    raytraceresult = entityraytraceresult;
                }

                if (raytraceresult != null && ((RayTraceResult)raytraceresult).getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = ((EntityRayTraceResult)raytraceresult).getEntity();
                    Entity entity1 = this.getOwner();
                    if (entity instanceof PlayerEntity && entity1 instanceof PlayerEntity && !((PlayerEntity)entity1).canHarmPlayer((PlayerEntity)entity)) {
                        raytraceresult = null;
                        entityraytraceresult = null;
                    }
                }

                if (raytraceresult != null && ((RayTraceResult)raytraceresult).getType() != RayTraceResult.Type.MISS && !flag && !ForgeEventFactory.onProjectileImpact(this, (RayTraceResult)raytraceresult)) {
                    this.onHit((RayTraceResult)raytraceresult);
                    this.hasImpulse = true;
                }

                if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
                    break;
                }

                raytraceresult = null;
            }

            vector3d = this.getDeltaMovement();
            double d3 = vector3d.x;
            double d4 = vector3d.y;
            double d0 = vector3d.z;

            double d5 = this.getX() + d3;
            double d1 = this.getY() + d4;
            double d2 = this.getZ() + d0;
            float f1 = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
            if (flag) {
                this.yRot = (float)(MathHelper.atan2(-d3, -d0) * 57.2957763671875);
            } else {
                this.yRot = (float)(MathHelper.atan2(d3, d0) * 57.2957763671875);
            }

            this.xRot = (float)(MathHelper.atan2(d4, (double)f1) * 57.2957763671875);
            this.xRot = lerpRotation(this.xRotO, this.xRot);
            this.yRot = lerpRotation(this.yRotO, this.yRot);
            float f2 = 0.99F;
            float f3 = 0.05F;
            if (this.isInWater()) {
                f2 = this.getWaterInertia();
            }

            this.setDeltaMovement(vector3d.scale((double)f2));

            this.setPos(d5, d1, d2);
            this.checkInsideBlocks();
        }
        if(a==1){
            if(Own instanceof LivingEntity) {
                PlayerEntity player = (PlayerEntity) Own;
                OneTimeCreativeClass.cretive(player);
            }
            a=0;
        }
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public ItemStack getPickupItem() {
        return shootstack;
    }
}
