package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class StagnationEnchantment extends AbstractLinkedCurseEnchantment {
    protected StagnationEnchantment() {
        super(Enchantments.RIPTIDE, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(RamChantments.ELECTRIC_ATTRACTION, RamChantments.BETRAYAL);
    }
}
