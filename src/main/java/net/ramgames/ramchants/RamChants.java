package net.ramgames.ramchants;

import net.fabricmc.api.ModInitializer;
import net.ramgames.ramchants.api.ResourceLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RamChants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Ramchants is preparing something magical!");
        RamChantments.onInitialize();
        ResourceLoaders.initialize();
    }
}
