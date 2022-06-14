package io.github.jamalam360.tb.bone.drops.mixin;

import io.github.jamalam360.tb.bone.drops.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Jamalam
 */

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
            method = "dropLoot",
            at = @At("TAIL")
    )
    public void tbbonedrops$injectBoneDrops(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        LivingEntity castedThis = (LivingEntity) (Object) this;

        //TODO(Jamalam360): PLEASE MAKE THIS A TAG

        if (
                castedThis instanceof AbstractSkeletonEntity ||
                        castedThis instanceof SlimeEntity ||
                        castedThis instanceof EnderDragonEntity ||
                        castedThis instanceof WitherEntity ||
                        castedThis instanceof BlazeEntity ||
                        castedThis instanceof CreeperEntity ||
                        castedThis instanceof SpiderEntity ||
                        castedThis instanceof GuardianEntity ||
                        castedThis instanceof EndermiteEntity ||
                        castedThis instanceof SilverfishEntity ||
                        castedThis instanceof GhastEntity ||
                        castedThis instanceof AllayEntity ||
                        castedThis instanceof BeeEntity ||
                        castedThis instanceof GolemEntity ||
                        castedThis instanceof FishEntity

        ) {
            return;
        }

        this.dropStack(Config.chance.getDrop(this.world.random));
    }
}
