package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class DeflectionEnchantment extends AbstractLinkedCurseEnchantment {
    protected DeflectionEnchantment() {
        super(Enchantments.PIERCING, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(RamChantments.INACCURACY);
    }
}
