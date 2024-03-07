package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;

public class AntiProtectionEnchantment extends ProtectionEnchantment {
    public AntiProtectionEnchantment(Rarity weight, Type protectionType, EquipmentSlot... slotTypes) {
        super(weight, protectionType, slotTypes);
    }

    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public int getProtectionAmount(int level, DamageSource source) {
        return -super.getProtectionAmount(level, source);
    }
}
