package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;

import java.util.Set;

import static net.minecraft.enchantment.Enchantments.*;
import static net.ramgames.ramchants.enchantments.RamChantments.*;

public class AntiProtectionEnchantment extends AbstractLinkedCurseEnchantment {

    public final ProtectionEnchantment.Type protectionType;
    public AntiProtectionEnchantment(Enchantment antiEnchant, Rarity weight, ProtectionEnchantment.Type protectionType, EquipmentSlot... slotTypes) {
        super(antiEnchant, weight, slotTypes);
        this.protectionType = protectionType;
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        return -super.getProtectionAmount(level, source);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if(getLinkedEnchantment() != FEATHER_FALLING) if(other == FIRE_PROTECTION || other == PROTECTION || other == PROJECTILE_PROTECTION || other == BLAST_PROTECTION) return false;
        return switch (protectionType) {
            case ALL -> {
                if(Set.of(BURNING, EXPLOSIVE_FRAGILITY, ARROWS_BANE).contains(other)) yield false;
                yield PROTECTION.canAccept(other);
            }
            case FIRE -> {
                if(Set.of(VULNERABILITY, EXPLOSIVE_FRAGILITY, ARROWS_BANE).contains(other)) yield false;
                yield FIRE_PROTECTION.canAccept(other);
            }
            case FALL -> {
                if(other == FEATHER_FALLING) yield false;
                yield FEATHER_FALLING.canAccept(other);
            }
            case EXPLOSION -> {
                if(Set.of(BURNING, VULNERABILITY, ARROWS_BANE).contains(other)) yield false;
                yield BLAST_PROTECTION.canAccept(other);
            }
            case PROJECTILE -> {
                if(Set.of(FIRE_PROTECTION, BLAST_PROTECTION, PROTECTION).contains(other)) yield false;
                yield PROJECTILE_PROTECTION.canAccept(other);
            }
        };
    }
}
