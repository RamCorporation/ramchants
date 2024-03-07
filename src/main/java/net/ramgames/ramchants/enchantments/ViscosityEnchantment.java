package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;

import static net.ramgames.ramchants.enchantments.ModEnchantments.ALL_ARMOR;

public class ViscosityEnchantment extends Enchantment {
    protected ViscosityEnchantment() {
        super(Enchantments.AQUA_AFFINITY.getRarity(), Enchantments.AQUA_AFFINITY.target, ALL_ARMOR);
    }

    @Override
    public int getMaxLevel() {
        return Enchantments.AQUA_AFFINITY.getMaxLevel();
    }

    @Override
    public int getMinPower(int level) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    protected boolean canAccept(Enchantment other) {
        if(other == Enchantments.AQUA_AFFINITY) return false;
        return super.canAccept(other);
    }
}
