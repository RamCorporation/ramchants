package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;

public class AntiLootEnchantment extends AbstractLinkedCurseEnchantment {
    protected AntiLootEnchantment(Enchantment antiEnchant, EnchantmentTarget target) {
        super(antiEnchant, target, RamChantments.MAIN_HAND);
    }
}
