package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityGroup;

import java.util.Set;

public class AntiDamageEnchantment extends AbstractLinkedCurseEnchantment {

    private final int typeIndex;

    protected AntiDamageEnchantment(Enchantment antiEnchant, Rarity rarity, int typeIndex) {
        super(antiEnchant, rarity, RamChantments.MAIN_HAND);
        this.typeIndex = typeIndex;
    }

    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if (this.typeIndex == 0) {
            return -(1.0F + (float)Math.max(0, level - 1) * 0.5F);
        } else if (this.typeIndex == 1 && group == EntityGroup.UNDEAD) {
            return -(float)level * 2.5F;
        } else {
            return this.typeIndex == 2 && group == EntityGroup.ARTHROPOD ? -(float)level * 2.5F : 0.0F;
        }
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return switch(typeIndex) {
            case 0 -> {
                if (other == RamChantments.ARTHROPODS_FAVOR || other == RamChantments.WRAITHWARD) yield false;
                yield Enchantments.SHARPNESS.canAccept(other);
            }
            case 1 -> {
                if (other == RamChantments.ARTHROPODS_FAVOR || other == RamChantments.DULLNESS) yield false;
                yield Enchantments.SMITE.canAccept(other);
            }
            case 2 -> {
                if (other == RamChantments.WRAITHWARD || other == RamChantments.DULLNESS) yield false;
                yield Enchantments.BANE_OF_ARTHROPODS.canAccept(other);
            }
            default -> throw new IllegalStateException("Unexpected type index: " + typeIndex);
        };
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(Enchantments.SHARPNESS, Enchantments.BANE_OF_ARTHROPODS, Enchantments.SMITE);
    }
}
