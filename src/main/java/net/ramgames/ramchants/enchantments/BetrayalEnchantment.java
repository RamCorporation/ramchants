package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class BetrayalEnchantment extends AbstractLinkedCurseEnchantment {
    protected BetrayalEnchantment() {
        super(Enchantments.LOYALTY, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(RamChantments.STAGNATION);
    }
}
