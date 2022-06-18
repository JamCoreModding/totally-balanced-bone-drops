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

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.github.jamalam360.jamlib.config.JamLibConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TotallyBalancedBoneDropsInit implements ModInitializer {
    public static final String MOD_NAME = "Totally Balanced Bone Drops";
    private static final Logger LOGGER = getLogger("Initializer");
    private static final Random RANDOM = new Random();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing " + MOD_NAME + "...");
        JamLibConfig.init("tb-bone-drops", Config.class);
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new DataLoader());
    }

    public static Logger getLogger(String name) {
        return LogManager.getLogger(MOD_NAME + "/" + name);
    }

    public enum Chance {
        VERY_RARE(0.05D, 1),
        RARE(0.1D, 2),
        COMMON(0.2D, 2),
        VERY_COMMON(0.4D, 2),
        ABUNDANT(0.7D, 3);

        private final double chance;
        private final int rolls;

        Chance(double chance, int rolls) {
            this.chance = chance;
            this.rolls = rolls;
        }

        public ItemStack getDrop() {
            int count = 0;

            for (int i = 0; i < this.rolls; i++) {
                if (RANDOM.nextDouble() < this.chance) {
                    count++;
                }
            }

            return new ItemStack(Items.BONE, count);
        }
    }

    public static class DataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
        public static List<Identifier> BLACKLIST = new ArrayList<>();

        public DataLoader() {
            super(new Gson(), "tb_bone_drops");
        }

        @Override
        public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
            BLACKLIST.clear();

            prepared.forEach((id, element) -> {
                for (var entry : element.getAsJsonObject().get("blacklist").getAsJsonArray()) {
                    BLACKLIST.add(new Identifier(entry.getAsString()));
                }
            });
        }

        @Override
        public Identifier getFabricId() {
            return new Identifier("tb-bone-drops", "tb_bone_drops_data");
        }
    }
}