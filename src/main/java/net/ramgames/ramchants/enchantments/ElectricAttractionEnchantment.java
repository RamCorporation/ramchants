package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import java.util.Set;

public class ElectricAttractionEnchantment extends AbstractLinkedCurseEnchantment {
    public ElectricAttractionEnchantment() {
        super(Enchantments.CHANNELING, RamChantments.MAIN_HAND);
    }

    @Override
    public Set<Enchantment> cannotCombine() {
        return Set.of(RamChantments.STAGNATION);
    }
}
