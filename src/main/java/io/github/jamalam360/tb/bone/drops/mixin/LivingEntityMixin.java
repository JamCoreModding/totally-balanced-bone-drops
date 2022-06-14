/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2022 Jamalam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
