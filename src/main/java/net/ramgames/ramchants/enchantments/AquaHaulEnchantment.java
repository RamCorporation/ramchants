package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class AquaHaulEnchantment extends AbstractLinkedCurseEnchantment {
    protected AquaHaulEnchantment() {
        super(Enchantments.DEPTH_STRIDER, RamChantments.FEET);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(Enchantments.FROST_WALKER);
    }
}
