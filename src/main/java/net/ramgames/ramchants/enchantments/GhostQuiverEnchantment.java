package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class GhostQuiverEnchantment extends AbstractLinkedCurseEnchantment {
    protected GhostQuiverEnchantment() {
        super(Enchantments.INFINITY, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(Enchantments.MENDING);
    }
}
