package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class AquaHaulEnchantment extends Enchantment {
    protected AquaHaulEnchantment() {
        super(Enchantments.DEPTH_STRIDER.getRarity(), Enchantments.DEPTH_STRIDER.target, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxLevel() {
        return Enchantments.DEPTH_STRIDER.getMaxLevel();
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if(other == Enchantments.DEPTH_STRIDER) return false;
        if(other == Enchantments.FROST_WALKER) return false;
        return super.canAccept(other);
    }
}
