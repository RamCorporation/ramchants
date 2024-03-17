package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class VoidingEnchantment extends AbstractLinkedCurseEnchantment {
    protected VoidingEnchantment() {
        super(Enchantments.SILK_TOUCH, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(RamChantments.SCARCITY);
    }
}
