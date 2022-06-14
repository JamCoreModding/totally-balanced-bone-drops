package io.github.jamalam360.tb.bone.drops;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import io.github.jamalam360.jamlib.config.JamLibConfig;

/**
 * @author Jamalam
 */
public class ModMenuScreen implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> JamLibConfig.getScreen(parent, "tb-bone-drops");
    }
}
