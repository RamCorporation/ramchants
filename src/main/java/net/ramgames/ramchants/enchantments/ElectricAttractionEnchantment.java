package net.ramgames.ramchants.enchantments;

import net.minecraft.enchantment.ChannelingEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;

public class ElectricAttractionEnchantment extends ChannelingEnchantment {
    public ElectricAttractionEnchantment(Rarity weight) {
        super(weight, EquipmentSlot.MAINHAND);
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
        if(other == Enchantments.CHANNELING) return false;
        return super.canAccept(other);
    }
}
