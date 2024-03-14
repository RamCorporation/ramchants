package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class AquaHaulEnchantment extends AbstractLinkedCurseEnchantment {
    protected AquaHaulEnchantment() {
        super(Enchantments.DEPTH_STRIDER, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if(other == Enchantments.FROST_WALKER) return false;
        return super.canAccept(other);
    }
}
