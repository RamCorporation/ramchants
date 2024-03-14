package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import static net.ramgames.ramchants.enchantments.ModEnchantments.ALL_ARMOR;

public class ViscosityEnchantment extends AbstractLinkedCurseEnchantment {
    protected ViscosityEnchantment() {
        super(Enchantments.AQUA_AFFINITY, ALL_ARMOR);
    }
}
