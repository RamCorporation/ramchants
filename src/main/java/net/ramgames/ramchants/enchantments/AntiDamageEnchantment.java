package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class AntiDamageEnchantment extends AbstractLinkedCurseEnchantment {

    private final int typeIndex;

    protected AntiDamageEnchantment(Enchantment antiEnchant, Rarity rarity, int typeIndex) {
        super(antiEnchant, rarity, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
        if(other == Enchantments.SHARPNESS || other == Enchantments.BANE_OF_ARTHROPODS || other == Enchantments.SMITE) return false;
        return switch(typeIndex) {
            case 0 -> {
                if (other == ModEnchantments.ARTHROPODS_FAVOR || other == ModEnchantments.WRAITHWARD) yield false;
                yield Enchantments.SHARPNESS.canAccept(other);
            }
            case 1 -> {
                if (other == ModEnchantments.ARTHROPODS_FAVOR || other == ModEnchantments.DULLNESS) yield false;
                yield Enchantments.SMITE.canAccept(other);
            }
            case 2 -> {
                if (other == ModEnchantments.WRAITHWARD || other == ModEnchantments.DULLNESS) yield false;
                yield Enchantments.BANE_OF_ARTHROPODS.canAccept(other);
            }
            default -> throw new IllegalStateException("Unexpected type index: " + typeIndex);
        };
    }
}
