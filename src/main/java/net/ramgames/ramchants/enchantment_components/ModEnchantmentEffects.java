package net.ramgames.ramchants.enchantment_components;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.ramgames.ramchants.RamChants;

public interface ModEnchantmentEffects {

    static void onInitialize() {
        Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, RamChants.id("ignite_user"), IgniteUserEnchantmentEffect.CODEC);

    }
}
