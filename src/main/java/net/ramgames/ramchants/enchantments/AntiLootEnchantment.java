package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;

public class AntiLootEnchantment extends AbstractLinkedCurseEnchantment {
    protected AntiLootEnchantment(Enchantment antiEnchant, EnchantmentTarget target) {
        super(antiEnchant, target, RamChantments.MAIN_HAND);
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if(getLinkedEnchantment() == Enchantments.FORTUNE) if(other == Enchantments.SILK_TOUCH || other == RamChantments.VOIDING) return false;
        return super.canAccept(other);
    }
}
