package net.ramgames.ramchants.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ramgames.ramchants.RamChants;

import java.util.Optional;

public record LinkedEnchantmentCurse(Identifier curse) {

    public static final Codec<LinkedEnchantmentCurse> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Identifier.CODEC.fieldOf("linkedCurse").forGetter(item -> item.curse)
    ).apply(builder, LinkedEnchantmentCurse::new));

    public Optional<Enchantment> getEnchantment(DynamicRegistryManager dynamicRegistryManager) {
        Identifier enchantmentId = dynamicRegistryManager.get(RamChants.LINKED_CURSE_KEY).getId(this);
        Enchantment enchantment = dynamicRegistryManager.get(RegistryKeys.ENCHANTMENT).get(enchantmentId);
        if(enchantment == null) return Optional.empty();
        else return Optional.of(enchantment);
    }

    public Optional<Enchantment> getLinkedCurse(DynamicRegistryManager dynamicRegistryManager) {
        Enchantment enchantment = dynamicRegistryManager.get(RegistryKeys.ENCHANTMENT).get(curse);
        if(enchantment == null) return Optional.empty();
        else return Optional.of(enchantment);
    }
}
