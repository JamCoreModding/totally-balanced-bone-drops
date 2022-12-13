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

package io.github.jamalam360.tb.bone.drops;

import io.github.jamalam360.jamlib.config.JamLibConfig;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class TotallyBalancedBoneDropsInit implements ModInitializer {
    public static final String MOD_NAME = "Totally Balanced Bone Drops";
    public static final TagKey<EntityType<?>> BLACKLIST = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier("tb-bone-drops", "blacklist"));
    private static final Logger LOGGER = getLogger("Initializer");
    private static final Random RANDOM = new Random();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + MOD_NAME + "...");
        JamLibConfig.init("tb-bone-drops", Config.class);
    }

    public static Logger getLogger(String name) {
        return LogManager.getLogger(MOD_NAME + "/" + name);
    }

    public enum Chance {
        VERY_RARE(0.05D, 1),
        RARE(0.1D, 2),
        COMMON(0.2D, 2),
        VERY_COMMON(0.35D, 2),
        ABUNDANT(0.55D, 3);

        private final double chance;
        private final int rolls;

        Chance(double chance, int rolls) {
            this.chance = chance;
            this.rolls = rolls;
        }

        public ItemStack getDrop(Entity attacker) {
            int count = 0;

            double mult = 1;

            if (attacker instanceof LivingEntity living) {
                mult = 1 + EnchantmentHelper.getLooting(living) * 0.3;
            }

            for (int i = 0; i < Math.ceil(this.rolls * mult); i++) {
                if (RANDOM.nextDouble() < (this.chance * mult)) {
                    count++;
                }
            }

            return new ItemStack(Items.BONE, count);
        }
    }
}
