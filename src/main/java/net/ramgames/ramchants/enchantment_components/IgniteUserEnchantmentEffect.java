package net.ramgames.ramchants.enchantment_components;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.enchantment.effect.entity.IgniteEnchantmentEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.ramgames.ramchants.RamChants;

public record IgniteUserEnchantmentEffect(EnchantmentLevelBasedValue duration) implements EnchantmentEntityEffect {
    public static final MapCodec<IgniteUserEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(EnchantmentLevelBasedValue.CODEC.fieldOf("duration").forGetter((igniteEnchantmentEffect) -> igniteEnchantmentEffect.duration)).apply(instance, IgniteUserEnchantmentEffect::new));

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        RamChants.LOGGER.info(String.valueOf(context));
        if(context.owner() == null) return;
        context.owner().setOnFireFor(duration.getValue(level));
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return IgniteEnchantmentEffect.CODEC;
    }
}
