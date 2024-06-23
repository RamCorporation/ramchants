package net.ramgames.ramchants;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.api.LinkedEnchantmentCurse;
import net.ramgames.ramchants.api.ResourceLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RamChants implements ModInitializer {

    public static final String MOD_ID = "ramchants";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final RegistryKey<Registry<LinkedEnchantmentCurse>> LINKED_CURSE_KEY = RegistryKey.ofRegistry(Identifier.of(MOD_ID, "linked_curse"));

    @Override
    public void onInitialize() {
        LOGGER.info("Ramchants is preparing something magical!");
        RamChantments.onInitialize();
        ModItemComponents.onInitialize();
        ResourceLoaders.onInitialize();

        DynamicRegistries.register(LINKED_CURSE_KEY, LinkedEnchantmentCurse.CODEC);
    }
}
