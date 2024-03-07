package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class VoidingEnchantment extends Enchantment {
    protected VoidingEnchantment() {
        super(Enchantments.SILK_TOUCH.getRarity(), Enchantments.SILK_TOUCH.target, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
    protected boolean canAccept(Enchantment other) {
        if(other == Enchantments.SILK_TOUCH) return false;
        return super.canAccept(other);
    }
}
