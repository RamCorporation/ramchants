package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class InaccuracyEnchantment extends AbstractLinkedCurseEnchantment{
    protected InaccuracyEnchantment() {
        super(Enchantments.MULTISHOT, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(RamChantments.DEFLECTION);
    }
}
